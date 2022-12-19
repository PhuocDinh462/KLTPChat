package Client;

import javax.swing.*;
import java.util.*;
import java.util.regex.Pattern;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;




public class SignUp extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Enum: Sign Up Status
     */
    public enum SignUpStatus {
        /**
         * Waiting for response
         */
        Waiting,

        /**
         * Failed cause account are already signed up
         */
        Failed,

        /**
         * Sign up successful
         */
        Accepted
    }

    /**
     * Attribute: SignUpStatus - status
     * The status of Sign Up Request
     */
    public static SignUpStatus status;

    /**
     * Default constructor
     */
    public SignUp() {
        addComponents();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Đăng ký");
        setResizable(false);
        setVisible(true);
    }
    //check valid email
    public static boolean patternMatches(String emailAddress, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
    
    public static String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
    
    /**
     * Add components to Sign Up JFrame
     */
    
    public void addComponents() {
        // Content Pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(11, 1, 0, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        

        // Back to Sign In Button
        JButton backButton = new JButton("Trở về");
        backButton.addActionListener(e -> backButtonEventHandler());
        backButton.setFocusPainted(false);

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));
        backPanel.add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Đăng ký", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        
        // Fullname Panel
        JTextField fullnameTextField = new JTextField(15);
        
        JPanel fullnamePanel = new JPanel();
        fullnamePanel.setLayout(new BoxLayout(fullnamePanel, BoxLayout.X_AXIS));
        fullnamePanel.add(new JLabel("Họ và Tên"));
        fullnamePanel.add(Box.createHorizontalStrut(40));
        fullnamePanel.add(fullnameTextField);
        
        
        //Gender Panel
        JRadioButton radioBtn1 = new JRadioButton("Nam");
        JRadioButton radioBtn2 = new JRadioButton("Nữ");
        radioBtn1.setBounds(50, 60, 170, 30);
        radioBtn2.setBounds(50, 100, 170, 30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioBtn1);
        bg.add(radioBtn2);

        JPanel GenderPanel = new JPanel();
        GenderPanel.setLayout(new BoxLayout(GenderPanel, BoxLayout.X_AXIS));
        GenderPanel.add(new JLabel("Giới tính "));
        GenderPanel.add(Box.createHorizontalStrut(40));
        GenderPanel.add(radioBtn1);
        GenderPanel.add(radioBtn2);
        String gender = new String();
        if(radioBtn1.isSelected()) {
        	gender="Nam";
        } else if(radioBtn2.isSelected()) {
        	gender="Nữ";
        }
        else {
        	gender="";
        }
        
        JPanel CalendarPanel = new JPanel();
        
        CalendarPanel.setLayout(new BoxLayout(CalendarPanel, BoxLayout.X_AXIS));
        
        JLabel lblNewLabel = new JLabel("Ngay Sinh");
        CalendarPanel.add(lblNewLabel);
        
        Component horizontalStrut = Box.createHorizontalStrut(40);
        CalendarPanel.add(horizontalStrut);
        
        JDateChooser dateChooser = new JDateChooser();
        CalendarPanel.add(dateChooser);
                        
        // Address Panel
        JTextField addressTextField = new JTextField(15);
        
        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.add(new JLabel("Địa chi "));
        addressPanel.add(Box.createHorizontalStrut(40));
        addressPanel.add(addressTextField);
        

        // Username Panel
        JTextField usernameTextField = new JTextField(15);
        
	    JPanel usernamePanel = new JPanel();
	    usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
	    usernamePanel.add(new JLabel("Tên tài khoản"));
	    usernamePanel.add(Box.createHorizontalStrut(40));
	    usernamePanel.add(usernameTextField);
	    
	 // Email Panel
        JTextField emailTextField = new JTextField(15);
        
	    JPanel emailPanel = new JPanel();
	    emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));
	    emailPanel.add(new JLabel("Email"));
	    emailPanel.add(Box.createHorizontalStrut(40));
	    emailPanel.add(emailTextField);
           
        // Password Panel
        JPasswordField passwordField = new JPasswordField(15);
        
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.add(new JLabel("Mật khẩu"));
        passwordPanel.add(Box.createHorizontalStrut(66));
        passwordPanel.add(passwordField);
        contentPane.add(passwordPanel);
        
        // Re-Password Panel
        JPasswordField repasswordField = new JPasswordField(15);
        
        JPanel repasswordPanel = new JPanel();
        repasswordPanel.setLayout(new BoxLayout(repasswordPanel, BoxLayout.X_AXIS));
        repasswordPanel.add(new JLabel("Nhập lại mật khẩu"));
        repasswordPanel.add(Box.createHorizontalStrut(18));
        repasswordPanel.add(repasswordField);
        contentPane.add(repasswordPanel);

        // Sign Up Now Button
        JButton signUpNowButton = new JButton("Đăng ký");
        
        signUpNowButton.addActionListener(e -> signUpNowButtonEventHandler(
        		fullnameTextField.getText(),
        		SignUp.getSelectedButtonText(bg),
        		dateChooser.getDate(),
        		addressTextField.getText(),
                usernameTextField.getText(),
                emailTextField.getText(),
                String.valueOf(passwordField.getPassword()),
//                Array.toString(passwordField.getPassword()),
                String.valueOf(repasswordField.getPassword())));
        signUpNowButton.setFocusPainted(false);
        
        
     // Add components to Content Pane and Settings
        contentPane.add(backPanel);
        contentPane.add(titleLabel);
        contentPane.add(fullnamePanel);
        contentPane.add(GenderPanel);
        contentPane.add(CalendarPanel);
        contentPane.add(addressPanel);
        contentPane.add(usernamePanel);
        contentPane.add(emailPanel);
        contentPane.add(passwordPanel);
        contentPane.add(repasswordPanel);
        contentPane.add(signUpNowButton);
        setContentPane(contentPane);
        pack();
    }

    /**
     * Back Button Event Handler: Open Sign In JFrame
     */
    void backButtonEventHandler() {
        new SignIn();
        dispose();
    }

    /**
     * Sign Up Now Button Event Handler
     * Check for validity of information, send Sign Up Request and Display result to user
     * @param username String
     * @param password String
     * @param repassword String
     */
    void signUpNowButtonEventHandler(String fullname,String gender,Date date, String address, String username,String email, String password, String repassword) {
    	String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    	Boolean test= SignUp.patternMatches(email, regexPattern);
    	if (fullname.isEmpty()){
        	JOptionPane.showMessageDialog(this, "Bạn chưa nhập họ tên!",
                  "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    	
        else if (gender==null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    	else if (date== null) {
    		JOptionPane.showMessageDialog(this, "Bạn chưa nhập ngày sinh!",
                  "Lỗi", JOptionPane.WARNING_MESSAGE);
    	}
        else if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập địa chỉ!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
        else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên tài khoản!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập email!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khẩu!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (repassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập lại mật khẩu!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (!password.equals(repassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu và mật khẩu nhập lại không trùng khớp!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        }else if(!test){
        	 JOptionPane.showMessageDialog(this, "Email nhập không hợp lệ ",
                     "Lỗi", JOptionPane.WARNING_MESSAGE);
        }else {
            status = SignUp.SignUpStatus.Waiting;

//            User createUser = new User(username, password, fullname, address, date.toString(),gender, email);
//            Main.sendMessage("Command_CreateAccount`" + username + "`" + password);
            Main.sendMessage("Command_CreateAccount`" + username + "`" + password + "`" + fullname + "`" + address + "`" +
            				date.toString()+ "`" + gender + "`" + email);
            while (status == SignUp.SignUpStatus.Waiting) System.out.print("");

            if (status == SignUp.SignUpStatus.Accepted) {
                Main.sendMessage("Command_SignedIn`" + username);
                new Main();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!",
                        "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}