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
        int checkAfterDays = 42; // Amount of days to get serum concentration results for.

        // Dosing is setup here.
        Modulator modulator = new Modulator(halfLifeHrs)
                .addDose(1, 10)
                .addDose(3, 10)
                .addDose(5, 10)
                .addDose(7, 10)
                .addDose(9, 10)
                .addDose(11, 10)
                .addDose(13, 10)
                .addDose(15, 10)
                .addDose(17, 10)
                .addDose(19, 10)
                .addDose(21, 10)
                .addDose(23, 10)
                .addDose(25, 10)
                .addDose(27, 10)
                .addDose(29, 10);

        StringBuilder stringBuilder = new StringBuilder("[     SERUM CONCENTRATION LEVELS PANEL   ]");
        for (Map.Entry<Integer, Double> entry : modulator.getSerumConcentrationsFor(checkAfterDays).entrySet()) {
            int day = entry.getKey();
            double serumConcentration = entry.getValue();
            stringBuilder.append("\nDay ").append(day).append(": ").append(serumConcentration).append("mg");
        }
        stringBuilder.append("\n[                                        ]");
        System.out.println(stringBuilder.toString());
    }
}
