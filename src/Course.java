public class Course {
    private String courseName;
    private String courseCode;
    private String courseType;
    private String courseCredit;
    Rating courseRating;
    public Course(String courseName, String courseCode, String courseType, String courseCredit, Rating courseRating) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseType = courseType;
        this.courseCredit = courseCredit;
        this.courseRating = courseRating;

    }
}
