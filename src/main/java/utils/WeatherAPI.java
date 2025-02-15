package utils;

import model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

/*TODO 1) Single Responsibility - just to return JSON
       2) Jason parsing in other package - in utils
       3) Business Logic in third class
       4) Object Data presentation
       5) Documentation SWAGGER
*/

public final class WeatherAPI {

    private static final String API_KEY = EnvVariables.API_KEY;
    private static final String HISTORIC_API_URL = "https://api.openweathermap.org/data/3.0/onecall/day_summary?lat={lat}&lon={lon}&date={date}&appid={API key}";
    private static final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q={City}&limit=1&appid={API key}";

    /**
     * TODO Rewrite explanation
     * Returns String with Json of type City with coordinates longitude and latitude
     *
     * @param  cityName  city name in String format
     * @return           City object with name,longitude and latitude
     */
    public static String requestCity(String cityName) throws IOException {
        String url = getCoordinatesRequestURL(cityName);
        return sendGET(url);
    }

    public static String requestHistoricalData(City city, LocalDate date) throws IOException {
        String url = getHistoricalDataRequest(city, date);
        return sendGET(url);
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
            //TODO added exception throwing
            return "GET request did not work";
        }
    }

    private static String getCoordinatesRequestURL(String cityName) {
        String template = GEOCODING_API_URL;
        template = template.replace("{City}", cityName);
        return template.replace("{API key}", API_KEY);
    }

    private static String getHistoricalDataRequest(City city, LocalDate date) {
        String template = HISTORIC_API_URL;
        template = template.replace("{lat}", Double.toString(city.getLattitude()));
        template = template.replace("{lon}", Double.toString(city.getLongtitude()));
        template = template.replace("{date}", date.toString());
        return template.replace("{API key}", API_KEY);
    }


}
