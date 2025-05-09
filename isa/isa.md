# ISA

Let's call this ISA `Rune`, for no apparent reason.
I couldn't come up with better name that suits the overall Norse theme of my other projects...

Also, this instruction set is somewhat similar to JVM Bytecode, because I plan on later investigate if I could write a
backend targeting said bytecode.

## Registers

The processor has 32678 (2^16) registers, each can hold either i32, i64 or pointer (marked I, L and A in mnemonics
accordingly).

During one function all registers are bound to a certain type, conversions are allowed only via explicit instructions:

```
x5 = i2l x4
```

<details>
<summary><b>Reason</b></summary>
The major part of JVMs perform additional typechecks when loading and linking bytecode. 
Also, it's required to include stack map frames at the labels (information about types of values in stack).
The difference is, there are also floats (F) and doubles (D). Besides, as there are no explicit memory management in JVM, 
we'll reuse reference (A) type for pointers, which are actually represented with longs, pointing to specific place in 
the manually managed heap.
</details>

## Functions

Functions have fixed number of arguments, which have to be explicitly typed.
On the caller site, you select which registers hold arguments to pass.
On the callee site, all arguments are in first N registers.
Function may have return type (value), or may not.
If it has return value, you may or may not use it.  
Functions can be public or private, private ones should have unique names inside file,
public ones -- across the program. Overload resolution is not supported.
You can import functions by their name and signature: name identifies function,
signature is used only while linking for verification.

## Labels

Labels mark instructions in the code.
They are used by branching commands (conditional or unconditional jumps).
Labels are local to functions and should have unique identifiers.

## Syntax

Only end-of-line comments are allowed, after `//` sign.

Identifiers follow the regex `[a-zA-Z_][a-zA-Z0-9_]*`.
(Using digits, however, is unadvisable for readability reasons. Generally `snake_case` is preferred.)
Identifiers of the form `x[0-9]+` are reserved for registers.

Functions are marked with `func` keyword, private ones have `#` before the identifier.
Argument types are comma-separated in parentheses after the identifier.
Return type is separated with colon after them:

```
func #add(I,I):I
```

The code of the function is a sequence of indented lines after its header.
Each line is either a label or an instruction.
Label is an identifier followed by a colon.

Instruction is one of the two things:

- optionally target register followed by an equal sign,
  then a command and its parameters. Parameter is either identifier, register, int number or long number.
  Int number is binary (`0b[01']+`), decimal (`[0-9']+`), hexadecimal (`0x[0-9a-fA-F']+`) number or a number in
  arbitrary
  base from 2 to 36 (`[0-9A-Za-z']_[0-9]+`). Long number is an int number followed by `L`.

- target register followed by an equal sign, then other register or a constant.

Relative indentation of parts of the code is irrelevant.

Functions can be imported using `import` keyword at the start of the file:

```
import printf(I,A,A) // File descriptor, string, arguments array, returns nothing
```

## Instructions

We mostly will the same syntax for arguments, as in function declarations.
Command are not case-sensitive.

### Arithmetic

- Conversion:
    - `i2l(I):L` -- convert by sign extension.
    - `l2i(L):I` -- convert by trimming high bits.
    - `i2a(I):A` -- if pointer is 32-bit, do nothing, if the pointer is 64-bit, convert by zero-extension.
    - `a2i(A):I` -- if pointer is 32-bit, do nothing, if the pointer is 64-bit, convert by trimming high bits.
    - `l2a(L):A` -- if pointer is 32-bit, convert by trimming high bits, if the pointer is 64-bit, do nothing.
    - `a2l(A):L` -- if pointer is 32-bit, convert by zero extension, if the pointer is 64-bit, do nothing.

- Addition:
    - `iadd(I,I):I`
    - `ladd(L,L):L`
    - `aiadd(A,I):A`
    - `aladd(A,L):A`

- Subtraction:
    - `isub(I,I):I`
    - `lsub(L,L):L`
    - `aisub(A,I):A`
    - `alsub(A,L):A`
    - `aasub(A,A):L`

- Negation:
    - `ineg(I):I`
    - `lneg(L):L`

- Multiplication:
    - `imul(I,I):I`
    - `lmul(L,L):L`

- Division:
    - `idiv(I,I):I`
    - `irem(I,I):I`
    - `ldiv(L,L):L`
    - `lrem(L,L):L`
    
