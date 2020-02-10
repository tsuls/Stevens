-module(shipping).
-compile(export_all).
-include_lib("./shipping.hrl").

get_ship(Shipping_State, Ship_ID) ->
  lists:keyfind(ShipID, 1, Shipping_State#shipping_state.ships).

get_container(Shipping_State, Container_ID) ->
  lists:keyfind(Container_ID, 1, Shipping_State#shipping_state.container).
    

get_port(Shipping_State, Port_ID) ->
   lists:keyfind(Port_ID, 1,Shipping_State#shipping_state.ports).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
get_occupied_docks_helper([], Port_ID) ->
  ok;
get_occupied_docks_helper([H|T], Port_ID) ->
  {P, D, S} = H,
  if P == Port_ID -> D.
  ; get_occupied_docks_helper(T)
  end.
get_occupied_docks(Shipping_State, Port_ID) ->
    get_occupied_docks_helper(Shipping_State#shipping_state.ship_locations, Port_ID).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
get_ship_location_helper([], Ship_ID) ->
  ok;
get_ship_location_helper([H|T], Ship_ID) ->
  {P, D, S} = H
  if S == Ship_ID  -> {P, D}.
  ; get_ship_location_helper(T, Ship_ID)
  end.
get_ship_location(Shipping_State, Ship_ID) ->
    get_ship_location_helper(Shipping_State#shipping_state.ship_locations, Ship_ID).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
get_container_weight_sum_helper([]) ->
  0;

get_container_weight_sum_helper([H|T]) ->
  H + get_container_weight_helper(T).

get_container_weight_helper(Weights, [], Shipping_State)->
  ok;
get_container_weight_helper(Weights, [H|T], Shipping_State) ->
  {I, W} = get_container(Shipping_State, H),
  get_container_weight_helper(Weights++[W], T, Shipping_State).

get_container_weight(Shipping_State, Container_IDs) ->
   WeightContainers = get_container_weight_helper([], Container_IDs, Shipping_State),
   get_container_weight_sum_helper(WeightContainers).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
get_ship_weight(Shipping_State, Ship_ID) ->
    get_container_weight(Shipping_State, maps:find(Ship_ID, Shipping_State#shipping_state.ship_inventory)).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
load_ship_helper(Shipping_State, []) ->
  Shipping_State.

load_ship_helper(Shipping_State, [H|T]) ->
  {V, M} = maps:take(H, Shipping_State#shipping_state.port_inventory),
  {V2, M2} = maps:put(V, Shipping_State#shipping_state.ship_inventory),

  ShipsN = Shipping_State#shipping_state.ships,
  ContainersN = Shipping_State#shipping_state.containers,
  PortsN = Shipping_State#shipping_state.ports,
  ShipLocsN = Shipping_State#shipping_state.ship_locations,
  ShipInvN = M2,
  PortInvN = M,

  Shipping_StateN = #shipping_state{ships = ShipsN, containers = ContainersN, ports = PortsN, ship_locations = ShipLocsN, ship_inventory = ShipInvN, port_inventory = PortInvN},
  load_ship_helper(Shipping_StateN, T).

load_ship(Shipping_State, Ship_ID, Container_IDs) ->
    {I, N, C} = get_ship(Shipping_State, Ship_ID),
    Container_Max = C,
    if (length(Container_IDs) + maps:size(Shipping_State#shipping_state.ship_inventory)) > C -> error.
   
    Shipping_State2 = load_ship_helper(Shipping_State, Container_IDs),
    Shipping_State2
  end.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

unload_ship_all(Shipping_State, Ship_ID) ->
   {P,D} = get_ship_location(Shipping_State, Ship_ID),
   Port = get_port(Shipping_State, P),
   if(maps:size(Shipping_State#shipping_state.ship_inventory + maps:size(Shipping_State#shipping_state.port_inventory) > Port#port.container_cap)) -> error.
   orelse
    Container_IDs = maps:get(Ship_ID, Shipping_State#shipping_state.ship_inventory),
    Shipping_State2 = unload_ship_helper(Shipping_State, Container_IDs),
    Shipping_State2
 end.

 unload_ship_helper(Shipping_State, []) -> 
 Shipping_State.

 unload_ship_helper(Shipping_State, [H|T]) ->
   {V, M} = maps:take(H, Shipping_State#shipping_state.ship_inventory),
   {V2, M2} = maps:put(V, Shipping_State#shipping_state.port_inventory),

   ShipsN = Shipping_State#shipping_state.ships,
   ContainersN = Shipping_State#shipping_state.containers,
   PortsN = Shipping_State#shipping_state.ports,
   ShipLocsN = Shipping_State#shipping_state.ship_locations,
   ShipInvN = M2,
   PortInvN = M,
   Shipping_StateN = #shipping_state{ships = ShipsN, containers = ContainersN, ports = PortsN, ship_locations = ShipLocsN, ship_inventory = ShipInvN, port_inventory = PortInvN},
   unload_ship_helper(Shipping_StateN, T).

unload_ship(Shipping_State, Ship_ID, Container_IDs) ->
  {P,D} = get_ship_location(Shipping_State, Ship_ID),
  Port = get_port(Shipping_State, P),
  if(length(Container_IDs) + maps:size(Shipping_State#shipping_state.port_inventory) > Port#port.container_cap) -> 
    error.
  orelse
    Shipping_State2 = unload_ship_helper(Shipping_State, Container_IDs),
    Shipping_State2
  end.

set_sail(Shipping_State, Ship_ID, {Port_ID, Dock}) ->
    OccDock = get_occupied_docks(Shipping_State, Port_ID),
    if(lists:member(Dock , OccDock) == true) ->
     error.
    orelse
    CurLoc = get_ship_location(Shipping_State, Ship_ID),
    NewShipLoc = lists:delete(CurLoc, Shipping_State#shipping_state.ship_locations),
    NewShipLoc2 = lists:append(NewShipLoc, [{Port_ID, Dock}]),

   ShipsN = Shipping_State#shipping_state.ships,
   ContainersN = Shipping_State#shipping_state.containers,
   PortsN = Shipping_State#shipping_state.ports,
   ShipLocsN = NewShipLoc2,
   ShipInvN = Shipping_State#shipping_state.ship_inventory,
   PortInvN = Shipping_State#shipping_state.port_inventory,

   Shipping_State2 = #shipping_state{ships = ShipsN, containers = ContainersN, ports = PortsN, ship_locations = ShipLocsN, ship_inventory = ShipInvN, port_inventory = PortInvN},
   Shipping_State2
 end.


%% Determines whether all of the elements of Sub_List are also elements of Target_List
%% @returns true is all elements of Sub_List are members of Target_List; false otherwise
is_sublist(Target_List, Sub_List) ->
    lists:all(fun (Elem) -> lists:member(Elem, Target_List) end, Sub_List).




%% Prints out the current shipping state in a more friendly format
print_state(Shipping_State) ->
    io:format("--Ships--~n"),
    _ = print_ships(Shipping_State#shipping_state.ships, Shipping_State#shipping_state.ship_locations, Shipping_State#shipping_state.ship_inventory, Shipping_State#shipping_state.ports),
    io:format("--Ports--~n"),
    _ = print_ports(Shipping_State#shipping_state.ports, Shipping_State#shipping_state.port_inventory).


%% helper function for print_ships
get_port_helper([], _Port_ID) -> error;
get_port_helper([ Port = #port{id = Port_ID} | _ ], Port_ID) -> Port;
get_port_helper( [_ | Other_Ports ], Port_ID) -> get_port_helper(Other_Ports, Port_ID).


print_ships(Ships, Locations, Inventory, Ports) ->
    case Ships of
        [] ->
            ok;
        [Ship | Other_Ships] ->
            {Port_ID, Dock_ID, _} = lists:keyfind(Ship#ship.id, 3, Locations),
            Port = get_port_helper(Ports, Port_ID),
            {ok, Ship_Inventory} = maps:find(Ship#ship.id, Inventory),
            io:format("Name: ~s(#~w)    Location: Port ~s, Dock ~s    Inventory: ~w~n", [Ship#ship.name, Ship#ship.id, Port#port.name, Dock_ID, Ship_Inventory]),
            print_ships(Other_Ships, Locations, Inventory, Ports)
    end.

print_containers(Containers) ->
    io:format("~w~n", [Containers]).

print_ports(Ports, Inventory) ->
    case Ports of
        [] ->
            ok;
        [Port | Other_Ports] ->
            {ok, Port_Inventory} = maps:find(Port#port.id, Inventory),
            io:format("Name: ~s(#~w)    Docks: ~w    Inventory: ~w~n", [Port#port.name, Port#port.id, Port#port.docks, Port_Inventory]),
            print_ports(Other_Ports, Inventory)
    end.
%% This functions sets up an initial state for this shipping simulation. You can add, remove, or modidfy any of this content. This is provided to you to save some time.
%% @returns {ok, shipping_state} where shipping_state is a shipping_state record with all the initial content.
shipco() ->
    Ships = [#ship{id=1,name="Santa Maria",container_cap=20},
              #ship{id=2,name="Nina",container_cap=20},
              #ship{id=3,name="Pinta",container_cap=20},
              #ship{id=4,name="SS Minnow",container_cap=20},
              #ship{id=5,name="Sir Leaks-A-Lot",container_cap=20}
             ],
    Containers = [
                  #container{id=1,weight=200},
                  #container{id=2,weight=215},
                  #container{id=3,weight=131},
                  #container{id=4,weight=62},
                  #container{id=5,weight=112},
                  #container{id=6,weight=217},
                  #container{id=7,weight=61},
                  #container{id=8,weight=99},
                  #container{id=9,weight=82},
                  #container{id=10,weight=185},
                  #container{id=11,weight=282},
                  #container{id=12,weight=312},
                  #container{id=13,weight=283},
                  #container{id=14,weight=331},
                  #container{id=15,weight=136},
                  #container{id=16,weight=200},
                  #container{id=17,weight=215},
                  #container{id=18,weight=131},
                  #container{id=19,weight=62},
                  #container{id=20,weight=112},
                  #container{id=21,weight=217},
                  #container{id=22,weight=61},
                  #container{id=23,weight=99},
                  #container{id=24,weight=82},
                  #container{id=25,weight=185},
                  #container{id=26,weight=282},
                  #container{id=27,weight=312},
                  #container{id=28,weight=283},
                  #container{id=29,weight=331},
                  #container{id=30,weight=136}
                 ],
    Ports = [
             #port{
                id=1,
                name="New York",
                docks=['A','B','C','D'],
                container_cap=200
               },
             #port{
                id=2,
                name="San Francisco",
                docks=['A','B','C','D'],
                container_cap=200
               },
             #port{
                id=3,
                name="Miami",
                docks=['A','B','C','D'],
                container_cap=200
               }
            ],
    %% {port, dock, ship}
    Locations = [
                 {1, 'B', 1},
                 {1, 'A', 3},
                 {3, 'C', 2},
                 {2, 'D', 4},
                 {2, 'B', 5}
                ],
    Ship_Inventory = #{
      1=>[14,15,9,2,6],
      2=>[1,3,4,13],
      3=>[],
      4=>[2,8,11,7],
      5=>[5,10,12]},
    Port_Inventory = #{
      1=>[16,17,18,19,20],
      2=>[21,22,23,24,25],
      3=>[26,27,28,29,30]
     },
    #shipping_state{ships = Ships, containers = Containers, ports = Ports, ship_locations = Locations, ship_inventory = Ship_Inventory, port_inventory = Port_Inventory}.
