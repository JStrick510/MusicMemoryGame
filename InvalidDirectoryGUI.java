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

public class InvalidDirectoryGUI extends JPanel implements ActionListener{
    
    private JFrame frame;
    
    public InvalidDirectoryGUI()
    {
        frame = new JFrame();
        JLabel title = new JLabel("Please select a valid file directory");
        JButton close = new JButton("Close");
        close.addActionListener(this);
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 3, 10));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        panel.add(close);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Error");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
         
    }
    
    public void actionPerformed(ActionEvent e) {
        frame.dispose();    
    }   
    
}
