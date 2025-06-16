let double = fn l => {
    match l {
        nil -> nil
      | x::y -> (x * 2)::(double(y))
    }
};
let l = 1::2::3::nil;
double(l);;

let even = fn n => { (n/2)*2 == n };
let filter = fn l => {
    match l {
        nil -> nil
      | x::y -> if (even(x)) { x::(filter(y)) } else { filter(y) }
    }
};
let l = 1::2::3::4::nil;
filter(l);;

let l = 1:?2:?4:?3::6::nil;
match l {
    nil -> println(nil)
|   x::y -> println(x); println(y)
};;

let fibo = fn a, b => { a :? (fibo (b) (a+b)) };
let fibogen = fibo (1) (1);
let count = box ( 15 ) ;
let lv = box( fibogen );
while (!count != 0) {
     match (!lv) {
        nil -> println (0)
      | v :: tail -> println (v); lv := tail
     };
     count := !count - 1
};;        
