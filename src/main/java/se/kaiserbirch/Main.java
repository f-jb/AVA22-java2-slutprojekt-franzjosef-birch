package se.kaiserbirch;

import se.kaiserbirch.controller.Controller;
import se.kaiserbirch.model.ModelController;
import se.kaiserbirch.view.ViewController;

public class Main {
    public static void main(String[] args) {
        /*
        controller.addProducer();
        controller.addProducer();
        controller.addProducer();
        controller.addProducer();
        controller.addProducer();
        controller.addProducer();
        controller.addProducer();
         */
        Controller controller = new Controller(new ModelController());
        ViewController viewController = new ViewController(controller);
        viewController.init();

    }
}