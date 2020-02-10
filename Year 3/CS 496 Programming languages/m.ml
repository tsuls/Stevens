
(**
    Module m: examples of basic OCaml
*)

let rec fact (n:int) : int =
  match n with
  | 0 -> 1
  | n -> n * fact (n-1)

let rec fact2 : int -> int  = function
  | 0 -> 1
  | n -> n * fact (n-1)

let rec sumList : int list -> int = function
  | [] -> 0
  | x::tail_of_list -> x + sumList tail_of_list

let rec length : 'a list -> int = function
  | [] -> 0
  | _::xs -> 1 + length xs

let rec bump : int list -> int list = function
  | [] -> []
  | x::xs -> (x+1) :: bump xs

let rec repeat : 'a -> int -> 'a list = fun e n ->
  match n with
  | 0 -> []
  | n -> e :: repeat e (n-1)

let rec stutter : 'a list -> int -> 'a list = fun xs n ->
  match xs with
  | [] -> []
  | y::ys -> repeat y n @ stutter ys n

let rec remAdjDupl : 'a list -> 'a list = function
  | [] -> []
  | [x] -> [x]
  | x::y::xs ->
    if x=y
    then remAdjDupl (y::xs)
    else x::remAdjDupl (y::xs)

let rec append : 'a list -> 'a list -> 'a list =
  fun xs ys ->
    match xs with
    | [] -> ys
    | x::xs -> x :: append xs ys

(** reverse - naive O(n^2) *) 
let rec reverse : 'a list -> 'a list = function
  | [] -> []
  | x::xs -> reverse xs @ [x]

(** fast-reverse O(n) *)                          
let rec freverse : 'a list -> 'a list -> 'a list =
  fun xs acc ->
    match xs with
    | [] -> acc
    | y::ys -> freverse ys (y::acc)

(* ******************************** *)
(* Higher-Order Programming Schemes *)
(* ******************************** *)


(* map *)
                 
let rec succl : int list -> int list = function
  | [] -> []
  | x::xs -> (x+1) :: succl xs

let rec upperl : char list -> char list = function
  | [] -> []
  | c::cs -> Char.uppercase_ascii c :: upperl cs

let rec zerol : int list -> bool list = function
  | [] -> []
  | x::xs -> (x=0) :: zerol xs

let rec map : ('a -> 'b) -> 'a list -> 'b list =
  fun f xs ->
    match xs with
    | [] -> []
    | y::ys -> f y :: map f ys


(* filter *)

let rec gtz = function
  | [] -> []
  | x::xs ->
    if x>0
    then x:: gtz xs
    else gtz xs

let rec upc = function
  | [] -> []
  | c::cs ->
    if Char.uppercase_ascii c=c
    then c:: upc cs
    else upc cs

let rec nzl = function
  | [] -> []
  | x::xs ->
    if x!=[]
    then x :: nzl xs
    else nzl xs

let rec filter : ('a -> bool) -> 'a list -> 'a list  =
  fun p xs ->
    match xs with
    | [] -> []
    | y::ys ->
      if p y
      then y :: filter p ys
      else filter p ys
