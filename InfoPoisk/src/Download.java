import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Download {

    static StringBuilder logger;

    public static void main(String args[]) throws Exception {
        logger = new StringBuilder();

        String mainLink = "https://ilibrary.ru/text/1544/p.";
        int postId = 100;

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("src/index.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int deltaValue = 0;

        for (int i = 0; i < 100 + deltaValue; i++) {
            File yourFile = new File("src/pages/" + (i - deltaValue) + ".txt");
            yourFile.createNewFile();
            OutputStream out = new FileOutputStream(yourFile);

            URL url = new URL(mainLink + (postId - i * 2));
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = null;
            try {
                is = conn.getInputStream();
            } catch (FileNotFoundException e) {
                deltaValue++;
                log("Page not found " + (postId - i * 2));
                continue;
            } catch (IOException e) {
                deltaValue++;
                log("Page moved or locked " + (postId - i * 2));
                continue;
            }

            byte[] buffer = new byte[4096];
            while (true) {
                int numBytes = is.read(buffer);
                if (numBytes == -1)
                    break;
                out.write(buffer, 0, numBytes);
            }

            is.close();
            out.close();

            printWriter.println(
                    String.format("%0" + 2 + "d", i - deltaValue)
                            + " " + mainLink + (postId - i * 2));

            log((i - deltaValue) + " finished");
        }

        printWriter.close();

        PrintWriter pw = null;
        try {
            pw = new PrintWriter("src/logs.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        pw.println(logger.toString());
        pw.close();
    }

    private static void log(String log) {
        System.out.println(log);
        logger.append(log).append("\n");
    }
}