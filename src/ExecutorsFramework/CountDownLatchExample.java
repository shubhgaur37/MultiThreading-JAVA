package ExecutorsFramework;

import java.util.concurrent.*;

public class CountDownLatchExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // submitting 3 tasks
        Future<String> future1 = executorService.submit(new DependentResource());
        Future<String> future2 = executorService.submit(new DependentResource());
        Future<String> future3 = executorService.submit(new DependentResource());

        // Block the main thread until all tasks complete
        future1.get();
        future2.get();
        future3.get();

        System.out.println("MAIN THREAD RESUMED EXECUTION AFTER TASKS COMPLETED");

        executorService.shutdown();

        // The problem with this approach is if we have a huge number of similar tasks to be completed
        // and if we want main thread to wait for their execution then we have to call
        // the get() method for each task separately

        // Better Solution: count down latch to the rescue
        System.out.println("########### BETTER APPROACH ###########");
        int numServices = 4;
        executorService = Executors.newFixedThreadPool(numServices);

        // Create a count down latch
        CountDownLatch countDownLatch = new CountDownLatch(numServices);
        // submit the task multiple times
        for (int i = 0; i < numServices ; i++){
            // shared countdown latch for different tasks
            executorService.submit(new DependentResourceWithCountDownLatch(countDownLatch));
        }

        // main thread waits until the latch has counted down to zero
        countDownLatch.await();

        System.out.println("MAIN THREAD RESUMED EXECUTION AFTER TASKS COMPLETED");

        System.out.println("########### COUNT DOWN LATCH AWAIT WITH TIMEOUT ###########");

        // Create a count down latch
        CountDownLatch countDownLatch2 = new CountDownLatch(numServices);
        // submit the task multiple times
        for (int i = 0; i < numServices ; i++){
            // shared countdown latch for different tasks
            executorService.submit(new DependentResourceWithCountDownLatch(countDownLatch2));
        }

        // count down latch with timeout : BLOCKING CALL
        countDownLatch.await(1,TimeUnit.SECONDS);
        System.out.println("MAIN THREAD RESUMED EXECUTION AFTER TIMEOUT");

        // threads are still running the task, so their output will be printed
        executorService.shutdown();
        Thread.sleep(100);
        // stop running threads immediately
//        executorService.shutdownNow();
    }
}

class DependentResourceWithCountDownLatch implements Callable<String>{
    private final CountDownLatch countDownLatch;

    public DependentResourceWithCountDownLatch(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    @Override
    public String call() throws Exception {
        // try needs to be followed by either catch or finally or both, i.e. try cannot exist alone
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " Executing Task with Countdown latch");
            // even if we return then also finally block runs
            return "ok";
        }
        finally {
            // locking countdown latch for correct count
            // just for demonstration purposes
            synchronized (countDownLatch) {
                System.out.println("Current Count from countdown latch : " + countDownLatch.getCount());
                countDownLatch.countDown();
            }
        }

    }
}
class DependentResource implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " Executing Task");
        return "ok";
    }
}