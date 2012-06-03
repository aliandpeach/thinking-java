//: annotations/database/Uniqueness.java
// Sample of nested annotations
package chapter20_annotation.database;

public @interface Uniqueness {
	Constraints constraints() default @Constraints(unique = true);
} // /:~
