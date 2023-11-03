package se.kaiserbirch.controller;

import se.kaiserbirch.log.Log;
import se.kaiserbirch.model.ModelController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Controller implements Flow.Processor<String, UIState> {
    SubmissionPublisher<UIState> submissionPublisher = new SubmissionPublisher<>();
    Flow.Subscription subscription;
    ModelController modelController;

    public Controller(ModelController modelController) {
        Log.LOG.subscribe(this);
        this.modelController = modelController;
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new WorkPercentageChecker());

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);


    }

    @Override
    public void onNext(String item) {
        UIState uiStateToSend = new UIState.Builder()
                .setUpdate(UIState.Updated.LOG_ENTRY)
                .setLogEntry(item)
                .build();
        submissionPublisher.submit(uiStateToSend);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public void addProducer() {
        modelController.addANewProducer();
    }

    public void removeProducer() {
        modelController.removeFirstProducer();
    }


    @Override
    public void subscribe(Flow.Subscriber<? super UIState> subscriber) {
        this.submissionPublisher.subscribe(subscriber);
    }

    private class WorkPercentageChecker implements Runnable {
        boolean active = true;

        @Override
        public void run() {
            while (active) {
                try {
                    int amountOfWorkUnits = modelController.getAmountOfUnitsInWorkQueue();
                    UIState stateToSend = new UIState.Builder()
                            .setUpdate(UIState.Updated.WORK_UNITS)
                            .setAmountOfWorkUnitsInQueue(amountOfWorkUnits)
                            .build();
                    submissionPublisher.submit(stateToSend);
                    SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }
}
