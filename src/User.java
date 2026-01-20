import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public abstract class User {
    protected String userName;
    protected String password;
    protected String email;
    protected String status;



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

    @Override
    public String toString(){
        return "Username: " + userName + " Password: " + password + " Email: " + email;
    }
}
