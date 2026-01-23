import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleUI {

    public void ConsoleUI(){

        Scanner sc = new Scanner(System.in);
        UserRegistration user = new UserRegistration();
        LogIn logIn = new LogIn();
        Administrator adminConstructor = new Administrator();
        Book bookConstructor = new Book();

        loopApp:

        //    Menu ----------------------

        //Keep the login the same but make special id for employ and user EX: Userid: U1,U2.....   EmployeeID: E1,E2.......

        while(true){
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");  //missing
            System.out.println("4. Log In");
            System.out.println("5. Log Out");
            System.out.println("6. Register");
            System.out.println("7. Filter");
            System.out.println("8. Search for book");
            System.out.println("9. Show current user profile");
            System.out.println("0. Exit");

            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){


                //    Add books ------------------


                case 1:
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

                //  Borrow book -----------------

                case 2:
                    System.out.println("Enter User Name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter Book title: ");
                    String bookTitle = sc.nextLine();
                    System.out.println("Enter Book Author: ");
                    String bookAuthor = sc.nextLine();
                    System.out.println("Enter the amount of days you want to borrow the book: ");
                    int nrOfDays = sc.nextInt();
                    sc.nextLine();
                    LocalDate borrowDate = LocalDate.now();

                    bookConstructor.borrowBook(name, bookTitle, bookAuthor, borrowDate, nrOfDays);

                    break;

                case 4:

                    //User Log In------------------

                    System.out.println("Enter User Name: ");
                    String loginName = sc.nextLine();
                    System.out.println("Enter Password: ");
                    String loginPassword = sc.nextLine();

                    logIn.LogIn(loginName, loginPassword);
                    break;

                case 5:

                    //LogOut-------------------

                    logIn.LogOut();
                    break;



                //  Register user ----------------

                case 6:

                    System.out.println("1. Register as a customer");
                    System.out.println("2. Register as an Administrator");
                    System.out.println("Enter your choice: ");
                    int choice2 = sc.nextInt();
                    sc.nextLine();


                    // Register Menu----------

                    switch(choice2){



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

                            if(user.getPassed() == true){
                                System.out.println("Enter User Name: ");
                                String adminName = sc.nextLine();
                                System.out.println("Enter Password: ");
                                String adminPassword = sc.nextLine();
                                System.out.println("Enter Email: ");
                                String adminEmail = sc.nextLine();

                                user.registerAdmin(adminName, adminPassword, adminEmail);
                            }

                            break;



                            //Switch to administrator--------
//                        case 3:
//                            System.out.println("Enter admin password: ");
//                            String adminCheck = sc.nextLine();
//
//                            if(adminCheck.equals(adminConstructor.getAdminPass())){
//                                System.out.println();
//                            }
//
//                            break;

                    }
                    break;



                    //Filter -----------------


                case 7:
                    System.out.println("1. Filter by gender");
                    System.out.println("2. Filter by year");
                    int filter = sc.nextInt();
                    sc.nextLine();

                    switch(filter){

                        //Filter by gender--------------------

                        case 1:
                            System.out.println("Enter genre: ");
                            String filterByGenre = sc.nextLine();




                            //Update : upload the books in an array list and upload
                            //Make it a method


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



                        //Filter by year published----------------


                        case 2:
                            System.out.println("Enter year range: ");
                            int yearRange1 = sc.nextInt();
                            sc.nextLine();
                            int yearRange2 = sc.nextInt();
                            sc.nextLine();


                            //Update :  upload the books in an array list and upload
                            //Make it a method


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



                //Search book ---------------------


                case 8:
                    System.out.println("Enter the book title: ");
                    String search = sc.nextLine();


                    //Read file to find the wanted book


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

                case 9:
                    System.out.println(Main.currentUser);
                    break;


                //Exit loop


                case 0:
                    System.out.println("Bye!");
                    break loopApp;


                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
