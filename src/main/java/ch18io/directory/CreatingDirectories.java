package ch18io.directory;

import java.nio.file.*;
import java.io.IOException;

public class CreatingDirectories {
    public static void main(String[] args) {
        Path relPath = Paths.get("junkDir");
        createSingleDirectory(relPath);                                    // Create directory in current
//        createSingleDirectory(relPath);                                    // then try it again...

        Path absPath = Paths.get("D:/Garbage/dir1/dir2/dir3");
        createSingleDirectory(absPath);                                    // Try creating as single directory
        createMultipleDirectories(absPath);                                // Now do it right
        createMultipleDirectories(absPath);                                // then try it again...
    }

    static void createSingleDirectory(Path path) {
        try {
            //Files.createDirectory(path);
            Files.createDirectories(path);
            path = path.toAbsolutePath();
            System.out.println("\n" + path + " directory created.");
        } catch (NoSuchFileException e) {
            System.err.println("\nDirectory creation failed:\n" + e);
        } catch (FileAlreadyExistsException e) {
            System.err.println("\nDirectory creation failed:\n" + e);
        } catch (IOException e) {
            System.err.println("\nDirectory creation failed:\n" + e);
        }
    }

    static void createMultipleDirectories(Path path) {
        try {
            Files.createDirectories(path);
            path = path.toAbsolutePath();
            System.out.println("\n" + path + " directory created.");
        } catch (IOException e) {
            System.err.println("\nDirectory creation failed:\n" + e);
        }
    }

}
