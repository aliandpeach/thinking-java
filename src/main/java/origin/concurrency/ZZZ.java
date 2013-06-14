package origin.concurrency;

public class ZZZ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ZZZ z = new ZZZ();
		synchronized (z) {
			z.notifyAll();
		}
	}

	private synchronized void aa() throws InterruptedException {
		new Float(32).wait();
	}
}
