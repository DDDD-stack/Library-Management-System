import java.time.LocalDate;
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
        Employee employeeConstructor = new Employee();
        Book bookConstructor = new Book();

        loopApp:

        //    Menu ----------------------

        //Keep the login the same but make special id for employ and user EX: Userid: U1,U2.....   EmployeeID: E1,E2.......

        while(true){
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");  //missing
            System.out.println("4. Log In");       //missing
            System.out.println("5. Register as a user");
            System.out.println("6. Filter");
            System.out.println("7. Search for book");
            System.out.println("8. Exit");

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

                    employeeConstructor.addBook(title, author, genre, ISBN, year);
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


                //  Register user ----------------

                case 5:

                    System.out.println("1. Register as a user");
                    System.out.println("2. Register as an Employee");
                    System.out.println("Enter your choice: ");
                    int choice2 = sc.nextInt();
                    sc.nextLine();


                    // Register Menu----------

                    switch(choice2){



                        //  Registration as user----------

                        case 1:
                            boolean usernameTaken = false;
                            System.out.println("Enter your Username: ");
                            String username = sc.nextLine();

                            System.out.println("Enter your Password: ");
                            String password = sc.nextLine();

                            System.out.println("Enter your Email: ");
                            String email = sc.nextLine();


                            //Check if the username is already taken
                            //Update :  upload the books in an array list and upload
                            //Make it a method

//                            try(BufferedReader reader = new BufferedReader(new FileReader("users.txt"))){
//                                String line;
//
//                                while((line = reader.readLine()) != null){
//                                    if (line.contains(username)) {
//                                        usernameTaken = true;
//                                        break;
//                                    }
//                                }
//                            }catch(IOException e){
//                                System.out.println("File not found: " + e.getMessage());
//                            }
//
//
//                            if(usernameTaken){
//                                System.out.println("Username already taken!\n");
//                            }else{
//                                User user = new User(username, password, email);
//
//
//
//                                //Write the  new user information on file
//
//
//                                try(BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))){
//                                    writer.write(user.toString() + "\n");
//                                    writer.newLine();
//                                    System.out.println(user.toString() + "\n");
//
//                                }catch(IOException e){
//                                    System.out.println("File not found: " + e.getMessage());
//                                }
//
//                                File file = new File(username + ".txt");
//                            }
//                            break;



                            //Register as an Employee option


                        case 2:
                            boolean employeeIDTaken = false;
                            System.out.println("Enter employee Name: ");
                            String userName = sc.nextLine();
                            System.out.println("Enter employee ID: ");
                            String employeeID = sc.nextLine();
                            System.out.println("Enter password: ");
                            String pass = sc.nextLine();
                            System.out.println("Enter email: ");
                            String mail = sc.nextLine();




                            //Check if the employee id is taken
                            //Update : upload the books in an array list and upload
                            //Make it a method


                            try(BufferedReader br = new BufferedReader(new FileReader("employees.txt"))){
                                String line;
                                while((line = br.readLine()) != null){
                                    if(line.contains(employeeID)){
                                        employeeIDTaken = true;
                                        break;
                                    }
                                }

                            }catch(IOException e){
                                System.out.println("File not found: " + e.getMessage());
                            }

                            if(employeeIDTaken){
                                System.out.println("Employee ID already registered!");
                            }else{
                                Employee employee = new Employee(employeeID,userName,pass,mail);



                                //Write the new employee information on files


                                try(BufferedWriter br = new BufferedWriter(new FileWriter("employees.txt",true))){

                                    br.write(employee.toString() + "\n");
                                    br.newLine();

                                }catch(IOException e){
                                    System.out.println("File not found: " + e.getMessage());
                                }
                            }
                    }



                    //Filter -----------------


                case 6:
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


                case 7:
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


                //Exit loop


                case 8:
                    System.out.println("Bye!");
                    break loopApp;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
