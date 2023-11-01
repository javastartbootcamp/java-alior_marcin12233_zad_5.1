package pl.javastart.task;

public class Student {

    private int index;

    private String groupCode;

    private String firstName;

    private String lastName;

    public Student(int index, String groupCode, String firstName, String lastName) {
        this.index = index;
        this.groupCode = groupCode;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
