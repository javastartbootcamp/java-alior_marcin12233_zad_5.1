package pl.javastart.task;

public class Grade {

    private int studentIndex;

    private String groupCode;

    private double grade;

    public Grade(int studentIndex, String groupCode, double grade) {
        this.studentIndex = studentIndex;
        this.groupCode = groupCode;
        this.grade = grade;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
