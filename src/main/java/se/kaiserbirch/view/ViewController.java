package se.kaiserbirch.view;

import se.kaiserbirch.controller.Controller;
import se.kaiserbirch.view.views.LogView;
import se.kaiserbirch.view.views.ProgressAndButtonsView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class ViewController extends JFrame implements Flow.Subscriber<String>{
    private Flow.Subscription subscription;
    private final LogView logView = new LogView();
    Controller controller;
    public ViewController(Controller controller){
        this.controller = controller;
        controller.subscribe(this);
    }
    public void init(){
        ProgressAndButtonsView progressAndButtonsView = new ProgressAndButtonsView.Builder()
                .setAddProducerButtonText("Add Producer")
                .setRemoveProducerButtonText("Remove Producer")
                .setAddProducerFunction(e -> controller.addProducer())
                .setRemoveProducerFunction(e -> controller.removeProducer())
                .build();
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        add(progressAndButtonsView,gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        add(logView,gridBagConstraints);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String entry) {
        logView.addToLog(entry);
        revalidate();
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
