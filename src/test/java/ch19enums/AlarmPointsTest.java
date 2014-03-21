package ch19enums;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-25
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates
 */
public class AlarmPointsTest{
    @Test
    public void testName() throws Exception {
        AlarmPoints alarmPoints = AlarmPoints.OFFICE2;
        System.out.println(alarmPoints.name());
    }
}
