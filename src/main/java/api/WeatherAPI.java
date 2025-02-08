package api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.City;
import utils.EnvVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {

    private static final String API_KEY = EnvVariables.API_KEY;
    private static final String API_URL = "https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid=";
    private static final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=London&limit=1&appid=";


    public City getCity(String cityName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String url = GEOCODING_API_URL + API_KEY;
        String response = sendGET(url);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        City[] cities = mapper.readValue(response, City[].class);
        return cities[0];
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

}
