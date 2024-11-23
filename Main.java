import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String[] urls = {
                "https://example.com/file1.txt", // Change for a valid URL to download a .txt
                "https://example.com/file2.txt", // Change for a valid URL to download a .txt
                "https://example.com/file3.txt" // Change for a valid URL to download a .txt
        };
        String downloadDir = "downloads";

        // Make a directory to save the downloads
        File dir = new File(downloadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Multithreading: Download the files
        List<Thread> threads = new ArrayList<>();
        for (String url : urls) {
            String savePath = downloadDir + "/" + url.substring(url.lastIndexOf("/") + 1);
            Thread thread = new Thread(new FileDownloader(url, savePath));
            threads.add(thread);
            thread.start();
        }

        // Wait for all downloads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Error writing thread: " + e.getMessage());
            }
        }

        // ForkJoinPool: Processing files
        ForkJoinPool pool = new ForkJoinPool();
        File[] files = dir.listFiles();

        if (files != null) {
            int totalWords = 0;
            for (File file : files) {
                FileProcessor task = new FileProcessor(file.getAbsolutePath());
                totalWords += pool.invoke(task);
            }
            System.out.println("Total words in all files: " + totalWords);
        }
    }
}
