import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import javax.swing.border.TitledBorder;

public class bank {
    private static JFrame frame;
    private static JPanel panel;
    private static Socket socket;
    // private static PrintWriter out;
    // private static BufferedReader in;
    private static DataInputStream in;
    private static DataOutputStream out;

    public static void sendMessage(String message) {
        try {
            out.write(message.getBytes());
            out.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error sending a message to the server", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String getResponse() {
        try {
            byte[] arr = new byte[1024];
            int bytes = in.read(arr);

            if (bytes == -1) { // конец потока
                return null;
            }
            return new String(arr, 0, bytes);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Data reading error: ", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] s) {
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("BankTerminalClient");
        frame.setSize(380, 420);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                // "\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435",
                "Connection to the server",
                TitledBorder.LEFT, TitledBorder.TOP));
        panel.setBounds(5, 0, 355, 90);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelAdress = new JLabel("adress");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelAdress, gbc);

        JTextField textFieldAdress = new JTextField("www.center.ogu");
        textFieldAdress.setPreferredSize(new Dimension(130, 20));
        gbc.gridx = 1;
        gbc.weightx = 2.0;
        panel.add(textFieldAdress, gbc);

        JLabel labelPort = new JLabel("port");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panel.add(labelPort, gbc);

        JTextField textFieldPort = new JTextField("2000");
        gbc.gridx = 1;
        gbc.gridheight = 1;
        panel.add(textFieldPort, gbc);

