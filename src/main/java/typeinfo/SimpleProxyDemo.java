package typeinfo;//: typeinfo/SimpleProxyDemo.java

import typeinfo.itf.Interface;
import typeinfo.pojo.RealObject;

import static net.mindview.util.Print.*;

class SimpleProxy implements Interface {
	private Interface proxied;

	public SimpleProxy(Interface proxied) {
		this.proxied = proxied;
	}

	public void doSomething() {
		print("SimpleProxy doSomething");
		proxied.doSomething();
	}

	public void somethingElse(String arg) {
		print("SimpleProxy somethingElse " + arg);
		proxied.somethingElse(arg);
	}
}

class SimpleProxyDemo {
	public static void consumer(Interface iface) {
		iface.doSomething();
		iface.somethingElse("bonobo");
	}

	public static void main(String[] args) {
		consumer(new RealObject());
		consumer(new SimpleProxy(new RealObject()));
	}
} /*
 * Output: doSomething somethingElse bonobo SimpleProxy doSomething doSomething
 * SimpleProxy somethingElse bonobo somethingElse bonobo
 */// :~
