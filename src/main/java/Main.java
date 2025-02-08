import api.WeatherAPI;
import model.City;
import utils.EnvVariables;

import java.io.IOException;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) throws IOException {
        WeatherAPI weatherAPI = new WeatherAPI();
        City city = weatherAPI.getCity("London");
        
    }
}
