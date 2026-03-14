

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

//        Using Lambda Function with a functional interface type
//        because there is a single argument in the method, we need not use () to write arguments

//        Student student = name -> {
//           return name + " is a boy";
//        };

        // Also, since there is a single statement in function definition, we can remove curly braces and return statement
        Student student = name -> name + " is a boy";

        System.out.println(student.getNameDesc("RAM"));

        // More than one argument, so needed to wrap them in circular brackets
        Person person =  (name,age) -> {
            System.out.println("Name: "+ name + " | Age: " + age);
        };

        person.printNameAge("Shubh",26);

    }
}

interface Student{
    String getNameDesc(String name);
}

interface Person{
    void printNameAge(String name, int age);
}


