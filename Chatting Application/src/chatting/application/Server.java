package chatting.application;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import chatting.CeaserCipher.Encodings_Decodings.ceaserCipher;

public class Server implements ActionListener{
    
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static JScrollPane scrollPane;
    static ceaserCipher c;
    
    Server(){
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLACK);
        p1.setBounds(0,0,450,60);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        i1 = new ImageIcon(i2);
        JLabel back = new JLabel(i1);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        
        });
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i4 = i3.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i4);
        JLabel moreover = new JLabel(i3);
        moreover.setBounds(400, 20,10,25);
        p1.add(moreover);
        
        JLabel name = new JLabel("Server");
        name.setBounds(50, 20, 100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD, 20));
        p1.add(name);
        
        a1 = new JPanel();
        a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));
        a1.setBounds(5, 75, 420, 530);
        a1.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5,75,420,530);
        f.setUndecorated(false);
        f.add(scrollPane);
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getBlockIncrement(1));
        
        text = new JTextField();
        text.setBounds(3, 615, 300, 40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN, 16 ));
        text.setForeground(Color.BLACK);
        text.setBackground(Color.lightGray);
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(305, 615, 125, 40);
        send.setBackground(Color.lightGray);
        send.addActionListener(this);
        f.add(send);
        
        f.setSize(450, 700);
        f.setLocation(200, 50);
        f.getContentPane().setBackground(Color.darkGray);
        
        f.setVisible(true);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        
        String output = text.getText();
        //System.out.println("Server: (Encrypted) "+output);
        
       // JLabel sendermsg = new JLabel(output);
        JPanel p2 = formatLabel(output);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        
        try {
            output = c.ceaserEncoding(output, 3);
            System.out.println("Server: (Encrypted) "+output);
            dout.writeUTF(output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        text.setText("");
        
        f.repaint();
        f.invalidate();
        f.validate();
        
        
        
    }
    
    public static JPanel formatLabel(String output){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel sendermsg = new JLabel("<html><p style=\"width: 150px\">"+output+"</p></html>");
        sendermsg.setFont(new Font("Tamoha", Font.PLAIN,16));
        sendermsg.setBackground(Color.BLACK);
        sendermsg.setForeground(Color.WHITE);
        sendermsg.setOpaque(true);
        sendermsg.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(sendermsg);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
        
    }
    
    public static void main(String[] args){
        new Server();
        c = new ceaserCipher();
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                while(true){
                    String clientmsg = din.readUTF();
                    System.out.println("Client: (Encrypted) "+clientmsg);
                    clientmsg = c.ceaserDecoding(clientmsg, 3);
                    JPanel panel = formatLabel(clientmsg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
