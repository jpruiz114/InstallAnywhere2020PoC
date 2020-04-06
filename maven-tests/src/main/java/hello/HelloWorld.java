package hello;

import org.joda.time.LocalTime;

//import java.util.Arrays;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("This is the main class");

        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        System.out.println("Printing input arguments");

        //Arrays.stream(args).forEach(System.out::println);

        for (int i = 0; i < args.length; i++) {
            System.out.println("Argument # " + i + " = " + args[i]);
        }

        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());
    }
}
