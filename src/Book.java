import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Book{
    private String title;
    private String author;
    private String genre;
    private String ISBN;
    private int publicationYear;
    private boolean available;


                                                                                                                                    //Constructors--------------
    ArrayList<String> lines = new ArrayList<>();
    ArrayList<String> ulines = new ArrayList<>();

    public Book(){ //No-arg constructor

        /*
        Filter method




        */


//removed user name
// removed the extends user
//removed the borrow book constructor not needed

    }


    public Book(String title, String author, String genre, String ISBN, int publicationYear, boolean available){ //Constructor for adding
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.available = available;
    }

                                                                                                                                                //Getters-------------------
    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getGenre(){
        return genre;
    }

    public String getISBN(){
        return ISBN;
    }

    public int getPublicationYear(){
        return publicationYear;
    }

    public boolean isAvailable(){
        return available;
    }

                                                                                                                                                        //Setters-------------
    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }

    public void setPublicationYear(int publicationYear){
        this.publicationYear = publicationYear;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

                                                                                                                                                //Methods----------------


    public void returnBook(String title, String author){}

    /*  fix later
    public String borrowString(){
        return "Customer: " +   " Borrowed: " + title + " Borrow Date: " + borrowDate + " Return Date: " + returnDate;          //unfinished
    }
*/
    @Override
    public String toString(){
        return "Book title is: " + title + "," + " Author is: " + author + "," + " ISBN is: " + ISBN + "," + " Genre: " + genre + "," + " Published in: " + publicationYear + "," + " Availability: " + available + "\n";
    }



}



