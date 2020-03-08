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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInspectionData() {
        return inspectionData;
    }

    public void setInspectionData(int inspectionData) {
        this.inspectionData = inspectionData;
    }

    public String getInspType() {
        return inspType;
    }

    public void setInspType(String inspType) {
        this.inspType = inspType;
    }

    public int getNumCrit() {
        return numCrit;
    }

    public void setNumCrit(int numCrit) {
        this.numCrit = numCrit;
    }

    public int getNonNumCrit() {
        return nonNumCrit;
    }

    public void setNonNumCrit(int nonNumCrit) {
        this.nonNumCrit = nonNumCrit;
    }

    public String getHazardRating() {
        return hazardRating;
    }

    public void setHazardRating(String hazardRating) {
        this.hazardRating = hazardRating;
    }

    public List<Integer> getViolationNums() {
        return violationNums;
    }

    public void setViolationNums(List<Integer> violationNums) {
        this.violationNums = violationNums;
    }



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
