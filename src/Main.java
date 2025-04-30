import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Main extends JFrame {
    String dbName = "CollegeClassManagementGUI";
    String connectionURL = "jdbc:derby:" + dbName + ";create=true";

    // Panels
    private static JPanel containerPanel; // Holds Login + Main
    private static JPanel loginPanel;
    private static JPanel adminWrapper;   // Holds cardPanel (+ adminPanel if admin)
    private static JPanel cardPanel;      // Holds Main menu cards
    private static JPanel adminPanel;     // Holds admin sub-panels

    // Login components
    private static JButton loginButton, createLogin;
    private static JTextField loginUser, loginPass;
    private static JLabel loginLabel;
    private static GridBagConstraints gbcLogin = new GridBagConstraints();

    // Card Panels
    private static JPanel panel1, panel2, panel3, panel4, panel5;
    private static JTextField text1, text2, text3, text4, text5;
    private static JLabel label1, label2, label3, label4, label5;
    private static JButton button1, button2, button3, button4, button5;

    // Admin buttons
    private static JButton deptAdd, deptEdit, deptDel;
    private static JButton courseAdd, courseEdit, courseDel;
    private static JButton studentAdd, studentEdit, studentDel;
    private static JButton teacherAdd, teacherEdit, teacherDel;

    // Layouts
    private static CardLayout cardLayout = new CardLayout();
    private static CardLayout adminLayout = new CardLayout();

    private menu navBar;

    public Main() {
        setTitle("College Class Management GUI");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Panel Initialization ---
        loginPanel = new JPanel(new GridBagLayout());
        cardPanel = new JPanel(cardLayout);
        adminPanel = new JPanel(adminLayout);
        adminWrapper = new JPanel(new BorderLayout());
        containerPanel = new JPanel(cardLayout);

        // --- Login Components ---
        loginLabel = new JLabel("Login");
        loginUser = new JTextField(20);
        loginPass = new JTextField(20);
        loginButton = new JButton("Login");
        createLogin = new JButton("Create Account");

        loginButton.addActionListener(e -> login());
        createLogin.addActionListener(e -> createLogin());

        gbcLogin.insets = new Insets(10, 10, 10, 10);
        gbcLogin.gridx = 0;
        gbcLogin.gridy = 0;

        loginPanel.add(loginLabel, gbcLogin);
        gbcLogin.gridy++;
        loginPanel.add(loginUser, gbcLogin);
        gbcLogin.gridy++;
        loginPanel.add(loginPass, gbcLogin);
        gbcLogin.gridy++;
        loginPanel.add(loginButton, gbcLogin);
        gbcLogin.gridy++;
        loginPanel.add(createLogin, gbcLogin);

        // --- Main Menu Panels ---
        panel1 = new JPanel(); label1 = new JLabel("Academic Department"); text1 = new JTextField(20);
        panel2 = new JPanel(); label2 = new JLabel("Course"); text2 = new JTextField(20);
        panel3 = new JPanel(); label3 = new JLabel("Rating"); text3 = new JTextField(20);
        panel4 = new JPanel(); label4 = new JLabel("Student"); text4 = new JTextField(20);
        panel5 = new JPanel(); label5 = new JLabel("Teacher"); text5 = new JTextField(20);

        panel1.add(label1); panel1.add(text1);
        panel2.add(label2); panel2.add(text2);
        panel3.add(label3); panel3.add(text3);
        panel4.add(label4); panel4.add(text4);
        panel5.add(label5); panel5.add(text5);

        cardPanel.add(panel1, "1");
        cardPanel.add(panel2, "2");
        cardPanel.add(panel3, "3");
        cardPanel.add(panel4, "4");
        cardPanel.add(panel5, "5");

        // --- Container Panel ---
        containerPanel.add(loginPanel, "Login");
        containerPanel.add(adminWrapper, "Main");

        // --- Admin Panels ---
        JPanel adminDeptPanel = new JPanel();
        JPanel adminCoursePanel = new JPanel();
        JPanel adminStudentPanel = new JPanel();
        JPanel adminTeacherPanel = new JPanel();

        // Buttons for admin sections
        deptAdd = new JButton("Add Department");
        deptEdit = new JButton("Edit Department");
        deptDel = new JButton("Del Department");
        courseAdd = new JButton("Add Course");
        courseEdit = new JButton("Edit Course");
        courseDel = new JButton("Del Course");
        studentAdd = new JButton("Add Student");
        studentEdit = new JButton("Edit Student");
        studentDel = new JButton("Del Student");
        teacherAdd = new JButton("Add Teacher");
        teacherEdit = new JButton("Edit Teacher");
        teacherDel = new JButton("Del Teacher");

        // Add buttons to panels
        adminDeptPanel.add(deptAdd); adminDeptPanel.add(deptEdit); adminDeptPanel.add(deptDel);
        adminCoursePanel.add(courseAdd); adminCoursePanel.add(courseEdit); adminCoursePanel.add(courseDel);
        adminStudentPanel.add(studentAdd); adminStudentPanel.add(studentEdit); adminStudentPanel.add(studentDel);
        adminTeacherPanel.add(teacherAdd); adminTeacherPanel.add(teacherEdit); adminTeacherPanel.add(teacherDel);

        // Add panels to adminPanel
        adminPanel.add(adminDeptPanel, "AdminDept");
        adminPanel.add(adminCoursePanel, "AdminCourse");
        adminPanel.add(adminStudentPanel, "AdminStudent");
        adminPanel.add(adminTeacherPanel, "AdminTeacher");

        // --- Final Wrapper (will be updated in login) ---
        adminWrapper.add(cardPanel, BorderLayout.CENTER);

        add(containerPanel); // Add to frame
    }

    private void createLogin() {
        String username = loginUser.getText();
        String password = loginPass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement("INSERT INTO Accounts (username, password, is_admin) VALUES (?, ?, FALSE)")) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account created successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating account: " + ex.getMessage());
        }
    }

    private void login() {
        String username = loginUser.getText();
        String password = loginPass.getText();
        boolean isAdmin = false;

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM Accounts WHERE username = ? AND password = ?")) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isAdmin = rs.getBoolean("is_admin");

                if (isAdmin) {
                    // Add admin panel only once
                    if (adminWrapper.getComponentCount() < 2) {
                        adminWrapper.add(adminPanel, BorderLayout.SOUTH);
                    }
                    adminLayout.show(adminPanel, "AdminDept");
                }

                JOptionPane.showMessageDialog(this, "Login successful.");
                navBar = new menu(cardPanel, adminPanel, isAdmin);
                setJMenuBar(navBar.getCustomMenuBar());

                cardLayout.show(containerPanel, "Main");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error logging in: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseController dbBuilder = new DatabaseController();
        dbBuilder.createTables();

        EventQueue.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}