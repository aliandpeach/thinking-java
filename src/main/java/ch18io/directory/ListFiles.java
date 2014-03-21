package ch18io.directory;

import static java.nio.file.FileVisitResult.*;                         // Import constants

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

public class ListFiles extends SimpleFileVisitor<Path> {
    // Output the directory path and set indent for entries
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
        System.out.format("%n%sDirectory: %s contains:", indent, dir);
        indent.append("  ");
        return CONTINUE;
    }

    // Note error after walking directory and reset indent
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) {
        if (e != null) {
            System.err.format("%n%sError walking directory: %s%n%s", indent, dir, e);
        }
        int len = indent.length();
        indent.delete(len - 2, len);
        return CONTINUE;
    }

    // Record file or symbolic link details
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        if (attr.isRegularFile()) {
            System.out.format("%n%sFile: %s", indent, file);
        } else if (attr.isSymbolicLink()) {
            System.out.format("%n%sSymbolic link: %s", indent, file);
        }
        return CONTINUE;
    }

    // Record file visit failure
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
        System.out.format("%n%sFile attributes could not be read for: %s%n%s",
                indent, file, e);
        return CONTINUE;
    }

    // Indent for entries
    private StringBuilder indent = new StringBuilder("  ");
}
