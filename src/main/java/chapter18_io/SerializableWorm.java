package chapter18_io;//: io/SerializableWorm.java
// Demonstrates object serialization.

import java.io.*;
import java.util.Random;
import static net.mindview.util.Print.print;

/**
 * Java序列化基本操作
 */
public class SerializableWorm implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6606629677633624280L;
    private static Random rand = new Random(47);
    private Data[] d = {new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10)), new Data(rand.nextInt(10))};
    private SerializableWorm next;
    private char c;

    // Value of i == number of segments
    public SerializableWorm(int i, char x) {
        print("SerializableWorm constructor: " + i);
        c = x;
        if (--i > 0)
            next = new SerializableWorm(i, (char) (x + 1));
    }

    public SerializableWorm() {
        print("Default constructor");
    }

    public String toString() {
        StringBuilder result = new StringBuilder(":");
        result.append(c);
        result.append("(");
        for (Data dat : d)
            result.append(dat);
        result.append(")");
        if (next != null)
            result.append(next);
        return result.toString();
    }

    /**
     * Java对象的序列化
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public void worm() throws IOException, ClassNotFoundException {
        SerializableWorm w = new SerializableWorm(6, 'a');
        print("w = " + w);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
                "worm.out"));
        out.writeObject("SerializableWorm storage\n");
        out.writeObject(w);
        out.close(); // Also flushes output
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                "worm.out"));
        String s = (String) in.readObject();
        SerializableWorm w2 = (SerializableWorm) in.readObject();
        print(s + "w2 = " + w2);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream(bout);
        out2.writeObject("SerializableWorm storage\n");
        out2.writeObject(w);
        out2.flush();
        ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(
                bout.toByteArray()));
        s = (String) in2.readObject();
        SerializableWorm w3 = (SerializableWorm) in2.readObject();
        print(s + "w3 = " + w3);
    }
} /*
 * Output: SerializableWorm constructor: 6 SerializableWorm constructor: 5 SerializableWorm constructor: 4 SerializableWorm
 * constructor: 3 SerializableWorm constructor: 2 SerializableWorm constructor: 1 w =
 * :a(853):b(119):c(802):d(788):e(199):f(881) SerializableWorm storage w2 =
 * :a(853):b(119):c(802):d(788):e(199):f(881) SerializableWorm storage w3 =
 * :a(853):b(119):c(802):d(788):e(199):f(881)
 */// :~

class Data implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -186220653674083351L;
    private int n;

    public Data(int n) {
        this.n = n;
    }

    public String toString() {
        return Integer.toString(n);
    }
}

