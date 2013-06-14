package origin.generics;//: generics/LimitsOfInference.java

import typeinfo.pets.*;

import java.util.*;

import net.mindview.util.*;

public class LimitsOfInference {
    static void f(Map<Person, List<? extends Pet>> petPeople) {
    }

    public static void main(String[] args) {
        //f(sls); // Does not compile
    }
} ///:~
