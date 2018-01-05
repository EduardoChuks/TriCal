package com.chukslabs.trical.model;

import java.io.Serializable;

/**
 * Created by educhuks on 10/12/17.
 */
@SuppressWarnings("serial")
public class CalcMethod implements Serializable {

    private Boolean isRoundedToNext;
    private Integer decimalsQuant;

    public CalcMethod(Boolean isRoundedToNext) {
        this.isRoundedToNext = isRoundedToNext;
        this.decimalsQuant = null;
    }

    public CalcMethod(Integer decimalsQuant) {
        this.isRoundedToNext = null;
        this.decimalsQuant = decimalsQuant;
    }

    public Boolean getRoundedToNext() {
        return isRoundedToNext;
    }

    public void setRoundedToNext(Boolean roundedToNext) {
        isRoundedToNext = roundedToNext;
    }

    public Integer getDecimalsQuant() {
        return decimalsQuant;
    }

    public void setDecimalsQuant(Integer decimalsQuant) {
        this.decimalsQuant = decimalsQuant;
    }

}
