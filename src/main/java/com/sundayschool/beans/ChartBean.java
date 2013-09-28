package com.sundayschool.beans;

import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ChartBean
{
    private PieChartModel pieModel = new PieChartModel();


    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
}
