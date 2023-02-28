import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Display extends JFrame{
    private DefaultTableModel model;
    private JTable table;
    private MyLinkedList<Book> myDatabase = new MyLinkedList<>();
    Vector column = new Vector<>(Arrays.asList("ISBN", "Title", "Available"));

    String inputISBN;
    String inputTitle;
    public Display(String title) throws IOException {
        //format Date
        myDatabase = WriterReader.read();
        setTitle(title);
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E MMM dd  HH:mm:ss 'CTS' yyyy");
        String formattedDate = myDateObj.format(myFormatObj);
        add(new JTextArea("Student Name and ID: ZHANG Chengcheng (20076355d)\n" +
                "Student Name and ID: CAI Zhuoang (20077025d)  \n" +
                formattedDate + "\n\n"), BorderLayout.NORTH);
        
        //table
        model = new DefaultTableModel(column, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //disable edit on select cell
        //DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        /*table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(table.getSelectedRow() !=-1) {
                    Display.this.ISBNfield.setText((String)table.getValueAt(table.getSelectedRow(), 0));
                    Display.this.titleField.setText((String)table.getValueAt(table.getSelectedRow(), 1));
                }
            }
            });*/
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                if(table.getSelectedRow() !=-1) {
                    Display.this.ISBNfield.setText((String)table.getValueAt(table.getSelectedRow(), 0));
                    Display.this.titleField.setText((String)table.getValueAt(table.getSelectedRow(), 1));
                }
            }
        });
        JTableHeader tHead = table.getTableHeader();
        tHead.setBackground(Color.LIGHT_GRAY);
        ((DefaultTableCellRenderer) tHead.getDefaultRenderer()) .setHorizontalAlignment (JLabel.CENTER);
        //((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)) .setHorizontalAlignment (JLabel.CENTER);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        //ButtonPanel
        add(new ButtonPanel(), BorderLayout.SOUTH);

        //refresh onload
        reloadData(myDatabase.toArray(new Book[myDatabase.size]));
    }
    public static void main(String[] args) throws IOException {
        JFrame frame = new Display("Library Admin System");
        frame.setSize(700,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //frame.validate();
    }

    public static class RecordTable extends JPanel{
        public RecordTable() {
            String data[][]={ {"101","Amit","670000"},
                    {"102","Jai","780000"},
                    {"101","Sachin","700000"}};
            String column[]={"ID","NAME","SALARY"};
            JTable table = new JTable(data, column);
            add(new JScrollPane(table));
        }
    }

    JTextField ISBNfield = new JTextField( 8);
    JTextField titleField = new JTextField(12);
    JCheckBox coverCheckBox = new JCheckBox("Select Cover");
    String selectCoverPath;
    public class ButtonPanel extends JPanel implements ActionListener {

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton saveBtn = new JButton("Save");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        JButton moreBtn = new JButton("More>>");
        JButton updateBtn = new JButton("Update DB");
        JButton loadBtn = new JButton("Load Test Data");
        JButton displayBtn = new JButton("Display All");
        JButton dISBNBtn = new JButton("Display all by ISBN");
        JButton dTitleBtn = new JButton("Display all by Title");
        JButton exitBtn = new JButton("Exit");

        public ButtonPanel() {
            setLayout(new GridLayout(3, 1, 3, 3));
            JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JPanel thirdRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

            saveBtn.setEnabled(false);

            addBtn.addActionListener(this);
            editBtn.addActionListener(this);
            saveBtn.addActionListener(this);
            deleteBtn.addActionListener(this);
            searchBtn.addActionListener(this);
            moreBtn.addActionListener(this);
            updateBtn.addActionListener(this);
            loadBtn.addActionListener(this);
            displayBtn.addActionListener(this);
            dISBNBtn.addActionListener(this);
            dTitleBtn.addActionListener(this);
            exitBtn.addActionListener(this);
            coverCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(coverCheckBox.isSelected()) {
                        JFileChooser chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            selectCoverPath = chooser.getSelectedFile().getPath();
                            System.out.println(selectCoverPath);
                        } else {
                            System.out.println("nothing");
                            coverCheckBox.setSelected(false);
                        }
                    }else {
                        selectCoverPath = null;
                    }
                }
            });

            firstRow.add(new JLabel("ISBN:"));
            firstRow.add(ISBNfield);
            firstRow.add(new JLabel("Title:"));
            firstRow.add(titleField);
            firstRow.add(coverCheckBox);
            secondRow.add(addBtn);
            secondRow.add(editBtn);
            secondRow.add(saveBtn);
            secondRow.add(deleteBtn);
            secondRow.add(searchBtn);
            secondRow.add(moreBtn);
            secondRow.add(updateBtn);
            thirdRow.add(loadBtn);
            thirdRow.add(displayBtn);
            thirdRow.add(dISBNBtn);
            thirdRow.add(dTitleBtn);
            thirdRow.add(exitBtn);
            add(firstRow);
            add(secondRow);
            add(thirdRow);
        }

        String lastISBN;//set for edit and save
        boolean reverseISBN = false;
        boolean reverseTitle = false;
        Book[] bookArray;
        @Override
        public void actionPerformed(ActionEvent e) {
            //Add
            if (e.getSource() == addBtn) {
                getInput();
                if (inputISBN.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "java.lang.Exception: Error: ISBN " +
                            "cannot be blank.");
                } else if (inputTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "java.lang.Exception: Error: Title " +
                            "cannot be blank.");
                } else {
                    addBook(inputISBN, inputTitle, selectCoverPath);
                }
                clear();
                //Edit
            } else if (e.getSource() == editBtn) {
                getInput();
                if (ISBNindexInDatabase(inputISBN) == -1) {
                    JOptionPane.showMessageDialog(this, "Error: book ISBN is not in the database.");
                } else {
                    titleField.setText(ISBNbookName(inputISBN));
                    JButton[] group = {addBtn, editBtn, saveBtn, deleteBtn, searchBtn, moreBtn, updateBtn, loadBtn, displayBtn,
                            dISBNBtn, dTitleBtn, exitBtn};
                    for (JButton btn : group)
                        btn.setEnabled(false);
                    saveBtn.setEnabled(true);
                    lastISBN = inputISBN;//store ISBN before edit
                }
                //Sava
            } else if (e.getSource() == saveBtn) {
                getInput();
                if (ISBNindexInDatabase(inputISBN) != -1 && !inputISBN.equals(lastISBN)) {
                    JOptionPane.showMessageDialog(this, "Error: book ISBN " +
                            "exists in the database.");
                    Display.this.ISBNfield.setText(lastISBN);
                    Display.this.titleField.setText(ISBNbookName(lastISBN));
                }
                else {
                    int indexInDatabase = ISBNindexInDatabase(lastISBN);
                    if(indexInDatabase != -1) {
                        model.setValueAt(inputISBN, indexInDatabase, 0);
                        model.setValueAt(inputTitle, indexInDatabase, 1);
                    }
                    myDatabase.get(indexInDatabase).setISBN(inputISBN);
                    myDatabase.get(indexInDatabase).setTitle(inputTitle);
                    JButton[] group = {addBtn, editBtn, saveBtn, deleteBtn, searchBtn, moreBtn, updateBtn,  loadBtn, displayBtn,
                            dISBNBtn, dTitleBtn, exitBtn};
                    for (JButton btn : group)
                        btn.setEnabled(true);
                    saveBtn.setEnabled(false);
                    clear();
                }
            //Delete
            } else if (e.getSource() == deleteBtn) {
                getInput();
                if (ISBNindexInDatabase(inputISBN) == -1)
                    JOptionPane.showMessageDialog(this, "Error: book ISBN does not" +
                            "exists in the database.");
                else {
                    if (ISBNindexInTable(inputISBN) != -1) {
                        System.out.println("delete in table");
                        System.out.println(inputISBN);
                        System.out.println();
                        model.removeRow(ISBNindexInDatabase(inputISBN));
                    }
                    myDatabase.remove(ISBNindexInDatabase(inputISBN));
                }
                clear();
            //Search
            } else if (e.getSource() == searchBtn) {
                getInput();
                String query = null;
                if (inputISBN.isEmpty() || inputTitle.isEmpty())
                    query = inputISBN + inputTitle;
                else
                    query = inputISBN + "|" + inputTitle;
                filter(query);
                clear();
            //more
            }else if(e.getSource() == moreBtn) {
                getInput();
                if (ISBNindexInDatabase(inputISBN) == -1)
                    JOptionPane.showMessageDialog(this, "Error: book ISBN is not in the database.");
                else {
                    try {
                        BorrowPanel borrowPanel = new BorrowPanel(inputTitle);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            //Update DB button
            }else if(e.getSource() == updateBtn){
                try {
                    WriterReader.save(myDatabase);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            //Load Test Data
            } else if (e.getSource() == loadBtn) {
                addBook("0131450913", "HTML How to Program", "cover/HTML How to Program.jpeg");
                addBook("0131857576", "C++ How to Program", "cover/C++ How to Program.jpg");
                addBook("0132222205", "Java How to Program", "cover/Java_How_to_Program.jpg");
                /*addBook("0131450913", "HTML How to Program");
                addBook("0131857576", "C++ How to Program");
                addBook("0132222205", "Java How to Program");*/
            //Display all
            } else if (e.getSource() == displayBtn) {
                bookArray = myDatabase.toArray(new Book[myDatabase.size]);
                reloadData(bookArray);
            }else if(e.getSource() == exitBtn) System.exit(0);
            //Display all by ISBN
            if (e.getSource() == dISBNBtn) {
                bookArray = myDatabase.toArray(new Book[myDatabase.size]);
                if(reverseISBN)
                    Arrays.sort(bookArray, Comparator.comparing(Book::getISBN).reversed());
                else
                    Arrays.sort(bookArray, Comparator.comparing(Book::getISBN));
                reloadData(bookArray);
                reverseISBN = !reverseISBN;
                //Display all by Title
            }else reverseISBN = false;
            if (e.getSource() == dTitleBtn) {
                bookArray = myDatabase.toArray(new Book[myDatabase.size]);
                if(reverseTitle)
                    Arrays.sort(bookArray, Comparator.comparing(Book::getTitle).reversed());
                else
                    Arrays.sort(bookArray, Comparator.comparing(Book::getTitle));
                reloadData(bookArray);
                reverseTitle = !reverseTitle;
            }else reverseTitle = false;
        }
    }
    /**--------------------------------------------------------------------------------------**/
    public class BorrowPanel extends JFrame implements ActionListener{
        Book book = myDatabase.get(ISBNindexInDatabase(inputISBN));
        MyQueue<String> queue= book.getReservedQueue();
        JButton borrowBtn = new JButton("Borrow");
        JButton returnBtn = new JButton("Return");
        JButton reserveBtn = new JButton("Reserve");
        JButton waitingBtn = new JButton("Waiting Queue");
        JButton[] btnGroup = {returnBtn, reserveBtn, waitingBtn};
        JTextArea bookInf = new JTextArea("ISBN: " + inputISBN + "\n" +
                "Title: " + book.getTitle() + "\n" +
                "Available: " + book.isAvailable() + "\n");
        JTextArea subtitle = new JTextArea();
        BorrowPanel(String title) throws IOException {
            add(bookInf, BorderLayout.NORTH);
            JPanel borrowButtonPanel = new BorrowButtonPanel();
            add(borrowButtonPanel, BorderLayout.CENTER);
            add(subtitle, BorderLayout.SOUTH);

            borrowBtn.addActionListener(this);
            returnBtn.addActionListener(this);
            reserveBtn.addActionListener(this);
            waitingBtn.addActionListener(this);

            if (book.isAvailable())
                for(JButton b : btnGroup)
                    b.setEnabled(false);
            else
                borrowBtn.setEnabled(false);

            setTitle(title);
            setSize(450, 300);
            setLocationRelativeTo(Display.this);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //Borrow
            if(borrowBtn == e.getSource()){
                book.setAvailable(false);
                for(JButton b : btnGroup)
                    b.setEnabled(true);
                borrowBtn.setEnabled(false);
                subtitle.setText("The book is borrowed.");
                bookInf.setText("ISBN: " + inputISBN + "\n" +
                        "Title: " + book.getTitle() + "\n" +
                        "Available: " + book.isAvailable() + "\n");
                if(ISBNindexInTable(inputISBN) != -1)
                    table.setValueAt("false", ISBNindexInTable(inputISBN), 2);
            //Return
            }else if(returnBtn == e.getSource()){
                subtitle.setText("The book is returned.\n");
                if(!queue.getList().isEmpty()){
                    subtitle.append("The book is now borrowed by " + queue.dequeue() + ".");
                }else{
                    for(JButton b : btnGroup)
                        b.setEnabled(false);
                    borrowBtn.setEnabled(true);
                    book.setAvailable(true);
                    bookInf.setText("ISBN: " + inputISBN + "\n" +
                            "Title: " + book.getTitle() + "\n" +
                            "Available: " + book.isAvailable() + "\n");
                    if(ISBNindexInTable(inputISBN) != -1)
                        table.setValueAt("true", ISBNindexInTable(inputISBN), 2);
                }
            //Reserve
            }else if(reserveBtn == e.getSource()){
                String userName = JOptionPane.showInputDialog(this,
                        "What's your name?", "Input", JOptionPane.PLAIN_MESSAGE);
                queue.enqueue(userName);
                subtitle.setText("The book is reserved by " + userName + ".");
            //Waiting Queue
            }else if(waitingBtn == e.getSource()){
                subtitle.setText("The waiting queue:\n");
                for(String s : queue.getList())
                    subtitle.append(s + "\n");
            }
        }

        class BorrowButtonPanel extends JPanel{
            BorrowButtonPanel() throws IOException {
                add(borrowBtn);
                add(returnBtn);
                add(reserveBtn);
                add(waitingBtn);
                if(book.getCover() != null) {
                    BufferedImage myPicture = ImageIO.read(new File(book.getCover()));
                    Image newResizedImage = myPicture.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
                    JLabel picLabel = new JLabel(new ImageIcon(newResizedImage));
                    add(picLabel);
                }
            }
        }
    }
    /**--------------------------------------------------------------------------------------**/
    // get current ISBN and title
    public void getInput(){
        inputISBN = Display.this.ISBNfield.getText();
        inputTitle = Display.this.titleField.getText();
    }

    // clear text field
    public void clear(){
        Display.this.ISBNfield.setText("");
        Display.this.titleField.setText("");
    }

    //add book
    public void addBook(String ISBN, String title, String cover){
        if(ISBNindexInDatabase(ISBN) == -1) {
            model.addRow(new Object[]{ISBN, title, "true"});
            Book book = new Book(ISBN, title, true, cover);
            myDatabase.add(book);
        }
        else
            JOptionPane.showMessageDialog(this, "Error: book " + ISBN +
                    " is already in the database.");
    }

    //row of provided ISBN in table
    public int ISBNindexInTable(String ISBN){
        for(int i = 0; i < table.getRowCount(); i++) {
            if (ISBN.equals(table.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    //bookName of provide ISBN
    public String ISBNbookName(String ISBN){
        for(int i = 0; i < myDatabase.size(); i++) {
            if (ISBN.equals(myDatabase.get(i).getISBN()))
                return myDatabase.get(i).getTitle();
        }
        return "";
    }

    //index of provided ISBN in database
    public int ISBNindexInDatabase(String ISBN){
        for(int i = 0; i < myDatabase.size(); i++) {
            if (ISBN.equals(myDatabase.get(i).getISBN())) {
                return i;
            }
        }
        return -1;
    }

    //filter
    public void filter(String query){
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        table.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    public void reloadData(Book[] bookArray){
        Vector data = new Vector<>();
        filter("");
        model.setRowCount(0);
        for(Book b : bookArray)
            data.add(new Vector<>(Arrays.asList(b.getISBN(),b.getTitle(), b.isAvailable())));
        model.setDataVector(data, column);
    }
}
