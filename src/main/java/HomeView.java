import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.Protocol;
import org.jgroups.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeView extends JPanel {
    public static JChannel ch =null;
    public static DefaultListModel<String> chatModel;
    private JList<String> chatList;
    private JScrollPane chatScrollPane;
    private JTextField messageField;
    private JButton sendButton;
    private JButton optionsButton;
    private JButton exitButton;
    private JButton joinButton;

    public  JPanel selectGroupPanel; // Reference to selectGroupPanel in SeShareApp

    public HomeView() {



        this.selectGroupPanel = selectGroupPanel;

        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F5F5));

        chatModel = new DefaultListModel<>();


        chatList = new JList<>(chatModel);
        chatList.setFont(new Font("Arial", Font.PLAIN, 14));
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.setBackground(new Color(0xFFFFFF));
        chatList.setFixedCellHeight(40);

        chatScrollPane = new JScrollPane(chatList);
        chatScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(0xFFFFFF));
        add(inputPanel, BorderLayout.SOUTH);


        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        messageField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0xEDEDED)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(messageField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(0x25D366));
        sendButton.setForeground(new Color(0xFFFFFF));
        sendButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(sendButton);

        optionsButton = new JButton("SendFile");
        optionsButton.setFont(new Font("Arial", Font.BOLD, 14));
        optionsButton.setBackground(new Color(0x0084FF));
        optionsButton.setForeground(new Color(0xFFFFFF));
        optionsButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(optionsButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(new Color(0xFF0000));
        exitButton.setForeground(new Color(0xFFFFFF));
        exitButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        exitButton.setVisible(false);
        buttonPanel.add(exitButton);

        joinButton = new JButton("Join Distributed Systems");
        joinButton.setFont(new Font("Arial", Font.BOLD, 14));
        joinButton.setBackground(new Color(0x0084FF));
        joinButton.setForeground(new Color(0xFFFFFF));
        joinButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(joinButton);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(0x0084FF));
        searchButton.setForeground(new Color(0xFFFFFF));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(searchButton, BorderLayout.NORTH);
        optionsButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilePathCopyApp filecopy = new FilePathCopyApp();
                filecopy.setVisible(true);
                chatModel.addElement(filecopy.getFilePathCopyApp());


            }
        }));

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle search button action
                String searchText = JOptionPane.showInputDialog(null, "SearchForContent:", "Search", JOptionPane.PLAIN_MESSAGE);
                if (searchText != null) {
                 //Db databasequery = new Db();
                // databasequery.
                   Connection conn= Db.connection();
                    try {
                        PreparedStatement pst = conn.prepareStatement("select fileUri,filename from coursematerial where filename like '%"+searchText+"%'");
                        ResultSet rs = pst.executeQuery();
                        while(rs.next()){

                            chatModel.addElement(rs.getString("filename"));

                           int choice= JOptionPane.showConfirmDialog(null, "Do you want to download this file?", "Download", JOptionPane.YES_NO_OPTION);
                            if(choice==JOptionPane.YES_OPTION){
                                String fileUri=rs.getString("fileUri");

                                try  {
                                    String destination="C:\\Users\\User\\Downloads\\Documents\\";
                                    Path source = Path.of(fileUri);
                                    Path dest = Path.of(destination);
                                    //Files.copy(source, destination);
                                    Files.copy(source, dest.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                                    int optionds=JOptionPane.showConfirmDialog(null, "DownLoadedSuccessFully", "OpenFile?", JOptionPane.YES_NO_OPTION);
                                      if(optionds==JOptionPane.YES_OPTION){
                                        Desktop desktop = Desktop.getDesktop();
                                        File file = new File(destination+source.getFileName());
                                        if(file.exists()) desktop.open(file);
                                    }
                                    System.out.println("File copied successfully");
                                      conn.close();

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }



                            }
                            else{
                                System.out.println("No");
                            }







                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText().trim();
                ch.setReceiver(new Receiver() {
                    @Override
                    public void receive(Message msg) {
                        System.out.println("<<  " + msg.getObject() + " [" + msg.getSrc() + "]");
                        chatModel.addElement( msg.getSrc().toString() +":"+ msg.getObject().toString());

                    }


                });

                if (!message.isEmpty()) {

                        //String line = Util.readStringFromStdin(": ");
                        try {
                            ch.send(null, message);
                            //chatModel.addElement(message);
                            messageField.setText("");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }



            }
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle options button action (e.g., file sharing)
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                SeShareApp sam = new SeShareApp();
                sam.showGroupSelectionDialog();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(HomeView.this);
                frame.getContentPane().remove(HomeView.this);
                frame.dispose();




            }
        });

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                joinButton.setEnabled(false);
                loadDistributedSystemsContent();
                buttonPanel.remove(joinButton); // Remove the button from the panel
                revalidate(); // Revalidate the panel to update the layout
                try {
                    HomeView.joinDistributedSystems();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void loadDistributedSystemsContent() {
        // Your code to load the extra content for Distributed Systems

        // Show the Exit button
        exitButton.setVisible(true);
    }
    public static void joinDistributedSystems() throws  Exception {
        String name = JOptionPane.showInputDialog(null, "Enter your name:", "Name Input", JOptionPane.PLAIN_MESSAGE);
        Protocol[] prot_stack = {
                new UDP().setValue("bind_addr", InetAddress.getByName("127.0.0.1")),
                new PING(),
                new MERGE3(),
                new FD_SOCK(),
                new FD_ALL(),
                new VERIFY_SUSPECT(),
                new BARRIER(),
                new NAKACK2(),
                new UNICAST3(),
                new STABLE(),
                new GMS(),
                new UFC(),
                new MFC(),
                new FRAG2()
        };
         ch = new JChannel(prot_stack).name(name);
        ch.setReceiver(new Receiver() {
            public void viewAccepted(View new_view) {
                // System.out.println("NewJoined" + new_view);
                String joinMessage = new_view + " has joined";
                chatModel.addElement(joinMessage);
                //messagesBySam.appendText(joinMessage + "\n");
                System.out.println(joinMessage);
            }


        });

        ch.connect("DistributedSystem");





    }
}
