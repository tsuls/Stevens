counter_loop(S) ->
	recieve 
		{bump} ->
			counter_loop(S + 1)
		{read, From, ref} ->
			From!{self(), Ref, S}
		{stop} ->
			ok
	end.

turnstile(0, C) ->
	ok;

turnstile(N, C) when N > 0 ->
	C!{bump}.
	turnstile(N-1, C).

startT(N) ->
	C = spawn(?MODULE, counter_loop, [0]),
	spawn(?MODULE, turnstile, [N,C]),
	spawn(?MODULE, turnstile, [N,C]),
	C.