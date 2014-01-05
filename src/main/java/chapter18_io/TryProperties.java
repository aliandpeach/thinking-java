package chapter18_io;

import java.util.Properties;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-1-5
 */
public class TryProperties {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        properties.list(System.out);
    }
}
