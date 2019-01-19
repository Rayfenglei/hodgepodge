package com.example.ray.myapplication.Function.Calendar;

public class EventsItem {

    private String eventTitle ;
    private boolean isAllday;
    private String startTime ;
    private String endTime ;
    private String startDate ;
    private String endDate ;
    private long eventID;

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public void setEventTitle(String eventTitle){
        this.eventTitle = eventTitle;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public void setAllday(boolean allday) {
        isAllday = allday;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public long getEventID() {
        return eventID;
    }

    public boolean isAllday() {
        return isAllday;
    }

    @Override
    public String toString() {
        return "EventsItem{" +
                "eventTitle='" + eventTitle + '\'' +
                ", isAllday=" + isAllday +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", eventID=" + eventID +
                '}';
    }
}
