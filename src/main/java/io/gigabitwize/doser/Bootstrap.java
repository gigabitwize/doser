package io.gigabitwize.doser;

import java.util.Map;

/**
 * Created by Giovanni on 2/14/2021
 * <p>
 * A tool to calculate the serum concentration levels of an
 * undefined medicine or drug in a human body, assuming one
 * knows the medicine or drug's half-life and the dosing frequency and interval.
 * <p>
 * This tool is based on 5 half-lives.
 * It takes approx. 5 half-lives for a drug to be considered (clinically) eliminated.
 * As per: https://www.ncbi.nlm.nih.gov/books/NBK554498/#:~:text=Thus%2C%20it%20follows%20that%20after,steady%2Dstate%20during%20an%20infusion.
 */
public class Bootstrap {

    public static void main(String[] args) {
        double halfLifeHrs = 60; // Half-life in hours of the medicine or drug
        int checkAfterDays = 26; // Amount of days to get serum concentration results for.
        PanelType panelType = PanelType.PEAK_AND_ELIMINATION; // Type of panel.

        if (halfLifeHrs <= 0) {
            System.err.println("Half-life can't be less than 0.");
            return;
        }

        if (checkAfterDays < 1) {
            System.err.println("No dose applied at this day.");
            return;
        }

        // Dosing is setup here.
        Modulator modulator = new Modulator(halfLifeHrs)
                .addDose(1, 10)
                .addDose(2, 10)
                .addDose(3, 10)
                .addDose(4, 10)
                .addDose(5, 10)
                .addDose(6, 10)
                .addDose(7, 10)
                .addDose(8, 10)
                .addDose(10, 10)
                .addDose(11, 10)
                .addDose(12, 10)
                .addDose(13, 10)
                .addDose(14, 10);

        switch (panelType) {
            case DAILY_CONCENTRATIONS:
                StringBuilder stringBuilder = new StringBuilder("[     SERUM CONCENTRATION LEVELS PANEL   ]");
                stringBuilder.append("Panel type: ").append("DAILY CONCENTRATIONS");
                for (Map.Entry<Integer, Double> entry : modulator.getSerumConcentrationsFor(checkAfterDays).entrySet()) {
                    int day = entry.getKey();
                    double serumConcentration = entry.getValue();
                    stringBuilder.append("\nDay ").append(day).append(": ").append(serumConcentration).append("mg");
                }
                stringBuilder.append("\n[                                        ]");
                System.out.println(stringBuilder.toString());
                break;
            case PEAK_AND_ELIMINATION:
                Concentration peakConcentration = modulator.getPeakDayWithConcentration();
                String peak = "[     SERUM CONCENTRATION LEVELS PANEL   ]" + "\nPanel type: " + "PEAK AND ELIMINATION DAY" +
                        "\nPeak day: " + peakConcentration.getDay() +
                        "\n   - Concentration: " + peakConcentration.getMg() + "mg" +
                        "\nElimination day: " + modulator.getTotalEliminationDay() +
                        "\n   - Concentration: 0.0 mg" +
                        "\n[                                        ]";
                System.out.println(peak);
                break;
        }
    }
}
