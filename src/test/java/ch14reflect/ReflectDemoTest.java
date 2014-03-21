package ch14reflect;

import ch14reflect.model.TargetDomain;
import ch14reflect.model.NotSerialization;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;


public class ReflectDemoTest {

	@Test
	public void testShowMethods() {
        ReflectDemo reflectDemo = new ReflectDemo();
        reflectDemo.showMethods(new StringBuilder("a"));
		assertTrue(true);
	}

    @Test
    public void testShowFields() throws Exception {
        ReflectDemo reflectDemo = new ReflectDemo();
        reflectDemo.showFields(new TargetDomain());
        assertTrue(true);
    }

    @Test
    public void testWriteOuter() {
        try {
            File file = new File("out.ser");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            TargetDomain serializeMe = new TargetDomain();
            serializeMe.setInsCompanyId("222222222");
            NotSerialization notSerialization = new NotSerialization();
            notSerialization.setAge(1212L);
            TargetDomain.notSerialization = notSerialization;
            oos.writeObject(serializeMe);
            oos.close();

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            TargetDomain dtoYou = (TargetDomain) ois.readObject();
            System.out.println(dtoYou.getInsCompanyId());
            System.out.println(TargetDomain.notSerialization.getAge());
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
