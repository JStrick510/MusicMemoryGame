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

public class ScoreboardGUI extends JPanel implements ActionListener{
    
    private JButton showAll;
    private JButton showStart;
    private JButton showRand;
    private JButton close;
    private JFrame frame;
    
    private JLabel type1;
    private JLabel name1;
    private JLabel score1;
    private JLabel date1;
    private JLabel type2;
    private JLabel name2;
    private JLabel score2;
    private JLabel date2;
    private JLabel type3;
    private JLabel name3;
    private JLabel score3;
    private JLabel date3;
    private JLabel type4;
    private JLabel name4;
    private JLabel score4;
    private JLabel date4;
    private JLabel type5;
    private JLabel name5;
    private JLabel score5;
    private JLabel date5;
    
    private boolean validDirectory = false;
    
    File[] filesInDirectory;
    
    public ScoreboardGUI()
    {
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
        
        int i = 8;
        int j = 4;
        JPanel[][] panelHolder = new JPanel[i][j];
        setLayout(new GridLayout(i,j));
        
        for(int m = 0; m < i; m ++)
        {
            for(int n = 0; n < j; n++)
            {
                panelHolder[m][n] = new JPanel();
                add(panelHolder[m][n]);
            }
        }
        
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
        
        for(int x = 0; x < i; x++)
        {
            for(int y = 0; y < j; y++)
                panel.add(panelHolder[x][y]);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Music Memory Game");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == showAll)
        {
 
        }
        else if(e.getSource() == showStart)
        {
           
        }
        else if(e.getSource() == showRand)
        {
                        
        }
        else if(e.getSource() == close)
        {
            frame.dispose();
            new GUI();
        }
    }
        
}
