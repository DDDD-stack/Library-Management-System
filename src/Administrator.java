import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Administrator extends User{
    private String adminID;
    private String adminPass = "admin123";

    




    public Administrator(){
    }

    public Administrator(String userName, String password, String email, String phoneNumber, String adminID){
        super(userName, password, email, phoneNumber);
        this.adminID= adminID;
        this.role = "Administrator";
    }

    public Administrator(String userName, String password, String email, String phoneNumber, String adminID, String role){
        super(userName,password,email,phoneNumber);
        this.adminID = adminID;
        this.role = role;
    }


    public String getAdminID(){
        return adminID;
    }

    public String getAdminPass(){
        return adminPass;
    }



    public void setAdminID(String employeeID){
        this.adminID = employeeID;
    }

    public void setAdminPass(String password){
        this.adminPass = password;
    }

    public void addBook(String title, String author, String genre, String ISBN, int year){

        Book book = new Book(title, author, genre, ISBN, year, true);

        try(BufferedWriter fw = new BufferedWriter(new FileWriter("books.txt", true))){
            fw.write(book.toString());

            fw.write("\n");
        }catch(IOException e){
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void UpdateHandler(String targetTitle, String targetAuthor, String keyword, String newValue) {
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                if (line.contains(targetTitle) && line.contains(targetAuthor)) {

                    String[] parts = line.split(",");
                    StringBuilder newLine = new StringBuilder();

                    for (int i = 0; i < parts.length; i++) {
                        String part = parts[i].trim();

                        if (part.startsWith(keyword)) {
                            newLine.append(newValue);
                        } else {
                            newLine.append(part);
                        }

                        if (i < parts.length - 1) {
                            newLine.append(", ");
                        }
                    }

                    lines.add(newLine.toString());
                    found = true;
                    System.out.println("Keyword updated: " + keyword);

                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : lines) {
                    writer.write(wline);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to books.txt");
            }
        } else {
            System.out.println("Book not found. Update failed.");
        }
    }



    public void updateBookTitle(String currentTitle, String author, String newTitle) {
        UpdateHandler(currentTitle, author, "Book title is:", "Book title is: " + newTitle);
    }

    public void updateBookAuthor(String title, String currentAuthor, String newAuthor) {
        UpdateHandler(title, currentAuthor, "Author is:", "Author is: " + newAuthor);
    }

    public void updateBookGenre(String title, String author, String newGenre) {
        UpdateHandler(title, author, "Genre:", "Genre: " + newGenre);
    }

    public void updateBookYear(String title, String author, int newYear) {
        UpdateHandler(title, author, "Published in:", "Published in: " + newYear);
    }

    public void updateBookAvailability(String title, String author, boolean newStatus) {
        UpdateHandler(title, author, "Availability:", "Availability: " + newStatus);
    }


    public void removeBook(String title, String author) {
        ArrayList<String> remainingBooks = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                boolean isMatch = line.contains(title) && line.contains(author);

                if (isMatch) {
                    found = true;
                } else {
                    remainingBooks.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : remainingBooks) {
                    writer.write(wline);
                    writer.newLine();
                }
                System.out.println("Success! Removed book: " + title);
            } catch (IOException e) {
                System.out.println("Error writing to books.txt");
            }
        } else {
            System.out.println("Book not found: " + title);
        }
    }



    @Override
    public String toString(){
        return super.toString() + "," + " Admin ID: " + adminID + "," + " Status: " + role;
    }
}
