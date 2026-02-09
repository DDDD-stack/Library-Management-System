import java.io.*;
import java.util.ArrayList;

public class Library {

    // 1. Helper: Load all books from file into a list
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String title = parts[0].replace("Title:", "").trim();
                String author = parts[1].replace("Author:", "").trim();
                String isbn = parts[2].replace("ISBN:", "").trim();
                String genre = parts[3].replace("Genre:", "").trim();

                int year = 0;
                try {
                    year = Integer.parseInt(parts[4].replaceAll("[^0-9]", ""));
                } catch (Exception e) { year = 0; }

                boolean isAvailable = parts[5].toLowerCase().contains("true");

                books.add(new Book(title, author, genre, isbn, year, isAvailable));
            }
        } catch (IOException e) {
            System.out.println("System: Could not load library. " + e.getMessage());
        }
        return books;
    }

    //Filter by Genre
    public ArrayList<Book> filterByGenre(String genre) {
        ArrayList<Book> allBooks = getAllBooks();
        ArrayList<Book> results = new ArrayList<>();

        for (Book b : allBooks) {
            if (b.getGenre().equalsIgnoreCase(genre)) {
                results.add(b);
            }
        }
        return results;
    }

    //Filter by Year Range
    public ArrayList<Book> filterByYear(int startYear, int endYear) {
        ArrayList<Book> allBooks = getAllBooks();
        ArrayList<Book> results = new ArrayList<>();

        for (Book b : allBooks) {
            if (b.getPublicationYear() >= startYear && b.getPublicationYear() <= endYear) {
                results.add(b);
            }
        }
        return results;
    }

    //Get Only Available Books
    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> allBooks = getAllBooks();
        ArrayList<Book> results = new ArrayList<>();

        for (Book b : allBooks) {
            if (b.isAvailable()) {
                results.add(b);
            }
        }
        return results;
    }
}