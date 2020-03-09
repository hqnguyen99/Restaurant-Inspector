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
    List<Integer> violationIds;

    public Inspection(
            String id,
            int inspectionData,
            String inspType,
            int numCrit,
            int numNonCrit,
            String hazardRating,
            List<Integer> violationIds
    ) {
        this.id = id;
        this.date = inspectionData;
        this.inspType = inspType;
        this.numCrit = numCrit;
        this.numNonCrit = numNonCrit;
        this.hazardRating = hazardRating;
        this.violationIds = violationIds;
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

    public String getHazardRating() {
        return hazardRating;
    }

    public List<Integer> getViolationIds() {
        return violationIds;
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
        String result = "";

        for (int i = 0; i < violationIds.size(); i++) {
            result += violationIds.get(i);

            if (i != violationIds.size() - 1) {
                result += ", ";
            }
        }

        return result;
    }
}
