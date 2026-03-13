package ExplicitLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Read_WriteLock {
    //    Allows multiple threads to read resources as long as no thread is writing to it
    //    Makes sure the values read are consistent across threads
//    if a thread is writing then ensures read is locked
    private static final ReadWriteLock lock = new ReentrantReadWriteLock(true); // using a fair lock to ensure uniformity in output[not necessarily feasible always]
//    communication happens between read and write locks is present
//    to ensure reads are consistent
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();
    private static int readWriteCounter = 0;

    static void incrementCounter() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() +" attempting to increment counter with value: " + readWriteCounter);
            readWriteCounter++;
        }
        catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + "interrupted");
            Thread.currentThread().interrupt();
        }
        finally {
            writeLock.unlock();
        }
    }

    static void getCounter() {
//        multiple locks can acquire the read lock at the same time given that write lock
//        is not acquired by any thread
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() +" reads Counter's current Value: " + readWriteCounter);
        }
        catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + "interrupted");
            Thread.currentThread().interrupt();
        }
        finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                // Read
                for (int i = 1; i < 10; i++) getCounter();
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 10; i++) incrementCounter();
            }
        };
        Thread writeThread = new Thread(writeTask,"Write-Thread");
        Thread readThread1 = new Thread(readTask,"Read-Thread-1");
        Thread readThread2 = new Thread(readTask,"Read-Thread-2");

        writeThread.start();
        readThread1.start();
        readThread2.start();

//        main threads wait for all threads to complete
        try {
            writeThread.join();
            readThread1.join();
            readThread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        getCounter();

    }

}
