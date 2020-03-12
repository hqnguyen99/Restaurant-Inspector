package model;

public class Violation
{
    int violationNum;
    private String critOrNot;
    private String description;
    private String repeat;

    Violation(int violationNum, String critOrNot, String description, String repeat) {
        this.violationNum = violationNum;
        this.critOrNot = critOrNot;
        this.description = description;
        this.repeat = repeat;
    }

    public String getFullDescription(){
        return critOrNot + ", " + description + ", " + repeat;
    }
    public String getBriefDescription(){
        return violationNum + ", " + critOrNot;
    }

    public ViolationType getType(){
        // location(100), food(200), equipment(300),
        // pest(304,305), employee(399), certification(500)

        if (violationNum == 304 || violationNum == 305) {
            return ViolationType.PEST;
        }

        int type = violationNum / 100;
        switch (type){
            case 1:
                return ViolationType.LOCATION;
            case 2:
                return ViolationType.FOOD;
            case 3:
                return ViolationType.EQUIPMENT;
            case 4:
                return ViolationType.EMPLOYEE;
            case 5:
                return ViolationType.CERTIFICATION;
            default:
                return null;
        }
    }

    public boolean isCrit(){
        return critOrNot.equals("Critical");
    }

    public int getViolationNum() {
        return violationNum;
    }
}
