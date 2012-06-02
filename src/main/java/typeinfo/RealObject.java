package typeinfo;

import static net.mindview.util.Print.print;

public class RealObject implements Interface {
	public void doSomething() {
		print("doSomething");
	}

	public void somethingElse(String arg) {
		print("somethingElse " + arg);
	}
}
