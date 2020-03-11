package model;

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
