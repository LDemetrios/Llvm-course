#include "sim.h"

int color(int state) {
    if (state == 0) {
        return 0xFF777777;
    } else if (state == 1) {
        return 0xFFFF0000;
    } else if (state == 2) {
        return 0xFFFF7700;
    } else if (state == 3) {
        return 0xFFFFFF00;
    } else {
        return 0xFF0000FF;
    }
}

const int coeff = 2;

void app() {
    char data[SIM_X_SIZE / coeff][SIM_Y_SIZE / coeff];

    for (int y = 0; y < SIM_Y_SIZE / coeff; ++y) {
        for (int x = 0; x < SIM_X_SIZE / coeff; ++x) {
            int rnd = (simRand() % 1024 + 1024) % 1024;
            data[x][y] = rnd / 256;
        }
    }
    for (int step = 0; step < 1000000000; ++step) {
        for (int y = 0; y < SIM_Y_SIZE / coeff; ++y) {
            for (int x = 0; x < SIM_X_SIZE / coeff; ++x) {
                int state = data[x][y] & 3;
                int newState;
                if (state == 2) {
                    newState = 3;
                } else if (state == 3) {
                    newState = 0;
                } else {
                    int count = 0;
                    for (int dy = -2; dy <= 2; ++dy) {
                        for (int dx = -2; dx <= 2; ++dx) {
                            int nx = (x + dx + SIM_X_SIZE) % (SIM_X_SIZE / 2);
                            int ny = (y + dy + SIM_Y_SIZE) % (SIM_X_SIZE / 2);
                            if ((data[nx][ny] & 3) == 1) {
                                ++count;
                            }
                        }
                    }
                    if (state == 0) {
                        newState = 6 <= count && count <= 7 ? 1 : 0;
                    } else {
                        newState = 8 <= count && count <= 12 ? 1 : 2;
                    }
                }
                data[x][y] += newState << 2;
                for (int dy = 0; dy < coeff; ++dy) {
                    for (int dx = 0; dx < coeff; ++dx) {
                        simPutPixel(x * coeff + dx, y * coeff + dy, color(newState));
                    }
                }
            }
        }
        simFlush();
        for (int y = 0; y < SIM_Y_SIZE / coeff; ++y) {
            for (int x = 0; x < SIM_X_SIZE / coeff; ++x) {
                data[x][y] >>= 2;
            }
        }
    }
}
