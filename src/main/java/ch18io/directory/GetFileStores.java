package ch18io.directory;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-12-31
 */
public class GetFileStores {
    public static void main(String[] args) {
        FileSystem fileSystem = FileSystems.getDefault();
        Iterable<FileStore> stores = fileSystem.getFileStores();
        long gigabyte = 1_073_741_824L;
        for (FileStore store : stores) {
            try {
                System.out.format("\nStore: %-20s %-5s Capacity: %5dgb Unallocated: %6dgb",
                        store.name(), store.type(),
                        store.getTotalSpace() / gigabyte,
                        store.getUnallocatedSpace() / gigabyte);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
