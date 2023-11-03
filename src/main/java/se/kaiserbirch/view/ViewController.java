package se.kaiserbirch.view;

import se.kaiserbirch.controller.Controller;
import se.kaiserbirch.controller.UIState;
import se.kaiserbirch.view.views.LogView;
import se.kaiserbirch.view.views.ProgressAndButtonsView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class ViewController extends JFrame implements Flow.Subscriber<UIState> {
    private final LogView logView = new LogView();
    Controller controller;
    private Flow.Subscription subscription;
    private ProgressAndButtonsView progressAndButtonsView;

    public ViewController(Controller controller) {
        this.controller = controller;
        controller.subscribe(this);
    }

    public void init() {
        progressAndButtonsView = new ProgressAndButtonsView.Builder()
                .setAddProducerButtonText("Add Producer")
                .setRemoveProducerButtonText("Remove Producer")
                .setAddProducerFunction(e -> controller.addProducer())
                .setRemoveProducerFunction(e -> controller.removeProducer())
                .build();
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        add(progressAndButtonsView, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        add(logView, gridBagConstraints);
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
    public void onNext(UIState entry) {
        switch (entry.getUpdated()) {
            case LOG_ENTRY -> logView.addToLog(entry.getLogEntry());
            case WORK_UNITS -> progressAndButtonsView.setProgress(entry.getAmountOfWorkUnitsInQueue());

        }

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
