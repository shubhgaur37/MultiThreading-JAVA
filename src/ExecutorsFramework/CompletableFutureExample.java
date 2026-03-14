package ExecutorsFramework;

// Introduced in JAVA 8 for asynchronous programming

import java.util.concurrent.*;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // We can supply any function inside supply async
        // here we are using a lambda
        // supply async runs the function in a daemon thread(background thread)
        // so it does not block the main thread
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("WORKER1");
            } catch (InterruptedException e) {

            }
            return "ok";
        });

        System.out.println("NON-BLOCKING : MAIN THREAD");
        System.out.println("##########################");

        // function to check for result without blocking the main thread
        // returns the result if available, else returns the passed value
        // needs exception handling as well
        System.out.println(f1.getNow("NOT YET COMPUTED"));
        System.out.println("##########################");
        // if we want main thread to wait until completable future thread completes
        // execution, we can use get() method to wait until, needs exception handling
        // returns the result if successful, otherwise throws exception
        String s = f1.get();
        System.out.println("COMPLETABLE FUTURE RESULT: " + s);
        System.out.println("BLOCKED BY COMPLETABLE FUTURE(DAEMON THREAD) : MAIN THREAD");

        System.out.println("##########################");

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(3000);
                System.out.println("WORKER2");
            } catch (InterruptedException e) {
            }
          return "ok";
        });
        System.out.println("########## JOIN TWO COMPLETABLE FUTURES ################");
// returns a new CompletableFuture after all the given CompletableFutures
// have completed execution. The variable is initialized immediately and
// represents a future result which will complete only after all tasks finish.
// If any of them completes with an exception, then the resulting
// CompletableFuture also completes exceptionally with that exception as the cause.

// Output: EXCEPTION CASE
// System.out.println(CompletableFuture.allOf(f1,f2));
// java.util.concurrent.CompletableFuture@5674cd4d[Completed exceptionally: java.util.concurrent.CompletionException]

// Mainly used to indicate whether all CompletableFuture executions
// have completed or not.
        CompletableFuture<Void> f = CompletableFuture.allOf(f1,f2);

        // blocks current thread until we get the result for f
        // or combined result of f1 and f2
        // does not require exception handling
        // returns the result when complete
        System.out.println(f.join());
        System.out.println("##########################");
        // then apply use to do some operation on the result
        // not a blocking call
       CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("WORKER3");
            } catch (InterruptedException e) {
            }
            return "ok";
        }).thenApply(x->x+x);

        // blocking call
        System.out.println(f3.get());
        System.out.println("##########################");
        // non blocking call
        // throws timeout exception
        // use exceptionally to handle exceptions
        CompletableFuture<String> f4 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("WORKER4");
            } catch (InterruptedException e) {
            }
            return "ok";
        }).orTimeout(2, TimeUnit.SECONDS).exceptionally(e -> "Timeout Occured");

        // block main thread to observe timeout
        System.out.println(f4.get());

        System.out.println("MAIN RESUMED");
        System.out.println("##########################");
        // providing executor threads to run these tasks on normal
        // threads instead of daemon threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("WORKER5 - NORMAL THREAD");
            } catch (InterruptedException e) {
            }
            return "ok";
        },executorService);

        executorService.shutdown();
    }


}
