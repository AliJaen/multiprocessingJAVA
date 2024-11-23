import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

public class FileDownloader implements Runnable {
    private String url;
    private String savePath;

    public FileDownloader(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
    }

    @Override
    public void run() {
        try {
            System.out.println("Downloading: " + url);
            URL website = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));
            PrintWriter out = new PrintWriter(savePath);

            String line;
            while ((line = in.readLine()) != null) {
                out.println(line);
            }

            in.close();
            out.close();
            System.out.println("Downloaded: " + savePath);
        } catch (Exception e) {
            System.err.println("Error downloading " + url + ": " + e.getMessage());
        }
    }
}
