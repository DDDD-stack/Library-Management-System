import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;

public class ConsoleUI {

    public void ConsoleUI(){

        Scanner sc = new Scanner(System.in);

        LogIn:
        while(true){
            System.out.println("Welcome to the App! Please Log In!");
            System.out.println("Enter username or email: ");
            String username = sc.nextLine();
            System.out.println("Enter password: ");
            String password = sc.nextLine();

            try(BufferedReader reader = new BufferedReader(new FileReader("users.txt"))){
                String line;
                while((line = reader.readLine()) != null){
                    if (line.contains(username) && line.contains(password)){
                        System.out.println("Welcome!");
                        break LogIn;
                    }else {
                        System.out.println("Invalid credentials!");
                    }
                }

            }catch(IOException e){
                System.out.println("File not found: " + e.getMessage());
            }
        }

        loopApp:
        while(true){
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
                    System.out.println("1. Filter by gender");
                    System.out.println("2. Filter by year");
                    int filter = sc.nextInt();
                    sc.nextLine();

                    switch(filter){
                        case 1://Filter by gender--------------------
                            System.out.println("Enter genre: ");
                            String filterByGenre = sc.nextLine();

                            try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
                                String line;
                                while((line = reader.readLine()) != null){
                                    for(int i = 0; i <= line.length() - filterByGenre.length(); i++){
                                        if(line.regionMatches(true, i, filterByGenre, 0, filterByGenre.length())){
                                            System.out.println(line);
                                        }
                                    }
                                }
                            }catch(IOException e){
                                System.out.println("File not found: " + e.getMessage());
                            }
                            break;

                        case 2://Filter by year published-------------------
                            System.out.println("Enter year range: ");
                            int yearRange1 = sc.nextInt();
                            sc.nextLine();
                            int yearRange2 = sc.nextInt();
                            sc.nextLine();

                            try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
                                String line;

                                while((line = reader.readLine()) != null) {
                                    for (int i = yearRange1; i <= yearRange2; i++) {
                                        if (line.contains("published in: " + i)) {
                                            System.out.println(line);
                                        }
                                    }
                                }
                            }catch(IOException e){
                                System.out.println("File not found: " + e.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;

                case 5: //Search book ---------------------
                    System.out.println("Enter the book title: ");
                    String search = sc.nextLine();

                    try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
                        String line;
                        while((line = reader.readLine()) != null){
                            for(int i = 0; i <= line.length() - search.length(); i++){
                                if(line.regionMatches(true, i, search, 0, search.length())){
                                    System.out.println(line);
                                    break;
                                }
                            }
                        }
                    }catch(IOException e){
                        System.out.println("File not found: " + e.getMessage());
                    }
                    break;
                case 6: //Exit loop
                    System.out.println("Bye!");
                    break loopApp;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
