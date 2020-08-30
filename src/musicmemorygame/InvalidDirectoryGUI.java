package musicmemorygame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The InvalidDirectoryGUI class is responsible for letting the user know there 
 * is not a valid directory included.
 * Responsibilities include: allowing the user to go back to the main menu
 * @author Jacob Strickland
 */
public class InvalidDirectoryGUI extends JPanel implements ActionListener{
    
    private JFrame frame;
    
    /**
     * The constructor for the InvalidDirectoryGUI class.
     */
    public InvalidDirectoryGUI()
    {
        //initizaling the GUI elements with text and action listeners
        frame = new JFrame();
        JLabel title = new JLabel("Please select a valid file directory");
        JButton close = new JButton("Close");
        close.addActionListener(this);
        
        //setting the panel parameters and adding the elements
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        panel.add(close);
        
        //setting the frame information
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Error");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
         
    }
    
    @Override
    /**
     * The method that makes things happen when the buttons are pressed.
     * @param e ActionEvent, a button that was pressed
     */
    public void actionPerformed(ActionEvent e) {
        frame.dispose();    //get rid of the current GUI
    }   
    
}
