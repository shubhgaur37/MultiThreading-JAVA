package ExplicitLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLock {
//    making the fair lock so that threads acquire lock in a fair manner
//    Avoids thread starvation
//    eg. 100 threads wanting to acquire lock, and one specific thread is losing
//    most of the time to other threads while trying to acquire the lock
//    here, its insured that the threads will acquire the lock in the order
//    they requested to acquire the lock
    private static final Lock lock = new ReentrantLock(true);

    public static void accessResource(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + " acquired lock");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Thread interrupted");
            Thread.currentThread().interrupt();
        }
        finally {
            System.out.println(Thread.currentThread().getName() + " release the lock");
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                accessResource();
            }
        };

        Thread thread1 = new Thread(task,"T1");
        Thread thread2 = new Thread(task,"T2");
        Thread thread3 = new Thread(task,"T3");
        Thread thread4 = new Thread(task,"T4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

//        Order of acquiring lock is FIFO in nature [fair]
//        based on which thread requested the lock first
//        doesn't depend on the order written above
//        but depends on OS-level scheduling mechanism
//        meaning whichever thread requested the lock first will acquire it and then
//        the 2nd and so on
    }

}
