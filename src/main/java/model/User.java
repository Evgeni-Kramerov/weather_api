package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String username;

    Set<City> cityList;

    public User(String username) {
        this.username = username;
        this.cityList = new HashSet<>();
    }

    public void addCity(City city) {
        cityList.add(city);
    }

    public Set<City> getCityList() {return cityList;}

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username+ ": "+cityList.toString();
    }
}
