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

        //Kontrolloji pak metodat per add edhe borrow edhe nese munesh ban 1 return method

        //Momentalisht duhet me rregullu kto para se me ba tjerat.

        //Edhe nmos tardht keq a munesh me e ba searching qe ti baj ignore case ke te tana si ke searchi i librave edhe ke validimi i metodave
    }
}