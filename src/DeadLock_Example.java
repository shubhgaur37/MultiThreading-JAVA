// Cyclic Dependency between resource execution
class Resource1 {
    String name = "Resource1";
    Resource2 resource2;

    public void setResource2(Resource2 resource2) {
        this.resource2 = resource2;
    }

    public synchronized void startProcessing() {
        System.out.println(Thread.currentThread().getName() + " got " + name);
        System.out.println(Thread.currentThread().getName() + " attempting to access " + resource2.name);
        resource2.processFromResource1();
    }

    public synchronized void processFromResource2() {
        System.out.println(Thread.currentThread().getName() + " got " + name);
        System.out.println(Thread.currentThread().getName() + " finished processing " + name);
    }
}

class Resource2 {
    String name = "Resource2";
    Resource1 resource1;

    public void setResource1(Resource1 resource1) {
        this.resource1 = resource1;
    }

    public synchronized void startProcessing() {
        System.out.println(Thread.currentThread().getName() + " got " + name);
        System.out.println(Thread.currentThread().getName() + " attempting to access " + resource1.name);
        resource1.processFromResource2();
    }

    public synchronized void processFromResource1() {
        System.out.println(Thread.currentThread().getName() + " got " + name);
        System.out.println(Thread.currentThread().getName() + " finished processing " + name);
    }
}


public class DeadLock_Example {
    public static void main(String[] args) {
        Resource1 r1 = new Resource1();
        Resource2 r2 = new Resource2();
//        adding dependency between resources
        r1.setResource2(r2);
        r2.setResource1(r1);

//        2 threads accessing resources
        Runnable task1 = new Runnable() {
            public void run() {
                r1.startProcessing();
            }
        };
        // Deadlock causing scenario
//        Runnable task2 = new Runnable(){
//            public void run(){
//                r2.startProcessing();
//            }
//        };

        // Deadlock Prevention by forcing threads to acquire the resources in the same/predefined order
        Runnable task2 = new Runnable() {
            public void run() {
                r1.startProcessing();
            }
        };


        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");

        thread1.start();
        thread2.start();

    }


}
