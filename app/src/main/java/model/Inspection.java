package model;

import java.util.List;

/**
 * Stores information about a single inspection
 */
public class Inspection implements Comparable<Inspection> {
    String id;
    int date;
    String inspType;
    int numCrit;
    int numNonCrit;
    String hazardRating;
    List<Violation> violations;

    public Inspection(
            String id,
            int inspectionData,
            String inspType,
            int numCrit,
            int numNonCrit,
            String hazardRating,
            List<Violation> violations
    ) {
        this.id = id;
        this.date = inspectionData;
        this.inspType = inspType;
        this.numCrit = numCrit;
        this.numNonCrit = numNonCrit;
        this.hazardRating = hazardRating;
        this.violations = violations;
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

    public int getNumNonCrit() {
        return numNonCrit;
    }

    public HazardRating getHazardRating() {
        switch(hazardRating){
            case "Low" :
                return HazardRating.LOW;
            case "Moderate" :
                return HazardRating.MODERATE;
            case "High" :
                return HazardRating.HIGH;
            default:
                return null;
        }

    }

    public List<Violation> getViolations() {
        return violations;
    }

    @Override
    public String toString() {
        return  "\n\tId: " + id +
                "\n\tInspection Date: " + date +
                "\n\tType: " + inspType +
                "\n\tCritical?: " + numCrit +
                "\n\tnon Critical?: " + numNonCrit +
                "\n\tHazard Rating: " + hazardRating +
                "\n\tViolation Numbers: " +  getViolationNumsString() + '\n';
    }

    // Compare by date, the smaller number (older date)
    // will be ordered first between two numbers
    @Override
    public int compareTo(Inspection other) {
        return (this.getDate() - other.getDate());
    }

    private String getViolationNumsString() {
        StringBuilder result = new StringBuilder();

        final int NUM_VIOLATIONS = violations.size();

        for (int i = 0; i < NUM_VIOLATIONS; i++) {
            result.append(violations.get(i).violationNum);

            if (i != NUM_VIOLATIONS - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
}
