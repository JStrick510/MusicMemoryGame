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
 * The ScoreboardGUI class is responsible for displaying the high scores.
 * Responsibilities include: displaying scoreboards based on the mode the
 * respective scores were achieved in, allowing the user to go back to the 
 * main menu
 * @author Jacob Strickland
 */
public class ScoreboardGUI extends JPanel implements ActionListener{
    
    private JButton showAll;    //button to show the overall leaderboard
    private JButton showStart;  //button to show the start leaderboard
    private JButton showRand;   //button to show the random leaderboard
    private JButton close;      //button to close the GUI
    private JFrame frame;
    
    //information for the #1 player
    private JLabel type1;
    private JLabel name1;
    private JLabel score1;
    private JLabel date1;
    //information for the #2 player
    private JLabel type2;
    private JLabel name2;
    private JLabel score2;
    private JLabel date2;
    //information for the #3 player
    private JLabel type3;
    private JLabel name3;
    private JLabel score3;
    private JLabel date3;
    //information for the #4 player
    private JLabel type4;
    private JLabel name4;
    private JLabel score4;
    private JLabel date4;
    //information for the #5 player
    private JLabel type5;
    private JLabel name5;
    private JLabel score5;
    private JLabel date5;
    
    private ScoreboardManager scoreboard;   //to get the information to display
    private String[] types;                 //array containing all of the top five types for the respective leaderboard
    private String[] names;                 //array containing all of the top five names for the respective leaderboard
    private String[] scores;                //array containing all of the top five scores for the respective leaderboard
    private String[] dates;                 //array containing all of the top five dates for the respective leaderboard
    
    /**
     * The constructor for the ScoreboardGUI class.
     * @param scoreboard ScoreboardManager, the class that gives this GUI information
     */
    public ScoreboardGUI(ScoreboardManager scoreboard)
    {
        this.scoreboard = scoreboard;
        
        //initizaling the GUI elements with text and action listeners
        frame = new JFrame();
        showAll = new JButton("Show All");
        showAll.addActionListener(this);
        showStart = new JButton("Start Only");
        showStart.addActionListener(this);
        showRand = new JButton("Random Only");
        showRand.addActionListener(this);
        close = new JButton("Close");
        close.addActionListener(this);
        JLabel title1 = new JLabel("HIGH");
        JLabel title2 = new JLabel("SCORES");
        JLabel type = new JLabel("Type");
        JLabel name = new JLabel("Name");
        JLabel score = new JLabel("Score");
        JLabel date = new JLabel("Date");
        type1 = new JLabel("");
        name1 = new JLabel("");
        score1 = new JLabel("");
        date1 = new JLabel("");
        type2 = new JLabel("");
        name2 = new JLabel("");
        score2 = new JLabel("");
        date2 = new JLabel("");
        type3 = new JLabel("");
        name3 = new JLabel("");
        score3 = new JLabel("");
        date3 = new JLabel("");
        type4 = new JLabel("");
        name4 = new JLabel("");
        score4 = new JLabel("");
        date4 = new JLabel("");
        type5 = new JLabel("");
        name5 = new JLabel("");
        score5 = new JLabel("");
        date5 = new JLabel("");
        
        //setting the panel parameters and adding the elements
        int i = 8;
        int j = 4;
        JPanel[][] panelHolder = new JPanel[i][j];
        setLayout(new GridLayout(i,j));
        
        for(int m = 0; m < i; m ++) //initialize a 2D array of blank panels
        {
            for(int n = 0; n < j; n++)
            {
                panelHolder[m][n] = new JPanel();
                add(panelHolder[m][n]);
            }
        }
        
        //add elements that aren't blank at specific coordinates
        panelHolder[0][1].add(title1);
        panelHolder[0][2].add(title2);
        panelHolder[1][0].add(type);
        panelHolder[1][1].add(name);
        panelHolder[1][2].add(score);
        panelHolder[1][3].add(date);
        panelHolder[2][0].add(type1);
        panelHolder[2][1].add(name1);
        panelHolder[2][2].add(score1);
        panelHolder[2][3].add(date1);
        panelHolder[3][0].add(type2);
        panelHolder[3][1].add(name2);
        panelHolder[3][2].add(score2);
        panelHolder[3][3].add(date2);
        panelHolder[4][0].add(type3);
        panelHolder[4][1].add(name3);
        panelHolder[4][2].add(score3);
        panelHolder[4][3].add(date3);
        panelHolder[5][0].add(type4);
        panelHolder[5][1].add(name4);
        panelHolder[5][2].add(score4);
        panelHolder[5][3].add(date4);
        panelHolder[6][0].add(type5);
        panelHolder[6][1].add(name5);
        panelHolder[6][2].add(score5);
        panelHolder[6][3].add(date5);
        panelHolder[7][0].add(showAll);
        panelHolder[7][1].add(showStart);
        panelHolder[7][2].add(showRand);
        panelHolder[7][3].add(close);
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridLayout(8, 4));
        
