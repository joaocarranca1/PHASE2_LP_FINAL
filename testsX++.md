/basic type checking/
let a = 5;
let b = false;
(a-3*a);;

let a = 7;
let b = true;
(a<10 || b);;

/* fails */
let a = 5;
let b = true;
(a<10 && a<b);;

/recursion/ 
let loop = box (fn x:int => {x});
let fib = fn n:int => {
      if (n<=1) { n }
        else { ((!loop)(n-1)) + ((!loop)(n-2)) }};
loop := fib;
fib (7)
;;

/* struct(s) */
{};;

{ #key=42, #value = "hello"};;

{ #key=42, #value = "hello"}.#key;;

{ #key=42, #value = "hello"}.#value;;

/* fails */
{ #key=42, #value = "hello"}.#missing;;

type Point = struct{#x:int, #y: int};
let p0 = {#x = 3, #y = 4};
p0.#x * p0.#x + p0.#y * p0.#y;;

type Point = struct{ #x:int, #y: int };
let p0:Point = {#x = 3, #y = 4};
p0.#x * p0.#y;;

/* subtyping -- succeeds */
type Point = struct{ #x:int, #y: int };
let p0:Point = {#x = 3, #y = 4, #z = 5};
p0.#x + p0.#y;;

/* subtyping -- fails */
type Point = struct{ #x:int, #y: int };
let p0:Point = {#x = 3};
p0.#x + p0.#y;;

/* mutable structs, redux */
type Counter = struct { #value: ref<int>, #step:ref<int>};
let c1 = { #value = box(0), #step = box(1)};
c1.#value := !(c1.#value) + !(c1.#step);
c1.#step := !(c1.#step) * 2;
!(c1.#value) * !(c1.#step)
;; 

/* unions */
type Result = union { #error:(), #success:int};
let r0:Result = #error(());
r0;;

type Result = union { #error:(), #success:int};
let r0:Result = #success(100);
r0;;

type Result = union { #error:(), #success:int};
let r0:Result = #error(());
match r0 {
    #error(_) -> 0
|   #success(i) -> i    
};;

type Result = union { #error:(), #success:int};
let r0:Result = #success(100);
match r0 {
    #error(_) -> 0
|   #success(i) -> i    
};;

type Result = union { #error:(), #success:int};
let r0:Result = #success(100);
match r0 {
    #error(_) -> 0
|   #success(i) -> i  
|   #unknown(_) -> true * false   /* dead code */
};;

/* fails */
type Result = union { #error:(), #success:int};
let r0:Result = #success(100);
match r0 {
    #error(_) -> 0
|   #success(i) -> (i && false)  
};;

/* fails */
type Result = union { #error:(), #success:int};
let r0:Result = #success(100);
match r0 {
    #error(_) -> 0
|   #success(i) -> (i == true)  
};;

/* fails */
type Result = union { #error:(), #success:int};
let r0:Result = #success(100);
match r0 {
    #error(_) -> 0
};;

/* fails */
type Result = union { #error:(), #success:int};
let r0:Result = #error(());
match r0 {
    #success(x) -> x + 1
};;

/* higher-order store */
type f2f = int->int;
type reff2f = ref<f2f>;
let handler:reff2f = box ( fn x:int => {x * x} );
let setHandler = fn h:f2f => { handler := h };
let compute = fn a:int,b:int => {
      if (b < 0) { (!handler) (a) }
        else { a + b }
};
setHandler (fn v:int => { v * -1 });
compute (10) (-5)
;;
