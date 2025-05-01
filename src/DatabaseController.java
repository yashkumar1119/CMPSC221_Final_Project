import java.sql.*;

public class DatabaseController {
    String dbName="CollegeClassManagementGUI";
    String connectionURL = "jdbc:derby:"+dbName+";create=true";
    Connection conn = null;
    Statement stm;
    ResultSet myResult;
    String data;
    String[] tableNames = {"Accounts", "Courses", "Departments", "Ratings", "Students", "Teachers"};
    String[] createTables = {
            "CREATE TABLE Accounts (" +
                    "account_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "username VARCHAR(50) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "is_admin BOOLEAN)",

            "CREATE TABLE Courses (" +
                    "course_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "course_name VARCHAR(100), " +
                    "course_code VARCHAR(20), " +
                    "course_type VARCHAR(50), " +
                    "course_credit VARCHAR(20))",

            "CREATE TABLE Departments (" +
                    "department_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "department_name VARCHAR(100), " +
                    "course_offered INT, " +
                    "FOREIGN KEY (course_offered) REFERENCES Courses(course_id))",

            "CREATE TABLE Ratings (" +
                    "rating_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "rating_value INT)",

            "CREATE TABLE Students (" +
                    "student_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "name VARCHAR(100), " +
                    "age INT, " +
                    "gender VARCHAR(10), " +
                    "student_course INT, " +
                    "FOREIGN KEY (student_course) REFERENCES Courses(course_id))",

            "CREATE TABLE Teachers (" +
                    "teacher_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "name VARCHAR(100), " +
                    "age INT, " +
                    "gender VARCHAR(10), " +
                    "course_taught INT, " +
                    "teacher_rating INT, " +
                    "FOREIGN KEY (course_taught) REFERENCES Courses(course_id), " +
                    "FOREIGN KEY (teacher_rating) REFERENCES Ratings(rating_id))"
    };

    public void createTables() {
        try {
            conn = DriverManager.getConnection(connectionURL);
            stm = conn.createStatement();
            for (int i = 0; i < createTables.length; i++) {
                myResult = conn.getMetaData().getTables(null, null, tableNames[i], new String[]{"TABLE"});
                if (myResult.next()) {
                    System.out.println("Table '" + tableNames[i] + "' already exists.");
                } else {
                    stm.execute(createTables[i]);
                    System.out.println("Table '" + tableNames[i] + "' is created.");
                }
                myResult.close();
            }
            // preset admin account
            stm.executeUpdate("INSERT INTO Accounts (username, password, is_admin) VALUES ('admin', 'admin123', TRUE)");
            conn.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}