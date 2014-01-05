package chapter18_io;

public class TryFormattedOutput {

    public static void main(String[] args) {
        // Just uncomment the fragment that you want to try and recompile...

//        int a = 5, b = 15, c = 255;
//        double x = 27.5, y = 33.75;
//        System.out.printf("x = %f y = %g", x, y);
//        System.out.printf(" a = %d b = %x c = %o", a, b, c);


//        int a = 5, b = 15, c = 255;
//        double x = 2333333337.5, y = 33.75;
//        System.out.printf("x = %2$f y = %1$g", x, y);
//        System.out.printf(" a = %3$d b = %1$x c = %2$o", a, b, c);
//        System.out.printf("%na = %3$d b = %%%<x c = %<o", a, b, c);
//
//
        int a = 5, b = 15, c = 255;
        double x = 27.5, y = 33.75;
        System.out.printf("x = %15f y = %8g", x, y);
        System.out.printf("a = %1$5d b = %2$5x c = %3$2o", a, b, c);
        System.out.printf("%na = %1$-5d b = %2$-5x c = %3$-5o", a, b, c);
//
//
//        double x = 27.5, y = 33.75;
//        System.out.printf("x = %15.2f y = %14.3f", x, y);
//
//
//        int count = 0;
//        for (int ch = 'a'; ch <= 'z'; ch++) {
//            System.out.printf("     %1$4c%1$4x", ch);
//            if (++count % 6 == 0) {
//                System.out.printf("%n");
//            }
//        }


        String str = "The quick brown fox.";
        System.out.printf("%nThe string is:%n%s%n%1$25s", str);


    }
}
