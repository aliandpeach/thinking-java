package chapter18_io;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class TryPath {
    public static void main(String[] args) {
        FileSystem fileSystem = FileSystems.getDefault();

        Path path = fileSystem.getPath("garbage.java");
        checkPath(path);

        path = Paths.get(System.getProperty("user.dir"));
        checkPath(path);

        // Amend the following path to your environment
        path = fileSystem.getPath("D:", "work",
                "github", "thinking-java", "src", "main",
                "java", "chapter18_io", "TryPath.java");
        checkPath(path);
    }

    // Check the attributes of a path
    static void checkPath(Path path) {
        System.out.println("\n" + path + " has " + path.getNameCount() + " elements.");
        System.out.println("\n" + path + " ddd " + path.getName(0) + " elements.");
        if (path.isAbsolute()) {
            System.out.println(path + " is an absolute path.");
            System.out.println("The parent path is " + path.getParent());
            System.out.println("The root is " + path.getRoot());
        } else {
            System.out.println(path + " is a relative path.");
            path = path.toAbsolutePath();
        }

        if (Files.notExists(path)) {
            System.out.println(path + " does not exist.");
            return;
        }

        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            if (attr.isDirectory())
                System.out.println(path.getFileName() + " is a directory.");
            else if (attr.isRegularFile()) {
                System.out.println(path.getFileName() + " is a file containing " +
                        attr.size() + " bytes.");
            }
            System.out.println(path + " was created " + attr.creationTime());
            System.out.println(path + " was last accessed " + attr.lastAccessTime());
            System.out.println(path + " was last modified " + attr.lastModifiedTime());
            System.out.println(path + " is " + attr.size() + " bytes.");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
