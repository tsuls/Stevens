-module(client).

-export([main/1, initial_state/2]).

-include_lib("./defs.hrl").

-spec main(_InitialState) -> _.
-spec listen(_State) -> _.
-spec initial_state(_Nick, _GuiName) -> _InitialClientState.
-spec loop(_State, _Request, _Ref) -> _.
-spec do_join(_State, _Ref, _ChatName) -> _.
-spec do_leave(_State, _Ref, _ChatName) -> _.
-spec do_new_nick(_State, _Ref, _NewNick) -> _.
-spec do_new_incoming_msg(_State, _Ref, _SenderNick, _ChatName, _Message) -> _.

%% Receive messages from GUI and handle them accordingly
%% All handling can be done in loop(...)
main(InitialState) ->
    %% The client tells the server it is connecting with its initial nickname.
    %% This nickname is guaranteed unique system-wide as long as you do not assign a client
    %% the nickname in the form "user[number]" manually such that a new client happens
    %% to generate the same random number as you assigned to your client.
    whereis(server)!{self(), connect, InitialState#cl_st.nick},
    %% if running test suite, tell test suite that client is up
    case whereis(testsuite) of
	undefined -> ok;
	TestSuitePID -> TestSuitePID!{client_up, self()}
    end,
    %% Begins listening
    listen(InitialState).

%% This method handles all incoming messages from either the GUI or the
%% chatrooms that are not directly tied to an ongoing request cycle.
listen(State) ->
    receive
        {request, From, Ref, Request} ->
	    %% the loop method will return a response as well as an updated
	    %% state to pass along to the next cycle
            {Response, NextState} = loop(State, Request, Ref),
	    case Response of
		{dummy_target, Resp} ->
		    io:format("Use this for whatever you would like~n"),
		    From!{result, self(), Ref, {dummy_target, Resp}},
		    listen(NextState);
		%% if shutdown is received, terminate
		shutdown ->
		    ok_shutdown;
		%% if ok_msg_received, then we don't need to reply to sender.
		ok_msg_received ->
		    listen(NextState);
		%% otherwise, reply to sender with response
		_ ->
		    From!{result, self(), Ref, Response},
		    listen(NextState)
	    end
    end.

%% This function just initializes the default state of a client.
%% This should only be used by the GUI. Do not change it, as the
%% GUI code we provide depends on it.
initial_state(Nick, GUIName) ->
    #cl_st { gui = GUIName, nick = Nick, con_ch = maps:new() }.

%% ------------------------------------------
%% loop handles each kind of request from GUI
%% ------------------------------------------
loop(State, Request, Ref) ->
    case Request of
	%% GUI requests to join a chatroom with name ChatName
	{join, ChatName} ->
	    do_join(State, Ref, ChatName);

	%% GUI requests to leave a chatroom with name ChatName
	{leave, ChatName} ->
	    do_leave(State, Ref, ChatName);

	%% GUI requests to send an outgoing message Message to chatroom ChatName
	{outgoing_msg, ChatName, Message} ->
	    do_msg_send(State, Ref, ChatName, Message);

	%% GUI requests the nickname of client
	whoami ->
	   whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, State#cl_st.nick},
        {State#cl_st.nick, State};
	%% GUI requests to update nickname to Nick
	{nick, Nick} ->
            do_new_nick(State, Ref, Nick);

	%% GUI requesting to quit completely
	quit ->
	    do_quit(State, Ref);

	%% Chatroom with name ChatName has sent an incoming message Message
	%% from sender with nickname SenderNick
	{incoming_msg, SenderNick, ChatName, Message} ->
	    do_new_incoming_msg(State, Ref, SenderNick, ChatName, Message);

	{get_state} ->
	    {{get_state, State}, State};

	%% Somehow reached a state where we have an unhandled request.
	%% Without bugs, this should never be reached.
	_ ->
	    io:format("Client: Unhandled Request: ~w~n", [Request]),
	    {unhandled_request, State}
    end.

%% executes `/join` protocol from client perspective
do_join(State, Ref, ChatName) ->
	%see if the chatroom exists
    case maps:is_key(ChatName, State#cl_st.con_ch) of
    	%it do sine the is_key returns true, send the message to the GUI
    	true -> Exist_Loc = whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, err},
    			io:format("Chatroom exits~n"),	
    			{err, State};
    	%join it
    	false -> server!{self(), Ref, join, ChatName},

    				receive
				    	{RecPID, Ref, connect, History} ->
				    		Chat_connection = maps:put(ChatName, RecPID, State#cl_st.con_ch), %get the chat history so we can put the new chatroom connection into it
				    		whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, History},
                            io:format("debugClient"),
				    		%create a new chat state with the new chatroom connection
				    		The_Chat_State = #cl_st{ gui = State#cl_st.gui, nick = State#cl_st.nick, con_ch = Chat_connection},
				    		io:format("Chatroom sucessfully joined~n"),
				    		%return that ish
				    		{History, The_Chat_State}
    				end 
    end.	

%% executes `/leave` protocol from client perspective
do_leave(State, Ref, ChatName) ->
	%if were gonna leave the chatroom, we need to find it in the map
    case maps:find(ChatName, State#cl_st.con_ch) of
    	%some error/chatroom isn't found in the map
    	error -> whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, err},
    		{err, State};
    	%found, tell the server that we are leaving
    	{ok, Val} -> server!{self(), Ref, leave, ChatName},
    		receive
    			%server acknowledges we are leaving, update the chat
    			{RecPID, Ref, ack_leave} ->
    				Chat_connection = maps:remove(ChatName, State#cl_st.con_ch), %remove the client from the chat's map
    				whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, ok},
    				%update the state of the chatroom with the removed user taken out
    				The_Updated_Chat_State = #cl_st{ gui = State#cl_st.gui, nick = State#cl_st.nick, con_ch = Chat_connection},

    				io:format("Removed ~p from chatroom: ~p~n", [ChatName, Chat_connection]),
    				{ok, The_Updated_Chat_State}
    		end
    end.

%% executes `/nick` protocol from client perspective
do_new_nick(State, Ref, NewNick) ->
    case State#cl_st.nick == NewNick of
    	%if we find this nickname is the one we are using, send an error to the GUI
    	true -> whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, err_same},
    		io:format("Name ~p is your current name. ~n", [NewNick]),
    		{err_same, State}; 
   		 %continue since the name is not our current one
    	false -> 
    		server!{self(), Ref, nick, NewNick}, %full send
    		receive
    			%server will send this error if we send it a nickname that has been used. Send the result of whats being used back to GUI
		    	{RecPID,Ref,err_nick_used} ->
		    			whereis(list_to_atom(State#cl_st.gui))!{result,self(),Ref,err_nick_used},
		    			io:format("Name ~p has been used. ~n", [NewNick]),
		    			{err_nick_used, State};
		    	%server says were gucci
		    	{RecPID, Ref, ok_nick} ->
		    			io:format("Name ~p is available. ~n", [NewNick]),
		    			Chat_State = #cl_st{ gui = State#cl_st.gui, nick = NewNick , con_ch = State#cl_st.con_ch},
		    			whereis(list_to_atom(Chat_State#cl_st.gui))!{result,self(), Ref, ok_nick},
		    			{ok_nick, Chat_State}
    		end

    end.

%% executes send message protocol from client perspective
do_msg_send(State, Ref, ChatName, Message) ->
	%grab client PID from chat map
    Client = maps:get(ChatName, State#cl_st.con_ch),
    %send the message
    Client!{self(), Ref, message, Message},
    %Server acknowleges we sent the message
    receive
    	{RecPid, Ref, ack_msg} ->
    		whereis(list_to_atom(State#cl_st.gui))!{result, self(), Ref, {msg_sent, State#cl_st.nick}}
    end,
    %return that the message is sent 
    {msg_sent, State}.

%% executes new incoming message protocol from client perspective
do_new_incoming_msg(State, _Ref, CliNick, ChatName, Msg) ->
	%receivng client protocol with API call to send to GUI
    gen_server:call(list_to_atom(State#cl_st.gui), {msg_to_GUI, ChatName, CliNick, Msg}),
    {ok_msg_received, State}.

%% executes quit protocol from client perspective
do_quit(State, Ref) ->
	%tell the server to quit
    server!{self(), Ref, quit},
    
    receive
    	%server acknowleges the quit and we quit. 
    	{RecPID, Ref, ack_quit} ->
    		
    		whereis(list_to_atom(State#cl_st.gui))!{self(), Ref, ack_quit}
    end,
    {ack_quit, State}.
