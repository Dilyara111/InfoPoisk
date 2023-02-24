import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Generate {
    public static void main(String[] args) {
        String mainLink = "https://ilibrary.ru/text/1544/p.";
        int postId = 100;

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("src/links.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 100; i++) {
            printWriter.println(mainLink + (postId - i * 2));
        }

        printWriter.close();
    }
}
