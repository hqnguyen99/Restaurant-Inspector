package cmpt276.restaurant_inspector.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to help with csv line parsing
 */
class csvParserUtil {
    static List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();
        char[] chars = line.toCharArray();
        StringBuffer section = new StringBuffer();
        boolean inQuotes = false;

        for (char c : chars) {
            if (inQuotes) {
                if (c == '\"') {
                    inQuotes = false;
                } else {
                    section.append(c);
                }
            } else {
                if (c == '\"') {
                    inQuotes = true;
                } else if (c == ',') {
                    result.add(section.toString());
                    section = new StringBuffer();
                } else {
                    section.append(c);
                }
            }
        }

        result.add(section.toString());
        return result;
    }
}
