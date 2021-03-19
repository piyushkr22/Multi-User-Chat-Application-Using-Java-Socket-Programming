package Server_Side;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class chat_Server
{
    static ArrayList<String> user_names = new ArrayList<String>();
    static ArrayList<PrintWriter> print_writer = new ArrayList<PrintWriter>();

    public static void main(String args[]) throws Exception
    {
        System.out.println(" Waiting for Clients......");

        ServerSocket ss = new ServerSocket(0006);

        while (true)
        {
            Socket soc = ss.accept();

            System.out.println("Connection Established Successfully......");

            chat_handler obj = new chat_handler(soc);
            obj.start();
        }


    }
}

class chat_handler extends Thread
{
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    String name;
    PrintWriter pw;
    static FileWriter fw;
    static BufferedWriter br;

    public chat_handler(Socket soc)throws IOException
    {
        this.socket =soc;
        fw = new FileWriter("/Users/piyushkumar/Desktop/PROJECT/Multi-user Chat Application/Chat Message/Chat-Logs.txt",true);
        br = new BufferedWriter(fw);
        pw = new PrintWriter(br,true);

    }

    public void run()
    {
        try
        {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);

            int count = 0;

            while(true)
            {
                if(count>0)
                {
                    output.println("Name already exists");
                }
                else
                {
                    output.println("Enter name");
                }
                name = input.readLine();

                if(name==null)
                {
                    return;
                }

                if(!chat_Server.user_names.contains(name))
                {
                    chat_Server.user_names.add(name);
                    break;
                }
                count++;
            }

            output.println("Name accepted"+ name);
            chat_Server.print_writer.add(output);

            while(true)
            {
                String message = input.readLine();

                if(message == null)
                {
                    return;
                }

                for (PrintWriter obj: chat_Server.print_writer)
                {
                 obj.println(name + " :: " + message);
                }

                pw.println(name + " :: " + message);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
