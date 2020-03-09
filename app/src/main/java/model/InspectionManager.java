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

    InspectionManager () {
    }

    InspectionManager(BufferedReader reader) {
        setListFromFile(reader);
    }

    public List<Inspection> getInspectionList() {
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
            while ((line = reader.readLine()) != null) {
                String values[] = line.split(",|\\|");

                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "");
                }

                // get the list of violation numbers
                List<Integer> violationNums = new ArrayList<>();
                final int START = 6;

                for (int i = START; i < values.length; i += 4) {
                    try {
                        violationNums.add(Integer.parseInt(values[i]));
                    } catch (Exception e) { }
                }

                add(new Inspection(
                        values[0],
                        Integer.parseInt(values[1]),
                        values[2],
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        values[5],
                        violationNums
                ));
            }
        } catch (IOException e) {
            Log.wtf("InspectionManager", "Error reading file on line " + line, e);
            e.printStackTrace();
        }
    }
}
