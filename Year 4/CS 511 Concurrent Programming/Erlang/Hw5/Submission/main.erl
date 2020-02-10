-module(main).
-import(watcher,[watcher_start/2]).
-import(sensor, [sensor_run/2]).
-compile(export_all).
-author("Tyler Sulsenti").
%I pledge my honor that I have abided by the Stevens Honor System


%given
start() ->
	{ok, [ N ]} = io:fread("enter number of sensors> ", "~d"), 
	if N =< 1 ->
		io:fwrite("setup: range must be at least 2~n", []); 
	true ->
		Num_watchers = 1 + (N div 10),
		setup_loop(N, Num_watchers) 
	end.

setup_loop(SenN, Watcher_count) ->
	setup_loop(SenN, Watcher_count, 0).

setup_loop(SenN, Watcher_count, SID) when SenN =< 10 ->
	spawn(watcher, watcher_start, [SenN, SID]);

setup_loop(SenN, Watcher_count, SID) ->
	spawn(watcher, watcher_start, [10, SID]),
	setup_loop(SenN - 10, Watcher_count - 1, SID + 10).