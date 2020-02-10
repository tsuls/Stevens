-module(b).
-compile(export_all).

barrier(0, N, L) ->
.

barrier(M,N,L) when M > 0 ->
.


pass_barrier(B) >
	B!{self(), reached},
	receive
		{B, ok} ->
			ok
	end.

client(B, Letter, Number) ->
	io:format("~p ~s~n", [self(), Letter]),
	pass_barrier(B),
	io:format("~p ~w~n", [self(), Number]).

start() ->
	B = spawn(?MODULE, barrier, [3,3[]]),
	spawn(?MODULE, client, [B, "a", 1]),
	spawn(?MODULE, client, [B, "b", 2]),
	spawn(?MODULE, client, [B, "c", 3]).



%%producers and consumers

barrier(Size, Consumers, Producers, Capacity) ->
	receive
		{From, startProduce} when Size + Producers < Capacity ->
			{self(), ok},
			buffer(Size, Consumers, Producers + 1, Capacity);
		{From, startProduce}
			buffer(Size+1, Consumers, Producer - 1, Capacity);
		{From, startConsume} when Size - Consumers > 0 ->
			{self(), ok},
			buffer(Size, Consumers+1, Producers, Capacity);
		{From, stopConsume}
			buffer(Size - 1, Consumers - 1, Producers, Capacity)

producer(B) ->
	B!{self(), startProduce},
	receive
		{B, ok} ->
			produce,
			B!{self(), startProduce}
	end.

consumer(B) ->
	B!{self(), startConsume},
	receive
		{B, ok} ->
			consume,
			B!{self(), stopConsume}
	end,