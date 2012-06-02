package chapter12_exception;//: exceptions/TurnOffChecking.java
// "Turning off" Checked exceptions.

import java.io.FileNotFoundException;
import java.io.IOException;
import static net.mindview.util.Print.print;


public class TurnOffChecking {

    /**
     * 把checked异常封装成unchecked异常抛出去
     * 然后通过getCause返回原始异常信息
     */
    public void trunOffCheckException() {
        WrapCheckedException wce = new WrapCheckedException();
        // You can call throwRuntimeException() without a try
        // block, and let RuntimeExceptions leave the method:
        wce.throwRuntimeException(3);
        // Or you can choose to catch exceptions:
        for (int i = 0; i < 4; i++)
            try {
                if (i < 3)
                    wce.throwRuntimeException(i);
                else
                    throw new SomeOtherException();
            } catch (SomeOtherException e) {
                print("SomeOtherException: " + e);
            } catch (RuntimeException re) {
                try {
                    throw re.getCause();
                } catch (FileNotFoundException e) {
                    print("FileNotFoundException: " + e);
                } catch (IOException e) {
                    print("IOException: " + e);
                } catch (Throwable e) {
                    print("Throwable: " + e);
                }
            }
    }
} /*
 * Output: FileNotFoundException: java.io.FileNotFoundException IOException:
 * java.io.IOException Throwable: java.lang.RuntimeException: Where am I?
 * SomeOtherException: SomeOtherException
 */// :~


class WrapCheckedException {
    void throwRuntimeException(int type) {
        try {
            switch (type) {
                case 0:
                    throw new FileNotFoundException();
                case 1:
                    throw new IOException();
                case 2:
                    throw new RuntimeException("Where am I?");
                default:
            }
        } catch (Exception e) { // Adapt to unchecked:
            throw new RuntimeException(e);
        }
    }
}

class SomeOtherException extends Exception {
}