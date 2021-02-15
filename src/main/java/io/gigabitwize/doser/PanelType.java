package io.gigabitwize.doser;

/**
 * Created by Giovanni on 2/15/2021
 */
public enum PanelType {

    /**
     * Returns a panel of daily serum concentrations.
     */
    DAILY_CONCENTRATIONS,

    /**
     * Returns a panel which includes the peak serum concentration
     * levels and the day at which the total serum concentration
     * levels are 0.0MG.
     */
    PEAK_AND_ELIMINATION;
}
