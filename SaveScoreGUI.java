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

public class SaveScoreGUI extends JPanel implements ActionListener{
    
    private JFrame frame;
    private JTextField nameAnswer;
    private String type;
    private String name;
    private int score;
    
    public SaveScoreGUI(boolean random, int score)
    {
        if(random)
            type = "R";
        else
            type = "S";
        
        this.score = score;
        
        frame = new JFrame();
        JLabel title = new JLabel("Please enter your name");
        nameAnswer = new JTextField();
        JButton close = new JButton("Submit");
        close.addActionListener(this);
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        panel.add(nameAnswer);
        panel.add(close);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Name Select");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
         
    }
    
    public void actionPerformed(ActionEvent e) {
        name = nameAnswer.getText();
        addToScoreboard();
        frame.dispose();
        new GUI();
    }
    
    public void addToScoreboard()
    {
        String scoreLine = type + "," + name + "," + score + "," + java.time.LocalDate.now() + "\n";
        System.out.println(scoreLine);
        
        try
        {
            File file = new File("scoreboard.txt");
            if(file.createNewFile())
            {
                Path filePath = Paths.get("scoreboard.txt");
                Files.write(filePath, scoreLine.getBytes(), StandardOpenOption.APPEND);
            }
            else
            {
                Path filePath = Paths.get("scoreboard.txt");
                Files.write(filePath, scoreLine.getBytes(), StandardOpenOption.APPEND);
            }
            
        }
        catch(IOException e)
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    }
    
}
