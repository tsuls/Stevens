-module(server).

-export([start_server/0]).

-include_lib("./defs.hrl").

-spec start_server() -> _.
-spec loop(_State) -> _.
-spec do_join(_ChatName, _ClientPID, _Ref, _State) -> _.
-spec do_leave(_ChatName, _ClientPID, _Ref, _State) -> _.
-spec do_new_nick(_State, _Ref, _ClientPID, _NewNick) -> _.
-spec do_client_quit(_State, _Ref, _ClientPID) -> _NewState.

start_server() ->
    catch(unregister(server)),
    register(server, self()),
    case whereis(testsuite) of
	undefined -> ok;
	TestSuitePID -> TestSuitePID!{server_up, self()}
    end,
    loop(
      #serv_st{
	 nicks = maps:new(), %% nickname map. client_pid => "nickname"
	 registrations = maps:new(), %% registration map. "chat_name" => [client_pids]
	 chatrooms = maps:new() %% chatroom map. "chat_name" => chat_pid
	}
     ).

loop(State) ->
    receive 
	%% initial connection
	{ClientPID, connect, ClientNick} ->
	    NewState =
		#serv_st{
		   nicks = maps:put(ClientPID, ClientNick, State#serv_st.nicks),
		   registrations = State#serv_st.registrations,
		   chatrooms = State#serv_st.chatrooms
		  },
	    loop(NewState);
	%% client requests to join a chat
	{ClientPID, Ref, join, ChatName} ->
	    NewState = do_join(ChatName, ClientPID, Ref, State),
	    loop(NewState);
	%% client requests to join a chat
	{ClientPID, Ref, leave, ChatName} ->
	    NewState = do_leave(ChatName, ClientPID, Ref, State),
	    loop(NewState);
	%% client requests to register a new nickname
	{ClientPID, Ref, nick, NewNick} ->
	    NewState = do_new_nick(State, Ref, ClientPID, NewNick),
	    loop(NewState);
	%% client requests to quit
	{ClientPID, Ref, quit} ->
	    NewState = do_client_quit(State, Ref, ClientPID),
	    loop(NewState);
	{TEST_PID, get_state} ->
	    TEST_PID!{get_state, State},
	    loop(State)
    end.


