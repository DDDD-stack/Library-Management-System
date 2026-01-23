import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Main{
    public static User currentUser = null;
    public static void main(String[] args){
        ConsoleUI console = new ConsoleUI();
        File file = new File("borrowed.txt");

        console.ConsoleUI();

        //Nuk krijohet file i ri kur ban borrow (E ke ke Book metoden)

        //E ktheva Userin ne klas abstracte per me nda fiks customer nga employee pra merru pak me user status

        //Bana 1 class per LogIn/LogOut edhe 1 class per UserRegistration per me nda ne mnyr ma tpaster metodat

        //Kontrolloji pak metodat per add, borrow, LogIn, LogOut edhe nese munesh ban 1 return method

        //Edhe se di si ia kam qr qe kur i baj SHow current user profile mi ban print ka 2 he psh: UserName: UserName: Denis, Password: Password: Denis1234robi12

        //Bana edhe ni file tjeter me emrin customers per me nda fiks adminat nga customers

        //Per ni far arsye nuk i gjen userat ke admin.txt

        //Momentalisht duhet me rregullu kto para se me ba tjerat.

        //Provoj edhe iher tana opsionet perveq returnit se se kam ba njat e shif a kam harru me shkruj naj gja ktu

        //Edhe nmos tardht keq a munesh me e ba searching qe ti baj ignore case ke te tana si ke searchi i librave edhe ke validimi i metodave
    }
}