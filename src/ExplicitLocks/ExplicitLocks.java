package ExplicitLocks;

public class ExplicitLocks {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
//        Cannot make an object of interface
//        so we defined an anonymous class implementing the interface
//        enforced by the reference type

        Runnable task = new Runnable() {
            @Override
            public void run() {
                bankAccount.withdrawAmount(10);
            }
        };
        Thread t1 = new Thread(task,"Thread1");
        Thread t2 = new Thread(task,"Thread2");

        t1.start();
        t2.start();

        // Reentrant Example
        Reenntrant_Example reentrantExample = new Reenntrant_Example();
        reentrantExample.outerMethod();
    }
}
