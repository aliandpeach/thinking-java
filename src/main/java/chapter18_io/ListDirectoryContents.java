package chapter18_io;

import java.nio.file.*;
import java.io.IOException;

public class ListDirectoryContents {
    public static void main(String[] args) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        currentPath = currentPath.getParent();

        // Get the entire directory contents
        filterDirectoryContents(currentPath, null);

        // Now try a filter for .txt files
        System.out.println("\nFilter for .txt:");
        filterDirectoryContents(currentPath, "*.txt");

        // Get files with a 3 character extension begiining with 'j'
        System.out.println("\nFilter for .j??:");
        filterDirectoryContents(currentPath, "*.j??");
    }

    // Filter the contents of a directory
    private static void filterDirectoryContents(Path path, String filter) {
        try (DirectoryStream<Path> paths = filter != null ?
                Files.newDirectoryStream(path, filter) : Files.newDirectoryStream(path)) {
            System.out.println("\n" + path + " directory contains:");
            for (Path p : paths) {
                System.out.println("  " + p.getFileName() +
                        (Files.isDirectory(p) ? " is a directory." : ""));
            }
        } catch (NotDirectoryException e) {
            System.err.println("Path is not a directory." + e);
        } catch (IOException e) {
            System.err.println("I/O error." + e);
        }
    }
}
