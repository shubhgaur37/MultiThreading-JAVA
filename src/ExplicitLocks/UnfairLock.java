package ExplicitLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnfairLock {
    private static final Lock lock = new ReentrantLock();

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

//        Order of acquiring lock is not FIFO in nature [Therefore Unfair]
//        Any thread can acquire the lock without considering which thread requested
//        the lock first
//        T2 acquired lock
//        T2 release the lock
//        T1 acquired lock
//        T1 release the lock
//        T4 acquired lock
//        T4 release the lock
//        T3 acquired lock
//        T3 release the lock
    }

}
