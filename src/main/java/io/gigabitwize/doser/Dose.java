package io.gigabitwize.doser;

/**
 * Created by Giovanni on 2/14/2021
 */
public class Dose {

    private final Modulator modulator;
    private final int applianceDay;
    private final double startConcentration;

    private double concentrationMg;

    public Dose(Modulator modulator, int applianceDay, double concentrationMg) {
        this.modulator = modulator;
        this.applianceDay = applianceDay;
        this.concentrationMg = concentrationMg;
        this.startConcentration = concentrationMg;
    }

    /**
     * Updates the serum concentration levels.
     * <p>
     * It takes approx. 5 half-lives for a drug to be considered (clinically) eliminated.
     * As per: https://www.ncbi.nlm.nih.gov/books/NBK554498/#:~:text=Thus%2C%20it%20follows%20that%20after,steady%2Dstate%20during%20an%20infusion.
     *
     * @param days Amount of days that have passed.
     */
    public void calculateAfter(double days) {
        this.concentrationMg = startConcentration;

        double halfLife = getHalfLife(); // 50% loss.
        double eliminatedHrs = halfLife * 5; // completely eliminated.

        int daysPassed = (int) days - applianceDay;
        if (daysPassed <= 1) return;

        double passedHours = daysPassed * 24;

        if (passedHours >= eliminatedHrs) {
            this.concentrationMg = 0;
            return;
        }

        // Get amount of passed half lives.
        int passedHalfLives = (int) (passedHours / halfLife);
        for (int i = 0; i < passedHalfLives; i++)
            this.concentrationMg = concentrationMg / 2;
    }

    /**
     * Returns the day at which this dose got applied.
     */
    public int getApplianceDay() {
        return applianceDay;
    }

    /**
     * Returns the half life in hours.
     */
    public double getHalfLife() {
        return modulator.getHalfLifeHrs();
    }

    /**
     * Returns the current serum concentration in MG.
     */
    public double getConcentrationMg() {
        return concentrationMg;
    }
}