        for(int x = 0; x < i; x++)  //add the panels stored in the 2D array to the panel in order
        {
            for(int y = 0; y < j; y++)
                panel.add(panelHolder[x][y]);
        }

        //setting the frame information
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Music Memory Game");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    /**
     * The method that makes things happen when the buttons are pressed.
     * @param e, ActionEvent a button that was pressed
     */
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == showAll)            //if button is pressed to show overall leaderboard
        {
            scoreboard.sortScores("");          //sort all scores
            types = scoreboard.getTypes(0);     //get the types to display from the ScoreboardManager
            names = scoreboard.getNames(0);     //get the names to display from the ScoreboardManager
            scores = scoreboard.getScores(0);   //get the scores to display from the ScoreboardManager
            dates = scoreboard.getDates(0);     //get the dates to display from the ScoreboardManager
            setText();                          //update the GUI
        }
        else if(e.getSource() == showStart)     //if button is pressed to show the starting leaderboard
        {
            scoreboard.sortScores("S");         //sort the scores only that played from the start
            types = scoreboard.getTypes(1);     //get the types to display from the ScoreboardManager
            names = scoreboard.getNames(1);     //get the names to display from the ScoreboardManager
            scores = scoreboard.getScores(1);   //get the scores to display from the ScoreboardManager
            dates = scoreboard.getDates(1);     //get the dates to display from the ScoreboardManager
            setText();                          //update the GUI
        }
        else if(e.getSource() == showRand)      //if button is pressed to show the random leaderboard
        {
            scoreboard.sortScores("R");         //sort the scores that played from a random start time
            types = scoreboard.getTypes(2);     //get the types to display from the ScoreboardManager
            names = scoreboard.getNames(2);     //get the names to display from the ScoreboardManager
            scores = scoreboard.getScores(2);   //get the scores to display from the ScoreboardManager
            dates = scoreboard.getDates(2);     //get the dates to display from the ScoreboardManager
            setText();                          //update the GUI
        }
        else if(e.getSource() == close)         //if button is pressed to close the GUI
        {
            frame.dispose();                    //get rid of the GUI
            new GUI();                          //go back to the main menu GUI
        }
    }
    
    /**
     * The method that updates the ScoreboardGUI with the proper information.
     */
    public void setText()
    {
        if(!(types[0].equals("N")))     //if the type is not null, display the information
        {
            type1.setText(types[0]);
            name1.setText(names[0]);
            score1.setText(scores[0]);
            date1.setText(dates[0]);
        }
        else                            //otherwise display nothing
        {
            type1.setText("");
            name1.setText("");
            score1.setText("");
            date1.setText("");
        }
        if(!(types[1].equals("N")))     //if the type is not null, display the information
        {
            type2.setText(types[1]);
            name2.setText(names[1]);
            score2.setText(scores[1]);
            date2.setText(dates[1]);
        }
        else                            //otherwise display nothing
        {
            type2.setText("");
            name2.setText("");
            score2.setText("");
            date2.setText("");
        }
        if(!(types[2].equals("N")))     //if the type is not null, display the information
        {
            type3.setText(types[2]);
            name3.setText(names[2]);
            score3.setText(scores[2]);
            date3.setText(dates[2]);
        }
        else                            //otherwise display nothing
        {
            type3.setText("");
            name3.setText("");
            score3.setText("");
            date3.setText("");
        }
        if(!(types[3].equals("N")))     //if the type is not null, display the information
        {
            type4.setText(types[3]);
            name4.setText(names[3]);
            score4.setText(scores[3]);
            date4.setText(dates[3]);
        }
        else                            //otherwise display nothing
        {
            type4.setText("");
            name4.setText("");
            score4.setText("");
            date4.setText("");
        }
        if(!(types[4].equals("N")))
        {
            type5.setText(types[4]);
            name5.setText(names[4]);
            score5.setText(scores[4]);
            date5.setText(dates[4]);
        }
        else                            //otherwise display nothing
        {
            type5.setText("");
            name5.setText("");
            score5.setText("");
            date5.setText("");
        }
    }
        
}
