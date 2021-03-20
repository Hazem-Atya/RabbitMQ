package Editor;

// Java Program to create a simple JTextArea

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;

class SendWindow extends JFrame implements DocumentListener {

    // JFrame
    JFrame f;

    String content;
    String queueName;
    JScrollPane scroll;

    // label to display text
    JLabel l;

    // text area
    JTextArea jt;


    public SendWindow(String queueName) {
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


        p.add ( scroll );

      //  p.add(jt);
        f.add(p);
        f.pack ();

        // set the size of frame


        f.setSize(500, 400);

        f.setVisible(true);
    }


    @Override
    public void insertUpdate(DocumentEvent e) {

        content = this.jt.getText();
        System.out.println("from p1" + content);
        try {
            Envoi.envoyer(content, queueName);
        } catch (Exception exception) {

        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        content = this.jt.getText();
        System.out.println("from p1" + content);
        try {
            Envoi.envoyer(content, queueName);
        } catch (Exception exception) {

        }

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
