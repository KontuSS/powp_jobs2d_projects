package edu.kis.powp.jobs2d.command.manager;

import java.util.logging.Logger;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

public class LoggerCommandChangeObserver implements Subscriber {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void update() {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (command != null) {
            System.out.println("Current command set to: " + command.toString());
        } else {
            System.out.println("Current command set to: null");
        }
    }

    public String toString() {
        return "Logger Command Change Observer";
    }

}
