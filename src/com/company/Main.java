package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        // read file
        ArrayList<String> tasks = new ArrayList<>(); // to store each line in input file
        try {
            File input = new File("Input1.txt");
            Scanner reader = new Scanner(input);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (!data.isBlank()) { // if a line is blank skip it
                    tasks.add(data);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // executes and manages 5 threads in pool
        ExecutorService executor = Executors.newFixedThreadPool(5);
        int ctime = (int)System.currentTimeMillis(); // initial time before program run
        // it will be fifo since we are giving the first five drivers to execute tasks,
        // when the first five finishes, the thread executes the next array, if there is a wait time it sleeps
        for(int i = 0; i<tasks.size(); i++){
            // worker threads initialization and passing only one driver's task
            Runnable worker = new WorkerThread(tasks.get(i), ctime);
            executor.execute(worker);
        }

        executor.shutdown();
        while(!executor.isTerminated()){} // wait until all threads return
        System.out.println("All Drivers finished");

    }

}
