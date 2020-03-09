package model;

import java.util.List;

public class Inspection {
    String id;
    int inspectionData;
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
        this.inspectionData = inspectionData;
        this.inspType = inspType;
        this.numCrit = numCrit;
        this.nonNumCrit = nonNumCrit;
        this.hazardRating = hazardRating;
        this.violationNums = violationNums;
    }

    public String getId() {
        return id;
    }

    public int getInspectionData() {
        return inspectionData;
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
                "\nInspection Data: " + inspectionData +
                "\nType: " + inspType +
                "\nCritcal?: " + numCrit +
                "\nnon Critical?: " + nonNumCrit +
                "\nHazard Rating: " + hazardRating +
                "\nViolation Numbers: " +  getViolationNumsString();
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
