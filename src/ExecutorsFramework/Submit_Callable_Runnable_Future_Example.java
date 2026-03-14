package ExecutorsFramework;

import java.util.List;
import java.util.concurrent.*;

public class Submit_Callable_Runnable_Future_Example {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // Important Interview Question:
        // The task passed in the executor service is a callable task because it returns a value
//        Runnable task = ()-> 45; // error because runnable run's method does not return anything

        // callable task is created using lambda when we return something
        Future<Integer> future = executorService.submit(() -> 45);

        // error if we don't pass the parameter type in callable reference, type safety due to generic
        Callable<String> task = () -> "HELLO";

//      task runs asynchronously: means main thread doesn't wait for its completion
        Future<String> future2 = executorService.submit(task);
        try {
            // get the result after task is completed,
            // blocking call : main waits until task completes and result is fetched
            System.out.println(future2.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // returns true
        if (future2.isDone()) {
            System.out.println("CALLABLE TASK IS COMPLETED");
        }
//        returns true if the submitted task is completed, interrupted, exception occurred, cancelled
//        returns true because the task was executed first and 2nd is already completed,
        if (future.isDone())
            System.out.println("Task is Completed");

        System.out.println(future.get());

        // if the task does not return anything, eg. a print task then we can use a future with a wildcard
        Future<?> future1 = executorService.submit(() -> System.out.println("HELLO"));
        if (future1.isDone())
            System.out.println("Task2 is Completed");

        // get the result after task is completed
        System.out.println(future1.get());

        System.out.println("#####################");
//        Another Usecase of submit, we can specify the result that should be returned on successful execution
//        we cannot use callable here as it does not make sense because, callable already returns a value
//        so if we use runnable and we want to return some success code or value then we explicitly specify it
        Future<String> future3 = executorService.submit(() -> System.out.println("HELLO"), "STATEMENT PRINTED");
        // get the result after runnable task is completed
        System.out.println(future3.get());

        System.out.println("#####################");
        // Get with timeout, blocking call with a timeout
        Future<Integer> future4 = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 42;
        });

        System.out.println("Future 4 is Done: " + future4.isDone());
        try {
            future4.get(700, TimeUnit.MILLISECONDS);
        } catch (TimeoutException t) {
            System.out.println("Timeout for Future 4");
        }

        System.out.println("######### CANCELLING TASKS USING FUTURE ############");
        Callable<Integer> task5 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Exception Occurred:" + e);
            }
            System.out.println("CANCELLED TASK EXECUTED");
            return 123;
        };
        Future<Integer> future5 = executorService.submit(task5);
        // cancel a task if it is running
        // if it is running then it gets cancelled and isCancelled returns true,
        // no effect on isCancelled() behavior if already completed/cancelled
        // Uncomment the next line to see the 2nd behavior
//        Thread.sleep(2000);
        future5.cancel(true);
        System.out.println("Future 5 Cancelled:" + future5.isCancelled());

        System.out.println("###### CANCEL(false) behavior ########");
        future5 = executorService.submit(task5);
//        the task thread is done sleeping and reached the print statement before being cancelled

        Thread.sleep(500);
        // if task is already running then don't interrupt it, but mark as cancelled
        future5.cancel(false);
        System.out.println("Future 5 incorrectly marked as cancelled before execution: " + future5.isCancelled());
        System.out.println(future.get()); // no exception

        System.out.println("######### SHUTTING DOWN MAIN EXECUTOR ############");
        executorService.shutdown();
        System.out.println("#####################");
        System.out.println(executorService.isShutdown());
//        isTerminated()
//        returns true if all the tasks have been completed post shutdown, may return false also
//        because we are not giving any time post running the shutdown command to evaluate termination
//        status  so for consistent results, introduce some sleep
        Thread.sleep(1);
        System.out.println(executorService.isTerminated());

        // Demonstrating InvokeAll() method from ExecutorService
        // Task Cancellation/ Rejection
        multipleTasks();

    }

    static void multipleTasks() throws Exception {
        System.out.println("#############################");
        Callable<Integer> c1 = () -> {
            Thread.sleep(1200);
            System.out.println("Running Task 1");
            return 1;
        };
        Callable<Integer> c2 = () -> {
            Thread.sleep(1200);
            System.out.println("Running Task 2");
            return 2;
        };
        Callable<Integer> c3 = () -> {
            Thread.sleep(1200);
            System.out.println("Running Task 3");
            return 3;
        };

        List<Callable<Integer>> taskList = List.of(c1, c2, c3);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

//        Execute multiple callable tasks and return a list of futures
//        Blocking call returns the main thread until all tasks are completed
        List<Future<Integer>> taskStatuses1 = executorService.invokeAll(taskList);
        for (Future<Integer> status : taskStatuses1) {
            System.out.println(status.get());
        }
        System.out.println("#####################");
        // invoke all with timeout, if task wasn't able to complete then get call will throw
//        cancellation exception
        // 2 threads running in parallel to complete the task
        // when the thread comes back to execute the remaining task, timeout is over
        // so the task was cancelled
        List<Future<Integer>> taskStatuses2 = executorService.invokeAll(taskList, 2, TimeUnit.SECONDS);
        for (Future<Integer> status : taskStatuses2) {
            System.out.println("Cancelled:" + status.isCancelled());
            //  get the result if task was completed, will throw cancellation exception
            try {
                status.get();
            } catch (CancellationException c) {
                c.printStackTrace();
            }

        }

        // shutdown the executor
        executorService.shutdown();
        System.out.println("#####################");
        // may return false, because of no sleep in between shutdown and termination check
        System.out.println("Is Executor Terminated: " + executorService.isTerminated());

//        task rejected after shutdown causing an exception
        try {
            executorService.submit(() -> "Hello");
        } catch (RejectedExecutionException r) {
            r.printStackTrace();
        }
        System.out.println("#####################");
        // Invoke Any() method
        // returns the result of one successfully completed task
        executorService = Executors.newFixedThreadPool(2);
        System.out.println("INVOKE ANY");
        System.out.println(executorService.invokeAny(taskList));
        System.out.println("WITH TIMEOUT");

        try {
            executorService.invokeAny(taskList, 100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException t) {
            // throws timeout exception before any of the task completed
            t.printStackTrace();
        }
        executorService.shutdown();
    }
}

// Difference between Runnable and Callable

//1. Runnable is Used when we don't return any value from the task,
// if we want to return some value from the task then we use callable

//2. Runnable has run() method for task, Callable has call method for task;

//3. Runnable method does not have a throws declaration while Callable has it and throws Exception
// which works to its advantage as we don't explicitly need to surround potential exception causing statements with try catch.
// But inside runnable we need to do handle exceptions.
