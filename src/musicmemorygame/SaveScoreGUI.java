package musicmemorygame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The SaveScoreGUI class is responsible for getting the name of the user and then
 * saving their score.
 * Responsibilities include: getting the user's name, creating a scoreboard text
 * file if one doesn't exist, saving the score to the existing scoreboard text file
 * @author Jacob Strickland
 */
public class SaveScoreGUI extends JPanel implements ActionListener{
    
    private JFrame frame;
    private JTextField nameAnswer;
    private String type;
    private String name;
    private int score;
    
    /**
     * The constructor for the SaveScoreGUI.
     * @param random boolean, if the game that was finished started the audio clips randomly
     * @param score int, the score the user achieved in the finished game
     */
    public SaveScoreGUI(boolean random, int score)
    {
        //set the type of the game properly
        if(random)
            type = "R";
        else
            type = "S";
        
        //set the score
        this.score = score;
               
        //initizaling the GUI elements with text and action listeners
        frame = new JFrame();
        JLabel title = new JLabel("Please enter your name");
        nameAnswer = new JTextField();
        JButton close = new JButton("Submit");
        close.addActionListener(this);
        
        //setting the panel parameters and adding the elements
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        panel.add(nameAnswer);
        panel.add(close);
        
        //setting the frame information
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Name Select");
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
        name = nameAnswer.getText();    //get the text in the text field
        addToScoreboard();              //add the name and information to the scoreboard text file
        frame.dispose();                //get rid of the current GUI
        new GUI();                      //create a new main menu GUI to go back to
    }
    
    /**
     * The method that adds the current information to the scoreboard text file.
     */
    public void addToScoreboard()
    {
        String scoreLine = type + "," + name + "," + score + "," + java.time.LocalDate.now() + "\n"; //create the string to be added
        
        try
        {
            File file = new File("scoreboard.txt");
            file.createNewFile();                                                   //create a new file if one doesn't already exist
            Path filePath = Paths.get("scoreboard.txt");
            Files.write(filePath, scoreLine.getBytes(), StandardOpenOption.APPEND); //append the created string to the file
            
        }
        catch(IOException e)
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    }
    
}
