package io.gigabitwize.doser;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * Created by Giovanni on 2/14/2021
 */
public class Modulator {

    private final double halfLifeHrs;
    private final HashMap<Integer, Dose> doseMap;

    public Modulator(double halfLifeHrs) {
        this.halfLifeHrs = halfLifeHrs;
        this.doseMap = Maps.newHashMap();

    }

    /**
     * Gets the total serum concentration at a specific day.
     */
    public double getSerumConcentrationAt(int day) {
        double concentration = 0;
        for (Dose dose : doseMap.values()) {
            dose.calculateAfter(day);
            if (dose.getApplianceDay() <= day)
                concentration += dose.getConcentrationMg();
        }
        return concentration;
    }

    /**
     * Returns a map of serum concentrations per day.
     */
    public HashMap<Integer, Double> getSerumConcentrationsFor(int days) {
        HashMap<Integer, Double> daysAndConcentrations = Maps.newHashMap();
        for (int i = 0; i < days + 1; i++) {
            if (i == 0) continue;
            double concentration = getSerumConcentrationAt(i);
            daysAndConcentrations.put(i, concentration);
        }
        return daysAndConcentrations;
    }

    /**
     * Adds a {@link Dose} to this modulator.
     */
    public Modulator addDose(Dose dose) {
        this.doseMap.put(dose.getApplianceDay(), dose);
        return this;
    }

    public Modulator addDose(int applianceDay, double concentrationMg) {
        this.doseMap.put(applianceDay, new Dose(this, applianceDay, concentrationMg));
        return this;
    }

    /**
     * Returns the half life of this modulator in hours.
     */
    public double getHalfLifeHrs() {
        return halfLifeHrs;
    }
}
