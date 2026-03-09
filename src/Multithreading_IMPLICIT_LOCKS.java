class Counter{
    private int count  = 0;

//    making the function synchronized to make sure only one thread can invoke the function
//    at any given time. synchronized offers mutual exclusion to threads
//    Actual Output: 2000
    public synchronized void increment(){
        count++;
    }
//    another method showing block synchronization instead of method synchronization
    public  void increment(String name){
        // tells that the counter object[shared resource] is locked by one object
//        at any given time making sure that counter is incremented properly
        synchronized (this){
            count++;
        }
    }
    public int getCount(){
        return count;
    }
}

class MyThread extends Thread{
    Counter counter;

    public MyThread(Counter counter){
        this.counter = counter;
    }

    public void run(){
        for(int i = 0; i < 1000; i++){
//            counter.increment();
            counter.increment("BLOCK SYNCHRONIZATION");
        }

    }
}


public class Multithreading_IMPLICIT_LOCKS {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        // 2 threads incrementing the same counter object
        // both threads use the same resource
        Thread t1 = new MyThread(counter);
        t1.start();

        Thread t2 = new MyThread(counter);
        t2.start();

        t1.join();
        t2.join();
        //    Ideally the value should be 2000
//    as both threads run 1000 times each
        System.out.println(counter.getCount());
//        Actual Output: 1661
//        There are some cases when both threads read the same value and increment it at the same time
//        which only increments it by one. but actually it should have been incremented by 2 leaving the
//        counter inconsistent. this is called a race condition.
//        a condition when a shared resource state becomes inconsistent due to concurrent access
//        without proper coordination
    }

}