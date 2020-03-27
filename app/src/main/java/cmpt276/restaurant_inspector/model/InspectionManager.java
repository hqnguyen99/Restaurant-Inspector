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

                if (values.contains("")) {
                    continue;
                }

                List<Violation> violations = getViolations(values.get(5));

                add(new Inspection(
                    values.get(0),
                    Integer.parseInt(values.get(1)),
                    values.get(2),
                    Integer.parseInt(values.get(3)),
                    Integer.parseInt(values.get(4)),
                    values.get(6),
                    violations
                ));
            }
        } catch (IOException e) {
            Log.wtf("InspectionManager", "Error reading file on line " + line, e);
            e.printStackTrace();
        }
    }

    private List<Violation> getViolations(String violationsText) {
        List<Violation> result = new ArrayList<>();

        if (violationsText.isEmpty()) {
            return result;
        }

        String[] fullDescriptions = violationsText.split("\\|");

        for (String desc : fullDescriptions) {
            int first = desc.indexOf(',');
            int second = desc.indexOf(',', first + 1);
            int last = desc. lastIndexOf(',');

            result.add(new Violation(
                Integer.parseInt(desc.substring(0, first)),
                desc.substring(first + 1, second),
                desc.substring(second + 1, last),
                desc.substring(last + 1)
            ));
        }

        return result;
    }
}
