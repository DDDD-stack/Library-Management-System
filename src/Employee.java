import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Employee extends User{
    private String employeeID;

    /*
    Fine method
Add book method
     */






    public Employee(){
    }

    public Employee(String employeeID,String userName, String password, String email){
        super(userName,password,email);
        this.employeeID = employeeID;
    }


    public String getEmployeeID(){
        return employeeID;
    }



    public void setEmployeeID(String employeeID){
        this.employeeID = employeeID;
    }

    public void addBook(String title, String author, String genre, String ISBN, int year){

        Book book = new Book(title, author, genre, ISBN, year, true);

        try(BufferedWriter fw = new BufferedWriter(new FileWriter("books.txt", true))){
            fw.write(book.toString() + "\n");
            fw.close();
        }catch(IOException e){
            System.out.println("File not found: " + e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "Employee ID: " + employeeID + " Username: " + getUserName() + " Password: "+ getPassword() + " Email: " + getEmail();
    }
}
