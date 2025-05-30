func #color(I):I
#define JUMP(n)                 \
    Ix1 = ieq Ix0 n            ;\
....ifinz Ix1 state ## n

    JUMP(0)
    JUMP(1)
    JUMP(2)
    JUMP(3)

#define RET_CLR(n, clr)         \
  state ## n:                  ;\
....Ix1 = clr                  ;\
....iret Ix1

  RET_CLR(4, 0xFF0000FF)
  RET_CLR(0, 0xFF777777)
  RET_CLR(1, 0xFFFF0000)
  RET_CLR(2, 0xFFFF7700)
  RET_CLR(3, 0xFFFFFF00)

func main(I,L):I
#define data Lx1
#define width 256
#define height 128
#define DATA_SIZE 32768L
#define step Ix2
#define y Ix3
#define x Ix4
#define state Ix5
#define newState Ix6
#define dy Ix7
#define dx Ix8
#define nx Ix9
#define ny Ix10
#define itmp Ix11
#define addr Ix12
#define count Ix13
    data = alloc DATA_SIZE

    y = 0
    outer_init_for:
        itmp = il y height
        ifiz itmp outer_init_for_end
        x = 0
        inner_init_for:
            itmp = il x width
            ifiz itmp inner_init_for_end
            itmp = rnd
            itmp = irem itmp 1024
            itmp = iadd itmp 1024
            itmp = irem itmp 1024
            itmp = idiv itmp 256
            addr = imul y width
            addr = iadd x addr
            bastore data addr itmp

            x = iadd x 1
            goto inner_init_for
        inner_init_for_end:
        y = iadd y 1
        goto outer_init_for
    outer_init_for_end:

    step = 0
    stepping_for:
        itmp = il step 1000000000
        ifiz itmp stepping_for_end
        y = 0
        stepping_y_for:
            itmp = il y height
            ifiz itmp stepping_y_for_end
             x = 0
             stepping_x_for:
                 itmp = il x width
                 ifiz itmp stepping_x_for_end

                 addr = imul y width
                 addr = iadd addr x
                 state = baload data addr
                 state = iand state 3

                 itmp = ieq state 2
                 ifinz itmp state2
                 itmp = ieq state 3
                 ifinz itmp state3
                 goto state01
                 state2:
                    newState = 3
                    goto end_if_state
                 state3:
                    newState = 0
                    goto end_if_state
                 state01:
                    count = 0
                    dy = -2
                    for_count_dy:
                        itmp = ile dy 2
                        ifiz itmp for_count_dy_end
                        dx = -2
                        for_count_dx:
                            itmp = ile dx 2
                            ifiz itmp for_count_dx_end
                            nx = iadd x dx
                            nx = iadd nx width
                            nx = irem nx width
                            ny = iadd y dy
                            ny = iadd ny height
                            ny = irem ny height
                            addr = imul ny width
                            addr = iadd addr nx
                            itmp = baload data addr
                            itmp = iand itmp 3
                            itmp = ieq itmp 1
                            count = iadd itmp count

                            dx = iadd dx 1
                            goto for_count_dx
                        for_count_dx_end:

                        dy = iadd dy 1
                        goto for_count_dy
                    for_count_dy_end:

                    ifiz state state0
                      // newState = 8 <= count && count <= 12 ? 1 : 2;
                      // newState = 2 - int(8 <= count && count <= 12)
                      itmp = ile 8 count
                      newState = ile count 12
                      newState = iand itmp newState
                      newState = isub 2 newState
                      goto end_if_state
                    state0:
                      // newState = 6 <= count && count <= 7 ? 1 : 0;
                      itmp = ile 6 count
                      newState = ile count 7
                      newState = iand newState itmp
                 end_if_state:
                 newState = ishl newState 2
                 addr = imul y width
                 addr = iadd x addr
                 itmp = baload data addr
                 itmp = iadd itmp newState
                 bastore data addr itmp
                 newState = ishr newState 2

                 dy = 0
                 display_dy_for:
                    itmp = il dy 2
                    ifiz itmp display_dy_for_end

                    dx = 0
                    display_dx_for:
                       itmp = il dx 2
                       ifiz itmp display_dx_for_end

                       ny = iadd y y
                       ny = iadd ny dy
                       nx = iadd x x
                       nx = iadd nx dx
                       itmp = call color (I):I newState
                       putpxl nx ny itmp

                       dx = iadd dx 1
                       goto display_dx_for
                    display_dx_for_end:

                    dy = iadd dy 1
                    goto display_dy_for
                 display_dy_for_end:

                 x = iadd x 1
                 goto stepping_x_for
             stepping_x_for_end:
            y = iadd y 1
            goto stepping_y_for
        stepping_y_for_end:

        scrflush

        y = 0
        update_y_for:
            itmp = il y height
            ifiz itmp update_y_for_end
            x = 0
            update_x_for:
                itmp = il x width
                ifiz itmp update_x_for_end

                addr = imul y width
                addr = iadd addr x
                itmp = baload data addr
                itmp = ishr itmp 2
                bastore data addr itmp

                x = iadd x 1
                goto update_x_for
            update_x_for_end:
            y = iadd y 1
            goto update_y_for
        update_y_for_end:
        goto stepping_for
    stepping_for_end:
    itmp = 0
    iret itmp
