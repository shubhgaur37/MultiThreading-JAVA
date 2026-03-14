package ExecutorsFramework;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // takes in a runnable and initial delay after which task needs to be executed
        // not a thread blocking call
        scheduler.schedule(() -> System.out.println("Scheduler 1 : Fixed Task Executed After 5 seconds delay"), 5, TimeUnit.SECONDS);
//        The below command ensures that scheduler:
//        1. Stops accepting new tasks
//        2. Allows already scheduled one-time tasks to run
//        3. Cancels periodic tasks that have not started

        scheduler.shutdown();

        scheduler = Executors.newScheduledThreadPool(1);

// scheduleAtFixedRate()
// Similar to a cron job that runs a periodic task at a fixed rate after an initial delay.
// The executor attempts to maintain fixed start times for each execution.
//
// nextStart = previousStart + period
//
// If a task execution takes longer than the specified period, the next execution
// starts immediately after the previous one finishes, meaning the fixed-rate
// schedule may not be strictly maintained.
//
// Periodic executions of the same task never overlap with themselves.
// Even if the scheduler has multiple threads, the same periodic task will not
// run concurrently with different execution times.
//
// However, if the thread pool contains multiple threads, different scheduled
// tasks can overlap and run concurrently as long as threads are available.
        scheduler.scheduleAtFixedRate(() -> {
                    System.out.println("Scheduler1: Task Executing at fixed 5 seconds interval");
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println("Scheduler1 : TASK EXECUTED");
                }
                , 3, 5, TimeUnit.SECONDS);

        // Calling shutdown() immediately may prevent even the first execution
        // of a periodic task because periodic tasks are suppressed if they
        // haven't started before shutdown.
        // tested with 0 delay - task didn't execute because shutdown may have been initiated before
        // the task started running
        // scheduler.shutdown();

        // Proper Handling of scheduler is required for periodic tasks
        System.out.println("Scheduler1: Shutting Down Scheduler after 20 seconds - MAIN THREAD");
        // Schedule a scheduler shutdown
        ScheduledExecutorService finalScheduler = scheduler;
        scheduler.schedule(() -> {
            System.out.println("INITIATING SHUTDOWN for first Scheduler!!!");
            finalScheduler.shutdown();
        }, 20, TimeUnit.SECONDS);

        // Creating a new Scheduler
        ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(2);

// scheduleWithFixedDelay()
// Runs a periodic task after an initial delay, ensuring a fixed delay between
// the completion of one execution and the start of the next.
//
// nextStart = previousFinish + delay
//
// The executor waits for the current execution to complete and then waits
// for the specified delay before starting the next execution.
//
// This means task frequency depends on execution time and delay,
// rather than maintaining a fixed start rate.
//
// Periodic executions of the same task never overlap with themselves.
// Even if multiple threads exist in the pool, the same task instance
// will not execute concurrently.
//
// However, different scheduled tasks can overlap and run concurrently
// if the thread pool has available threads.

        // two different tasks demonstrating parallel execution
        scheduler2.scheduleWithFixedDelay(() -> {
            System.out.println("Scheduler2 Task A running with delay of 5 seconds");
            try { Thread.sleep(4000); } catch (Exception e) {}
        }, 2, 5, TimeUnit.SECONDS);

        scheduler2.scheduleWithFixedDelay(() -> {
            System.out.println("Scheduler2 Task B running with delay of 5 seconds");
            try { Thread.sleep(4000); } catch (Exception e) {}
        }, 2, 5, TimeUnit.SECONDS);

        // Schedule a scheduler shutdown
        scheduler2.schedule(() -> {
            System.out.println("Scheduler 2 : INITIATING SHUTDOWN!!!");
            scheduler2.shutdown();
        }, 25, TimeUnit.SECONDS);
    }

}

