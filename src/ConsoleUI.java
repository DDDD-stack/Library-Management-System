import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;

public class ConsoleUI {

    //Search method--------------
    public static Book searchBook(String title){
        try(BufferedReader br = new BufferedReader(new FileReader("books.txt"))){
            String line;

            while ((line = br.readLine()) != null){
                Book book = Book.fromString(line);

                if (book.getTitle().equals(title)) {
                    return book;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void ConsoleUI(){

        Scanner sc = new Scanner(System.in);

        loop:
        while(true){
            System.out.println("Welcome to the APP!");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Register as a user");
            System.out.println("4. Filter");
            System.out.println("5. Search for book");
            System.out.println("6. Exit");

            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1: //Add books ---------------------

                    System.out.println("Enter Book Title: ");
                    String title = sc.nextLine();

                    System.out.println("Enter Book Author: ");
                    String author = sc.nextLine();

                    System.out.println("Enter Book Genre: ");
                    String genre = sc.nextLine();

                    System.out.println("Enter Book ISBN: ");
                    String ISBN = sc.nextLine();

                    System.out.println("Enter Publication Year: ");
                    int year = sc.nextInt();

                    Book book = new Book(title, author, genre, ISBN, year, true);

                    try(BufferedWriter fw = new BufferedWriter(new FileWriter("books.txt", true))){
                        fw.write(book.toString() + "\n");
                        fw.close();
                    }catch(IOException e){
                        System.out.println("File not found: " + e.getMessage());
                    }
                    break;
                case 2: //Borrow book --------------------
                    System.out.println("Borrowed Book!");
                    break;

                case 3: //Register user --------------------

                    boolean usernameTaken = false;
                    System.out.println("Enter your Username: ");
                    String username = sc.nextLine();

                    System.out.println("Enter your Password: ");
                    String password = sc.nextLine();

                    System.out.println("Enter your Email: ");
                    String email = sc.nextLine();

                    try(BufferedReader reader = new BufferedReader(new FileReader("users.txt"))){
                        String line;

                        while((line = reader.readLine()) != null){
                            if (line.contains(username)) {
                                usernameTaken = true;
                                break;
                            }
                        }
                    }catch(IOException e){
                        System.out.println("File not found: " + e.getMessage());
                    }

                    if(usernameTaken){
                        System.out.println("Username already taken!\n");
                    }else{
                        User user = new User(username, password, email);

                        try(BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))){
                            writer.write(user.toString() + "\n");
                            writer.newLine();
                            System.out.println(user.toString() + "\n");

                        }catch(IOException e){
                            System.out.println("File not found: " + e.getMessage());
                        }
                    }
                    break;

                case 4: //Filter -----------------
                    System.out.println("Filtered Books!");
                    break;
                case 5: //Search book ---------------------
                    System.out.println("Enter the book title: ");
                    String search = sc.nextLine();

                    Book result = searchBook(search);

                    System.out.println("THe results: " + result);
                    break;
                case 6: //Exit loop
                    System.out.println("Bye!");
                    break loop;
            }
        }
    }
}
