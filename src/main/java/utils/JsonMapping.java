package utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.City;

import java.io.IOException;

public final class JsonMapping {

    public static City getCity(String jsonResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        City[] cities = mapper.readValue(jsonResponse, City[].class);
        return cities[0];
    }

    public static Double getTemperature(String jsonResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = mapper.readTree(jsonResponse);
        return root.get("temperature").get("afternoon").asDouble();
    }


    public static void main(String[] args) throws IOException {
    }
}
