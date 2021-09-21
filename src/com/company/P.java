package com.company;

class P {
    int d_time_low;
    int d_time_high;
    int g_time;
    int w_time;

    public P() {
        d_time_low = 200; // lower limit of delivery time
        d_time_high = 1300; // upper limit of delivery time
        g_time = 800; // gas fill time
        w_time = 1000; // warehouse load time
    }

    public int get_d_time() {
        return (int)(Math.random()*(d_time_high-d_time_low))+d_time_low; // generate a random between upper and lower t
    }

    public int get_g_time() {
        return g_time;
    }

    public int get_w_time() {
        return g_time;
    }

    public String get_name() {
        return "P";
    }
}