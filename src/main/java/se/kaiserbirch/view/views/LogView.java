package se.kaiserbirch.view.views;

import javax.swing.*;
import java.awt.*;

public class LogView extends JPanel {
    JTextArea textLog = new JTextArea(20,40);
    public LogView(){
        JScrollPane scrollPane = new JScrollPane(textLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
//        add(textLog);

    }

    public void addToLog(String logEntry){
        textLog.insert(logEntry,0);
        repaint();
    }


}
