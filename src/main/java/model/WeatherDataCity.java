package model;

import utils.WeatherAPI;
import utils.JsonMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDataCity {

    private static final int DAYS_FOR_DISPLAY = 3;

    private City city;

    private Map<LocalDate, Double> temperatureByDays;

    private double temperatureYesterday;

    private double temperatureLastNDays;

    public WeatherDataCity(City city) throws IOException {
        this.city = city;
        this.temperatureByDays = getTemperatureByDays();
        temperatureYesterday = temperatureByDays.get(LocalDate.now().minusDays(1));
        temperatureLastNDays = calculateAverageTemperature();
    }

    private double calculateAverageTemperature() {
        double averageTemperature = 0;
        int count = 0;
        for (Double temperature : temperatureByDays.values()) {
            averageTemperature += temperature;
            count++;
        }
        return round(averageTemperature / count, 2);
    }

    private Map<LocalDate, Double> getTemperatureByDays() throws IOException {
        Map<LocalDate, Double> temperatureByDays = new HashMap<>();
        //For last number of days defines in DAYS_FOR_DISPLAY
        List<LocalDate> lastdays = getLastDaysFromCurrentDate();
        for (LocalDate date : lastdays) {
            temperatureByDays.put(
                    date,
                    JsonMapping.getTemperature(WeatherAPI.requestHistoricalData(this.city, date))
            );
        }
        return temperatureByDays;
    }

    private List<LocalDate> getLastDaysFromCurrentDate() {
        return LocalDate.now(ZoneId.systemDefault())
                .minusDays(DAYS_FOR_DISPLAY)
                .datesUntil(LocalDate.now(ZoneId.systemDefault()))
                .toList();
    }

    public City getCity() {
        return city;
    }

    public double getTemperatureYesterday() {
        return temperatureYesterday;
    }

    public void setTemperatureYesterday(double temperatureYesterday) {
        this.temperatureYesterday = temperatureYesterday;
    }

    public double getTemperatureLastNDays() {
        return temperatureLastNDays;
    }

    public void setTemperatureLastNDays(double temperatureLastNDays) {
        this.temperatureLastNDays = temperatureLastNDays;
    }

    //helper function to round Double values for temperature
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
