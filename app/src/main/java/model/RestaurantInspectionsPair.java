package model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.sort;

/**
 * Stores a restaurant and all of its inspections.
 * Sorted by restaurant name in lexicographical order.
 */
public class RestaurantInspectionsPair implements Comparable<RestaurantInspectionsPair> {
    private Restaurant restaurant;
    private List<Inspection> inspections;

    private int numViolations;

    RestaurantInspectionsPair(Restaurant restaurant, List<Inspection> inspections) {
        this.restaurant = restaurant;
        this.inspections = inspections;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<Inspection> getInspections() {
        return inspections;
    }

    public int getNumViolations() {
        return numViolations;
    }

    void addInspection(Inspection inspection) {
        inspections.add(inspection);
        sort(inspections, Collections.<Inspection>reverseOrder());
        numViolations += inspection.getNumCrit() + inspection.numNonCrit;
    }

    public String newestHazardRating() {
        return inspections.isEmpty() ? "" : inspections.get(0).hazardRating;
    }

    /**
     * @return null if list of inspections is empty
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate newestInspectionDate() {
        if (inspections.isEmpty()) {
            return null;
        }

        int remainder = inspections.get(0).getDate();
        int year = remainder / 10000;
        remainder %= 10000;
        int month = remainder / 100;
        remainder %= 100;
        int dayOfMonth = remainder;

        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * @return -1 if no inspection date is found
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long daysFromNewestInspection() {
        LocalDate inspectionDate = newestInspectionDate();

        if (inspectionDate == null) {
            return -1;
        }

        return ChronoUnit.DAYS.between(inspectionDate, LocalDate.now());
    }

    @Override
    public int compareTo(RestaurantInspectionsPair other) {
        return this.restaurant.compareTo(other.restaurant);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(restaurant.toString()).append("\n");

        for (Inspection i : inspections) {
            result.append("Inspection:");
            result.append(i.toString());
        }

        return result.toString();
    }
}
