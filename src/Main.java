import javafx.application.Application;
import java.io.File;
import java.io.IOException;

// ERROR 1 FIX: Remove "extends Application" from this class
public class Main {

    // Keep this here because your other classes (Login, Customer) use Main.currentUser
    public static User currentUser = null;

    public static void main(String[] args) {
        initializeFiles();

        // ERROR 2 & 3 FIX:
        // Do not use 'new GUI()'.
        // Tell JavaFX explicitly which class to launch:
        Application.launch(GUI.class, args);
    }

    private static void initializeFiles() {
        String[] files = {"customers.txt", "admins.txt", "books.txt", "borrowed.txt"};

        for (String fileName : files) {
            File file = new File(fileName);
            try {
                if (file.createNewFile()) {
                    System.out.println("New file created: " + fileName);
                }
            } catch (IOException e) {
                System.out.println("Error creating file " + fileName);
            }
        }
    }
}