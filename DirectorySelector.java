package musicmemorygame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.*;

public class DirectorySelector extends JPanel implements ActionListener {
    
    JButton go;
    
    JFileChooser chooser;
    String chooserTitle;
    
    File[] filesInDirectory;
    
    public DirectorySelector()
    {
        go = new JButton("Open the directory selector");
        go.addActionListener(this);
        add(go);
    }
    
    public File[] getFilesInDirectory()
    {
        return this.filesInDirectory;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(chooserTitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            filesInDirectory = chooser.getSelectedFile().listFiles();
        else
            System.out.println("No Selection");
    }
    
    public Dimension getPreferredSize()
    {
        return new Dimension(400, 400);
    }
    
    public static void main(String s[])
    {
        JFrame frame = new JFrame("");
        DirectorySelector panel = new DirectorySelector();
        frame.addWindowListener(
            new WindowAdapter() 
            { 
                public void windowClosing(WindowEvent e) 
                {
                    System.exit(0);
                }
            }
        );
        
        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}
