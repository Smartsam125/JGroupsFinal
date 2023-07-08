import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeShareApp extends JFrame {
    private Connection conn;
    public static String email;

    public SeShareApp() {
        super("SeShare Login");

        conn = Db.connection();

        // Creating labels
        JLabel label1 = new JLabel("UserName");
        JLabel label2 = new JLabel("Password");

        // Creating input fields
        JTextField textField1 = new JTextField();
        JPasswordField textField2 = new JPasswordField();

        // Creating buttons
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        JButton signUpButton = new JButton("Sign Up");

        // Creating a panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adding components to the panel
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(submitButton);
        panel.add(clearButton);
        panel.add(signUpButton);

        // Styling buttons
        submitButton.setBackground(Color.darkGray);
        submitButton.setForeground(Color.white);
        clearButton.setBackground(Color.darkGray);
        clearButton.setForeground(Color.white);
        signUpButton.setBackground(Color.darkGray);
        signUpButton.setForeground(Color.white);

        // Adding event listeners
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textField1.getText();
                String password = new String(textField2.getPassword());
                SeShareApp.this.email = email;
                String loginInfo = email + password;

                try {
                    PreparedStatement pst = conn.prepareStatement("select name,password from student where  name=? and password=?");
                    pst.setString(1, email);
                    pst.setString(2, password);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "WelcomeSam");
                        showGroupSelectionDialog();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "PleaseSignup");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getCause());
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createSignUpScene();
            }
        });

        // Adding the panel to the frame
        add(panel);

        // Setting frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createSignUpScene() {
        // Creating labels
        JLabel label1 = new JLabel("Username");
        JLabel label2 = new JLabel("New Password");

        // Creating input fields
        JTextField textField1 = new JTextField();
        JPasswordField textField2 = new JPasswordField();

        // Creating buttons
        JButton signUpButton = new JButton("Sign Up");

        // Creating a panel
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adding components to the panel
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(signUpButton);

        // Styling buttons
        signUpButton.setBackground(Color.darkGray);
        signUpButton.setForeground(Color.white);

        // Adding event listener
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = new String(textField2.getPassword());
                String signUpInfo = username + password;

                try {
                    PreparedStatement pst = conn.prepareStatement("INSERT INTO student (name, password) VALUES (?, ?)");
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.executeUpdate();
                } catch (Exception r) {
                    System.out.println(r.getMessage());
                }

                remove(panel);
                SeShareApp.this.revalidate();
                SeShareApp.this.repaint();
            }
        });

        // Adding the panel to the frame
        add(panel);

        // Setting frame properties
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

    public  void showGroupSelectionDialog() {
        String[] groupOptions = {"Distributed Systems", "Systems Programming", "Research Methodology", "Embedded Systems"};
        JComboBox<String> groupsComboBox = new JComboBox<>(groupOptions);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select a Group:"));
        panel.add(groupsComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Group Selection",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedGroup = (String) groupsComboBox.getSelectedItem();
            if (selectedGroup.equals("Distributed Systems")) {
                createHomeView();
            } else {
                // Handle other group selections
            }
        }
    }

    private void createHomeView() {
        JFrame frame = new JFrame("HomeView");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(new HomeView());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SeShareApp();
    }
}
