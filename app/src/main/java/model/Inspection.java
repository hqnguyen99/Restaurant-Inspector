package model;

import java.util.List;

public class Inspection implements Comparable<Inspection> {
    String id;
    int inspectionDate;
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
        this.inspectionDate = inspectionData;
        this.inspType = inspType;
        this.numCrit = numCrit;
        this.nonNumCrit = nonNumCrit;
        this.hazardRating = hazardRating;
        this.violationNums = violationNums;
    }

    public String getId() {
        return id;
    }

    public int getInspectionDate() {
        return inspectionDate;
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
                "\nInspection Data: " + inspectionDate +
                "\nType: " + inspType +
                "\nCritcal?: " + numCrit +
                "\nnon Critical?: " + nonNumCrit +
                "\nHazard Rating: " + hazardRating +
                "\nViolation Numbers: " +  getViolationNumsString();
    }

    // Compare by date
    @Override
    public int compareTo(Inspection other) {
        return (this.getInspectionDate() - other.getInspectionDate());
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