%% executes join protocol from server perspective
do_join(ChatName, ClientPID, Ref, State) ->
	%search and see if the chatroom that the client requests to join exists in the map
     case maps:find(ChatName, State#serv_st.chatrooms) of
		   		{ok, ChatPID} ->  %chatroom exists so lets join usinig the found pid from find and the given state
		    			{_,ClientName} = maps:find(ClientPID, State#serv_st.nicks),

					    %with the name, we can tell the chatroom that this new client is joining
					    ChatPID!{self(), Ref, register, ClientPID, ClientName},

					    %grab the registered clients from the chat map and prepare to update
					    {_,Resgistered_Clients} = maps:find(ChatName,State#serv_st.registrations),

					    %append the new client's PID to the list of regsitered cients to add them. Erlang requires a new list to be created.
					    Resgistered_Clients_Appeneded = Resgistered_Clients++[ClientPID],
					    io:format("The Registered clients to the chatroom now are:  ~p~n",[Resgistered_Clients_Appeneded]),

					    %put the new registered list in the map
					    ChatMap = maps:put(ChatName, Resgistered_Clients_Appeneded, State#serv_st.registrations),
					    io:format("ChatMap is now: ~p~n", [ChatMap]),

					    Updated_Chat_State = #serv_st{nicks = State#serv_st.nicks, registrations = ChatMap, chatrooms = State#serv_st.chatrooms},
					    Updated_Chat_State;
		   				   
		   				   
		   		%or chatroom doesny exist so we must spawn a new one
		    	error -> ChatSpawnPID = spawn(chatroom, start_chatroom, [ChatName]),
		    			io:format("The new chatroom has PID: ~p~n", [ChatSpawnPID]),

		    			Updated_ChatMap = maps:put(ChatName, ChatSpawnPID, State#serv_st.chatrooms),
		    			io:format("The new chatmap, after adding the new chatroom ~p is: ~p~n", [ChatSpawnPID, Updated_ChatMap]),

		    			%registered clients will be an empty list since a new chatroom wont have any clients in it.....yet
		    			Clients = maps:put(ChatName, [], State#serv_st.registrations),
		    			io:format("debug~n"),
		    			%update the chat state to have the new chatmap with the new chatroom
		    			Chat_State = #serv_st{nicks = State#serv_st.nicks, registrations = Clients, chatrooms = Updated_ChatMap},
		    			io:format("Chat state: ~p~n", [Chat_State]),
		    			io:format("debug2~n"),

		    			{_,ClientName} = maps:find(ClientPID, Chat_State#serv_st.nicks),

					    %with the name, we can tell the chatroom that this new client is joining
					    ChatSpawnPID!{self(), Ref, register, ClientPID, ClientName},

					    %grab the registered clients from the chat map and prepare to update
					    {_,Resgistered_Clients} = maps:find(ChatName,Chat_State#serv_st.registrations),

					    %append the new client's PID to the list of regsitered cients to add them. Erlang requires a new list to be created.
					    Resgistered_Clients_Appeneded = Resgistered_Clients++[ClientPID],
					    io:format("The Registered clients to the chatroom now are:  ~p~n",[Resgistered_Clients_Appeneded]),

					    %put the new registered list in the map
					    ChatMap = maps:put(ChatName, Resgistered_Clients_Appeneded, Chat_State#serv_st.registrations),
					    io:format("ChatMap is now: ~p~n", [ChatMap]),

					    Updated_Chat_State = #serv_st{nicks = Chat_State#serv_st.nicks, registrations = ChatMap, chatrooms = Chat_State#serv_st.chatrooms},
					    Updated_Chat_State

		    			%{ClientPID, ChatState} => {ChatSpawnPID, Chat_State}
		    	end.
		

%% executes leave protocol from server perspective
do_leave(ChatName, ClientPID, Ref, State) ->
	%Client will be in chatroom named ChatName. Finding the PID of it from the chat map.
    Chatroom = maps:get(ChatName, State#serv_st.chatrooms),
    io:format("Chatroom PID is: ~p~n", [Chatroom]),

    %Lets see which clients are in the chatroom
    Clients_Registered = maps:get(ChatName, State#serv_st.registrations),
    io:format("Current Clienst Resgitered in chatroom: ~p~n", [State#serv_st.registrations]),

    %Remove the PID od the client who is leaving. Its given.
    New_Clients_Registered = Clients_Registered--[ClientPID],
    io:format("Registered Clients Now that we have removed ~p: ~p~n", [ClientPID, New_Clients_Registered]),

    %create a new map with the new list of registered clients
    New_ChatMap = maps:put(ChatName, New_Clients_Registered,State#serv_st.registrations),
    io:format("Updated chatmap is: ~p~n", [New_ChatMap]),

    %send the message that th client has been remeoved and acknowledgement to the client
    Chatroom!{self(), Ref, unregister, ClientPID},
    ClientPID!{self(), Ref, ack_leave},
    Chat_State = #serv_st{nicks = State#serv_st.nicks, registrations = New_ChatMap, chatrooms = State#serv_st.chatrooms},

    Chat_State.

%% executes new nickname protocol from server perspective
do_new_nick(State, Ref, ClientPID, NewNick) ->
   %get all the nicknames from the map so that we can check against them
   Nicknames = maps:values(State#serv_st.nicks),
   io:format("The nicknames present in the chatmap are: ~p~n", [Nicknames]),

    case lists:member(NewNick, Nicknames) of
    	%Server sends message when a client tries to have a nickname that is already in use
    	true -> ClientPID!{self(),Ref,err_nick_used},
    			io:format("This nickname is already in use.~n"),
    			State;
    	%other case is its not in use. So we add it into the map
    	false ->  Updated_Nicknames = maps:put(ClientPID, NewNick, State#serv_st.nicks),
    			io:format("The nicknames now in the chatmap are: ~p~n", [Updated_Nicknames]),

    			Resgistered_Clients = maps:to_list(State#serv_st.registrations),
    			lists:foreach(fun(T) ->
    				{K,V} = T,
    				case lists:member(ClientPID, V) of
    					true ->	maps:get(K,State#serv_st.chatrooms)!{self(), Ref, update_nick, ClientPID, NewNick}; %send the updated nickname
	    				false -> io:format("Client ~p is not in chatroom ~p~n", [ClientPID, K])
    				end
    			end, Resgistered_Clients),
    			%send the ok and change state
    			ClientPID!{self(), Ref, ok_nick},
    			Chat_State = #serv_st{nicks = Updated_Nicknames, registrations = State#serv_st.registrations, chatrooms = State#serv_st.chatrooms},
    			Chat_State
    end.

%% executes client quit protocol from server perspective
do_client_quit(State, Ref, ClientPID) ->
	%Nams of the client who requested to quit
    Quit_Client_Name = maps:remove(ClientPID, State#serv_st.nicks),
    %list of the registered clients
    Resgistered_Clients = maps:to_list(State#serv_st.registrations),
	lists:foreach(fun(T) ->
		{K,V} = T,
		case lists:member(ClientPID, V) of
			%searching through the list of clients ini the chatroom, if true, it is found and we send the unregister messgae
			true -> maps:get(K,State#serv_st.chatrooms)!{self(),Ref,unregister,ClientPID};
			false ->io:format("The Client ~p  is not in the chatroom ~p quit operation not possible~n", [ClientPID, K]) %false case is self explanitory, client isnt in the chatroom for some reason

		end

	end, Resgistered_Clients),
	%remove the client from the other chatrooms and update th state of the chat
	Chat_State = cleanup_chatrooms(Resgistered_Clients, [], ClientPID, State#serv_st.registrations, State),
	%Send the ackowledgement to the client who requested to quir
    ClientPID!{self(), Ref, ack_quit},
    Chat_State.

%Helper functions for the list operations needed to clean up the other chatrooms when a client quits
cleanup_chatrooms([H|T], Resgistered_Clients, ClientPID, Chat_Map, State) ->
	%start at head
	{Key,Value} = H,

	%take out the quitting client PID
	V = Value -- [ClientPID],

	%save thsi as the new head
	Updated_Head = {Key, V},

	%put this updated head into the map as we removed the PID
	Updated_ChatMap = maps:put(Key, V, Chat_Map),
	Updated_Resgistered_Clients = Resgistered_Clients++[Updated_Head],

	%loop through list
	cleanup_chatrooms(T, Updated_Resgistered_Clients, ClientPID, Updated_ChatMap, State);

%second helper function for empty list
cleanup_chatrooms([], Resgistered_Clients, ClientPID, Chat_Map, State) ->
	Chat_State = #serv_st{nicks = State#serv_st.nicks, registrations = Chat_Map, chatrooms = State#serv_st.chatrooms},
	Chat_State.

