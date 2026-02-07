import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Book extends User{
    private String title;
    private String author;
    private String genre;
    private String ISBN;
    private int publicationYear;
    private boolean available;
    private LocalDate borrowDate;
    private LocalDate returnDate;

                                                                                                                                    //Constructors--------------
    ArrayList<String> lines = new ArrayList<>();
    ArrayList<String> ulines = new ArrayList<>();

    public Book(){ //No-arg constructor

        /*
        Filter method




        */





    }

    public Book(String userName, String title, String author,LocalDate borrowDate, LocalDate returnDate){ // Constructor for borrowing
        this.userName =userName;
        this.title = title;
        this.author = author;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
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
    public void borrowBook(String customerName, String title, String author, LocalDate borrowDate, int nrOfDays){

        this.returnDate = borrowDate.plusDays(nrOfDays);
        Book borrowed = new Book(userName, title, author,borrowDate, returnDate);


        //Availability Validation and Update
        try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
            String line;
             available = false;

            while((line = reader.readLine()) != null){

                if(line.contains(title) && line.contains(author)){
                    if(line.contains("Availability: true")){

                        line=line.replace("Availability: true", "Availability: false");
                        available = true;

                    }else if(line.contains("Availability: false")){
                        System.out.println("Book is not available!");
                    }
                }
                lines.add(line);
            }
        }catch(IOException e){
            e.getMessage();
        }

        if(isAvailable()){
            //Writing to file

            try(BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))){
                for(String line : lines){
                    writer.write(line);
                    writer.newLine();
                }
                readForToBorrowList();
                uploadToBorrowList();
            }catch(IOException e){
                System.out.println("File not found!");
            }
        }
    }

    public void returnBook(String title, String author){}

    public String borrowString(){
        return "Customer: " + userName + " Borrowed: " + title + " Borrow Date: " + borrowDate + " Return Date: " + returnDate;
    }

    @Override
    public String toString(){
        return "Book title is: " + title + "," + " Author is: " + author + "," + " ISBN is: " + ISBN + "," + " Genre: " + genre + "," + " Published in: " + publicationYear + "," + " Availability: " + available + "\n";
    }


    //Method to read the books file so it can find and save in the ArrayList all the books not available
    public void readForToBorrowList(){
        ulines.clear();     //to clear the arraylines when program stops to prevent duplication
        try(BufferedReader reader = new BufferedReader(new FileReader("books.txt"))){
            String line;

            while((line = reader.readLine()) != null){
                    if(line.contains("Availability: false")){
                        ulines.add(line);
                    }

            }
        }catch(IOException e){
            e.getMessage();
        }
    }
    //the books that aren't saved in the ulines are uploaded into the borrowed file
    public void uploadToBorrowList(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("borrowed.txt",true))){
            for(String line : ulines){
                writer.write(line);
                writer.newLine();
            }
        }catch(IOException e){
            System.out.println("File not found!");
        }
    }
}



