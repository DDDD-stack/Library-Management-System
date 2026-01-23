import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Administrator extends User{
    private String adminID;
    private String adminPass = "admin123";

    /*
    Fine method
Add book method
     */






    public Administrator(){
    }

    public Administrator(String userName, String password, String email, String adminID){
        super(userName,password,email);
        this.adminID = adminID;
        this.role = "Administrator";
    }


    public String getAdminID(){
        return adminID;
    }

    public String getAdminPass(){
        return adminPass;
    }



    public void setAdminID(String employeeID){
        this.adminID = employeeID;
    }

    public void setAdminPass(String password){
        this.adminPass = password;
    }

    public void addBook(String title, String author, String genre, String ISBN, int year){

        Book book = new Book(title, author, genre, ISBN, year, true);

        try(BufferedWriter fw = new BufferedWriter(new FileWriter("books.txt", true))){
            fw.write(book.toString());

            fw.write("\n");

            fw.close();
        }catch(IOException e){
            System.out.println("File not found: " + e.getMessage());
        }
    }

    @Override
    public String toString(){
        return super.toString() + "," + " Admin ID: " + adminID + "," + " Status: " + role;
    }
}
