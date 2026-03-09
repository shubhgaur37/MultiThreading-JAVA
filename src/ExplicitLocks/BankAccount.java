package ExplicitLocks;

public class BankAccount {
    private int balance = 100;

    public void withdrawAmount(int amount) {

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
