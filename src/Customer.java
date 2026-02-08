import java.io.*;
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



    public void borrowBook(String customerName, String title, String author, LocalDate borrowDate, int nrOfDays){

          LocalDate returnDate;
        this.lines.clear();
        //Availability Validation and Update
        try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
            String line;
            returnDate = borrowDate.plusDays(nrOfDays);

            while((line = reader.readLine()) != null){

                if(line.contains(title) && line.contains(author)){
                    if(line.contains("Availability: true")){
                        try(BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))){
                            for(String wline : lines){
                                writer.write(line);
                                writer.newLine();
                            }
                            readForToBorrowList();
                            uploadToBorrowList();
                        }catch(IOException e){
                            System.out.println("File not found!");
                        }
                        line=line.replace("Availability: true", "Availability: false");


                    }else if(line.contains("Availability: false")){
                        System.out.println("Book is not available!");
                    }
                }
                lines.add(line);
            }
        }catch(IOException e){
            e.getMessage();
        }
    }


    public void ReturnBook(String ISBN){

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
            e.getMessage();
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
