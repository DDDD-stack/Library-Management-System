import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogIn {

    public LogIn(){}

                                                                                            //LogIn method---------------

    public static void LogInCustomer(String userName, String password) {
        boolean found = false; //will be used as verification if the user is found or not

        //Check the users file--------
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                //Seperate the line into parts to make it easier to find specific values
                String[] parts = line.split(",");

                String CName = parts[0].split(":")[1].trim();
                String CPass = parts[1].split(":")[1].trim();
                String CEmail = parts[2].split(":")[1].trim();
                String CPnumber = parts[3].split(":")[1].trim();
                String CcustomerID = parts[4].split(":")[1].trim();
                String CMembershipID = parts[5].split(":")[1].trim();
                String CMembershiptType = parts[6].split(":")[1].trim();
                String CRole = parts[7].split(":")[1].trim();


                //Check username and password for a match
                if (parts[0].contains(userName) && parts[1].contains(password)) {
                    found = true;

                    //Create the customer constructor to use as the user
                    Customer customer = new Customer(CName, CPass, CEmail, CPnumber, CcustomerID, CMembershipID, CMembershiptType,CRole);
                    Main.currentUser=customer;
                    String[] parts2 = parts[0].split(":");
                    System.out.println("Logged in! Welcome back: " + parts2[1].trim());
                      break;

                } else {
                    System.out.println("Invalid username or password");
                }

            }
        }catch (IOException e) {
            System.out.println("File not found!");
        }
    }
        //If not found int the users file then check the admins file same steps apply here
        public static void LogInAdmin(String userName, String password) {
        boolean found = false;
            try(BufferedReader br = new BufferedReader(new FileReader("admins.txt"))){
                String line;

                while((line = br.readLine()) != null){
                    String[] parts = line.split(",");

                    String CName = parts[0].split(":")[1].trim();
                    String CPass = parts[1].split(":")[1].trim();
                    String CEmail = parts[2].split(":")[1].trim();
                    String CAdminID = parts[3].split(":")[1].trim();
                    String CStatus = parts[4].split(":")[1].trim();

                    if(parts[0].equals(userName) && parts[1].equals(password)){
                        found = true;

                        Administrator admin = new Administrator(CName, CPass, CEmail, CAdminID, CStatus);
                        Main.currentUser=admin;
                              String[] parts2 = parts[0].split(":");
                            System.out.println("Logged in! Welcome back: " + parts2[1].trim());
                        break;
                    }
                    else {
                        System.out.println("Invalid username or password");
                    }
                }
            }catch(IOException e){
                System.out.println("File not found!");
            }
        }

                                                                                            //Log Out method---------------

    public static void LogOut(){
        Main.currentUser = null;
    }
}
