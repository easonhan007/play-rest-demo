package utils;

import models.*;
import play.Logger;

public class DemoData {

    public static Customer tom;
    public static Customer jack;
    public static Customer joe;

    public static void loadDemoData() {
        
        Logger.info("Loading Demo Data");

        Logger.info("Create customer");
        tom = new Customer("tom", "AKL", "23333333");
        tom.save();
        jack = new Customer("jack", "AKL", "9998888");
        jack.save();
        joe = new Customer("joe", "AKL", "123435");
        joe.save();
    }

}
