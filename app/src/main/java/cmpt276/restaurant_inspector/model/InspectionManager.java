package cmpt276.restaurant_inspector.model;

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
class InspectionManager
{
    private List<Inspection> inspectionList = new ArrayList<>();

    InspectionManager(BufferedReader reader) {
        setListFromFile(reader);
    }

    List<Inspection> getInspectionList() {
        return inspectionList;
    }

    private void add(Inspection inspection) {
        inspectionList.add(inspection);
        sort(inspectionList);
    }

    private void setListFromFile(BufferedReader reader)
    {
        String line = "";

        try {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                List<String> values = csvParserUtil.parseLine(line);

                for (int i = 0; i < values.length; i++) {
                    values.get(i) = values.get(i).replaceAll("^\"|\"$", "");
                }

                List<Violation> violations = getViolations(values);
                add(new Inspection(
                    values.get(0),
                    Integer.parseInt(values.get(1)),
                    values.get(2),
                    Integer.parseInt(values.get(3)),
                    Integer.parseInt(values.get(4)),
                    values.get(5),
                    violations
                ));
            }
        } catch (IOException e) {
            Log.wtf("InspectionManager", "Error reading file on line " + line, e);
            e.printStackTrace();
        }
    }

    private List<Violation> getViolations(String[] values) {
        List<Violation> result = new ArrayList<>();
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

            result.add(new Violation(
                violationNum,
                values[i + 1],
                values[i + 2],
                values[i + 3]
            ));
        }

        return result;
    }
}
