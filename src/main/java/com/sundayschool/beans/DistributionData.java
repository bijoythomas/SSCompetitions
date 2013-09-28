package com.sundayschool.beans;

public class DistributionData
{
    String churchName;
    String number;

    public DistributionData(String churchName, String number) {
        this.churchName = churchName;
        this.number = number;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
