//: enumerated/EnumSets.java
// Operations on EnumSets
package origin.enumerated;

import static enumerated.AlarmPoints.BATHROOM;
import static enumerated.AlarmPoints.KITCHEN;
import static enumerated.AlarmPoints.OFFICE1;
import static enumerated.AlarmPoints.OFFICE4;
import static enumerated.AlarmPoints.STAIR1;
import static enumerated.AlarmPoints.STAIR2;
import static net.mindview.util.Print.print;

import java.util.EnumSet;

public class EnumSets {
	public static void main(String[] args) {
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
