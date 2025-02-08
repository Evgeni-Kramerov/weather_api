package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    private String name;

    @JsonProperty("lat")
    private double lattitude;

    @JsonProperty("lon")
    private double longtitude;

    public City() {}

    public City(String name, double lattitude, double longitude) {
        this.name = name;
        this.lattitude = lattitude;
        this.longtitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", latitude=" + lattitude +
                ", longitude=" + longtitude +
                '}';
    }
}
