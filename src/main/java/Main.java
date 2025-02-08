import api.WeatherAPI;
import model.City;
import utils.EnvVariables;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        WeatherAPI weatherAPI = new WeatherAPI();
        City city = weatherAPI.getCity("London");



        long unixTimestamp = Instant.now().getEpochSecond();

        String date = Long.toString(unixTimestamp);

        System.out.println(date);
        
    }
}
