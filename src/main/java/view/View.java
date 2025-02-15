package view;

import model.City;
import model.User;
import model.WeatherDataCity;

import java.io.IOException;
import java.util.List;

public class View {

    public static void displayWeather(User user) throws IOException {
        List<City> cityList = user.getCityList();
        System.out.println("***City***|***Yesterday***|");
        for (City city : cityList) {
            WeatherDataCity weatherDataCity = new WeatherDataCity(city);
            System.out.println(city.getName() + " | " + weatherDataCity.getTemperatureYesterday() + " | ");
        }

    }


}
