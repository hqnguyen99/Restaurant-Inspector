package model;

public class Restaurant {
    String id;
    String name;
    String address;
    String city;
    String type;
    double latitude;
    double longitude;

    public Restaurant(
            String id,
            String name,
            String address,
            String city,
            String type,
            double latitude,
            double longitude
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Number: " + id +
            "\nName: " + name +
            "\nAddress: " + address +
            "\nCity: " + city +
            "\nType: " + type +
            "\nLatitude: " + latitude +
            "\nLongitude: " + longitude;
    }
}
