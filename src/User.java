import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class User {
    private String userName;
    private String password;
    private String email;



    /*
    borrow method
    return method




    */



    //Constructors---------
    public User(){};

    public User(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    //Getters------------
    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    //Setters------------
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void userLogIn(String username, String password){
        try(BufferedReader br = new BufferedReader(new FileReader("users.txt"))){
            String line;
            while((line = br.readLine()) != null){
                if(line.contains(username) && line.contains(password)){

                }
            }
        }catch(IOException e){
            System.out.println("File not found : " + e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "Username: " + userName + ", Password: " + password + ", Email: " + email;
    }
}
