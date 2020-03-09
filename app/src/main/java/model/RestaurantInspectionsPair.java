package model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class RestaurantInspectionsPair implements Comparable<RestaurantInspectionsPair> {
    Restaurant restaurant;
    List<Inspection> inspections = new ArrayList<>();

    private int numViolations;

    RestaurantInspectionsPair(Restaurant restaurant, List<Inspection> inspections) {
        this.restaurant = restaurant;
        this.inspections = inspections;
    }

    int getNumViolations() {
        return numViolations;
    }

    void addInspection(Inspection inspection) {
        inspections.add(inspection);
        sort(inspections);
        numViolations += inspection.getNumCrit() + inspection.numNonCrit;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    LocalDate newestInspectionDate() {
        int remainder = inspections.get(0).getDate();
        int year = remainder / 10000;
        remainder %= 10000;
        int month = remainder / 100;
        remainder %= 100;
        int dayOfMonth = remainder;

        return LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    public int compareTo(RestaurantInspectionsPair other) {
        return this.restaurant.compareTo(other.restaurant);
    }
}
