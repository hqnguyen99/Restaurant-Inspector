package model;

import java.util.List;

public class Inspection implements Comparable<Inspection> {
    String id;
    int date;
    String inspType;
    int numCrit;
    int nonNumCrit;
    String hazardRating;
    List<Integer> violationNums;

    public Inspection(
            String id,
            int inspectionData,
            String inspType,
            int numCrit,
            int nonNumCrit,
            String hazardRating,
            List<Integer> violationNums
    ) {
        this.id = id;
        this.date = inspectionData;
        this.inspType = inspType;
        this.numCrit = numCrit;
        this.nonNumCrit = nonNumCrit;
        this.hazardRating = hazardRating;
        this.violationNums = violationNums;
    }

    public String getId() {
        return id;
    }

    public int getDate() {
        return date;
    }

    public String getInspType() {
        return inspType;
    }

    public int getNumCrit() {
        return numCrit;
    }

    public int getNonNumCrit() {
        return nonNumCrit;
    }

    public String getHazardRating() {
        return hazardRating;
    }

    public List<Integer> getViolationNums() {
        return violationNums;
    }

    @Override
    public String toString() {
        return "Number: " + id +
                "\nInspection Data: " + date +
                "\nType: " + inspType +
                "\nCritcal?: " + numCrit +
                "\nnon Critical?: " + nonNumCrit +
                "\nHazard Rating: " + hazardRating +
                "\nViolation Numbers: " +  getViolationNumsString();
    }

    // Compare by date
    @Override
    public int compareTo(Inspection other) {
        return (this.getDate() - other.getDate());
    }

    private String getViolationNumsString() {
        String result = "";

        for (int i = 0; i < violationNums.size(); i++) {
            result += violationNums.get(i);

            if (i != violationNums.size() - 1) {
                result += ", ";
            }
        }

        return result;
    }
}
