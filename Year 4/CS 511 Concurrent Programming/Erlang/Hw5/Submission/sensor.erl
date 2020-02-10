-module(sensor).
-export([sensor_run/2]).
-author("Tyler Sulsenti").
%I pledge my honor that I have abided by the Stevens Honor System


sensor_run(From, SID) ->
    %Generate random number 1-11
    Measurement = rand:uniform(11),
    if Measurement == 11 ->
        %crash if we get 11
         exit(anomalous_reading);
    true ->
        %send measurement if we get anything else
         From!{SID,Measurement}
    end,

    %sleep for 10000 ms and then loop as shown in assignment spec
    Sleep_time = rand:uniform(10000),
    timer:sleep(Sleep_time),
    sensor_run(From,SID).