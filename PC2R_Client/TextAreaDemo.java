package music;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.SliderUI;

import org.omg.stub.java.rmi._Remote_Stub;

public class TextAreaDemo extends JFrame {
    //============================================== instance variables
	static JTextArea _resultArea = new JTextArea(6, 20);
        
    //====================================================== constructor
    public TextAreaDemo() {
        JScrollPane scrollingArea = new JScrollPane(_resultArea);        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollingArea, BorderLayout.CENTER);
        
        this.setContentPane(content);
        this.setTitle("Chat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
    
    //============================================================= main
    public static String getText() {
        JFrame win = new TextAreaDemo();
        win.setVisible(true);
        while(true)
        	if(_resultArea.getText().matches(".+OK")){
                win.setVisible(false);
        		return(_resultArea.getText().split("OK")[0]);
        	}
    }
}
