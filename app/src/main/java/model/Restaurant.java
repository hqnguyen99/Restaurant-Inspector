package model;

import androidx.annotation.NonNull;

public class Restaurant {
    String trackingNumber;
    String name;
    String address;
    String city;
    String type;
    Double latitude;
    Double longitude;

    Restaurant(
            String trackingNumber,
            String name,
            String address,
            String city,
            String type,
            double latitude,
            double longitude
    ) {
        this.trackingNumber = trackingNumber;
        this.name = name;
        this.address = address;
        this.city = city;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "Number: " + trackingNumber +
            "\nName: " + name +
            "\nAddress: " + address +
            "\nCity: " + city +
            "\nType: " + type +
            "\nLatitude: " + latitude +
            "\nLongitude: " + longitude;
    }
}
