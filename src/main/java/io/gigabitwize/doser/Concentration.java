package io.gigabitwize.doser;

/**
 * Created by Giovanni on 2/15/2021
 */
public class Concentration {

    private final int day;
    private final double mg;

    public Concentration(int day, double mg) {
        this.day = day;
        this.mg = mg;
    }

    /**
     * Returns the concentration in mg.
     */
    public double getMg() {
        return mg;
    }

    /**
     * Returns the day at which this concentration has been recorded.
     */
    public int getDay() {
        return day;
    }
}
