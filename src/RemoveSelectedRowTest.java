import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class RemoveSelectedRowTest extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private Object[][] data;
    private String[] columnNames;
    private JButton button;
    public RemoveSelectedRowTest() {
        setTitle("RemoveSelectedRow Test");
        data = new Object[][] {{"101", "Ramesh"}, {"102", "Adithya"}, {"103", "Jai"}, {"104", "Sai"}};
        columnNames = new String[] {"ID", "Name"};
        //model = new DefaultTableModel(data, columnNames);
        //table = new JTable(model);
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        model.addRow(new Object[]{"123","true"});
        model.addRow(new Object[]{"123","true"});
        model.addRow(new Object[]{"123","true"});
        model.addRow(new Object[]{"123","true"});
        model.addRow(new Object[]{"123","true"});
        //table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        button = new JButton("Remove");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check for selected row first
                if(table.getSelectedRow() != -1) {
                    // remove selected row from the model
                    model.removeRow(table.getSelectedRow());
                    //JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
                }
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String args[]) {
        new RemoveSelectedRowTest();
    }
}