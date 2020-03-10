package model;

public class Violation {
    int violationNum;
    private String critOrNot;
    private String description;
    private String repeat;

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
        return critOrNot + description + repeat;
    }
    public String getBriefDescription(){
        return violationNum + critOrNot;
    }


    public ViolationType getType(){
        //food(200),pest(304,305),equipment(300),location(100), employee(400), certification(500)

        if(violationNum == 304 || violationNum == 305){//in the case of pests
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
        return critOrNot == "Critical";
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
