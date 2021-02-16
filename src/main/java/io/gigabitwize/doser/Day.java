package io.gigabitwize.doser;

/**
 * Created by Giovanni on 2/16/2021
 */
public enum Day {

    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private final int index;

    Day(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
