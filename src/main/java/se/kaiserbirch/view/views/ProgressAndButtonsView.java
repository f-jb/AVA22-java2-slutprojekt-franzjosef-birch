package se.kaiserbirch.view.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProgressAndButtonsView extends JPanel{
    JProgressBar progressBar;
    JButton addProducerButton;
    JButton removeProducerButton;

    private ProgressAndButtonsView(Builder builder) {
        setLayout(new FlowLayout());
        this.addProducerButton = builder.addProducerButton;
        this.removeProducerButton = builder.removeProducerButton;
        this.progressBar = builder.progressBar;
        add(progressBar);
        add(addProducerButton);
        add(removeProducerButton);
    }

    public static class Builder{
        JButton addProducerButton = new JButton();
        JButton removeProducerButton = new JButton();
        JProgressBar progressBar = new JProgressBar(0,20);
        public Builder setAddProducerButtonText(String addProducerButtonText){
            addProducerButton.setText(addProducerButtonText);
            return this;
        }

        public Builder setAddProducerFunction(ActionListener addProducer){
            this.addProducerButton.addActionListener(addProducer);
            return this;
        }
        public Builder setRemoveProducerButtonText(String removeProducerButtonText){
            this.removeProducerButton.setText(removeProducerButtonText);
            return this;
        }
        public Builder setRemoveProducerFunction(ActionListener removeProducer){
            this.removeProducerButton.addActionListener(removeProducer);
            return this;
        }
        public ProgressAndButtonsView build(){
            return new ProgressAndButtonsView(this);
        }



    }


}
