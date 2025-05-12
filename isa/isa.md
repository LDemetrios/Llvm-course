# ISA

Let's call this ISA `Rune`, for no apparent reason.
I couldn't come up with better name that suits the overall Norse theme of my other projects...

Also, this instruction set is somewhat similar to JVM Bytecode, because I plan on later investigate if I could write a
backend targeting said bytecode.

## Registers

The processor has 4096 registers, for each of the 6 types.

| LLVM type | Register kind name | Mnemonic |
|-----------|--------------------|----------|
| i32       | int                | I        |
| i64       | long               | L        |
| f32       | float              | F        |
| f64       | double             | D        |
| ptr       | reference          | A        |
| ptr       | function           | M        |

They are converted only explicitly via instructions

```
Lx5 = i2l Ix4
```

<details>
<summary><b>Reason</b></summary>
The major part of JVMs perform additional typechecks when loading and linking bytecode. 
Also, it's required to include stack map frames at the labels (information about types of values in stack).
 Besides, as there are no explicit memory management in JVM, 
we'll reuse reference (A) type for pointers. 
Specifics of heap management are not quite clear yet, as there are different possible ways of handling it. 
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
Identifiers of the form `[ILFDIM]x[0-9]+` are reserved for registers.

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
  then a command and its parameters. Parameter is either label, register, int number or long number, or a function
  reference.
    - Label is simply an identifier
    - Int number is binary (`[+-]?0b[01']+`), decimal (`[+-]?[0-9']+`), hexadecimal (`0x[0-9a-fA-F']+`) number or a
      number in
      arbitrary
      base from 2 to 36 (`[+-]?[0-9A-Za-z']_[0-9]+`).
    - Long number is an int number followed by `L`.
    - Function reference is an identifier prepended by `&`.

- target register followed by an equal sign, then other register or a constant.

Relative indentation of parts of the code is irrelevant.

Functions can be imported using `import` keyword at the start of the file:

```
import printf(I,A,A) // File descriptor, string, arguments array, returns nothing
```

## Instructions

We mostly will the same syntax for arguments, as in function declarations.
Command are not case-sensitive.

Floating point numbers are, for now, left outside the scope of this project.

### Arithmetic

- Conversion:
    - `i2l(I):L` -- convert by sign extension.
    - `l2i(L):I` -- convert by trimming high bits.
    - `i2a(I):A` -- if pointer is 32-bit, do nothing, if the pointer is 64-bit, convert by zero-extension.
    - `a2i(A):I` -- if pointer is 32-bit, do nothing, if the pointer is 64-bit, convert by trimming high bits.
    - `l2a(L):A` -- if pointer is 32-bit, convert by trimming high bits, if the pointer is 64-bit, do nothing.
    - `a2l(A):L` -- if pointer is 32-bit, convert by zero extension, if the pointer is 64-bit, do nothing.
    - `l2m(L):M` and `m2l(M):L` are guaranteed to be inverse to each other, and aren't guaranteed to mean anything else.

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

- Bitwise operations:
    - `iand(I,I):I`, `land(L,L):L`
    - `ior(I,I):I`, `lor(L,L):L`
    - `ixor(I,I):I`, `lxor(L,L):L`
    - `iinv(I):I`, `linv(L):L`
    - `ishl(I,I):I`, `lshl(L,I):L`
    - `ishr(I,I):I`, `lshr(L,I):L`
    - `iushr(I,I):I`, `lushr(L,I):L`

All the arithmetic follow 2's complement scheme. Overflow of pointers is undefined behaviour, as pointer could be either
32-bit or 64-bit.

### Memory access

- `alloc(L):A` -- allocate said amount of bytes. The argument is treated as an _unsigned_ long, although it's unlikely
  to allow you to allocate so many bytes it becomes important.
- `free(A)` -- free memory, previously allocated by `alloc`.
  Attempts to free any other memory, or to free the same memory twice, will lead to an error.
- Storing:
    - `bastore(A,I,I)` -- store lower byte of an int.
    - `castore(A,I,I)` -- store two lower bytes of an int.
    - `iastore(A,I,I)` -- store an int.
    - `lastore(A,I,L)` -- store a long.
    - `aastore(A,I,A)` -- store a pointer.
    - `mastore(A,I,M)` -- store a function pointer.
- Loading:
    - `baload(A,I):I` -- load lower byte of an int with sign extension.
    - `caload(A,I):I` -- load two lower bytes of an int with sign extension.
    - `iaload(A,I):I` -- load an int.
    - `laload(A,I):L` -- load a long.
    - `aaload(A,I):A` -- load a pointer.
    - `maload(A,I):M` -- load a function pointer.

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
  It is, of course, forbidden to assign the return value to any register, if the function returns nothing. Allows only
  statically resolved functions.

- `dyncall(M, <signature>, ...)` -- dynamic call of the function by pointer, treating it as if it has said signature.

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

## Example

With all that...

```
func #square(I):L                // arg -> Ix0
    Lx0 = i2l Ix0                // Convert the first parameter to long
    Lx0 = lmul Lx0 Lx0           // Reassignment is fine
    lret Lx0
    
func #sumOf(A,I,M):L             // array -> Ax0, size -> Ix0, transform -> Mx0
    Lx0 = 0L                     // Sum
    Ax1 = aiadd Ax0 Ix0          // End index
  check:
    Ix1 = age Ax0 Ax1            // If start is greater or equal to end, 
    jne Ix1 end_cycle            // then jump to the end
    Ix2 = iaload Ax0 0           // load an int
    Lx1 = dyncall Mx0 (I):L Ix2  // process a value
    Lx0 = ladd Lx0 Lx1           // accumulate
    Ax0 = aiadd Ax0 4            // 4 is the size of an int.
    goto check    
  end_cycle:
    lret Lx0    
 
func main(I,A)                   // argc -> Ix0, argv -> Ax0
    Ax1 = alloc 20               // new int[5], so to speak
    iastore Ax1 0 1
    iastore Ax1 4 2
    iastore Ax1 8 3
    iastore Ax1 12 4
    iastore Ax1 16 5
    
    Lx0 = call sumOf(A,I,M):L Ax1 5 &square
    free Ax1
```