- Comparison (note that there are no separate type for booleans):
    - `il(I,I):I` 
    - `ile(I,I):I`
    - `ig(I,I):I`
    - `ige(I,I):I`
    - `ieq(I,I):I`
    - `ineq(I,I):I`
    - ... etc. for `L` and `A`

All the arithmetic follow 2's complement scheme. Overflow of pointers is undefined behaviour, as pointer could be either
32-bit or 64-bit.

### Memory access

- `alloc(L):A` -- allocate said amount of bytes. The argument is treated as an _unsigned_ long, although it's unlikely
  to allow you to allocate so many bytes it becomes important.
- `free(A)` -- free memory, previously allocated by `alloc`.
  Attempts to free any other memory, or to free the same memory twice, will lead to an error.
- Storing:
    - `bastore(A,I)` -- store lower byte of an int.
    - `castore(A,I)` -- store two lower bytes of an int.
    - `iastore(A,I)` -- store an int.
    - `lastore(A,L)` -- store a long.
    - `aastore(A,A)` -- store a pointer.
- Loading:
    - `baload(A):I` -- load lower byte of an int with sign extension.
    - `caload(A):I` -- load two lower bytes of an int with sign extension.
    - `iaload(A):I` -- load an int.
    - `laload(A):L` -- load a long.
    - `aaload(A):A` -- store a pointer.

### Control flow

- `goto` -- unconditional jump.
- Conditional jumps:
    - If the argument is zero: `ifiz(I, <label>)`, `iflz(L, <label>)`, `ifaz(A, <label>)`.
    - If the argument is not zero: `ifinz(I, <label>)`, `iflnz(L, <label>)`, `ifanz(A, <label>)`.
      ... That's all for now.
- Return a value from function:
    - `iret`, if the return type of the function is I.
    - `lret`, if the return type of the function is L.
    - `aret`, if the return type of the function is A.
    - `ret`, if the function returns nothing.
- `call` -- Call a function. It takes a function name and signature as its first argument,
  and then the corresponding amount of arguments passed to a function:
  ```
  x38 = call add(I,I):I x36 x37
  ```
  You can add `#` to signify the local function call, but it's not necessary and is not checked.
  It is, of course, forbidden to assign the return value to any register, if the function returns nothing.

### Special instructions

Here's some instructions for having fun.

- `ilookupN`, `llookupN`, `alookupN` -- Table lookup.
  It takes one argument (I), and then N arguments of the respective type `T`, and returns `T`.
  It is equivalent to:
  ```
  TlookupN(I case, T opt1, T opt2, ..., T optN) -> T {
      if (case == 0) return opt1
      else if (case == 1) return opt2
      ...
      else if (case == N - 2) return opt(N-1)
      else return optN
  } 
  ```

- `iinrng(I,I,I):I`, `linrng(L,L,L):I`, `ainrng(A,A,A):I` -- checks if the first argument is in range of the second and
  the third.
  It is equivalent to:
  ```
  Tinrng(T x, T l, T r) -> I {
      if (l <= x && x <= r) return 1
      else return 0
  } 
  ```

Why? Because they seem to be quite useful in my application...

## Dynamic loading (if I have time)

Let's consider it an extension of the language. We now allow function name be an argument too, it automatically converts to a pointer value upon compilation. Also, we should add another command:

`dyncall(A, <signature>, ...)` -- dynamic call of the function by pointer, treating it as if it has said signature. 

## Example 

With all that...

```
func #square(I):L
    x1 = i2l x0               // Convert the first parameter to long
    x1 = lmul x1 x1           // Reassignment is fine
    lret x1
    
func #sumOf(A,I,A):L          // Int array with size, then function of signature (I):L
    x3 = 0L                   // Sum
    x4 = aiadd x0 x1          // End index
  check:
    x5 = age x0 x4            // If start is greater or equal to end, 
    jne x5 end_cycle          // then jump to the end
    x6 = iaload x0            // load an int
    x7 = dyncall x2 (I):L x6  // process a value
    x3 = iadd x7 x3           // accumulate
    x0 = aiadd x0 4           // 4 is the size of an int.
    jmp check    
  end_cycle:
    lret x3    
 
func main(I,A) 
    x2 = alloc 20             // new int[5], so to speak
    iastore x2 1
    x2 = aiadd x2 4
    iastore x2 2
    x2 = aiadd x2 4
    iastore x2 3
    x2 = aiadd x2 4
    iastore x2 4
    x2 = aiadd x2 4
    iastore x2 5
    x2 = aisub x2 16
    
    x3 = call sumOf(A,I,A):L x2 5 square
```