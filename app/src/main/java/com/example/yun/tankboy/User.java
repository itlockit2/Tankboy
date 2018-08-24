package com.example.yun.tankboy;

public class User {
    private String key;
    private Meter meter;
    private String username;
    private int weatherAreaCode;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWeatherAreaCode() {
        return weatherAreaCode;
    }

    public void setWeatherAreaCode(int weatherAreaCode) {
        this.weatherAreaCode = weatherAreaCode;
    }
}