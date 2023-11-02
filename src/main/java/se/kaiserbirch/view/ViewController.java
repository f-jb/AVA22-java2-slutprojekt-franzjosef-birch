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
    }
    public void init(){
        ProgressAndButtonsView progressAndButtonsView = new ProgressAndButtonsView.Builder()
                .setAddProducerButtonText("Add Producer")
                .setRemoveProducerButtonText("Remove Producer")
                .setAddProducerFunction(e -> controller.addProducer())
                .setRemoveProducerFunction(e -> controller.removeProducer())
                .build();
        setLayout(new BorderLayout());
        add(progressAndButtonsView,BorderLayout.CENTER);
        add(logView,BorderLayout.PAGE_END);
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
    public void onNext(String item) {
        logView.addToLog(item);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
