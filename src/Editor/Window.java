package Editor;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

class Window extends JFrame implements DocumentListener {

    // JFrame
    JFrame f;

    JScrollPane scroll;

    // label to display text
    JLabel l;

    // text area
    JTextArea jt;
    JTextArea autre;

    public JTextArea getJt() {
        return jt;
    }

    public void setAutre(JTextArea autre) {
        this.autre = autre;
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

        //  p.add(jt);
        f.add(p);
        f.pack();

        // set the size of frame


        f.setSize(500, 400);

        f.setVisible(true);
    }


    @Override
    public void insertUpdate(DocumentEvent evt) {
        System.out.println(this.f.getTitle() + " has " + this.jt.getText().length() + " caracters");
        if (this.f.getTitle() == "Window 1") {

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
            this.autre.insert(modified, startOffset);

        }

    }


    @Override
    public void removeUpdate(DocumentEvent evt) {

        if (this.f.getTitle() == "Window 1") {
            int startOffset = evt.getOffset();
            int endOffset = startOffset + evt.getLength();

            System.out.println("Start offset: " + startOffset);
            System.out.println("end offset: " + endOffset);
            System.out.println("Delete");
            this.autre.replaceRange("", startOffset, endOffset);

        }

    }


    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    public static void main(String[] args) {
        Window w1 = new Window();
        Window w2 = new Window();
        w1.afficher("Window 1");
        w2.afficher("Window 2");

        w1.setAutre(w2.getJt());

    }
}
