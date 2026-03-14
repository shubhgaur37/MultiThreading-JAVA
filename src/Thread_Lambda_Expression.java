public class Thread_Lambda_Expression {

    public static void main(String[] args) {
//        Lambda Runnable, because of it being a functional interface i.e. single abstract method
        Runnable task = () -> {
            System.out.println("Thread Executed using lambda runnable");
        };

        Thread myThread = new Thread(()->{
            System.out.println("Thread Executed using lambda expression");
        });

        Thread runnableThread = new Thread(task);

        myThread.start();
        runnableThread.start();
    }
}


