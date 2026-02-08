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

                if (line.contains(title) && line.contains(author)) {
                    if (line.contains("Availability: true")) {
                        // Only change availability in books.txt
                        line = line.replace("Availability: true", "Availability: false");
                        bookFoundAndBorrowed = true;
                    } else if (line.contains("Availability: false")) {
                        System.out.println("Book is not available!");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }


        if (bookFoundAndBorrowed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : lines) {
                    writer.write(wline);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing books.txt");
            }


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("borrowed.txt", true))) {


                String currentUserName = (Main.currentUser != null) ? Main.currentUser.getUserName() : "Unknown";


                String borrowedLine = "Title: " + title
                        + ", Author: " + author
                        + ", Borrowed Date: " + borrowDate
                        + ", Return Date: " + dueDate
                        + ", MemID: " + this.MembershipID
                        + ", Username: " + currentUserName;

                writer.write(borrowedLine);
                writer.newLine();

            } catch (IOException e) {
                System.out.println("Error writing to borrowed.txt");
            }

            if (Main.currentUser != null) {
                System.out.println(Main.currentUser.getUserName() + " (ID: " + this.MembershipID + ") Borrowed successfully!");
            }
        } else {
            System.out.println("Book not found or already borrowed.");
        }
    }

    public void returnBook(String title, String author) {
        this.lines.clear(); // We use this for books.txt
        boolean bookReturnedInInventory = false;

                                                                                            //Update books.txt ---------
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                // Find the book based on Title and Author
                if (line.contains(title) && line.contains(author)) {

                    // If it says "false", we make it "true"
                    if (line.contains("Availability: false")) {
                        line = line.replace("Availability: false", "Availability: true");
                        bookReturnedInInventory = true;
                    } else if (line.contains("Availability: true")) {
                        System.out.println("This book is already marked as returned in the inventory.");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading books.txt: " + e.getMessage());
            return;
        }

        // Save changes to books.txt
        if (bookReturnedInInventory) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                for (String wline : lines) {
                    writer.write(wline);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to books.txt");
            }

                                                                                    //Remove from borrowed.txt ---------

            ArrayList<String> borrowedLines = new ArrayList<>();
            boolean foundInBorrowedFile = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("borrowed.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {


                    boolean isMyBook = line.contains(title) &&
                            line.contains(author) &&
                            line.contains(this.MembershipID);

                    if (isMyBook) {

                        foundInBorrowedFile = true;
                    } else {

                        borrowedLines.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading borrowed.txt");
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("borrowed.txt"))) {
                for (String wline : borrowedLines) {
                    writer.write(wline);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error updating borrowed.txt");
            }


            if (Main.currentUser != null) {
                if (foundInBorrowedFile) {
                    System.out.println(Main.currentUser.getUserName() + " returned the book successfully!");
                } else {
                    System.out.println("Book availability updated, but record not found in borrowed.txt (Manual return?).");
                }
            }

        } else {
            System.out.println("Could not return book. It might not be in the library or is already returned.");
        }
    }



    @Override
    public String toString(){
        return super.toString() + "," + " Customer ID: " + customerID + "," +"Membership ID: "+ MembershipID+ "," + "Membership Type: " + membershipType + ","+ "Status: " + role;
    }

}
