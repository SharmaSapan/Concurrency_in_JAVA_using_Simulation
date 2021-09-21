package com.company;


import java.util.concurrent.Semaphore;

class WorkerThread implements Runnable {

    public String driver;
    int ctime;
    // gas semaphore with only two permits at a time, and with first in first out guarantee
    final Semaphore gas = new Semaphore(2, true);
    final Semaphore warehouse = new Semaphore(1, true);
    final Semaphore output = new Semaphore(1, true);

    // object for each driver class
    P driver_p = new P();
    B driver_b = new B();
    L driver_l = new L();

    public WorkerThread(String s, int ctime) {
        this.driver = s;
        this.ctime = ctime;
    }

    public void run() {

        String[] task = driver.split(",");
        String[] time = task[1].split("-"); // split again at index 1 to get time and first task
        int start_time = Integer.parseInt(time[0]); // wait time
        int current_time = (int)System.currentTimeMillis()-ctime; // system time - program start time to get current time
        if(start_time - current_time >= 0){ // if positive then this block sleeps the thread for that time
            try {
                Thread.currentThread().sleep(start_time-current_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            output.acquire(); // gets the semaphore permit to print to ensure mutual exclusion
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(task[0]+Thread.currentThread().getId()+" is starting");
        output.release(); // releases the semaphore permit


        // start of 1st task from time[1], checks if task is d,g or w
        if (time[1].equalsIgnoreCase("d")) {
            try {
                delivery(task[0]); // does the delivery task for driver from the file for this thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (time[1].equalsIgnoreCase("g")) {
            try {
                fill_gas(task[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (time[1].equalsIgnoreCase("w")) {
            try {
                load_ware(task[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // end of time[1] block

        // iterating over tasks for this thread
        for (int j=0; j<task.length; j++){

            // start of tasks starting from 2nd task, by checking if it matches d, g, or w task
            if (task[j].equalsIgnoreCase("d")) {
                try {
                    delivery(task[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (task[j].equalsIgnoreCase("g")) {
                try {
                    fill_gas(task[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (task[j].equalsIgnoreCase("w")) {
                try {
                    load_ware(task[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            output.acquire(); // gets the semaphore permit to print, to ensure mutual exclusion
            System.out.println(task[0]+Thread.currentThread().getId()+" has finished");
            output.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void delivery(String first_char) throws InterruptedException {

        if (first_char.equalsIgnoreCase("P")) { // if the driver is P
            output.acquire(); // gets the semaphore permit to print to ensure mutual exclusion
            System.out.println(first_char+Thread.currentThread().getId()+" is making delivery");
            output.release();
            Thread.sleep(driver_p.get_d_time()); // sleep the driver using P class attribute
        }

        if (first_char.equalsIgnoreCase("B")) { // if the driver is B
            output.acquire();
            System.out.println(first_char+Thread.currentThread().getId()+" is making delivery");
            output.release();
            Thread.sleep(driver_b.get_d_time());
        }


        if (first_char.equalsIgnoreCase("L")) {
            output.acquire();
            System.out.println(first_char+Thread.currentThread().getId()+" is making delivery");
            output.release();
            Thread.sleep(driver_l.get_d_time());
        }

    }

    public void fill_gas(String first_char) throws InterruptedException {

        if (first_char.equalsIgnoreCase("P")) { // if the driver is P
            gas.acquire(); // gets one of semaphore permit to fill gas to ensure mutual exclusion, if none available,
            // it waits to acquire until some thread releases it
            output.acquire();  // acquires print permit when the gas permit is available
            System.out.println(first_char+""+Thread.currentThread().getId()+" is filling gas");
            output.release();
            Thread.sleep(driver_p.get_g_time());
            gas.release();
        }

        if (first_char.equalsIgnoreCase("B")) {
            gas.acquire();
            output.acquire();
            System.out.println(first_char+""+Thread.currentThread().getId()+" is filling gas");
            output.release();
            Thread.sleep(driver_b.get_g_time());
            gas.release();
        }

        if (first_char.equalsIgnoreCase("L")) {
            gas.acquire(); // gets one of semaphore permit to fill gas to ensure mutual exclusion, if none available,
            // it waits to acquire until some thread releases it
            output.acquire();
            System.out.println(first_char+""+Thread.currentThread().getId()+" is filling gas");
            output.release();
            Thread.sleep(driver_l.get_g_time());
            gas.release();
        }

    }


    public void load_ware(String first_char) throws InterruptedException {

        if (first_char.equalsIgnoreCase("P")) {

            warehouse.acquire(); // gets one of semaphore permit to load warehouse to ensure mutual exclusion,
            // if none available, it waits to acquire until some thread releases it
            output.acquire(); // acquires print permit when the warehouse permit is available
            System.out.println(first_char+""+Thread.currentThread().getId()+" is loading at warehouse");
            output.release();
            Thread.sleep(driver_p.get_w_time());
            warehouse.release(); // releases the permit so other thread can use it
        }

        if (first_char.equalsIgnoreCase("B")) {

            warehouse.acquire();
            output.acquire();
            System.out.println(first_char+""+Thread.currentThread().getId()+" is loading at warehouse");
            output.release();
            Thread.sleep(driver_b.get_w_time());
            warehouse.release();
        }

        if (first_char.equalsIgnoreCase("L")) {

            warehouse.acquire(); // gets one of semaphore permit to load warehouse to ensure mutual exclusion,
            // if none available, it waits to acquire until some thread releases it
            output.acquire(); // acquires print permit when the warehouse permit is available
            System.out.println(first_char+""+Thread.currentThread().getId()+" is loading at warehouse");
            output.release();
            Thread.sleep(driver_l.get_w_time());
            warehouse.release();
        }
    }
}
