//Thread Safety
//The below code is thread safe because it guarantees that no unexpected results would be produced i.e. no
//race conditions or data corruptions would be there.
class SharedResource{
    int data;
    boolean hasData;

    // thread communication methods wait(), notify(), notifyAll() are only valid for
    // synchronized blocks [can only be called within a synchronized context]
    public synchronized void produceData(int data){
        // if data is present notify producer threads to wait
        // until data is no longer available
        while(hasData){
            try {
                // the producer thread releases the monitor lock if data is present i.e. it is not consumed yet
                // and goes into waiting state
                // which prevents repetitive while checking
                // until another consumer thread notifies it to wake up
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " produced data : " + data);
        this.data = data;
        hasData = true;
        notify();
    }

    public synchronized void consumeData(){
        while(!hasData){
            try {
//                if data is not present, then pause/sleep the thread
//                if a producer produced and called notify then thread wakes up and resumes its execution
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        notify();
        System.out.println(Thread.currentThread().getName() + " consumed data : " + data);
    }
}


public class ThreadCommunication {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Runnable ProducerTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    resource.produceData(i);
                }
            }
        };

        Runnable ConsumerTask = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    resource.consumeData();
                }
            }
        };

        Thread producer = new Thread(ProducerTask, "Producer");
        Thread consumer = new Thread(ConsumerTask, "Consumer");

        producer.start();
        consumer.start();

    }
}
