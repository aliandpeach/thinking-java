package chapter19_enums;

public class EnumsDemo {
    public static void main(String[] args) {
        AlarmPoints a = AlarmPoints.BATHROOM;
        if (a == AlarmPoints.BATHROOM) {
            System.out.println("yes");
        }
    }
}
