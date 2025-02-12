import api.WeatherAPI;
import model.City;

import java.io.IOException;
import java.time.Instant;

/*TODO 2) Get last 30 days from API
       3) Object Data presentation


*/
public class Main {
    public static void main(String[] args) throws IOException {
        WeatherAPI weatherAPI = new WeatherAPI();
        City city = weatherAPI.getCity("Haifa");


        System.out.println(city);

        long unixTimestamp = Instant.now().getEpochSecond();

        String date = Long.toString(unixTimestamp);

        System.out.println(date);
        
    }
}
