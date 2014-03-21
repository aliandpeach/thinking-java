package ch18io.directory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-1-7
 */
public class TryMove {
    public static void main(String[] args) {
        Path sourcePath = Paths.get("D:/okk");
        try {
            Files.move(sourcePath, sourcePath.resolveSibling("ko"));
        } catch (IOException e) {
            System.err.println("I/O error" + e);
        }
    }
}
