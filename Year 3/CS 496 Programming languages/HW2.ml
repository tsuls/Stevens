(* Tyler Sulsenti
 * I pledge my honor that I have abided by the Stevens Honor System *)


type dTree = 
  Empty
  | Leaf of int
  | Node of char * dTree * dTree


let tLeft = Node ('w', Node ('x', Leaf 2, Leaf 5), Leaf 8)



let tRight = Node ('w', Node ('x', Leaf 2, Leaf 5), Node ('y', Leaf 7, Leaf 5))


let rec dTree_height tree =
  match tree with
  Empty -> 0
  | Leaf i -> 0
  | Node(x, lt, rt) ->
     if dTree_height lt > dTree_height rt
     then 1 + dTree_height lt
     else 1 + dTree_height rt

let rec dTree_size tree = match tree with
  | Empty -> 0
  | Leaf i -> 1
  | Node(x, lt, rt) -> dTree_size lt + dTree_size rt + 1


let rec dTree_paths tree =
  match tree with
  | Leaf i -> [[]]
  | Node(x, lt, rt) -> let concat i l = i::l in (List.map (concat 0) (dTree_paths lt)) @ (List.map (concat 1) (dTree_paths rt))


let helper x y = match (x,y) with
  | (true, true) -> true
  | (false, false) -> false
  | (false, true) -> false
  | (true, false) -> false


let rec dTree_is_perfect per = match per with
  Empty -> true
  | Leaf x -> true
  | Node(i, l, r) ->
    if(dTree_height l = dTree_height r)
    then helper (dTree_is_perfect l) (dTree_is_perfect r)
    else false


let rec dTree_map f g t =
  match t with
  Empty -> Leaf(0)
  |Leaf(i) -> Leaf(g i)
  |Node(x, lt, rt) -> Node(f x, dTree_map f g lt, dTree_map f g rt)


let rec list_to_tree l =
  match l with
  | [] -> Leaf(0)
  | x::xs -> Node(x, list_to_tree xs, list_to_tree xs)


let rec change x i d = match x, d with
  | [], Leaf(j) -> Leaf(i)
  | x::xs, Node(c, lt, rt) ->
    if x=0 then Node(c, change xs i lt, rt) else Node(c, lt, change xs i rt)


let rec replace_leaf_at tree d =
  match tree with
  | [] -> d
  | (y, i)::xs -> replace_leaf_at xs (change y i d)


let bf_to_dTree pair = replace_leaf_at (snd pair) (list_to_tree (fst pair))
