package ch19enums;//: enumerated/CarWash.java

import java.util.*;
import static net.mindview.util.Print.*;

public class CarWash {
    /**
     * 常量相关方法，比匿名内部类更高效
     */
    public void wash() {
        CarWash wash = new CarWash();
        print(wash);
        wash.washCar();
        // Order of addition is unimportant:
        wash.add(Cycle.BLOWDRY);
        wash.add(Cycle.BLOWDRY); // Duplicates ignored
        wash.add(Cycle.RINSE);
        wash.add(Cycle.HOTWAX);
        print(wash);
        wash.washCar();
    }
    public enum Cycle {
        UNDERBODY {
            void action() {
                print("Spraying the underbody");
            }
        },
        WHEELWASH {
            void action() {
                print("Washing the wheels");
            }
        },
        PREWASH {
            void action() {
                print("Loosening the dirt");
            }
        },
        BASIC {
            void action() {
                print("The basic wash");
            }
        },
        HOTWAX {
            void action() {
                print("Applying hot wax");
            }
        },
        RINSE {
            void action() {
                print("Rinsing");
            }
        },
        BLOWDRY {
            void action() {
                print("Blowing dry");
            }
        };

        abstract void action();
    }

    EnumSet<Cycle> cycles = EnumSet.of(Cycle.BASIC, Cycle.RINSE);

    private void add(Cycle cycle) {
        cycles.add(cycle);
    }

    private void washCar() {
        for (Cycle c : cycles)
            c.action();
    }

    public String toString() {
        return cycles.toString();
    }

} /*
 * Output: [BASIC, RINSE] The basic wash Rinsing [BASIC, HOTWAX, RINSE, BLOWDRY]
 * The basic wash Applying hot wax Rinsing Blowing dry
 */// :~
