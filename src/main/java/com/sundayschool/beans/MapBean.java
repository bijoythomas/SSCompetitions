package com.sundayschool.beans;

import com.sundayschool.constants.ChurchLocations;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MapBean {
    protected String church;
    private MapModel mapModel;
    private String center = "32.97610,-96.91260";

    public MapBean() {
        mapModel = new DefaultMapModel();
    }

    public String getChurch() {
        return church;
    }

    public void setChurch(String church) {
        this.church = church;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public void updateMap() {
        center = ChurchLocations.getLatLongFor(church);
        String[] coordinates = center.split(",");
        mapModel.addOverlay(new Marker(new LatLng(Double.parseDouble(coordinates[0].trim()), Double.parseDouble(coordinates[1].trim())), church));
    }
}
