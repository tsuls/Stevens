-module(chatroom).

-include_lib("./defs.hrl").

-export([start_chatroom/1]).

-spec start_chatroom(_ChatName) -> _.
-spec loop(_State) -> _.
-spec do_register(_State, _Ref, _ClientPID, _ClientNick) -> _NewState.
-spec do_unregister(_State, _ClientPID) -> _NewState.
-spec do_update_nick(_State, _ClientPID, _NewNick) -> _NewState.
-spec do_propegate_message(_State, _Ref, _ClientPID, _Message) -> _NewState.

start_chatroom(ChatName) ->
    loop(#chat_st{name = ChatName,
		  registrations = maps:new(), history = []}),
    ok.

loop(State) ->
    NewState =
	receive
	    %% Server tells this chatroom to register a client
	    {_ServerPID, Ref, register, ClientPID, ClientNick} ->
		do_register(State, Ref, ClientPID, ClientNick);
	    %% Server tells this chatroom to unregister a client
	    {_ServerPID, _Ref, unregister, ClientPID} ->
		do_unregister(State, ClientPID);
	    %% Server tells this chatroom to update the nickname for a certain client
	    {_ServerPID, _Ref, update_nick, ClientPID, NewNick} ->
		do_update_nick(State, ClientPID, NewNick);
	    %% Client sends a new message to the chatroom, and the chatroom must
	    %% propegate to other registered clients
	    {ClientPID, Ref, message, Message} ->
		do_propegate_message(State, Ref, ClientPID, Message);
	    {TEST_PID, get_state} ->
		TEST_PID!{get_state, State},
		loop(State)
end,
    loop(NewState).

%% This function should register a new client to this chatroom
do_register(State, Ref, ClientPID, ClientNick) ->
	%Put the new client into a map and save it%
    Client_Register = maps:put(ClientPID, ClientNick, State#chat_st.registrations),
    io:format("A new Client has registerd: ~p~n", [Client_Register]),
   
   	%put the new map into a chat state so we can update the chat's current state%
    Chat_State = #chat_st{name = State#chat_st.name, registrations = Client_Register, history = State#chat_st.history},
    io:format("the Chat's new and current state: ~p~n", [Chat_State]),
    %full send fam -> retun the update state as well
    ClientPID!{self(), Ref, connect, Chat_State#chat_st.history},
    
    Chat_State.

%% This function should unregister a client from this chatroom
do_unregister(State, ClientPID) ->
   Client_Register = maps:remove(ClientPID, State#chat_st.registrations),
   Chat_State = #chat_st{name = State#chat_st.name, registrations = Client_Register, history = State#chat_st.history},
   io:format("Unregistered Client:~p~n", [ClientPID]),
   Chat_State.

%% This function should update the nickname of specified client.
do_update_nick(State, ClientPID, NewNick) ->
	%Put the new nickname in the chatroom map and assign it for use later
   Client_Register = maps:put(ClientPID, NewNick, State#chat_st.registrations),
   Chat_State = #chat_st{name = State#chat_st.name, registrations = Client_Register, history = State#chat_st.history},
   io:format("Updated nickname of the registered Client:~p~n", [Client_Register]),
   %Update and return the new chat state, containing the updated nickname
   Chat_State.

%% This function should update all clients in chatroom with new message
%% (read assignment specs for details)
do_propegate_message(State, Ref, ClientPID, Message) ->
    ClientPID!{self(), Ref, ack_msg},
    %Obtain the chat register and nickname of the sender so we can properly format the message to send
   	Client_Register = maps:to_list(State#chat_st.registrations),
   	Client_Nickname = maps:get(ClientPID,State#chat_st.registrations),
   	%Look through the list of PIDS to carefully send the message
   	lists:foreach(fun(T) ->
    				{PID,X} = T,
    				case ClientPID == PID of
    					%Found the sender PID in the PID list. We should ignore this as we don't want the sender to recieve the message they sent.
    					true -> nothing; % i dont know what to put here to make it do nothing
    					false -> PID!{request, self(), Ref, {incoming_msg, Client_Nickname, State#chat_st.name, Message}}
    				end
    end, Client_Register),
    
	 io:format("Message sent to chatroom!~n"),

    New_Client_Register = State#chat_st.history++[{Client_Nickname, Message}],
    %Update abd return the chat state to include the new message 
    Chat_State = #chat_st{name = State#chat_st.name, registrations = State#chat_st.registrations, history = New_Client_Register},
	  Chat_State.
