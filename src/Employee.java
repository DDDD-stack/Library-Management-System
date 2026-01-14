import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Employee {
    private String employeeName;
    private String employeeID;

    public Employee(){
    }

    public Employee(String employeeName, String employeeID){
        this.employeeName = employeeName;
        this.employeeID = employeeID;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public String getEmployeeID(){
        return employeeID;
    }

    public void setEmployeeName(String employeeName){
        this.employeeName = employeeName;
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
        return "Employee Name: " + employeeName + ", Employee ID: " + employeeID;
    }
}
