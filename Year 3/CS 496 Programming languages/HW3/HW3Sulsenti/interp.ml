(*Tyler Sulsenti*)
open Ast
open Ds

let rec eval (en:env) (e:expr):exp_val =
  match e with
  | Int n           -> NumVal n
  | Var x           -> lookup en x
  | Let(x, e1, e2)  ->
    let v1 = eval en e1  in
    eval (extend_env en x v1) e2
  | IsZero(e1)      ->
    let v1 = eval en e1  in
    let n1 = numVal_to_num v1 in
    BoolVal (n1 = 0)
  | ITE(e1, e2, e3) ->
    let v1 = eval en e1  in
    let b1 = boolVal_to_bool v1 in
    if b1 then eval en e2 else eval en e3
  | Sub(e1, e2)     ->
    let v1 = eval en e1 in
    let v2 = eval en e2  in
    NumVal ((numVal_to_num v1) - (numVal_to_num v2))
    (*Adds Two numbers, returns a numval*)
  | Add(e1, e2)     ->
    let n1 = eval en e1 in
    let n2 = eval en e2  in
    NumVal ((numVal_to_num n1) + (numVal_to_num n2))
    (*Divides Two numbers, returns a numval*)
  | Div(e1, e2)     ->
    let n1 = eval en e1 in
    let n2 = eval en e2  in
    NumVal ((numVal_to_num n1) / (numVal_to_num n2))
    (*Multiplies Two numbers, returns a numval*)
  | Mul(e1, e2)     ->
    let n1 = eval en e1 in
    let n2 = eval en e2  in
    NumVal ((numVal_to_num n1) * (numVal_to_num n2))
    (*returns a numval of the absolute value*)
  | Abs(e1)         ->
     let n1 = eval en e1 in
     NumVal (abs (numVal_to_num n1))
  (*Adds to the end of the list*)
  | Cons(e1, e2)    ->
     let v1 = eval en e1 in
     let v2 = eval en e2 in
     ListVal (v1 :: listVal_to_list v2)
  (*returns the head of a list. Will fail if the list is empty*)
  | Hd(e1)          ->
     let n1 = eval en e1 in
     List.hd(listVal_to_list n1)
  (*Returns the tail end of a list. Will fail if the list is empty*)
  | Tl(e1)          ->
     let n1 = eval en e1 in
     ListVal (List.tl (listVal_to_list n1))
  (*Checks whether a list is empty or not. Supposets both lists and trees*)
  | Empty(e1)       ->
     let n1 = eval en e1 in
     (match n1 with
     |ListVal ([]) -> BoolVal true (*matches for an empty list*)
     |TreeVal (Empty) -> BoolVal true(*matches for an empty tree*)
     |_ -> BoolVal false) (*else*)
  (*Returns an Empty list*)
  | EmptyList       -> ListVal ([])
  (*Returns an Empty Tree*)
  | EmptyTree       -> TreeVal (Empty)
  (*creates a tree based upton the values given. E1 is head node, e2 and e3 are left and right subtrees*)
  | Node(e1,e2,e3)  ->
     let v1 = eval en e1 in
     let leftST = eval en e2 in
     let rightST = eval en e3 in
     TreeVal (Node(v1, (treeVal_to_tree leftST), (treeVal_to_tree rightST)))
   (*Evaluates a tree*)
  | CaseT(e1,e2,id1,id2,id3,e3) ->
     let v1 = eval en e1 in
     match treeVal_to_tree (v1) with
     | Empty -> eval en e2
     | Node(nev1,nev2,nev3) ->
        let env1 = extend_env en id1 nev1 in
        let env2 = extend_env env1 id2 (TreeVal(nev2)) in
        let env3 = extend_env env2 id3 (TreeVal(nev3)) in
        eval env3 e3 
     (*Fails if input is not a tree*)
     | _ -> failwith("e1 is not a tree!")


(***********************************************************************)
(* Everything above this is essentially the same as we saw in lecture. *)
(***********************************************************************)

(* Parse a string into an ast *)
let parse s =
  let lexbuf = Lexing.from_string s in
  let ast = Parser.prog Lexer.read lexbuf in
  ast


(* Interpret an expression *)
let interp (e:string):exp_val =
  e |> parse |> eval (empty_env ())
