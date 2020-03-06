package hqnguyen.sfu.restaurantinspector.model;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InspectionManager {
    private List<Inspection> inspectionList;

    public InspectionManager() {
        inspectionList = new ArrayList<>();
    }

    public List<Inspection> getInspectionList() {
        return inspectionList;
    }

    public void add(Inspection inspection) {
        inspectionList.add(inspection);
    }

    public void remove(Inspection inspection) {
        inspectionList.remove(inspection);
    }

    public void setListFromFile(File file) {
        if (file.exists() && !file.isDirectory()) {
            inspectionList.clear();
        }

        try {
            Scanner in = new Scanner(file);
            in.nextLine();

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String values[] = line.split(",|\\|");

                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "");
                }

                // get the list of violation numbers
                List<Integer> violationNums = new ArrayList<>();
                final int START = 6;

                for (int i = START; i < values.length; i+=4) {
                    try {
                        violationNums.add(Integer.parseInt(values[i]));
                    } catch (Exception e) { }
                }

                inspectionList.add(new Inspection(
                        values[0],
                        Integer.parseInt(values[1]),
                        values[2],
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        values[5],
                        violationNums
                ));
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid File.");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
