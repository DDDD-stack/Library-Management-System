public class User {
    private String userName;
    private String password;
    private String email;

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
        return "Username: " + userName + ", Password: " + password + ", Email: " + email;
    }
}
