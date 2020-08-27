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
    
    private JButton quizStart;
    private JButton quizRand;
    private JButton highScore;
    private JButton setDirectory;
    private JButton exit;
    private JCheckBox saveScore;
    private JFrame frame;
    
    private boolean validDirectory = false;
    private boolean doSave = false;
    
    File[] filesInDirectory;
    
    public GUI()
    {
        frame = new JFrame();
        
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
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Music Memory Game");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == quizStart)
        {
            if(validDirectory)
            {
                System.out.println("Valid");
                frame.dispose();
                try
                {
                    new GameGUI(false, filesInDirectory, doSave);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
                new InvalidDirectoryGUI();
            
        }
        else if(e.getSource() == quizRand)
        {
            if(validDirectory)
            {
                System.out.println("Valid");
                frame.dispose();
                try
                {
                    new GameGUI(true, filesInDirectory, doSave);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
                new InvalidDirectoryGUI();
        }
        else if(e.getSource() == highScore)
        {
            ScoreboardManager scoreboard = null;
            try 
            { 
                scoreboard = new ScoreboardManager();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
            frame.dispose();
            new ScoreboardGUI(scoreboard);
            
        }
        else if(e.getSource() == setDirectory)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                filesInDirectory = chooser.getSelectedFile().listFiles();
                validDirectory = true;
            }
            else
            {
                System.out.println("No Selection");
                validDirectory = false;
            }
            
            //for(int i = 0; i < filesInDirectory.length; i++)
                //System.out.println(filesInDirectory[i]);
        }
        else if(e.getSource() == exit)
        {
            frame.dispose();
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ae)
    {
        Object source = ae.getItemSelectable();
        
        if(source == saveScore)
            doSave = true;
        
        if(ae.getStateChange() == ItemEvent.DESELECTED)
            doSave = false;
    }
        
}
