package musicmemorygame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JPanel implements ActionListener{
    
    private JButton quizStart;
    private JButton quizRand;
    private JButton highScore;
    private JButton setDirectory;
    private JButton exit;
    private JFrame frame;
    
    private boolean validDirectory = false;
    
    public GUI()
    {
        frame = new JFrame();
        
        quizStart = new JButton("Quiz on songs from the start");
        quizStart.addActionListener(this);
        quizRand = new JButton("Quiz on songs from a random point");
        quizRand.addActionListener(this);
        highScore = new JButton("Check High Scores");
        highScore.addActionListener(this);
        setDirectory = new JButton("Set Music Directory");
        setDirectory.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        
        JLabel title = new JLabel("Welcome to the Music Memory Game");
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        panel.setLayout(new GridLayout(0, 1, 0, 50));
        panel.add(title);
        panel.add(quizStart);
        panel.add(quizRand);
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
        InvalidDirectoryGUI error;
        
        if(e.getSource() == quizStart)
        {
            if(validDirectory)
            {
                System.out.println("Valid");
            }
            else
                error = new InvalidDirectoryGUI();
            
        }
        else if(e.getSource() == quizRand)
        {
            if(validDirectory)
            {
                System.out.println("Valid");
            }
            else
                error = new InvalidDirectoryGUI();
        }
        else if(e.getSource() == highScore)
        {
            
        }
        else if(e.getSource() == setDirectory)
        {
            File[] filesInDirectory = null;
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
        
}