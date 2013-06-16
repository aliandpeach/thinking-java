//: enumerated/Competitor.java
// Switching one enum on another.
package origin.enumerated;

public interface Competitor<T extends Competitor<T>> {
  Outcome compete(T competitor);
} ///:~
