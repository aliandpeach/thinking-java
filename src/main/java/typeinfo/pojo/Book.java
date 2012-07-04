package typeinfo.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-5-27
 * Time: 下午9:47
 * Book
 */
public class Book {
    boolean checkedOut = false;

    public Book(boolean checkOut) {
        checkedOut = checkOut;
    }

    public void checkIn() {
        checkedOut = false;
    }

    protected void finalize() throws Throwable {
        if (checkedOut)
            System.out.println("Error: checked out");
        // Normally, you'll also do this:
        super.finalize(); // Call the base-class version
    }
}
