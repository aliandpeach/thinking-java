//: origin.annotations/database/Uniqueness.java
// Sample of nested origin.annotations
package chapter20_annotation.database;

public @interface Uniqueness {
	Constraints constraints() default @Constraints(unique = true);
} // /:~
