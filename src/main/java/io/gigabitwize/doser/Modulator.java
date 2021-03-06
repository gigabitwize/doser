package io.gigabitwize.doser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by Giovanni on 2/14/2021
 */
public class Modulator {

    private final double halfLifeHrs;
    private final LinkedHashMap<Integer, Dose> doseMap;

    public Modulator(double halfLifeHrs) {
        this.halfLifeHrs = halfLifeHrs;
        this.doseMap = Maps.newLinkedHashMap();

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
     * Returns the day at which the total serum concentration is 0.0MG.
     */
    public int getTotalEliminationDay() {
        Dose lastDose = (Dose) doseMap.values().toArray()[doseMap.size() - 1];
        return lastDose.getApplianceDay() + (int) (halfLifeHrs / 24 * 5) + 1;
    }

    /**
     * Returns the highest serum concentration levels its corresponding day.
     */
    public Concentration getPeakDayWithConcentration() {
        HashMap<Integer, Double> concentrations = getSerumConcentrationsFor(getTotalEliminationDay());
        int day = Collections.max(concentrations.entrySet(), Map.Entry.comparingByValue()).getKey();
        return new Concentration(day, concentrations.get(day));
    }

    /**
     * Adds a {@link Dose} to this modulator.
     */
    public Modulator addDose(Dose dose) {
        this.doseMap.put(dose.getApplianceDay(), dose);
        return this;
    }

    /**
     * Adds a persistent amount of mg throughout a certain amount of days
     * to this modulator.
     *
     * @param days The amount of days.
     * @param mg   The concentration.
     */
    public Modulator addDosePersistent(int days, double mg) {
        for (int i = 0; i < days + 1; i++) {
            if (i == 0) continue;
            addDose(i, mg);
        }
        return this;
    }

    /**
     * Adds a weekly {@link Dose} to the modulator.
     *
     * @param week The week number.
     * @param mg   The dose per day in this week.
     */
    public Modulator addWeeklyDose(int week, double mg) {
        int totalDays = week * 7;
        int daysStart = totalDays - 6;
        for (int i = daysStart; i < totalDays + 1; i++) {
            if (i == 0) continue;
            addDose(i, mg);
        }
        return this;
    }

    /**
     * Adds a {@link Dose} to the modulator, at specific days in a specific week.
     *
     * @param week The week.
     * @param mg   The mg of the dose.
     * @param days The days the dose gets applied.
     */
    public Modulator addWeeklyDoseAt(int week, double mg, Day... days) {
        List<Integer> daysApplicable = Lists.newArrayList();
        int totalDays = week * 7;
        int daysStart = totalDays - 6;
        for (int i = daysStart; i < totalDays + 1; i++) {
            if (i == 0) continue;
            daysApplicable.add(i);
        }

        for (Day day : days) {
            addDose(daysApplicable.get(day.getIndex()), mg);
        }
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
