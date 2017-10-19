//: enumerated/EnumMaps.java
// Basics of EnumMaps.
package ch19enums;

import java.util.*;

import static ch19enums.AlarmPoints.*;
import static mindview.Print.*;

public class EnumMaps {
    /**
     * EnumMap基本操作
     */
    public void enumMap() {
        EnumMap<AlarmPoints, Command> em = new EnumMap<AlarmPoints, Command>(
                AlarmPoints.class);
        em.put(KITCHEN, new Command() {
            public void action() {
                print("Kitchen fire!");
            }
        });
        em.put(BATHROOM, new Command() {
            public void action() {
                print("Bathroom alert!");
            }
        });
        for (Map.Entry<AlarmPoints, Command> e : em.entrySet()) {
            printnb(e.getKey() + ": ");
            e.getValue().action();
        }
        try { // If there's no value for a particular key:
            em.get(UTILITY).action();
        } catch (Exception e) {
            print(e);
        }
    }
} /*
 * Output: BATHROOM: Bathroom alert! KITCHEN: Kitchen fire!
 * java.lang.NullPointerException
 */// :~
