package model;

import utils.WeatherAPI;
import utils.JsonMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDataCity {

    private static final int DAYS_FOR_DISPLAY = 30;

    private City city;

    private Map<LocalDate, Double> temperatureByDays;

    private double temperatureYesterday;

    private double temperatureLast7Days;

    private double temperatureLast30Days;

    public WeatherDataCity(City city) throws IOException {
        this.city = city;
        this.temperatureByDays = getTemperatureByDays();
        temperatureYesterday = temperatureByDays.get(LocalDate.now().minusDays(1));
    }

    private Map<LocalDate, Double> getTemperatureByDays() throws IOException {
        Map<LocalDate, Double> temperatureByDays = new HashMap<>();
        List<LocalDate> last30days = getLastDaysFromCurrentDate();
        for (LocalDate date : last30days) {
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

    public double getTemperatureLast7Days() {
        return temperatureLast7Days;
    }

    public void setTemperatureLast7Days(double temperatureLast7Days) {
        this.temperatureLast7Days = temperatureLast7Days;
    }

    public double getTemperatureLast30Days() {
        return temperatureLast30Days;
    }

    public void setTemperatureLast30Days(double temperatureLast30Days) {
        this.temperatureLast30Days = temperatureLast30Days;
    }

}
