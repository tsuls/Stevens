-module(watcher).
-import(sensor, [sensor_run/2]).
-export([watcher_start/2]).
-author("Tyler Sulsenti").
%I pledge my honor that I have abided by the Stevens Honor System

watcher_start(SenN, SID) ->
	%report start and run the watcher. Generate the list with number of sensors
	io:fwrite("here"),
	TheSensorList = gen_list(SenN, SID, []),

	io:fwrite("The watcher service is started ~w~n", [TheSensorList]),
	watcher_run(TheSensorList).

watcher_run(SensorList) ->
	receive
		%recieve a measurement, print it out and mainatain the same list as it has not changed
		{From, Measurement} ->
			io:fwrite("Sensor ~p gave a Measurement of ~p~n", [From, Measurement]),
			UpdatedSensorList = SensorList;
		%process with PID is down, a sesnor crashed
		{'DOWN', _, process, PID, Reason} ->
			%Find the sensor ID (SID) of the process that is down
			{_, SID} = lists:keyfind(PID, 1, SensorList),
			%report the failure that has been deteced
			io:fwrite("Sensor ~p has crashed with error ~p i will attempt to restart it~n", [SID, Reason]),
			%restart the sensor, the new process will have a new PID & report it
			{UpdatedPID, _} = spawn_monitor(sensor, sensor_run, [self(), SID]),
			%Now we canm actually updatethe list with a new value
			UpdatedSensorList = lists:keyreplace(SID, 2, SensorList, {UpdatedPID, SID}),
			io:fwrite("The sensor ~p was restarted with PID ~p", [SID, UpdatedPID])
	end,
	watcher_run(UpdatedSensorList).

%Base case when we are out of SenN monitors
gen_list(0, _, TheList) ->
	TheList;

gen_list(SenN, SID, TheList) ->
	%create the monitor and continue unil all the SenN number of monitors have been created
	{PID, _} = spawn_monitor(sensor, sensor_run, [self(), SID]),
	%continue and add the tuple of the new monitor to the list
	gen_list(SenN - 1, SID +1, TheList++[{PID, SID}]).

