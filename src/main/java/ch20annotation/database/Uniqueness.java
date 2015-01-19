//: annotations/database/Uniqueness.java
// Sample of nested annotations
package ch20annotation.database;

public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
} // /:~
