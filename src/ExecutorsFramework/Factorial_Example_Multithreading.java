package ExecutorsFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Factorial_Example_Multithreading {

    public static void main(String[] args) {
        printFactorialThreadPool();
//        printFactorialSingleThreaded();
//        printFactorialMultiThreaded();
    }
    public static void printFactorialThreadPool(){
        System.out.println("MULTI THREADED FACTORIAL USING EXECUTORS FRAMEWORK");
//        Initialise a thread pool of 9 threads
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        long startTime = System.currentTimeMillis();
//        for storing threads
        List<Thread> threads = new ArrayList<>();

        for(int num = 1; num < 10; num++){
            int finalNum = num;
            // throws error if num is directly used in lambd
            // because num can change, and variable used in lambda expression should be final

            // submit takes in a runnable, which is passed as a lambda
            executorService.submit(() ->{
                System.out.println("Num: " + finalNum + " | Factorial: " + factorial(finalNum));
            });
        }
        // used to close threads when the work is fully completed, if this is not used
        // then the threads remain active and application keeps running(needed to manually stop)
        // and usable for future tasks
        executorService.shutdown();
        // after shutdown new tasks cannot be submitted to the thread pool
        // runtime exception

        // moreover main thread does not wait for executor to shutdown, it executes by itself
        long endTime = System.currentTimeMillis();
//        Output
//        Time Taken: 1
//############################
//        Num: 2 | Factorial: 2
//        Num: 3 | Factorial: 6

        // we want all tasks to complete before running the below statement which can be achieved using awaitTermination
        // always used after a shutdown method invokation
        try {
            // waits for maximum of 100 seconds(timeout) for all tasks to complete, or current thread interruption and then resumes
//            executorService.awaitTermination(100, TimeUnit.SECONDS);

            // Unlimited Waiting for tasks to finish(not recommended)
            while(!executorService.awaitTermination(300,TimeUnit.MILLISECONDS)){
                System.out.println("WAITING FOR ALL THREADS TO COMPLETE");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Time Taken: " + (endTime - startTime));
        System.out.println("############################");
    }

    public static void printFactorialSingleThreaded(){
        System.out.println("SINGLE THREADED FACTORIAL");
        long startTime = System.currentTimeMillis(); // milliseconds elapsed from 01 JAN 1970 00:00 UTC
        for(int num = 1; num < 10; num++){
            System.out.println("Num: " + num + " | Factorial: " + factorial(num));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Time Taken: " + (endTime - startTime));
        System.out.println("########################");
    }

    public static void printFactorialMultiThreaded(){

        System.out.println("MULTI THREADED FACTORIAL");
        long startTime = System.currentTimeMillis();
//        for storing threads
        List<Thread> threads = new ArrayList<>();

        for(int num = 1; num < 10; num++){
            int finalNum = num;
            // throws error if num is directly used in lambda
            // because num can change, and variable used in lambda expression should be final
            Thread thread = new Thread(() ->{
                System.out.println("Num: " + finalNum + " | Factorial: " + factorial(finalNum));
            });
            threads.add(thread);
            thread.start();
//            Incorrect: will work as single threaded, because main thread waits for current thread to complete
//            and then resumes the loop
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
//        Important step: main thread needs to wait until all threads have finished
//        if this is not there then we directly print the time before even calculating the factorial of all numbers
//        Manually managing threads
        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken: " + (endTime - startTime));
        System.out.println("############################");
    }

    public static int factorial(int n) {
        // adding sleep to show heavy computation
        // this will help us analyse the results between single threaded and multithreaded version
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int result = 1;
        for (int i = 1; i <= n; i++ )
            result *= i;
        return result;
    }
}
