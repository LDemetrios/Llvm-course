func #square(I):L                // arg -> Ix0
    Lx0 = i2l Ix0                // Convert the first parameter to long
    Lx0 = lmul Lx0 Lx0           // Reassignment is fine
    lret Lx0

func #sum_of(L,I,L):L            // array -> Lx0, size -> Lx0, transform -> Lx1
    Lx2 = 0L                     // Sum
    Lx4 = i2l Ix0
    Lx4 = lmul Lx4 4L            // 4 is the size of an int
    Lx3 = ladd Lx0 Lx4           // End index
  check:
    Ix1 = lge Lx0 Lx3            // If start is greater or equal to end,
    ifinz Ix1 end_cycle           // then jump to the end
    Ix2 = iaload Lx0 0           // load an int
    Lx5 = dyncall Lx1 (I):L Ix2  // process a value
    Lx2 = ladd Lx2 Lx5           // accumulate
    Lx0 = ladd Lx0 4L            // 4 is the size of an int
    goto check
  end_cycle:
    lret Lx2

func main(I,L):I                  // argc -> Ix0, argv -> Lx0
    brpoint
    Lx1 = alloc 20L               // new int[5], so to speak
    iastore Lx1 0 1
    iastore Lx1 4 2
    iastore Lx1 8 3
    iastore Lx1 12 4
    iastore Lx1 16 5

    Ix2 = 5
    Lx3 = &square
    Lx0 = call sum_of (L,I,L):L Lx1 Ix2 Lx3
    free Lx1
    brpoint
    Ix3 = 0
    iret Ix3
