package ExplicitLocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int balance = 100;
    // Defining an explicit lock:
    private Lock lock = new ReentrantLock();

    //    Any thread invoking this function will wait
//    until another thread which entered before completes its execution
//    Any coming thread will wait indefinitely until implicit lock is released
//    But this is a problem, as we don't have fine-grained control for threads
//    wanting to execute this method
    public void withdrawAmount(int amount) {
//        lock.lock(); // same as synchronized, incoming thread keeps on waiting until lock is released

//        if(lock.tryLock(); // without wait time, if lock is free then returns true else false

        try {
            // another thread waits for a specified time for the lock to be released.
//            if it becomes available then returns true and another thread acquires the lock
//            else false is returned and the thread stops waiting and exits
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                if (balance >= amount) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " attempting to Withdraw " + amount);
                        // assuming an intermediate processing step
                        Thread.sleep(3000);
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " Completed Withdrawal of " + amount);
                    } catch (Exception e) {

                    } finally {
//                        lock should always be unlocked in finally block
//                        as its meant to be used for releasing resources
//                        and its ensured that this block will always run
                        lock.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " : Insufficient Balance to Withdraw");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " couldn't acquire lock. Will Try later");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
