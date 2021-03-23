package Editor;

// Java Program to create a simple JTextArea

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class ReceiveWindow extends JFrame {
    // JFrame
    static JFrame f;

    // JButton
    static JButton b;


    static HashMap<String, TextArea> t = new HashMap<String, TextArea>();

    public ReceiveWindow(ArrayList<String> queues) {

        f = new JFrame("Receiver");
        f.setLocation(900, 10);
        JPanel p = new JPanel();

        for (int i = 0; i < queues.size(); i++) {
            String queue = queues.get(i);
            t.put(queue, new TextArea());

            JLabel l = new JLabel(queue+ "  \n");
            t.get(queue).setColumns(50);
            t.get(queue).setRows(10);
            t.get(queue).setEditable(false);
            p.add(l);
            p.add(t.get(queue));
        }

        for (int i = 0; i < queues.size(); i++) {

            try {
                String queue= queues.get(i);
                //Reception.recevoir(t.get(queue), queue);
                Receive.recevoir(t.get(queue), queue);
            } catch (Exception e) {

            }
        }
        f.add(p);
        // set the size of frame
        f.setSize(500, 180*queues.size());
        f.setVisible(true);
    }

    // main class
   /*public static void main(String[] args) {
        ArrayList<String> queues = new ArrayList<String>();
        int n=4;
        for (int i =1 ;i<=n;i++) {
            String queueName = "file" + i;
            queues.add(queueName);

        }
        ReceiveWindow ri = new ReceiveWindow(queues);
    }
*/

}

