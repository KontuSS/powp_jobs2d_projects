package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

public class RotationDriver implements Job2dDriver {
    private Job2dDriver driver;
    private double angle;

    public RotationDriver(Job2dDriver driver, double angleDegree) {
        this.driver = driver;
        this.angle = Math.toRadians(angleDegree);
    }

    @Override
    public void setPosition(int x, int y) {
        int newX = (int) (x * Math.cos(angle) - y * Math.sin(angle));
        int newY = (int) (x * Math.sin(angle) + y * Math.cos(angle));
        this.driver.setPosition(newX, newY);
    }

    @Override
    public void operateTo(int x, int y) {
        int newX = (int) (x * Math.cos(angle) - y * Math.sin(angle));
        int newY = (int) (x * Math.sin(angle) + y * Math.cos(angle));
        this.driver.operateTo(newX, newY);
    }

    @Override
    public String toString() {
        return "Rotate: " + driver.toString();
    }
}