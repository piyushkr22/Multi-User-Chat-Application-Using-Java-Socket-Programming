package Client_Side;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class chat_Client
{
    static JFrame chat_window = new JFrame(" CHAT APPLICATION ");
    static JTextArea chat_text_area = new JTextArea(20,30);
    static JTextField text_Field = new JTextField(40);
    static JLabel blank_space = new JLabel("");
        // just used for displaying a line space between chat_text_area and text_Field

    static JLabel name_Label = new JLabel("");

    static JButton send_message = new JButton("Send Message");

    static BufferedReader in ;
    static PrintWriter out;

    chat_Client()
    {
        chat_window.setLayout(new FlowLayout());

        chat_window.add(new JScrollPane(chat_text_area));
        chat_window.add(blank_space);
        chat_window.add(name_Label);
        chat_window.add(text_Field);
        chat_window.add(send_message);

        chat_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chat_window.setSize(500,450);
        chat_window.setVisible(true);
        text_Field.setEditable(false);
        chat_text_area.setEditable(false);

        send_message.addActionListener(new Listner());
        text_Field.addActionListener(new Listner());

    }

    void start_chat() throws Exception
    {
        String IP_Address = JOptionPane.showInputDialog
                        (chat_window,"Enter IP Address : ",
                        "Alert IP Address Required !!!",JOptionPane.PLAIN_MESSAGE);

        Socket soc = new Socket(IP_Address,0006);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(soc.getOutputStream(),true);

        while (true)
        {
            String str = in.readLine();

            if(str=="Enter name")
            {
                String name = JOptionPane.showInputDialog
                        (chat_window,"Enter Your Unique Name...",
                                "Unique Name Required !!!",JOptionPane.PLAIN_MESSAGE);
                out.println(name);
            }

            else if(str=="Name already exists")
            {
                String name = JOptionPane.showInputDialog
                        (chat_window,"Enter Another Unique Name...",
                                "Name Already Exists !!!",JOptionPane.PLAIN_MESSAGE);
                out.println(name);
            }

            else if(str.startsWith("Name accepted"))
            {
                text_Field.setEditable(true);
                name_Label.setText("You are logged in as >> "+ str.substring(13));
            }
            else
            {
                chat_text_area.append(str+"/n");
            }

        }
    }


    public static void main(String args[]) throws Exception
    {
        chat_Client chatClient = new chat_Client();

        chatClient.start_chat();

    }
}

class Listner implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        chat_Client.out.println(chat_Client.text_Field.getText());
        chat_Client.text_Field.setText(" ");

    }
}
