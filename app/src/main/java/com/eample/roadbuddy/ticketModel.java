package com.eample.roadbuddy;

public class ticketModel {

    private String Plate_number;
    private String car_latitude;
    private String car_longitude;
    private String car_timestamp;
    private String sign_latitude;
    private String sign_longitude;
    private String sign_timestamp;
    private String ticketID;
    private String ai_verified;
    private String rto_verified;

    public String getAi_verified() {
        return ai_verified;
    }

    public void setAi_verified(String ai_verified) {
        this.ai_verified = ai_verified;
    }

    public String getRto_verified() {
        return rto_verified;
    }

    public void setRto_verified(String rto_verified) {
        this.rto_verified = rto_verified;
    }

    public ticketModel() {
    }

    public String getCar_timestamp() {
        return car_timestamp;
    }

    public void setCar_timestamp(String car_timestamp) {
        this.car_timestamp = car_timestamp;
    }

    public String getSign_latitude() {
        return sign_latitude;
    }

    public void setSign_latitude(String sign_latitude) {
        this.sign_latitude = sign_latitude;
    }

    public String getSign_longitude() {
        return sign_longitude;
    }

    public void setSign_longitude(String sign_longitude) {
        this.sign_longitude = sign_longitude;
    }

    public String getSign_timestamp() {
        return sign_timestamp;
    }

    public void setSign_timestamp(String sign_timestamp) {
        this.sign_timestamp = sign_timestamp;
    }

    public ticketModel(String Plate_number, String car_latitude, String car_longitude, String car_timestamp, String sign_latitude, String sign_longitude, String sign_timestamp, String ticketID) {

        this.Plate_number = Plate_number;
        this.car_latitude = car_latitude;
        this.car_longitude = car_longitude;
        this.car_timestamp = car_timestamp;
        this.sign_latitude = sign_latitude;
        this.sign_longitude = sign_longitude;
        this.sign_timestamp = sign_timestamp;
        this.ticketID = ticketID;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getPlate_number() {
        return Plate_number;
    }

    public void setPlate_number(String plate_number) {
        Plate_number = plate_number;
    }

    public String getCar_latitude() {
        return car_latitude;
    }

    public void setCar_latitude(String car_latitude) {
        this.car_latitude = car_latitude;
    }

    public String getCar_longitude() {
        return car_longitude;
    }

    public void setCar_longitude(String car_longitude) {
        this.car_longitude = car_longitude;
    }
}
