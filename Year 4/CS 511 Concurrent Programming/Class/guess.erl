-module(guess).
-compile(export_all).

server() ->
	recieve
		{From, Ref, start} ->
			S = spawn(?MODULE, servlet, [rand:uniform(20)]),
			FROM{self(), Ref, S},
			server()
	end.

client() ->
	R = make_ref(),
	S!{self(), R, start},
	recieve
	{S, R, Servlet} ->
		client_loop(Servlet, 0)
	end.

client_loop(Servlet, C) ->
	R = make_ref(),
	Servlet{self(), R, guess, rand:uniform(20)},
	recieve
		{Servlet, R, gotIt} ->
			io:format("Client ~p guessed in ~w attemps\n", [self() C]);
		{Servlet, R, tryAgain} ->
			client_loop(Servlet, C+1)
	end.

servlet(Cl, Number) ->
	recieve
		{From, Ref, guess, N}. ->
			if
				N == Number -> From!{self(), Ref, gotIt};
				true -> From!{self(), Ref, tryAgain},
				servlet(Cl, Number)
			end
	end.

start() ->
	spawn(fun server/0).

