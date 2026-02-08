import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends User{
    private String customerID;



    ArrayList<String> lines = new ArrayList<>();
    ArrayList<String> ulines = new ArrayList<>();

                                                                                            //Constructors--------------
    public Customer(){
        // No-arg constructor
    }

    public Customer(String userName, String password, String email, String customerID){ //Customer registration constructor
        super(userName, password, email);
        this.customerID = customerID;
        this.role = "Customer";
    }

                                                                                            //Getters-------------------

    public String getCustomerID(){
        return customerID;
    }

                                                                                            //Setters-------------------

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }



    public void borrowBook(String title, String author, LocalDate borrowDate, int nrOfDays) {
        this.lines.clear();
        boolean bookFoundAndBorrowed = false; // Flag to track success


        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains(title) && line.contains(author)) {

                    if (line.contains("Availability: true")) {

                        line = line.replace("Availability: true", "Availability: false");
                        bookFoundAndBorrowed = true; // Mark success flag

                    } else if (line.contains("Availability: false")) {
                        System.out.println("Book is not available!");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (bookFoundAndBorrowed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : lines) {
                    writer.write(wline);
                    writer.newLine();
                }

                readForToBorrowList();
                uploadToBorrowList();
                System.out.println(Main.currentUser.getUserName() + "Borrowed the book successfully!");


            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());

            }
        } else {
            System.out.println("Book not found in the library database.");

        }
    }


    public void returnBook(String title, String author, LocalDate borrowDate, int nrOfDays) {
        this.lines.clear();
        boolean success = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains(title) && line.contains(author)) {

                    if (line.contains("Availability: false")) {

                        line = line.replace("Availability: false", "Availability: true");
                          success=true;
                    } else if (line.contains("Availability: true")) {
                        System.out.println("Book is already returned!");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        if (success) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : lines) {
                    writer.write(wline);
                    writer.newLine();
                }
                readForToBorrowList();
                uploadToBorrowList();
                System.out.println(Main.currentUser.getUserName() + "Returned the book successfully!");

            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());

            }
        }
        else{
            System.out.println("Book is already returned");
        }
    }



    @Override
    public String toString(){
        return super.toString() + "," + " Customer ID: " + customerID + "," + " Status: " + role;
    }




    //Method to read the books file so it can find and save in the ArrayList all the books not available
    public void readForToBorrowList(){
        ulines.clear();     //to clear the arraylines when program stops to prevent duplication
        try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
            String line;

            while((line = reader.readLine()) != null){
                if(line.contains("Availability: false")){
                    ulines.add(line);
                }

            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    //the books that aren't saved in the ulines are uploaded into the borrowed file
    public void uploadToBorrowList(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("borrowed.txt",true))){
            for(String line : ulines){
                writer.write(line);
                writer.newLine();
            }
        }catch(IOException e){
            System.out.println("File not found!");
        }
    }


}
