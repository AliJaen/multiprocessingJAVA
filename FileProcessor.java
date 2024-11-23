import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.RecursiveTask;

public class FileProcessor extends RecursiveTask<Integer> {
    private String filePath;

    public FileProcessor(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Integer compute() {
        int wordCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordCount += line.split("\\s+").length;
            }
            System.out.println(filePath + " have " + wordCount + " words.");
        } catch (Exception e) {
            System.err.println("Error processing " + filePath + ": " + e.getMessage());
        }
        return wordCount;
    }
}
