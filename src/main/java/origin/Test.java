package origin;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Runnable doHelloWorld = new Runnable() {
			public void run() {
				System.out.println("Hello World on " + Thread.currentThread());
			}
		};

		SwingUtilities.invokeLater(doHelloWorld);
		TimeUnit.SECONDS.sleep(1);
		System.out
				.println("This might well be displayed before the other message.");

	}

}
