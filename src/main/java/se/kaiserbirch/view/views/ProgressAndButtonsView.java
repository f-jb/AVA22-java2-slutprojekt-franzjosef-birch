package se.kaiserbirch.view.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProgressAndButtonsView extends JPanel{
    JProgressBar progressBar;
    JButton addProducerButton;
    JButton removeProducerButton;

    private ProgressAndButtonsView(Builder builder) {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.progressBar = builder.progressBar;

        this.addProducerButton = builder.addProducerButton;
        this.removeProducerButton = builder.removeProducerButton;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(progressBar,gridBagConstraints);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        add(addProducerButton,gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 1;
        add(removeProducerButton,gridBagConstraints);
    }

    public void setProgress(int amountOfWorkUnitsInQueue) {
        if(amountOfWorkUnitsInQueue <= progressBar.getMaximum()* 0.1){
            progressBar.setBackground(Color.RED);
        }else if(amountOfWorkUnitsInQueue >= progressBar.getMaximum()* 0.9){
            progressBar.setBackground(Color.RED);
        } else {
            progressBar.setBackground(Color.GREEN);
        }
        progressBar.setValue(amountOfWorkUnitsInQueue);
        progressBar.validate();
    }

    public static class Builder{
        JButton addProducerButton = new JButton();
        JButton removeProducerButton = new JButton();
        JProgressBar progressBar = new JProgressBar(0,100);
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
