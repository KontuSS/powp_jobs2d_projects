package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

public class TransformerDriver implements Job2dDriver {
    private Job2dDriver driver;
    private double scaleX;
    private double scaleY;

    public TransformerDriver(Job2dDriver driver, double scaleX, double scaleY) {
        this.driver = driver;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public void setPosition(int x, int y) {
        this.driver.setPosition((int) (x * scaleX), (int) (y * scaleY));
    }

    @Override
    public void operateTo(int x, int y) {
        this.driver.operateTo((int) (x * scaleX), (int) (y * scaleY));
    }

    @Override
    public String toString() {
        return "Transform: " + driver.toString();
    }
}