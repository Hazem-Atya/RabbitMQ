// Java Program to create a simple JTextArea

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

class Window extends JFrame implements DocumentListener {

    // JFrame
    JFrame f;
    boolean test=true;
    //String content;
    String queueName;
    int n;
    JScrollPane scroll;

    // label to display text
    JLabel l;

    // text area
    JTextArea jt;


    public Window(String queueName,int n) {
        this.n=n;
        this.queueName = queueName;
    }


    public void afficher(String titre) {
        // create a new frame to store text field and button
        f = new JFrame(titre);
        // create a text area, specifying the rows and columns
        jt = new JTextArea(20, 40);
        scroll = new JScrollPane(jt);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jt.getDocument().addDocumentListener(this);
        JPanel p = new JPanel();

        // add the text area and button to panel

        p.add(scroll);

        f.add(p);
        f.pack();

        // set the size of frame


        f.setSize(450, 400);

        f.setVisible(true);

        HashMap<String, JTextArea> t = new HashMap<String, JTextArea>();
        for (int i = 1; i < n; i++) {
            int numQueue = queueNum();
            String queue = ((numQueue + i) % n == 0) ? "sender" + n : "sender" + (numQueue + i) % n;
            t.put(queue, new JTextArea());
        }
        TreeMap<String, JTextArea> sorted = new TreeMap<>(t);
        ArrayList<String> qList = new ArrayList<>(t.keySet());
        for (int i = 1; i < n; i++) {
            String queue = qList.get(qList.size()-(i));
            JLabel l = new JLabel(queue);
            t.get(queue).setColumns(40);
            t.get(queue).setRows(10);
            t.get(queue).setEditable(false);
            p.add(l);
            p.add(sorted.get(queue));
            try {
                Receive.recevoir(t.get(queue), queue, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void insertUpdate(DocumentEvent evt) {
        if(!test)
            return;
        int startOffset = evt.getOffset();
        int endOffset = startOffset + evt.getLength();

        String modified = "";
        try {
            modified = jt.getText(startOffset, endOffset - startOffset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        String msg =modified;
        try {
            for (int i=0;i<n-1;i++)
                Send.envoyer(msg, queueName,startOffset,endOffset,"i","sender"+queueNum());
        } catch (Exception exception) {

        }

    }

    @Override
    public void removeUpdate(DocumentEvent evt) {
        if(!test)
            return;
        int startOffset = evt.getOffset();
        int endOffset = startOffset + evt.getLength();
        String msg = "";
        try {
            for (int i=0;i<n-1;i++)
                Send.envoyer(msg, queueName,startOffset,endOffset,"d","sender"+queueNum());
        } catch (Exception exception) {

        }

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
    public int queueNum(){
        return Integer.parseInt("0"+queueName.charAt(queueName.length() - 1));
    }
}
