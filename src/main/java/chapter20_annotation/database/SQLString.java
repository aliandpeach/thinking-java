//: annotations/database/SQLString.java
package chapter20_annotation.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import chapter20_annotation.database.Constraints;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
	int value() default 0;

	String name() default "";

	Constraints constraints() default @Constraints;
} // /:~
