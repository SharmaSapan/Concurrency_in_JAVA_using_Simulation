package com.company;

class B {
    int d_time_low;
    int d_time_high;
    int g_time;
    int w_time;

    public B() {
        d_time_low = 1000; // lower limit of delivery time
        d_time_high = 3000; // upper limit of delivery time
        g_time = 1500; // gas fill time
        w_time = 2100; // warehouse load time
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
        return "B";
    }
}