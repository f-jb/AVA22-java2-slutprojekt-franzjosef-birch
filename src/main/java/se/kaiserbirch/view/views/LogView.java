package se.kaiserbirch.view.views;

import javax.swing.*;

public class LogView extends JPanel {
    JTextArea textLog = new JTextArea();
    public LogView(){
        add(textLog);
    }

    public void addToLog(String logEntry){
        textLog.insert(logEntry,0);
    }


}
