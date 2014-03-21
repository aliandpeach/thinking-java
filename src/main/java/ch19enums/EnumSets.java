//: enumerated/EnumSets.java
// Operations on EnumSets
package ch19enums;

import java.util.EnumSet;
import static ch19enums.AlarmPoints.*;
import static net.mindview.util.Print.print;

public class EnumSets {
    /**&
     * EnumSet基本操作
     */
    public void enumSet() {
        // Empty
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);
        // set
        points.add(BATHROOM);
        print(points);
        points.addAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        print(points);
        points = EnumSet.allOf(AlarmPoints.class);
        points.removeAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        print(points);
        points.removeAll(EnumSet.range(OFFICE1, OFFICE4));
        print(points);
        points = EnumSet.complementOf(points);
        print(points);
    }
} /*
 * Output: [BATHROOM] [STAIR1, STAIR2, BATHROOM, KITCHEN] [LOBBY, OFFICE1,
 * OFFICE2, OFFICE3, OFFICE4, BATHROOM, UTILITY] [LOBBY, BATHROOM, UTILITY]
 * [STAIR1, STAIR2, OFFICE1, OFFICE2, OFFICE3, OFFICE4, KITCHEN]
 */// :~
