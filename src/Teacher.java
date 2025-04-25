public class Teacher {
    private String name;
    private int age;
    private String gender;
    Course teacherCourse;
    Rating teacherRating;
    public Teacher(String name, int age, String gender, Course teacherCourse, Rating teacherRating) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.teacherCourse = teacherCourse;
        this.teacherRating = teacherRating;
    }
}
