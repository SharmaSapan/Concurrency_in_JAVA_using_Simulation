package com.company;

class L {
    int d_time_low;
    int d_time_high;
    int g_time;
    int w_time;

    public L() {
        d_time_low = 2000; // lower limit of delivery time
        d_time_high = 6500; // upper limit of delivery time
        g_time = 3000; // gas fill time
        w_time = 4000; // warehouse load time
    }

    public int get_d_time() {
        return (int)(Math.random()*(d_time_high-d_time_low))+d_time_low;
    }

    public int get_g_time() {
        return g_time;
    }

    public int get_w_time() {
        return g_time;
    }

    public String get_name() {
        return "L";
    }
}