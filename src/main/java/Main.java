import utils.WeatherAPI;
import model.City;
import model.User;
import utils.JsonMapping;
import view.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        City city1 = JsonMapping.getCity(WeatherAPI.requestCity("London"));
        City city2 = JsonMapping.getCity(WeatherAPI.requestCity("Haifa"));
        User user1 = new User("Test_User");
        user1.addCity(city1);
        user1.addCity(city2);
        View.displayWeather(user1);

    }

}
