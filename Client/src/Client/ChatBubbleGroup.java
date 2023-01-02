package Client;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class ChatBubbleGroup extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @Enum: Bubble Type
     */
    public enum BubbleType {
        /**
         * User chat bubble
         */
        Mine,

        /**
         * Others user chat bubble
         */
        Others,

        /**
         * File chat bubble
         */
        File
    }

    /**
     * Default constructor
     * @param bubbleType BubbleType
     * @param content String: Content of this Chat Bubble
     */
    public ChatBubbleGroup(BubbleType bubbleType, String content,String username,String timeTemp) {
   	 setBackground(Color.WHITE);
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = new Date();
    	String timeNow = formatter.format(date);
    	System.out.println("time now " + timeNow);
        String []str = timeTemp.split("@");
        System.out.println("timeTemp: " + str[0] + str[1]);
    	JLabel timeLabel = new JLabel(timeTemp);
        timeLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        if(timeNow.equals(str[0])) {
        	timeLabel.setText(str[1]);
        }

       JButton contentButton = new JButton(brContent(content));
       contentButton.setBorderPainted(false);
       contentButton.setFocusPainted(false);
       
       JLabel viewed = new JLabel("đã xem");
       viewed.setFont(new Font("Times New Roman", Font.PLAIN, 10));
       
       JLabel sender = new JLabel(username + " sent");
       sender.setFont(new Font("Times New Roman", Font.PLAIN, 10));

       switch (bubbleType) {
           case Mine -> {
               contentButton.setBackground(new java.awt.Color(0,132,255));
               contentButton.setForeground(Color.WHITE);
               setLayout(new FlowLayout(FlowLayout.RIGHT));
               add(viewed);
               add(timeLabel);
               add(contentButton);
           }
           case Others -> {
               contentButton.setBackground(new java.awt.Color(217,217,217));
               setLayout(new FlowLayout(FlowLayout.LEFT));
               add(contentButton);
               add(timeLabel);
               add(sender);
               
           }
           case File -> {
               contentButton.addActionListener(e -> downloadFile(content));
               contentButton.setBackground(new java.awt.Color(217,217,217));
               setLayout(new FlowLayout(FlowLayout.LEFT));
               add(contentButton);
               add(timeLabel);
           }
       }
   }
    
    private String brContent(String content) {
    	int brLen = 50;
    	int maxlen = brLen + 20;
    	int len = content.length();
    	
    	for(int i = 0, j = 0; i < len; i++, j++) {
    		if((j >= brLen && content.charAt(i) == ' ') || j >= maxlen) {
    			content = content.substring(0, i) + "<br>" + content.substring(i, content.length());
    			i += 4;
    			len += 4;
    			j = 0;
    		}
    			
    	}
   	
    	return "<html>" + content;
    }
    
    public ChatBubbleGroup(BubbleType bubbleType, String content,String username) {
        setBackground(Color.WHITE);
        

        JLabel timeLabel = new JLabel(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()));
        timeLabel.setFont(new Font("Arial", Font.ITALIC, 10));

        JButton contentButton = new JButton(brContent(content));
        contentButton.setBorderPainted(false);
        contentButton.setFocusPainted(false);
        
        JLabel viewed = new JLabel("đã xem");
        viewed.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        
        JLabel sender = new JLabel(username + " sent");
        sender.setFont(new Font("Times New Roman", Font.PLAIN, 10));

        switch (bubbleType) {
            case Mine -> {
                contentButton.setBackground(new java.awt.Color(0,132,255));
                contentButton.setForeground(Color.WHITE);
                setLayout(new FlowLayout(FlowLayout.RIGHT));
                add(viewed);
                add(timeLabel);
                add(contentButton);
            }
            case Others -> {
                contentButton.setBackground(new java.awt.Color(217,217,217));
                setLayout(new FlowLayout(FlowLayout.LEFT));
                add(contentButton);
                add(timeLabel);
                add(sender);
                
            }
            case File -> {
                contentButton.addActionListener(e -> downloadFile(content));
                contentButton.setBackground(new java.awt.Color(217,217,217));
                setLayout(new FlowLayout(FlowLayout.LEFT));
                add(contentButton);
                add(timeLabel);
            }
        }
    }

    /**
     * Download a file when user click on File-ChatBubble
     * @param filename String
     */
    private void downloadFile(String filename) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fileInputStream = new FileInputStream(filename);
                byte[] data = fileInputStream.readAllBytes();
                fileInputStream.close();

                FileOutputStream fileOutputStream = new FileOutputStream(
                        fileChooser.getSelectedFile().getAbsolutePath());
                fileOutputStream.write(data);
                fileOutputStream.close();

                File file = new File(filename);
                if (file.delete()) JOptionPane.showMessageDialog(this, "Đã tải!");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this,
                        "Tải file thất bại!\nError: " + exception,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
