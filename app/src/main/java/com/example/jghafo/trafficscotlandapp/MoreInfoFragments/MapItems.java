package com.example.jghafo.trafficscotlandapp.MoreInfoFragments;

import com.google.android.gms.maps.model.LatLng;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class MapItems {

    private String Name;
    private String location;
    private String startdate;
    private String enddate;
    private String info;
    private String url;
    private String author;
    private String comments;
    private String pubDate;
    private LatLng latLng;

    public MapItems() {
    }

    public MapItems(String name, String location, String startdate, String end_date, String delay_information, String url, String author, String comments, String pubDate, LatLng latLng) {
        Name = name;
        this.location = location;
        this.startdate = startdate;
        this.enddate = end_date;
        this.info = delay_information;
        this.url = url;
        this.author = author;
        this.comments = comments;
        this.pubDate = pubDate;
        this.latLng = latLng;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
