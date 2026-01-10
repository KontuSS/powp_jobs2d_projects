package edu.kis.powp.jobs2d.features;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.UsageTrackingDriverDecorator;

/**
 * Provides runtime monitoring of the currently selected driver. Users can enable
 * monitoring through the menu, which wraps the active driver with a
 * {@link UsageTrackingDriverDecorator} to track distances. The logged summaries
 * show travel and drawing usage. This decouples monitoring from driver setup.
 */
public final class MonitoringFeature {

    /** Holds the currently monitored driver, if any. */
    private static UsageTrackingDriverDecorator monitoredDriver = null;

    /** Reference to the driver manager to swap in/out decorated drivers. */
    private static DriverManager driverManager;

    /** Target logger where summaries are printed. */
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private MonitoringFeature() {
    }

    /**
     * Sets up the Monitoring menu with runtime actions to enable/disable monitoring,
     * report usage, and reset counters. Called once during application startup.
     *
     * @param app               The application context.
     * @param manager           The {@code DriverManager} to enable driver swapping.
     * @param monitoringLogger  Custom logger; if {@code null}, uses the default application logger.
     */
    public static void setupMonitoringPlugin(Application app, DriverManager manager, Logger monitoringLogger) {
        driverManager = manager;
        if (monitoringLogger != null) {
            logger = monitoringLogger;
        }

        app.addComponentMenu(MonitoringFeature.class, "Monitoring", 0);
        app.addComponentMenuElement(MonitoringFeature.class, "Enable monitoring on current driver",
                MonitoringFeature::enableMonitoring);
        app.addComponentMenuElement(MonitoringFeature.class, "Disable monitoring", MonitoringFeature::disableMonitoring);
        app.addComponentMenuElement(MonitoringFeature.class, "Report usage summary", MonitoringFeature::logUsage);
        app.addComponentMenuElement(MonitoringFeature.class, "Reset counters", MonitoringFeature::resetCounters);
    }

    /**
     * Menu action that enables monitoring on the currently selected driver.
     * Creates a new {@link UsageTrackingDriverDecorator} around the current driver and
     * replaces it in the manager so subsequent operations track distance and usage.
     *
     * @param e The action event triggered by the menu selection.
     */
    private static void enableMonitoring(ActionEvent e) {
        if (monitoredDriver != null) {
            logger.info("Monitoring: already enabled on current driver");
            return;
        }
        Job2dDriver current = driverManager.getCurrentDriver();
        monitoredDriver = new UsageTrackingDriverDecorator(current, current.toString());
        driverManager.setCurrentDriver(monitoredDriver);
        logger.info("Monitoring: enabled on current driver");
    }

    /**
     * Menu action that disables monitoring on the currently monitored driver.
     * Clears the monitored driver reference and stops tracking. Note that the original
     * driver is lost; this is a simplified implementation.
     *
     * @param e The action event triggered by the menu selection.
     */
    private static void disableMonitoring(ActionEvent e) {
        if (monitoredDriver == null) {
            logger.info("Monitoring: not currently enabled");
            return;
        }
        monitoredDriver = null;
        logger.info("Monitoring: disabled");
    }

    /**
     * Menu action that prints a usage summary for the currently monitored driver.
     * Reports the total travel distance and drawing distance (ink/filament usage)
     * to the logger.
     *
     * @param e The action event triggered by the menu selection.
     */
    private static void logUsage(ActionEvent e) {
        if (monitoredDriver == null) {
            logger.info("Monitoring: no driver currently monitored");
            return;
        }
        logger.info(String.format("[%s] usage summary -> travel=%.2f, ink=%.2f",
                monitoredDriver.getLabel(), monitoredDriver.getTravelDistance(),
                monitoredDriver.getDrawingDistance()));
    }

    /**
     * Menu action that resets usage counters on the currently monitored driver.
     * Sets both travel distance and drawing distance to zero.
     *
     * @param e The action event triggered by the menu selection.
     */
    private static void resetCounters(ActionEvent e) {
        if (monitoredDriver == null) {
            logger.info("Monitoring: no driver currently monitored");
            return;
        }
        monitoredDriver.reset();
        logger.info("Monitoring: counters reset");
    }
}
