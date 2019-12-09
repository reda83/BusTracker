package com.example.bustracker;

public class Route {
    private String location;
    private String time;
    private String line;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Route(String location, String time, String line) {
        this.location = location;
        this.time = time;
        this.line = line;
    }
}
