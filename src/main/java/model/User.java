package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;

    List<City> cityList;

    public User(String username) {
        this.username = username;
        this.cityList = new ArrayList<>();
    }

    public void addCity(City city) {
        cityList.add(city);
    }

    public List<City> getCityList() {
        return cityList;
    }

    public String getUsername() {
        return username;
    }

}
