public class Book {
    private String title;
    private String author;
    private String genre;
    private String ISBN;
    private int publicationYear;
    private boolean available;

    //Constructors--------------
    public Book(){

    }

    public Book(String title, String author, String genre, String ISBN, int publicationYear, boolean available){
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

    //Turn toString into an Object-------
    public static Book fromString(String line){
        String[] data = line.split(", ");

        return new Book(
                data[0],
                data[1],
                data[2],
                data[3],
                Integer.parseInt(data[4]),
                Boolean.parseBoolean(data[5])
        );
    }

    //Display the search results----------
    public String display(){
        return "Title: " + title + "\nAuthor: " + author + "\nGenre: " + genre + "\nISBN: " + ISBN + "\nPublication Year: " + publicationYear + "\nAvailable: " + available;
    }

    @Override
    public String toString(){
        return "Book title is: " + title + ", Author is: " + author + ", ISBN is: "+ ISBN + ", published in: " + publicationYear + "Availability: " + available + "\n";
    }
}
