package ExplicitLocks;

public class BankAccount {
    private int balance = 100;

//    Any thread invoking this function will wait
//    until another thread which entered before completes its execution
//    Any coming thread will wait indefinitely until implicit lock is released
//    But this is a problem, as we don't have fine-grained control for threads
//    wanting to execute this method
    public synchronized void withdrawAmount(int amount) {
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " attempting to Withdraw " + amount);
            try {
                // assuming an intermediate processing step
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println();
            }
            balance -= amount;
        }
        else {
            System.out.println(Thread.currentThread().getName()+" : Insufficient Balance to Withdraw");
        }

    }
}
