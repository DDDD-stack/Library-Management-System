import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;
//import java.util.UUID;
import java.util.ArrayList;

public class ConsoleUI {

    public void ConsoleUi() {

        Scanner sc = new Scanner(System.in);
        UserRegistration user = new UserRegistration();
        Administrator adminConstructor = new Administrator();
        //Book bookConstructor = new Book();   per ca duhet kjo??

        //    Menu ----------------------

        //Keep the login the same but make special id for employ and user EX: Userid: U1,U2.....   EmployeeID: E1,E2.......
        System.out.println("1. Register.\n" +
                           "2. Log In");
int choice1;

        logInLoop: while(Main.currentUser == null) {
            choice1=sc.nextInt();
            switch (choice1) {
                case 1: {
                    System.out.println("1. Register as a customer");
                    System.out.println("2. Register as an Administrator");
                    System.out.println("Enter your choice: ");
                    int choice2 = sc.nextInt();
                    sc.nextLine();


                    // Register Menu----------

                    switch (choice2) {


                        //  Registration as user----------

                        case 1:

                            String memType;

                            System.out.println("Enter User Name: ");
                            String customerName = sc.nextLine();
                            System.out.println("Enter Password: ");
                            String customerPassword = sc.nextLine();
                            System.out.println("Enter Email: ");
                            String customerEmail = sc.nextLine();
                            System.out.println("Enter Phone Number: ");
                            String customerPhoneNumber = sc.nextLine();
                            System.out.println("Chose Membership Type: ");
                            System.out.println("1. Standard");
                            System.out.println("2. Premium");
                            int memChoice = sc.nextInt();
                            sc.nextLine();

                            switch (memChoice){
                                case 1:
                                    memType = "Standard";

                                case 2:
                                    memType = "Premium";

                                    default: {
                                        System.out.println("Invalid input!");
                                    memType="Standart";
                                    }
                            }

                            user.registerCustomer(customerName, customerPassword, customerEmail, customerPhoneNumber, memType);

                            break;


                        //Register as an Admin------------------


                        case 2:
                            System.out.println("Enter admin password: ");
                            String adminPass = sc.nextLine();
                            user.checkAdminPass(adminPass);

                            if (user.getPassed()) {
                                System.out.println("Enter User Name: ");
                                String adminName = sc.nextLine();
                                System.out.println("Enter Password: ");
                                String adminPassword = sc.nextLine();
                                System.out.println("Enter Email: ");
                                String adminEmail = sc.nextLine();
                                System.out.println("Enter PhoneNumber: ");
                                String adminPhoneNumber = sc.nextLine();

                                user.registerAdmin(adminName, adminPassword, adminEmail, adminPhoneNumber);
                            }else if (!user.getPassed()){
                                System.out.println("Invalid input!");
                            }

                            break;

                    }


                }

                case 2: {
                    System.out.println("1.Log In as customer");
                    System.out.println("2.Log In as administrator");
                    System.out.println("Enter your choice: ");
                    int logInChoice = sc.nextInt();
                    sc.nextLine();

                    if (logInChoice == 1) {
                        System.out.println("Enter UserName: ");
                        String userName = sc.nextLine();
                        System.out.println("Enter Password: ");
                        String password = sc.nextLine();

                        LogIn.LogInCustomer(userName, password);

                    } else if (logInChoice == 2) {

                        System.out.println("Enter UserName: ");
                        String userName = sc.nextLine();
                        System.out.println("Enter Password: ");
                        String password = sc.nextLine();

                        LogIn.LogInAdmin(userName, password);
                    } else {
                        System.out.println("Already logged in!");
                    }
                    break;
                }
            }
        }

        loopApp: while (true) {
                System.out.println("1. Add Book");      //complete
                System.out.println("2. Borrow Book");    //complete
                System.out.println("3. Return Book");  //complete
                System.out.println("4. Log In");       //defect
                System.out.println("5. Log Out");      //complete
                System.out.println("6. Register");     //complete
                System.out.println("7. Filter");        //complete
                System.out.println("8. Search for book");   //complete
                System.out.println("9. Show current user profile");   //defect
                System.out.println("0. Exit");    //complete

                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {


                    //    Add books ------------------


                    case 1: {
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

                        adminConstructor.addBook(title, author, genre, ISBN, year);
                        break;
                    }
                    //  Borrow book -----------------

                    case 2: {

                        System.out.println("Enter Book title: ");
                        String bookTitle = sc.nextLine();
                        System.out.println("Enter Book Author: ");
                        String bookAuthor = sc.nextLine();
                        System.out.println("Enter the amount of days you want to borrow the book: ");
                        int nrOfDays = sc.nextInt();
                        sc.nextLine();
                        LocalDate borrowDate = LocalDate.now();
                        Customer customer = (Customer) Main.currentUser;
                        customer.borrowBook(bookTitle, bookAuthor, borrowDate, nrOfDays);

                        break;
                    }

                    //Return Book------------------
                    case 3: {
                        System.out.println("Enter Book title: ");
                        String bookTitle = sc.nextLine();
                        System.out.println("Enter Book Author: ");
                        String bookAuthor = sc.nextLine();
                        Customer customer = (Customer) Main.currentUser;
                        customer.returnBook(bookTitle, bookAuthor);

                        break;
                    }
                    case 4: {

                        //User Log In------------------
                        if (Main.currentUser == null) {

                            System.out.println("1.Log In as customer");
                            System.out.println("2.Log In as administrator");
                            System.out.println("Enter your choice: ");
                            int logInChoice = sc.nextInt();
                            sc.nextLine();

                            if (logInChoice == 1) {
                                System.out.println("Enter UserName: ");
                                String userName = sc.nextLine();
                                System.out.println("Enter Password: ");
                                String password = sc.nextLine();

                                LogIn.LogInCustomer(userName, password);

                            } else if (logInChoice == 2) {

                                System.out.println("Enter UserName: ");
                                String userName = sc.nextLine();
                                System.out.println("Enter Password: ");
                                String password = sc.nextLine();

                                LogIn.LogInAdmin(userName, password);
                            }
                        } else {
                            System.out.println("Already logged in!");
                        }
                        break;
                    }
                    case 5: {

                        //LogOut-------------------
                        if (Main.currentUser != null) {
                            LogIn.LogOut();
                        }
                        break;
                    }


                    //  Register user ----------------

                    /*case 6: {

                        System.out.println("1. Register as a customer");
                        System.out.println("2. Register as an Administrator");
                        System.out.println("Enter your choice: ");
                        int choice2 = sc.nextInt();
                        sc.nextLine();


                        // Register Menu----------

                        switch (choice2) {


                            //  Registration as user----------

                            case 1:
                                System.out.println("Enter User Name: ");
                                String customerName = sc.nextLine();
                                System.out.println("Enter Password: ");
                                String customerPassword = sc.nextLine();
                                System.out.println("Enter Email: ");
                                String customerEmail = sc.nextLine();

                                user.registerCustomer(customerName, customerPassword, customerEmail);

                                break;


                            //Register as an Admin------------------


                            case 2:
                                System.out.println("Enter admin password: ");
                                String adminPass = sc.nextLine();
                                user.checkAdminPass(adminPass);

                                if (user.getPassed()) {
                                    System.out.println("Enter User Name: ");
                                    String adminName = sc.nextLine();
                                    System.out.println("Enter Password: ");
                                    String adminPassword = sc.nextLine();
                                    System.out.println("Enter Email: ");
                                    String adminEmail = sc.nextLine();

                                    user.registerAdmin(adminName, adminPassword, adminEmail);
                                }

                                break;

                        }
                        break;
                    }
*/

                    //Filter -----------------


                    case 7: {
                        System.out.println("1. Filter by genre");
                        System.out.println("2. Filter by year");
                        System.out.println("3. Filter by available");
                        int filter = sc.nextInt();
                        sc.nextLine(); // Consume newline

                        // 1. LOAD THE DATA ONCE
                        ArrayList<Book> allBooks = loadBooksFromFile();

                        switch (filter) {
                            // --- Filter by Genre ---
                            case 1:
                                System.out.println("Enter genre: ");
                                String inputGenre = sc.nextLine().trim();

                                try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {

                                        //Skips empty line------------------
                                        if (line.trim().isEmpty()) {
                                            continue;
                                        }

                                        String[] parts = line.split(",");

                                        //checks if all parts are there-----------
                                        if (parts.length < 4) {
                                            continue;             //Skip the incorrect line and go to the next one----------
                                        }

                                        String Genrename = parts[3];

                                        //Clean and Compare--------
                                        String fileGenre = Genrename.replace("Genre:", "").trim();

                                        if (fileGenre.equalsIgnoreCase(inputGenre)) {
                                            System.out.println(line);
                                        }
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error reading file: " + e.getMessage());
                                }
                                break;

                            // --- Filter by Year ---
                            case 2:
                                System.out.println("Enter start year: ");
                                int startYear = sc.nextInt();
                                System.out.println("Enter end year: ");
                                int endYear = sc.nextInt();
                                sc.nextLine();

                                for (Book b : allBooks) {
                                    if (b.getPublicationYear() >= startYear && b.getPublicationYear() <= endYear) {
                                        System.out.println(b);
                                    }
                                }
                                break;

                            // --- Filter by Availability ---
                            case 3:
                                System.out.println("--- Available Books ---");
                                for (Book b : allBooks) {
                                    if (b.isAvailable()) {
                                        System.out.println(b);
                                    }
                                }
                                break;

                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                        break;
                    }


                    //Search book ---------------------


                    case 8: {
                        System.out.println("Enter the book title: ");
                        String search = sc.nextLine();


                        //Read file to find the wanted book


                        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
                            String line;

                            while ((line = reader.readLine()) != null) {


                                String[] parts = line.split(",");
                                if (parts[0].equalsIgnoreCase("Book title is: " + search)) {
                                    System.out.println(line);
                                }

                            }
                        } catch (IOException e) {
                            System.out.println("File not found: " + e.getMessage());
                        }
                        break;
                    }
                    case 9: {
                        System.out.println(Main.currentUser);
                        break;
                    }

                    //Exit loop


                    case 0: {
                        System.out.println("Bye!");
                        break loopApp;
                    }

                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            }
        }


    // Helper method to read the file into a list of Book objects
    private ArrayList<Book> loadBooksFromFile() {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // SECURITY CHECK: Ensure line has enough parts before accessing index 5
                if (parts.length < 6) {
                    continue;
                }
                // Clean the data to get just the values
                String title = parts[0].trim();
                String author = parts[1].trim();
                String isbn = parts[2].trim();
                String genre = parts[3].replace("Genre:", "").trim();
                int year = Integer.parseInt(parts[4].replaceAll("[^0-9]", ""));
                boolean isAvailable = parts[5].contains("true");

                books.add(new Book(title, author, isbn, genre, year, isAvailable));
            }
        } catch (IOException e) {
            System.out.println("Error reading library: " + e.getMessage());
        }
        return books;
    }
}
