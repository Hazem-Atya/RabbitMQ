package Editor;

// Java Program to create a simple JTextArea

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

class SendWindow extends JFrame implements DocumentListener {

    // JFrame
    JFrame f;
    boolean test=true;
    //String content;
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
        this.
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

        //  p.add(jt);
        f.add(p);
        f.pack();

        // set the size of frame


        f.setSize(500, 400);

        f.setVisible(true);
        try {
            //Reception.recevoir(t.get(queue), queue);
            Receive.recevoir(jt, queueName,this);
        } catch (Exception e) {

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
        System.out.println("Start offset: " + startOffset);
        System.out.println("end offset: " + endOffset);
        System.out.println("Insertion: " + modified);
        String msg =modified;
        try {
            //Envoi.envoyer(msg, queueName,startOffset,endOffset,"i");
            Send.envoyer(msg, queueName,startOffset,endOffset,"i");
        } catch (Exception exception) {

        }

    }

    @Override
    public void removeUpdate(DocumentEvent evt) {
        if(!test)
            return;
        int startOffset = evt.getOffset();
        int endOffset = startOffset + evt.getLength();

        System.out.println("Start offset: " + startOffset);
        System.out.println("end offset: " + endOffset);
        System.out.println("Delete");
        String msg = "";
        try {
            //Envoi.envoyer(msg, queueName,startOffset,endOffset,"d");
            Send.envoyer(msg, queueName,startOffset,endOffset,"d");
        } catch (Exception exception) {

        }

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
