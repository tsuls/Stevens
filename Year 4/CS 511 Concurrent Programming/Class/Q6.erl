-module(Q6).
-compile(export_all).




dryCleaner(Clean, Dirty) ->
	Ref = makeref(),
	recieve
		{dropOffOverall} ->
			DC!{self(), 
			dryCleaner(Clean, Dirty + 1)
		{From, Ref, dryCleanItem} ->
			dryCleaner(Clean + 1, Dirty - 1)
		{From Ref, pickUpOverall} when Clean > 0 ->
			dryCleaner(Clean - 1 , Dirty)
	end.

	dryCleanMachin(DC) ->
	R = makeref(),
	recieve
		


start(E,M) ->
	DC=spawn(?MODULE ,dryCleaner ,[0,0]),
	[ spawn(?MODULE ,employee ,[DC]) || _ <- lists:seq(1,E) ],
	[ spawn(?MODULE ,dryCleanMachine ,[DC]) || _ <- lists:seq(1,M) ].