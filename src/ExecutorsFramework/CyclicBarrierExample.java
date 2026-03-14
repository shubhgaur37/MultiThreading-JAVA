package ExecutorsFramework;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

public class CyclicBarrierExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException  {

        // CYCLIC BARRIER:
        // A synchronization aid that allows multiple threads to wait
        // for each other to reach a common execution point (barrier)
        // before continuing execution.

        // USECASE:
        // When multiple independent tasks must complete before the system proceeds.
        // Example:
        // - Microservices initialization
        // - Parallel computations (matrix calculations)
        // - Multiplayer game round start
        // - System startup where multiple subsystems must initialize
        // - Matrix Multiplication

        // number of participating threads that must reach the barrier
        int numSubsystems = 4;

        // When all threads call await(), the barrier action runs
        // Here it signals that the system is ready
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
                numSubsystems,
                // barrier action: executes when the last thread calls awaits
                () -> System.out.println("All Subsystems are up and running!!! System Initialization Complete!!")
        );

        // Creating subsystem tasks
        Subsystem db = new Subsystem("Database",2000,cyclicBarrier);
        Subsystem cache = new Subsystem("Cache",1000,cyclicBarrier);
        Subsystem webServer = new Subsystem("WebServer",3000,cyclicBarrier);
        Subsystem messagingService = new Subsystem("Messaging Service",500,cyclicBarrier);

        // Each subsystem initializes in its own thread
        Thread initDB = new Thread(db);
        Thread initCache = new Thread(cache);
        Thread initWebServer = new Thread(webServer);
        Thread initMessagingService = new Thread(messagingService);

        initDB.start();
        initCache.start();
        initWebServer.start();
        initMessagingService.start();
    }
}

class Subsystem implements Runnable {

    // CyclicBarrier is used when we want multiple threads
    // to reach a synchronization point before proceeding further.

    private final String name;
    private final long initialisationTime;
    private final CyclicBarrier barrier;

    public Subsystem(String name, long initialisationTime, CyclicBarrier barrier) {
        this.name = name;
        this.initialisationTime = initialisationTime;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {

            // Simulating subsystem startup
            System.out.println("BOOTING UP : " + name);

            Thread.sleep(initialisationTime);

            System.out.println("INITIALISATION COMPLETE: " + name);

            // WORKING:
            // Each thread calls await() once its task is complete.
            // The thread then BLOCKS at the barrier.

            // Once all participating threads reach this point,
            // the barrier action executes and all threads are released.

            barrier.await();

            // After barrier release, threads continue execution
            // (not shown here)

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}