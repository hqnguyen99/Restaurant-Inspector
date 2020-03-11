package model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

/**
 * Contains the data of all inspections (of multiple restaurants).
 * Loads data into underlying its ArrayList if initialized with a csv filepath
 */
public class InspectionManager {
    private List<Inspection> inspectionList = new ArrayList<>();

    InspectionManager(BufferedReader reader) {
        setListFromFile(reader);
    }

    List<Inspection> getInspectionList() {
        return inspectionList;
    }

    public void add(Inspection inspection) {
        inspectionList.add(inspection);
        sort(inspectionList);
    }

    public void remove(Inspection inspection) {
        inspectionList.remove(inspection);
    }

    private void setListFromFile(BufferedReader reader) {
        String line = "";

        try {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split("[,|]");

                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "");
                }

                // get the list of violation numbers
                List<Violation> violations = new ArrayList<>();
                final int START = 6;
                final int SEGMENTS_PER_VIOLATION = 4;

                for (int i = START; i < values.length; i += SEGMENTS_PER_VIOLATION) {
                    int violationNum = -1;

                    try {
                        violationNum = Integer.parseInt(values[i]);
                    } catch (Exception e) {
                        Log.wtf("InspectionManager",
                                "Error when converting string " + values[i] + " to int");
                        e.getStackTrace();
                    }

                    violations.add(new Violation(
                            violationNum,
                            values[i + 1],
                            values[i + 2],
                            values[i + 3]
                    ));
                }

                add(new Inspection(
                        values[0],
                        Integer.parseInt(values[1]),
                        values[2],
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        values[5],
                        violations
                ));
            }
        } catch (IOException e) {
            Log.wtf("InspectionManager", "Error reading file on line " + line, e);
            e.printStackTrace();
        }
    }
}
