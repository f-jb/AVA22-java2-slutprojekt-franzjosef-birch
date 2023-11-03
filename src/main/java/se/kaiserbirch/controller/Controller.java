package se.kaiserbirch.controller;

import se.kaiserbirch.log.Log;
import se.kaiserbirch.model.ModelController;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Controller implements Flow.Processor<String,String> {
    UIState currentUIState;
    SubmissionPublisher<String> submissionPublisher = new SubmissionPublisher<>();
    Flow.Subscription subscription;
    ModelController modelController;
    public Controller(ModelController modelController){
        Log.LOG.subscribe(this);
        this.modelController = modelController;
        /*
        currentUIState = new UIState.Builder()
                .setAmountOfWorkUnitsInQueue(modelController.getAmountOfUnitsInWorkQueue())
                .setNoActiveProducers(modelController.isActiveProducersEmpty())
                .build();

         */

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);


    }

    @Override
    public void onNext(String item) {
        submissionPublisher.submit(item);
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
    public void removeProducer(){
        modelController.removeFirstProducer();
    }


    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {

        this.submissionPublisher.subscribe(subscriber);
    }
}
