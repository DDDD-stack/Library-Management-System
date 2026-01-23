import java.io.*;
import java.util.UUID;

public class UserRegistration {
    private boolean Passed;

    public UserRegistration(){}

    Administrator admin = new Administrator();

    public void registerCustomer(String userName, String password, String email){
        boolean usernameTaken = false;

        try(BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))){
            String line;

            while((line = reader.readLine()) != null){
                if (line.contains(userName)) {
                    usernameTaken = true;
                    break;
                }
            }
        }catch(IOException e){
            System.out.println("File not found: " + e.getMessage());
        }

        if(usernameTaken){
            System.out.println("Username already taken!\n");
        }else {

            String customerID = UUID.randomUUID().toString();

            Customer customer = new Customer(userName, password, email, customerID);


            //Write the  new user information on file


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
                writer.write(customer.toString() + "\n");
                writer.newLine();
                System.out.println(customer.toString() + "\n");

            } catch (IOException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
    }

    public void registerAdmin(String userName, String password, String email){

            String adminId = UUID.randomUUID().toString();

            Administrator admin = new Administrator(userName, password, email, adminId);

            try (BufferedWriter br = new BufferedWriter(new FileWriter("admins.txt", true))) {

                br.write(admin.toString());
                br.newLine();
                System.out.println(admin.toString() + "\n");
                br.close();

            } catch (IOException e) {
                System.out.println("File not found: " + e.getMessage());
            }
    }

    public void switchToAdmin(){

    }

    public void switchToUser(){

    }

    public void checkAdminPass(String adminPass){
        if(adminPass.equals(admin.getAdminPass())){
            this.Passed = true;
        }else{
            this.Passed = false;
            System.out.println("Admin Password Mismatch!\n");
        }
    }

    public boolean getPassed(){
        return Passed;
    }
}
