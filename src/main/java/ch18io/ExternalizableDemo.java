package ch18io;//: io/ExternalizableDemo.java
// Reconstructing an externalizable object.

import java.io.*;

import static mindview.Print.*;

/**
 * 利用Externalizable接口手动控制序列化
 */
public class ExternalizableDemo implements Externalizable {
    private int i;
    private String s; // No initialization

    public ExternalizableDemo() {
        print("ExternalizableDemo Constructor");
        // s, i not initialized
    }

    public ExternalizableDemo(String x, int a) {
        print("ExternalizableDemo(String x, int a)");
        s = x;
        i = a;
        // s & i initialized only in non-default constructor.
    }

    public String toString() {
        return s + i;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        print("ExternalizableDemo.writeExternal");
        // You must do this:
        out.writeObject(s);
        out.writeInt(i);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        print("ExternalizableDemo.readExternal");
        // You must do this:
        s = (String) in.readObject();
        i = in.readInt();
    }

    /**
     * 利用Externalizable接口手动控制序列化
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void serial() throws IOException, ClassNotFoundException {
        print("Constructing objects:");
        ExternalizableDemo b3 = new ExternalizableDemo("A String ", 47);
        print(b3);
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
                "ExternalizableDemo.out"));
        print("Saving object:");
        o.writeObject(b3);
        o.close();
        // Now get it back:
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                "ExternalizableDemo.out"));
        print("Recovering b3:");
        b3 = (ExternalizableDemo) in.readObject();
        print(b3);
    }
} /*
 * Output: Constructing objects: ExternalizableDemo(String x, int a) A String 47 Saving
 * object: ExternalizableDemo.writeExternal Recovering b3: ExternalizableDemo Constructor
 * ExternalizableDemo.readExternal A String 47
 */// :~
