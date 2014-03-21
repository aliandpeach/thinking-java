package ch15generic;//: generics/GenericReading.java

import ch15generic.typeinfo.pets.Dog;
import ch15generic.typeinfo.pets.Pet;
import java.util.*;

public class GenericReading {
	static <T> T readExact(List<T> list) {
		return list.get(0);
	}

	static List<Dog> dogs = Arrays.asList(new Dog());
	static List<Pet> pets = Arrays.asList(new Pet());

	// A static method adapts to each call:
	private void f1() {
        Dog a = readExact(dogs);
        Pet f = readExact(pets);
		f = readExact(dogs);
	}

	// If, however, you have a class, then its type is
	// established when the class is instantiated:
	private class Reader<T> {
		T readExact(List<T> list) {
			return list.get(0);
		}
	}

	private void f2() {
		Reader<Pet> fruitReader = new Reader<Pet>();
        Pet f = fruitReader.readExact(pets);
		// Fruit a = fruitReader.readExact(dogs); // Error:
		// readExact(List<Fruit>) cannot be
		// applied to (List<Apple>).
	}

	private class CovariantReader<T> {
		T readCovariant(List<? extends T> list) {
			return list.get(0);
		}
	}

	public void read() {
		CovariantReader<Pet> petReader = new CovariantReader<Pet>();
        Pet f = petReader.readCovariant(pets);
        Pet a = petReader.readCovariant(dogs);
	}
} // /:~
