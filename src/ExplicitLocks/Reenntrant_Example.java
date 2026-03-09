package ExplicitLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Reenntrant_Example {
//    Reentrant lock allows the same thread to acquire the lock multiple times
//    using a counter. It allows re-entry/relocking to the same thread thereby having
//    the name reentrant lock

    private final Lock lock = new ReentrantLock();

    public void outerMethod(){
//        lock.lockInterruptibly(); // TODO
        try {
            System.out.println("Outer Method Called and lock acquired by" + Thread.currentThread().getName());
            innerMethod();
        }
        finally{
            lock.unlock();
        }
    }

    public void innerMethod(){
        // if this wouldn't have been a reentrant lock
//        then same thread would have tried to lock an object which is already locked
//        and will wait until its released
//        so outer method's thread acquired the lock and same thread goes to inner_method
//        acquire it again and would wait indefinitely because of lock already in use
//        which would have resulted in a deadlock and both methods stuck.
        lock.lock();
        try {
            System.out.println("Inner Method Called and lock acquired by" + Thread.currentThread().getName());
        }
        finally{
            lock.unlock();
            // here the lock is unlocked two times after it was acquired twice by the same thread
            // then outer method again tries to unlock it, which is now fully unlocked
//            thus an exception would be thrown
//            lock.unlock();
//            Exception below
//            Exception in thread "main" java.lang.IllegalMonitorStateException
//            at java.base/java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:176)
//            at java.base/java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1099)
//            at java.base/java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:495)
//            at ExplicitLocks.Reenntrant_Example.outerMethod(Reenntrant_Example.java:20)
//            at ExplicitLocks.ExplicitLocks.main(ExplicitLocks.java:24)
        }
    }
}
