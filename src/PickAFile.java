import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class PickAFile {
    public static void main(String[] args){
        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "JPG & GIF Images", "jpg", "gif");
//        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            System.out.println(chooser.getSelectedFile().getPath());
            File tmpDir = new File(chooser.getSelectedFile().getName());
            boolean exists = tmpDir.exists();
            //if(exists) System.out.println(chooser.getSelectedFile());
        }
        else
            System.out.println("nothing");
    }
}