package com.example.weatherappprojectschiff;

public class WeatherDataPoint {
    private String townName;
    private String time;
    private String temperature;
    private String weather;
    private String description;

    public WeatherDataPoint(String time, String temperature, String weather, String description, String townName) {
        this.townName = townName;
        this.time = time;
        this.temperature = temperature;
        this.weather = weather;
        this.description = description;
    }

    public String getTownName() {
        return townName;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }
}
