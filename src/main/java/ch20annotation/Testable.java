//: annotations/Testable.java
package ch20annotation;

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
