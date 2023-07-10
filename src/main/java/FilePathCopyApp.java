import org.jgroups.BytesMessage;
import org.jgroups.JChannel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FilePathCopyApp extends JFrame {
    public static JChannel channel= HomeView.ch;
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
                            "File Sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
                             try{
                                 FileInputStream in=new FileInputStream(Path.of(filePath).toFile());

                                 byte[] buf=new byte[8096];
                                Connection conn= Db.connection();
                                Path filename= Paths.get(filePath);


                                 PreparedStatement pst = conn.prepareStatement("INSERT INTO coursematerial (filename,fileUri) VALUES (?, ?)");
                                 pst.setString(1, filename.getFileName().toString());
                                 System.out.println(filename.getFileName().toString());
                                 //ResultSet rs=pst.executeQuery();
                                 pst.setString(2, filePath);
                                    pst.executeUpdate();
                                 for(;;) {
                                     int bytes=in.read(buf);
                                     if(bytes == -1)
                                         break;
                                     channel.send(new BytesMessage(null, buf, 0, bytes));
                                     System.out.println("File sent");


                                 }

                             }catch (Exception ex){
                                 //System.out.println(ex.getCause());
                                 //System.out.println(ex.getMessage());
                                 ex.printStackTrace();
                             }



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
