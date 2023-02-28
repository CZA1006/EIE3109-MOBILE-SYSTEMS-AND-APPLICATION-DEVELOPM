public class Book{
    private String title; // store the title of the book
    private String ISBN; // store the ISBN of the book
    private boolean available = true; // keep the status of whether the book is available;
                                // initially should be true
    private MyQueue<String> reservedQueue = new MyQueue<>(); // store the queue of waiting list
    private  String cover;
    public Book(){}

    public Book(String ISBN, String title, boolean available, String cover){
        this.title = title;
        this.ISBN = ISBN;
        this.available = available;
        this.cover = cover;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }

    public String getISBN(){
        return ISBN;
    }

    public void setAvailable(Boolean available){
        this.available = available;
    }

    public Boolean isAvailable(){
        return available;
    }

    public MyQueue<String> getReservedQueue(){
        return reservedQueue;
    }
    public void setCover(String s){
        cover = s;
    }
    public String getCover(){
        return cover;
    }
}
