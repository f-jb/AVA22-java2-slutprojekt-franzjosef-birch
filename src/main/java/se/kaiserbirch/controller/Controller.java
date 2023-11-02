package se.kaiserbirch.controller;

import se.kaiserbirch.model.ModelController;
import se.kaiserbirch.log.Log;

import java.util.concurrent.Flow;

public class Controller implements Flow.Subscriber<String>{
    Flow.Subscription subscription;
    ModelController modelController;
    public Controller(ModelController modelController){
        Log.LOG.subscribe(this);
        this.modelController = modelController;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);


    }

    @Override
    public void onNext(String item) {
        System.out.print(item);
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
}
