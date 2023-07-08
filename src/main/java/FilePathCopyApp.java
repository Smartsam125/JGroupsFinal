import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

public class FilePathCopyApp extends JFrame {
    public  JTextField filePathField;
    private JButton openButton;
    private JButton copyButton;
    public String filePath;

    public String  getFilePathCopyApp() {
        setTitle("File Path Copy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);

        filePathField = new JTextField();
        mainPanel.add(filePathField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        openButton = new JButton("Open Folder");
        buttonPanel.add(openButton);

        copyButton = new JButton("SendFile");
        buttonPanel.add(copyButton);

        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(FilePathCopyApp.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
       // filePath = filePathField.getText();
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ) {
                filePath = filePathField.getText();
               // System.out.println(filePath);
                if (!filePath.isEmpty()) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                            new StringSelection(filePath), null);
                    JOptionPane.showMessageDialog(FilePathCopyApp.this,
                            "File Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);



                      dispose();

                } else {
                    JOptionPane.showMessageDialog(FilePathCopyApp.this,
                            "No file path selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            //dispose();


        });

        System.out.println(filePath);


  return filePath;
    }
}
