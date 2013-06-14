//: origin.annotations/Testable.java
package chapter20_annotation;

import net.mindview.atunit.*;

public class Testable {
	public void execute() {
		System.out.println("Executing..");
	}

	@Test
	void testExecute() {
		execute();
	}
} // /:~
