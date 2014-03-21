package ch13string;//: strings/ScannerRead.java

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Scanner;

public class ScannerRead {
    /**
     * 用Scanner扫描输入
     */
    public void betterRead() {
        Scanner stdin = new Scanner(new BufferedReader(
                new StringReader("Sir Robin of Camelot\n22 1.6.1803")));
        System.out.println("What is your name?");
        String name = stdin.nextLine();
        System.out.println(name);
        System.out.println("How old are you? What is your favorite double?");
        System.out.println("(input: <age> <double>)");
        int age = stdin.nextInt();
        double favorite = stdin.nextDouble();
        System.out.println(age);
        System.out.println(favorite);
        System.out.format("Hi %s.\n", name);
        System.out.format("In 5 years you will be %d.\n", age + 5);
        System.out.format("My favorite double is %f.", favorite / 2);
    }
} /*
 * Output: What is your name? Sir Robin of Camelot How old are you? What is your
 * favorite double? (input: <age> <double>) 22 1.61803 Hi Sir Robin of Camelot.
 * In 5 years you will be 27. My favorite double is 0.809015.
 */// :~
