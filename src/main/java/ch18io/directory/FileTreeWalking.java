package ch18io.directory;

import static java.nio.file.FileVisitOption.*;

import java.nio.file.*;
import java.io.IOException;
import java.util.EnumSet;

public class FileTreeWalking {
    public static void main(String[] args) {
        Path treeBase = Paths.get(System.getProperty("java.home")).getParent().resolve("jre");
        FileVisitor<Path> listFiles = new ListFiles();
        int depth = 3;
        try {
            Files.walkFileTree(treeBase, EnumSet.of(FOLLOW_LINKS), depth, listFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
