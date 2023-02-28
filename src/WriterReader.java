import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class WriterReader {

    public static void main(String[] args) throws IOException {
        MyLinkedList<Book> bookLinkedList = new MyLinkedList<Book> ();


        //save(bookLinkedList);
        bookLinkedList = read();
        save(bookLinkedList);
        System.out.println(bookLinkedList.getFirst().getTitle());

    }
    public static void save(MyLinkedList<Book> myDatabase) throws IOException{
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter("savedData.txt");
            int i = 0;
            String bookInf = null;
            for (Book b: myDatabase) {
                bookInf = b.getISBN() + b.getTitle() + b.isAvailable();
                outputStream.println(b.getISBN());
                outputStream.println(b.getTitle());
                outputStream.println(b.isAvailable());
                outputStream.println(b.getCover());
            }

        }finally{
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
        }
    }
    public static MyLinkedList<Book> read()throws IOException{
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;
        MyLinkedList<Book> myDatabase = new MyLinkedList<>();
        try{
            File tmpDir = new File("savedData.txt");
            boolean exists = tmpDir.exists();
            if(!exists) return myDatabase;
            System.out.println("abc");
            inputStream = new BufferedReader(new FileReader("savedData.txt"));
            int i = 0;
            String bookISBN = null;
            String bookTitle = null;
            String bookAvailable = null;
            String bookCover = null;
            while ((bookISBN = inputStream.readLine()) != null){
                bookTitle = inputStream.readLine();
                bookAvailable = inputStream.readLine();
                bookCover = inputStream.readLine();
                myDatabase.add(new Book(bookISBN, bookTitle, bookAvailable.equals("true") ? true:false,
                        bookCover.equals("null")?null:bookCover));
            }
            return myDatabase;
        } finally{
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
        }
    }
}