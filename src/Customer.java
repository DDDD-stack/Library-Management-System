import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends User{
    private String customerID;
    private String MembershipID;
    private String membershipType;
    private int maxBooks;


    ArrayList<String> lines = new ArrayList<>();
    ArrayList<String> ulines = new ArrayList<>();

                                                                                            //Constructors--------------
    public Customer(){
        // No-arg constructor
    }

    public Customer(String userName, String password, String email, String phoneNumber, String customerID, String MembershipID, String membershipType){
        super(userName, password, email, phoneNumber);
        this.customerID = customerID;
        this.MembershipID = MembershipID;
        this.membershipType = membershipType;
        this.role = "Customer";

        if (this.membershipType.equalsIgnoreCase("Premium")) {
            this.maxBooks = 5;
        } else {
            this.maxBooks = 2;
        }
    }

    public Customer(String userName, String password, String email, String phoneNumber, String customerID, String MembershipID, String membershipType,String role){
        super(userName, password, email, phoneNumber);
        this.customerID = customerID;
        this.MembershipID = MembershipID;
        this.membershipType = membershipType;
        this.role = role;

        if (this.membershipType.equalsIgnoreCase("Premium")) {
            this.maxBooks = 5;
        } else {
            this.maxBooks = 2;
        }
    }
                                                                                            //Getters-------------------

    public String getCustomerID(){
        return customerID;
    }

    public String getMembershipID() {
        return MembershipID;
    }

    public int getCurrentBorrowedCount() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowed.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if this specific user borrowed this book
                // NOTE: This assumes your borrowed.txt lines contain the username!
                if (line.contains(this.getUserName())) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading borrowed file to count books.");
        }
        return count;
    }

                                                                                            //Setters-------------------

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }



    public void borrowBook(String title, String author, LocalDate borrowDate, int nrOfDays) {
        // 1. Check Membership Limit (Optional but recommended)
        int currentBooks = getCurrentBorrowedCount();
        if (currentBooks >= this.maxBooks) {
            System.out.println("Limit reached! You have a " + this.membershipType + " membership.");
            return;
        }

        this.lines.clear();
        boolean bookFoundAndBorrowed = false;
        LocalDate dueDate = borrowDate.plusDays(nrOfDays);

        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if it is the correct book
                if (line.contains(title) && line.contains(author)) {

                    if (line.contains("Availability: true")) {
                        // --- MODIFICATION START ---
                        // We mark it false, and STAMP the Membership ID onto the line
                        line = line.replace("Availability: true", "Availability: false")
                                + ", Borrowed Date: " + borrowDate
                                + ", Return Date: " + dueDate
                                + ", MemID: " + this.MembershipID; // <--- Saving the ID here!
                        // --- MODIFICATION END ---

                        bookFoundAndBorrowed = true;
                    } else if (line.contains("Availability: false")) {
                        System.out.println("Book is not available!");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Write changes to books.txt
        if (bookFoundAndBorrowed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : lines) {
                    writer.write(wline);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing books.txt");
            }

            // Update the helper files
            readForToBorrowList();
            uploadToBorrowList();

            if (Main.currentUser != null) {
                System.out.println(Main.currentUser.getUserName() + " (ID: " + this.MembershipID + ") Borrowed successfully!");
            }
        } else {
            System.out.println("Book not found or already borrowed.");
        }
    }

    public void returnBook(String title, String author) {
        this.lines.clear();
        boolean success = false;
        boolean wrongUser = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(title) && line.contains(author)) {

                    if (line.contains("Availability: false")) {

                        // --- MODIFICATION START ---
                        // SECURITY CHECK: Does this line contain MY Membership ID?
                        if (line.contains(this.MembershipID)) {

                            // It's my book! Reset it.
                            // We strip away all the extra dates and IDs by keeping only the first part.
                            int statusIndex = line.indexOf("Availability: false");
                            if (statusIndex != -1) {
                                line = line.substring(0, statusIndex) + "Availability: true";
                            }
                            success = true;

                        } else {
                            // The book is borrowed, but NOT by this user.
                            wrongUser = true;
                        }
                        // --- MODIFICATION END ---

                    } else if (line.contains("Availability: true")) {
                        System.out.println("Book is already in the library!");
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
            } catch (IOException e) {
                System.out.println("Error writing file.");
            }

            // Update lists
            readForToBorrowList();
            uploadToBorrowList(); // Remember: remove 'true' from FileWriter inside this method!

            System.out.println("Returned successfully!");

        } else if (wrongUser) {
            System.out.println("Error: You cannot return a book borrowed by another member!");
        } else {
            System.out.println("Could not return book.");
        }
    }



    @Override
    public String toString(){
        return super.toString() + "," + " Customer ID: " + customerID + "," +"Membership ID: "+ MembershipID+ "," + "Membership Type: " + membershipType + ","+ "Status: " + role;
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
