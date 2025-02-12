package api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.City;
import utils.EnvVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*TODO
       3) Object Data presentation
       4) Transfer all testing to package mains
       5) Documentation
*/

public class WeatherAPI {

    private static final String API_KEY = EnvVariables.API_KEY;
    private static final String HISTORIC_API_URL = "https://api.openweathermap.org/data/3.0/onecall/day_summary?lat={lat}&lon={lon}&date={date}&appid={API key}";
    private static final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q={City}&limit=1&appid={API key}";
    private static final int DAYS_FOR_DISPLAY = 5;

    /**
     * Returns an Object of type City with coordinates longitude and latitude
     *
     * @param  cityName  city name in String format
     * @return           City object with name,longitude and latitude
     */
    public City getCity(String cityName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String url = getCoordinatesRequestURL(cityName);
        String response = sendGET(url);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        City[] cities = mapper.readValue(response, City[].class);
        return cities[0];
    }

    public Map<Date,Double> getHistoricalData(City city) throws IOException {
        String url = getHistoricalDataRequest(city);

        List<LocalDate> requestDates = getLastDaysFromCurentDate();

        for(LocalDate requestDate : requestDates) {
            String requestUrl = url.replace("{date}", requestDate.toString());
            String response = sendGET(requestUrl);
            System.out.println(response);
        }

        return new HashMap<Date,Double>();
    }

    private List<LocalDate> getLastDaysFromCurentDate() {
        return LocalDate.now(ZoneId.systemDefault())
                .minusDays(DAYS_FOR_DISPLAY)
                .datesUntil(LocalDate.now(ZoneId.systemDefault()))
                .toList();
    }


    private static String sendGET(String requestURL) throws IOException {
        URL obj = new URL(requestURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            return response.toString();
        } else {
            return "GET request did not work";
        }
    }

    private String getCoordinatesRequestURL(String cityName) {
        String template = GEOCODING_API_URL;
        template = template.replace("{City}", cityName);
        return template.replace("{API key}", API_KEY);
    }

    private String getHistoricalDataRequest(City city) {
        String template = HISTORIC_API_URL;
        template = template.replace("{lat}", Double.toString(city.getLattitude()));
        template = template.replace("{lon}", Double.toString(city.getLongtitude()));
        return template.replace("{API key}", API_KEY);
    }

    public static void main(String[] args) throws IOException {
        WeatherAPI weatherAPI = new WeatherAPI();
        City city = weatherAPI.getCity("Haifa");
        System.out.println(city);
        weatherAPI.getHistoricalData(city);
    }


}
