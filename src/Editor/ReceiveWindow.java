package Editor;

// Java Program to create a simple JTextArea
import javax.swing.*;
import java.awt.*;

class ReceiveWindow extends JFrame {
    // JFrame
    static JFrame f;

    // JButton
    static JButton b;

    // label to display text
    static JLabel l1;
    static JLabel l2;
    static TextArea t1;
    static TextArea t2;
    public ReceiveWindow()
    {
        f = new JFrame("Receiver");
        l1 = new JLabel("Sender 1: \n");
        t1= new TextArea("");
        t1.setColumns(50);
        t1.setRows(10);
        t1.setEditable(false);
        l2 = new JLabel("Sender 2: \n");
        t2= new TextArea();
        t2.setColumns(50);
        t2.setRows(10);
        t2.setEditable(false);
        JPanel p = new JPanel();
        //setLayout(new BoxLayout(p,BoxLayout.PAGE_AXIS) );

        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);

        f.add(p);
        // set the size of frame
        f.setSize(500, 500);
        try{
            Reception.recevoir(t1,"file1");
            Reception.recevoir(t2,"file2");
        }catch(Exception e){

        }

        f.setVisible(true);
    }

    // main class
    public static void main(String[] args)
    {
        ReceiveWindow ri = new ReceiveWindow();
    }


}

