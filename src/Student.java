public class Student {
    private String name;
    private int age;
    private String gender;
    Course studentCourse;

    public Student(String name, int age, String gender, Course studentCourse) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.studentCourse = studentCourse;
    }
}
