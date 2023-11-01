package pl.javastart.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniversityApp {

    private List<Lecturer> lecturers;

    private List<Group> groups;

    private List<Student> students;

    private List<Grade> grades;

    public UniversityApp() {
        this.lecturers = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.students = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {
        if (this.isLecturerExistsById(id)) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
            return;
        }

        this.lecturers.add(new Lecturer(id, degree, firstName, lastName));
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */
    public void createGroup(String code, String name, int lecturerId) {
        if (this.isGroupExistsByCode(code)) {
            System.out.println("Grupa " + code + " już istnieje");
            return;
        }

        if (!isLecturerExistsById(lecturerId)) {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
            return;
        }

        this.groups.add(new Group(code, name, lecturerId));
    }

    private boolean isLecturerExistsById(int lecturerId) {
        for (Lecturer lecturer : this.lecturers) {
            if (lecturer.getId() == lecturerId) {
                return true;
            }
        }

        return false;
    }

    private boolean isGroupExistsByCode(String code) {
        for (Group group : this.groups) {
            if (group.getCode().equals(code)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        if (!this.isGroupExistsByCode(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }

        if (this.isStudentExistsInGroup(index, groupCode)) {
            System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
            return;
        }

        this.students.add(new Student(index, groupCode, firstName, lastName));
    }

    private boolean isStudentExistsInGroup(int index, String groupCode) {
        for (Student student : this.students) {
            if (student.getIndex() == index && student.getGroupCode().equals(groupCode)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        if (!this.isGroupExistsByCode(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
            return;
        }

        Group group = this.findGroupByCode(groupCode);
        Lecturer lecturer = this.findLecturerByCode(group.getLecturerId());

        System.out.println("Kod: " + group.getCode());
        System.out.println("Nazwa: " + group.getName());
        System.out.println("Prowadzący: " + lecturer.getDegree() + " " + lecturer.getFirstName() + " " + lecturer.getLastName());

        if (!this.students.isEmpty()) {
            System.out.println("Uczestnicy:");
            for (Student student : this.students) {
                System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName());
            }
        }

    }

    private Group findGroupByCode(String groupCode) {
        for (Group groupRow : this.groups) {
            if (groupRow.getCode().equals(groupCode)) {
                return groupRow;
            }
        }

        return null;
    }

    private Lecturer findLecturerByCode(int id) {
        for (Lecturer lecturerRow : this.lecturers) {
            if (lecturerRow.getId() == id) {
                return lecturerRow;
            }
        }

        return null;
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */
    public void addGrade(int studentIndex, String groupCode, double grade) {
        if (!this.isGroupExistsByCode(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }

        if (!this.isStudentExistsInGroup(studentIndex, groupCode)) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }

        if (this.isGradeExists(studentIndex, groupCode, grade)) {
            System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " + groupCode);
            return;
        }

        this.grades.add(new Grade(studentIndex, groupCode, grade));
    }

    private boolean isGradeExists(int studentIndex, String groupCode, double grade) {
        for (Grade gradeRow : this.grades) {
            if (gradeRow.getStudentIndex() == studentIndex && gradeRow.getGroupCode().equals(groupCode)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        for (Grade grade : this.grades) {
            if (grade.getStudentIndex() == index) {
                Group group = this.findGroupByCode(grade.getGroupCode());
                System.out.println(group.getName() + ": " + grade.getGrade());
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        if (!this.isGroupExistsByCode(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }

        for (Grade grade : this.grades) {
            if (grade.getGroupCode().equals(groupCode)) {
                Student student = this.findStudentByIndex(grade.getStudentIndex());
                System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName() + ": " + grade.getGrade());
            }
        }
    }

    private Student findStudentByIndex(int index) {
        for (Student student : this.students) {
            if (student.getIndex() == index) {
                return student;
            }
        }

        return null;
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        Set<Integer> indexes = new HashSet<>();
        for (Student student : this.students) {
            if (!indexes.contains(student.getIndex())) {
                System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName());
                indexes.add(student.getIndex());
            }
        }
    }
}
