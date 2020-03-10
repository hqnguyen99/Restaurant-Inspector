package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Violation {
    int violationNum;
    String critOrNot;
    String description;
    String repeat;

    Violation(int violationNum,
              String critOrNot,
              String description,
              String repeat
    ) {
        this.violationNum = violationNum;
        this.critOrNot = critOrNot;
        this.description = description;
        this.repeat = repeat;

    }

    //getting full description into 1 string
    public String getfullDescription(){
        String fullDescription = critOrNot + description + repeat;
        return fullDescription;
    }

    //getters and setters
    public int getViolationNum() {
        return violationNum;
    }

    public String getCritOrNot() {
        return critOrNot;
    }

    public String getDescription() {
        return description;
    }

    public String getRepeat() {
        return repeat;
    }
}