        JButton createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(105, 45));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        panel.add(createButton, gbc);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String adress = textFieldAdress.getText();
                    int port = Integer.parseInt(textFieldPort.getText());
                    ;
                    socket = new Socket(adress, port);
                    // out = new PrintWriter(socket.getOutputStream(), true);
                    // out = new PrintWriter(new BufferedWriter(new
                    // OutputStreamWriter(socket.getOutputStream())));
                    // in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    // accountWindow();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error: Invalid port: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);

                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error: Unknown host: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    // accountWindow();
                    // accountTransaction();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error connection: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.setLayout(null);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void accountWindow() {
        // frame.getContentPane().remove(panel);
        frame.remove(panel);

        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

        JPanel cratePanel = new JPanel();
        cratePanel.setLayout(new GridBagLayout());
        cratePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                // "\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435",
                "Creating an account",
                TitledBorder.LEFT, TitledBorder.TOP));
        cratePanel.setBounds(5, 0, 355, 90);

        GridBagConstraints gbcCreate = new GridBagConstraints();
        gbcCreate.fill = GridBagConstraints.HORIZONTAL;
        gbcCreate.insets = new Insets(5, 5, 5, 5);

        JLabel labelNumber = new JLabel("account N");
        gbcCreate.gridx = 0;
        gbcCreate.gridy = 0;
        cratePanel.add(labelNumber, gbcCreate);

        JTextField textFieldAccount = new JTextField();
        textFieldAccount.setPreferredSize(new Dimension(130, 20));
        gbcCreate.gridx = 1;
        gbcCreate.gridheight = 1;
        cratePanel.add(textFieldAccount, gbcCreate);

        JLabel labelPasswd = new JLabel("Password");
        gbcCreate.gridx = 0;
        gbcCreate.gridy = 1;
        gbcCreate.weightx = 1;
        cratePanel.add(labelPasswd, gbcCreate);

        JPasswordField textFieldPasswd = new JPasswordField();
        gbcCreate.gridx = 1;
        gbcCreate.gridheight = 1;
        cratePanel.add(textFieldPasswd, gbcCreate);

        JButton createAccountButton = new JButton("Create");
        createAccountButton.setPreferredSize(new Dimension(105, 45));
        gbcCreate.gridx = 2;
        gbcCreate.gridy = 0;
        gbcCreate.gridheight = 2;
        cratePanel.add(createAccountButton, gbcCreate);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = textFieldAccount.getText();
                String accountPasswd = String.valueOf(textFieldPasswd.getPassword());
                if (accountNumber.trim().isEmpty() || accountPasswd.trim().isEmpty())
                    JOptionPane.showMessageDialog(
                            frame, "Error: empty input");
                else {
                    String message = "create " + accountNumber + " " + accountPasswd;
                    // out.println("create " + accountNumber + " " + accountPasswd);
                    // out.writeBytes(message);
                    // out.write(getBytes(message));
                    // out.write(message.getBytes());
                    // out.flush();
                    sendMessage(message);
                    String response = getResponse();
                    System.out.println(response);

                    if (response == null)
                        JOptionPane.showMessageDialog(
                                frame, "No response from server", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        if ((response.trim()).equals("error"))
                            JOptionPane.showMessageDialog(
                                    frame, "Creation failed", "Error", JOptionPane.ERROR_MESSAGE);
                        else if ((response.trim()).equals("ok"))
                            JOptionPane.showMessageDialog(
                                    frame, "Creation successful");
                    }
                }
            }
        });

        frame.add(cratePanel);

        JPanel registrPanel = new JPanel();
        registrPanel.setLayout(new GridBagLayout());
        registrPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                // "\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435",
                "User registration",
                TitledBorder.LEFT, TitledBorder.TOP));
        registrPanel.setBounds(5, 95, 355, 90);

        GridBagConstraints gbcRegistr = new GridBagConstraints();

        gbcRegistr.fill = GridBagConstraints.HORIZONTAL;
        gbcRegistr.insets = new Insets(5, 5, 5, 5);

        JLabel labelNumberRegistr = new JLabel("account N");
        gbcRegistr.gridx = 0;
        gbcRegistr.gridy = 0;
        registrPanel.add(labelNumberRegistr, gbcRegistr);

        JTextField textFielAccountRegistr = new JTextField();
        textFielAccountRegistr.setPreferredSize(new Dimension(130, 20));
        gbcRegistr.gridx = 1;
        gbcRegistr.gridheight = 1;
        registrPanel.add(textFielAccountRegistr, gbcRegistr);

        JLabel labelPasswdRegistr = new JLabel("Password");
        gbcRegistr.gridx = 0;
        gbcRegistr.gridy = 1;
        gbcRegistr.weightx = 1;
        registrPanel.add(labelPasswdRegistr, gbcRegistr);

        JPasswordField textFieldPasswdRegistr = new JPasswordField();
        gbcRegistr.gridx = 1;
        gbcRegistr.gridheight = 1;
        registrPanel.add(textFieldPasswdRegistr, gbcRegistr);

        JButton registrationtButton = new JButton("Registration");
        registrationtButton.setPreferredSize(new Dimension(105, 45));
        gbcRegistr.gridx = 2;
        gbcRegistr.gridy = 0;
        gbcRegistr.gridheight = 2;
        registrPanel.add(registrationtButton, gbcRegistr);

        registrationtButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = textFielAccountRegistr.getText();
                String accountPasswd = String.valueOf(textFieldPasswdRegistr.getPassword());
                if (accountNumber.isEmpty() || accountPasswd.isEmpty())
                    JOptionPane.showMessageDialog(
                            frame, "Error: input empty");
                else {
                    String message = "open " + accountNumber + " " + accountPasswd;
                    sendMessage(message);
                    String response = getResponse();

                    if (response == null)
                        JOptionPane.showMessageDialog(
                                frame, "No response from server", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        if (response.trim().equals("error"))
                            JOptionPane.showMessageDialog(
                                    frame, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
                        else if (response.trim().equals("ok"))
                            accountTransaction();
                    }
                }
            }
        });

        frame.add(registrPanel);
        frame.setLayout(null);
        frame.revalidate();
        frame.repaint();
    }

    public static void accountTransaction() {

        JPanel accountTransactionPanel = new JPanel();
        accountTransactionPanel.setLayout(new GridBagLayout());
        accountTransactionPanel.setBounds(5, 180, 355, 90);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lablelTransaction = new JLabel("account number   amount");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        accountTransactionPanel.add(lablelTransaction, gbc);

        JTextField textFieldTransaction = new JTextField();
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        textFieldTransaction.setPreferredSize(new Dimension(130, 20));
        accountTransactionPanel.add(textFieldTransaction, gbc);

        // Dimension buttonSize = new Dimension(80, 30);

        JButton statusButton = new JButton("status");
        // statusButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        accountTransactionPanel.add(statusButton, gbc);

        JButton withdrawButton = new JButton("withdraw");
        // withdrawButton.setPreferredSize(buttonSize);
        gbc.gridx = 1;
        accountTransactionPanel.add(withdrawButton, gbc);

        JButton depositButton = new JButton("deposit");
        // depositButton.setPreferredSize(buttonSize);
        gbc.gridx = 2;
        accountTransactionPanel.add(depositButton, gbc);

        JButton transferButton = new JButton("transfer");
        // transferButton.setPreferredSize(buttonSize);
        gbc.gridx = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        accountTransactionPanel.add(transferButton, gbc);

        frame.add(accountTransactionPanel);

        JTextArea textPaneTransaction = new JTextArea();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        textPaneTransaction.setEditable(false);
        // textPaneTransaction.setPreferredSize(new Dimension(340,90));
        textPaneTransaction.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)));

        textPaneTransaction.setCaretPosition(textPaneTransaction.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(
                textPaneTransaction,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(340, 90));
        scrollPane.setBounds(10, 275, 330, 80);

        statusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage("view");
                String response = getResponse();
                if (response == null)
                    JOptionPane.showMessageDialog(
                            frame, "No response from server", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    if (response.trim().equals("error"))
                        JOptionPane.showMessageDialog(
                                frame, "View account failed", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        // StyledDocument doc = textPaneTransaction.getStyledDocument();
                        // SimpleAttributeSet attrs = new SimpleAttributeSet();
                        // /* if(textPaneTransaction.getText().trim().isEmpty()) */
                        // textPaneTransaction.append("Your account\tamount\n");
                        // /* else */ textPaneTransaction.add(response + "\n");
                        textPaneTransaction.append("Your account\tamount\n");
                        textPaneTransaction.append(response + "\n");
                    }
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFieldTransaction.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(
                            frame, "Error: input empty");
                else {
                    sendMessage("get " + textFieldTransaction.getText());
                    String response = getResponse();
                    if (response == null)
                        JOptionPane.showMessageDialog(
                                frame, "No response from server", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        if (response.trim().equals("error"))
                            textPaneTransaction.append("Error in withdrawing the amount\n");
                        else if (response.trim().equals("ok")) {
                            textPaneTransaction.append("Successful withdrawal of the amount\n");
                        }
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amount = textFieldTransaction.getText();
                if (amount.trim().isEmpty())
                    JOptionPane.showMessageDialog(
                            frame, "Error: input empty");
                else {
                    sendMessage("put " + amount);
                    String response = getResponse();
                    if (response == null)
                        JOptionPane.showMessageDialog(
                                frame, "No response from server", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        if (response.trim().equals("error"))
                            textPaneTransaction.append("Sum stacking error\n");
                        else if (response.trim().equals("ok")) {
                            textPaneTransaction.append("Successful stacking of the amount\n");
                        }
                    }
                }
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFieldTransaction.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(
                            frame, "Error: input empty");
                else {
                    sendMessage("send " + textFieldTransaction.getText());
                    String response = getResponse();
                    if (response == null)
                        JOptionPane.showMessageDialog(
                                frame, "No response from server", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        if (response.trim().equals("error"))
                            textPaneTransaction.append("Error in transferring the amount\n");
                        else if (response.trim().equals("ok")) {
                            textPaneTransaction.append("Successful transfer of the amount\n");
                        }
                    }
                }
                /*
                 * String [] amount = textPaneTransaction.getText().split(" ");
                 * if(amount.length == 2) out.println("send" + amount[0] + " " + amount[1]);
                 * else JOptionPane.showMessageDialog(
                 * frame, "Error input for transaction", "Error", JOptionPane.ERROR_MESSAGE);
                 * try{
                 * String response = in.readLine();
                 * if(response == "error")
                 * textPaneTransaction.setText("Sum stacking error");
                 * // JOptionPane.showMessageDialog(
                 * // frame, "Withdraw mony failed", "Error", JOptionPane.ERROR_MESSAGE);
                 * else if(response == "ok"){
                 * textPaneTransaction.setText("Successful stacking of the amount");
                 * }
                 * }
                 * catch(IOException ex){
                 * JOptionPane.showMessageDialog(frame, "Error reading server response",
                 * "Error", JOptionPane.ERROR_MESSAGE);
                 * }
                 */
            }
        });

        // frame.add(accountTransactionPanel);
        // textPaneTransaction.setFont(new Font("Arial", Font.PLAIN, 14));

        frame.add(scrollPane);
        // frame.add(textPaneTransaction);
        frame.revalidate();
        frame.repaint();

        // frame.setLayout(null);
    }

}
