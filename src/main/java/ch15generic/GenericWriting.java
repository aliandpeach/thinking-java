package ch15generic;//: generics/GenericWriting.java

import ch15generic.typeinfo.pets.Dog;
import ch15generic.typeinfo.pets.Pet;
import java.util.*;

public class GenericWriting {
    private <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }

    private List<Dog> dogs = new ArrayList<Dog>();
    private List<Pet> pets = new ArrayList<Pet>();

    private void f1() {
        writeExact(dogs, new Dog());
        // writeExact(pets, new Apple()); // Error:
        // Incompatible types: found Fruit, required Apple
    }

    private <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }

    public void write() {
        writeWithWildcard(dogs, new Dog());
        writeWithWildcard(pets, new Dog());
    }
} // /:~
