package au.edu.sydney.soft3202.reynholm.erp.project.Project;
public class Project {
    public int getId() {
        return 0;
    }
    public String getName(){
        return "";
    }

    public double getStandardRate(){
        return 0;
    }

    public void setStandardRate(double standardRate)
            throws IllegalArgumentException {
    }

    public double getOverDifference(){
        return 0;
    }

    public void setOverDifference(double overDifference)
            throws IllegalArgumentException {
    }

    public static Project makeProject(int id,
                                      String name,
                                      double standardRate,
                                      double overDifference)
            throws IllegalArgumentException {
        return new Project();
    }

}
