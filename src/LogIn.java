import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogIn {
    public LogIn(){}

                                                                                            //LogIn method---------------

    public static void LogIn(String userName, String password){
        boolean found = false; //will be used as verification if the user is found or not

        //Check the users file--------
        try(BufferedReader br = new BufferedReader(new FileReader("users.txt"))){
            String line;

            while((line = br.readLine()) != null){
                //Seperate the line into parts to make it easier to find specific values
                String[] parts = line.split(",");

                //Check username and password for a match
                if (parts[0].contains(userName) && parts[1].contains(password)){
                    found = true;

                    //Create the cutomer constructor to use as the user
                    Main.currentUser = new Customer(parts[0], parts[1], parts[2], parts[3]);

                    System.out.println("Logged in! Welcome back: " + parts[0]);
                    break;

                }
            }

        }catch(IOException e){
            System.out.println("File not found!");
        }

        //If not found int the users file then check the admins file same steps apply here
        if(found == false){
            try(BufferedReader br = new BufferedReader(new FileReader("admins.txt"))){
                String line;

                while((line = br.readLine()) != null){
                    String[] parts = line.split(",");

                    if(parts[0].equals(userName) && parts[1].equals(password)){
                        found = true;

                        Main.currentUser = new Customer(parts[0], parts[1], parts[2], parts[3]);

                        System.out.println("Logged in! Welcome back: " + parts[0]);
                        break;
                    }
                }
            }catch(IOException e){
                System.out.println("File not found!");
            }
        }

        if(found == false){
            System.out.println("User not found!");
        }

    }

                                                                                            //Log Out method---------------

    public static void LogOut(){
        Main.currentUser = null;
    }
}
