package model;

public class Restaurant {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

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
