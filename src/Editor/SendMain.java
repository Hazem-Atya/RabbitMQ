package Editor;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SendMain {


    public static void main(String[] args) {
        SendWindow p1 = new SendWindow("file1");
        p1.afficher("P1");
        SendWindow p2 = new SendWindow("file2");
        p2.afficher("P2");

    }
}