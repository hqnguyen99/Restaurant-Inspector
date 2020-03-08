package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViolationsManager {
    private List<Violations> violationsList;

    public ViolationsManager() {
        violationsList = new ArrayList<>();
    }

    public List<Violations> getViolationsList() {
        return violationsList;
    }

    public void setListFromFile(File file) {
        if (file.exists() && !file.isDirectory()) {
            violationsList.clear();
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
                final int START = 1;

                for (int i = START; i < values.length; i+=4) {
                    try {
                        violationNums.add(Integer.parseInt(values[i]));
                    } catch (Exception e) { }
                }

                violationsList.add(new Violations(
                        Integer.parseInt(values[0]),
                        values[1],
                        values[2],
                        values[3]
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
