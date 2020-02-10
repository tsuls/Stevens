type 'a tree = Empty | Node of 'a * 'a tree * 'a tree

let t : int tree = Node(74,Node(50,Empty,Node(62,Empty,Empty)),Node(88,Node(83,Empty,Empty),Empty))

let rec find : 'a -> 'a tree -> bool = fun k t ->
	match t with
	| Empty -> false
	| Node (i,lt,rt) when k=i -> true
		(if k<i
		then find k lt
		else find k rt)

let rec add : 'a -> 'a tree -> 'a tree = fun k t ->
	match t with
	| Empty -> Node(k,Empty, Empty)
	| Node(i,lt,rt) when i=k -> t
	| Node(i,lt,rt) -> 
		if k<i
		then Node(i, add k lt, rt)
		else Node (i,lt, add k rt) 


let rec max : 'a tree -> 'a = fun t ->
	match t with
	| Empty -> failwith "not possible"
	| Node(i, lt, Empty) -> i
	| Node (i,lt,rt) -> max rt


let rec remove : 'a -> 'a tree -> 'a tree = fun k t ->
	match t with
	| Empty -> failwith "Element Absent"
	| Node(i,lt,Empty) when k=i -> lt
	| Node(i,Empty,rt) when k=i -> rt
	| Node(i,lt,rt) when k=i ->
		let m = max lt
		in Node(m, remove m lt, rt)
	| Node (i,lt,rt) ->
		if k<i
		then Node(i, remove k lt, rt)
		else Node(i, lt, remove k rt)

