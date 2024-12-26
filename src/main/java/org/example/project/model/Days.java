package org.example.project.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Days {
    JAN(31),
    FEB(28),
    MAR(31),
    APR(30),
    MAY(31),
    JUN(30),
    JUL(31),
    AUG(31),
    SEP(30),
    OCT(31),
    NOV(30),
    DEC(31);

    private Integer day;

    Days (Integer day) {
                this.day = day;
            }

    public Integer getDays() {
        return day;
    }
}
