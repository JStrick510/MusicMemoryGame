package musicmemorygame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The GUI class is responsible for displaying the main menu GUI that branches 
 * to the others.
 * Responsibilities include: selecting a file directory, allowing user to go 
 * to different GUIs
 * @author Jacob Strickland
 */
public class GUI extends JPanel implements ActionListener, ItemListener{
    
    private JButton quizStart;      //button for audio files playing from start
    private JButton quizRand;       //button for audio files playing from a random point
    private JButton highScore;      //button to open highScoreGUI
    private JButton setDirectory;   //button to set the directory for audio files
    private JButton exit;           //button to exit
    private JCheckBox saveScore;    //checkbox to see if user wants to save score
    private JFrame frame;
    
    private boolean validDirectory = false;     //boolean for checking if a valid directory has been selected
    private boolean doSave = false;             //boolean for checking if saving scores
    
    private File[] filesInDirectory;            //array of Files that contains the files in the selected directory, if valid
    
    /**
     * The constructor for the GUI class.
     */
    public GUI()
    {
        frame = new JFrame();
        
        //initizaling the GUI elements with text and action listeners
        quizStart = new JButton("Quiz on songs from the start");
        quizStart.addActionListener(this);
        quizRand = new JButton("Quiz on songs from a random point");
        saveScore = new JCheckBox("Challenge mode (save score)");
        saveScore.addItemListener(this);
        quizRand.addActionListener(this);
        highScore = new JButton("Check High Scores");
        highScore.addActionListener(this);
        setDirectory = new JButton("Set Music Directory");
        setDirectory.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        
        JLabel title = new JLabel("Welcome to the Music Memory Game");
        
        //setting the panel parameters and adding the elements
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridLayout(0, 1, 0, 10));
        panel.add(title);
        panel.add(quizStart);
        panel.add(quizRand);
        panel.add(saveScore);
        panel.add(highScore);
        panel.add(setDirectory);
        panel.add(exit);
        
        //setting the frame information
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Music Memory Game");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    /**
     * The main method for the overall program.
     */
    public static void main(String[] args) {
        new GUI();
    }

    @Override
    /**
     * The method that makes things happen when the buttons are pressed.
     * @param e ActionEvent, a button that was pressed
     */
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == quizStart)                              //if the button to start the quiz with files play from the start is pressed
        {
            if(validDirectory)                                      //check if the directory is valid
            {
                frame.dispose();                                    //get rid of the current main menu GUI
                try
                {
                    new GameGUI(false, filesInDirectory, doSave);   //create a new GameGUI set up for playing from start
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
                new InvalidDirectoryGUI();                          //inform the user that the directory chosen is not valid
            
        }
        else if(e.getSource() == quizRand)                          //if the button to start the quiz with files play from the start is pressed
        {
            if(validDirectory)                                      //check if the directory is valid
            {
                frame.dispose();                                    //get rid of the current main menu GUI
                try
                {
                    new GameGUI(true, filesInDirectory, doSave);    //create a new GameGUI set up for playing from random point
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
                new InvalidDirectoryGUI();                          //inform the user that the directory chosen is not valid
        }
        else if(e.getSource() == highScore)             //if the button to see the high scores is pressed
        {
            ScoreboardManager scoreboard = null;        //initilize a new scoreboard
            try                                         //try-catch since it opens a new file in the constructor
            { 
                scoreboard = new ScoreboardManager();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
            frame.dispose();                            //get rid of the current main menu GUI
            new ScoreboardGUI(scoreboard);              //create a new ScoreboardGUI with the previously created scoreboard
            
        }
        else if(e.getSource() == setDirectory)                                  //if the button is pressed to set the current directory
        {
            //create a new file chooser that only allows directories to be selected
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)     //if valid selection
            {
                filesInDirectory = chooser.getSelectedFile().listFiles();       //set the files in directory
                validDirectory = true;                                          //set valid directory flag
                //put something here to only allow directories with .wav files
                //to be chosen and only allow .wav files to go in directory?
            }
            else
            {
                System.out.println("No Selection");
                validDirectory = false;
            }
        }
        else if(e.getSource() == exit)  //if the button is pressed to exit
        {
            frame.dispose();            //get rid of the current main menu GUI
        }
    }
    
    @Override
    /**
     * The method that makes things happen when the checkbox's state is changed.
     * @param ae ItemEvent, the check box state changing
     */
    public void itemStateChanged(ItemEvent ae)
    {
        Object source = ae.getItemSelectable();
        
        if(source == saveScore)                         //if the checkbox is selected
            doSave = true;                              //set the saving score flag to true
        
        if(ae.getStateChange() == ItemEvent.DESELECTED) //if the checkbox is unselected
            doSave = false;                             //set the savings score flag to false
    }
        
}
