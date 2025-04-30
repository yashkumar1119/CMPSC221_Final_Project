import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class menu extends JMenuBar implements ActionListener {
    private JMenu menuNavigation;
    private JMenuItem academicDepartment, course, rating, student, teacher;
    private JPanel cardPanel, adminPanel;
    boolean isAdmin;

    public menu(JPanel cardPanel, JPanel adminPanel, boolean isAdmin) {
        this.cardPanel = cardPanel;
        this.adminPanel = adminPanel;
        this.isAdmin = isAdmin;

        menuNavigation = new JMenu("Menu");

        academicDepartment = new JMenuItem("Academic Department");
        course = new JMenuItem("Course");
        rating = new JMenuItem("Rating");
        student = new JMenuItem("Student");
        teacher = new JMenuItem("Teacher");

        academicDepartment.addActionListener(this);
        course.addActionListener(this);
        rating.addActionListener(this);
        student.addActionListener(this);
        teacher.addActionListener(this);

        menuNavigation.add(academicDepartment);
        menuNavigation.add(course);
        menuNavigation.add(rating);
        menuNavigation.add(student);
        menuNavigation.add(teacher);

        this.add(menuNavigation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        CardLayout al = (CardLayout) adminPanel.getLayout();

        if (command.equals("Rating")) {
            adminPanel.setVisible(false);
        } else {
            adminPanel.setVisible(isAdmin);
        }

        switch (command) {
            case "Academic Department":
                cl.show(cardPanel, "1");
                if (isAdmin) {
                    al.show(adminPanel, "AdminDept");
                }
                break;
            case "Course":
                cl.show(cardPanel, "2");
                if (isAdmin) {
                    al.show(adminPanel, "AdminCourse");
                }
                break;
            case "Rating":
                cl.show(cardPanel, "3");
                break;
            case "Student":
                cl.show(cardPanel, "4");
                if (isAdmin) {
                    al.show(adminPanel, "AdminStudent");
                }
                break;
            case "Teacher":
                cl.show(cardPanel, "5");
                if (isAdmin) {
                    al.show(adminPanel, "AdminTeacher");
                }
                break;
        }
    }

    public JMenuBar getCustomMenuBar() {
        return this;
    }
}
