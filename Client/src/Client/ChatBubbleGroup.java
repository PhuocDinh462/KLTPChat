package Client;

import javax.swing.*;

import java.awt.*;
import java.io.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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
    public ChatBubbleGroup(BubbleType bubbleType, String content,String username) {
        setBackground(Color.WHITE);
        

        JLabel timeLabel = new JLabel(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()));
        timeLabel.setFont(new Font("Arial", Font.ITALIC, 10));

        JButton contentButton = new JButton(content);
        contentButton.setBorderPainted(false);
        contentButton.setFocusPainted(false);
        
        JLabel viewed = new JLabel("đã xem");
        viewed.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        
        JLabel sender = new JLabel(username);
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
