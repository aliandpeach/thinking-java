import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		Runnable doHelloWorld = new Runnable() {
//			public void run() {
//				System.out.println("Hello World on " + Thread.currentThread());
//			}
//		};
//
//		SwingUtilities.invokeLater(doHelloWorld);
//		TimeUnit.SECONDS.sleep(1);
//		System.out.println("This might well be displayed before the other message.");

        List<String> list1 = new ArrayList<String>(Arrays.asList("88", "33"));
        List<String> list2 = new ArrayList<String>(Arrays.asList("88", "33", "44"));
        System.out.println(list1.retainAll(list2));
        System.out.println(list1.size());
        System.out.println(list2.size());


        Map<String, String> map = new HashMap<String, String>(){{
            put("11", "11");
            put("22", "22");
        }};

        Set<String> keySet = map.keySet();
        keySet.add("sss");
        System.out.println(keySet);

	}



}
