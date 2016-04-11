/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpserver;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author thelastlaugh
 */
public class FTPServer {public static void main(String args[]) throws Exception
    {
        ServerSocket soc=new ServerSocket(5217);
        System.out.println("FTP Server Started on Port Number 5217");
        while(true)
        {
            System.out.println("Waiting for Connection ...");
            transferfile t=new transferfile(soc.accept());
            
        }
    }
}

class transferfile extends Thread
{
    Socket ClientSoc;

    DataInputStream inpstr;
    DataOutputStream outstr;
    
    transferfile(Socket soc)
    {
        try
        {
            ClientSoc=soc;                        
            inpstr=new DataInputStream(ClientSoc.getInputStream());
            outstr=new DataOutputStream(ClientSoc.getOutputStream());
            System.out.println("FTP Client Connected ...");
			start();
        }
        catch(Exception e)
        {
        }        
		
    }
    void SendFile() throws Exception
    {        
        String filename=inpstr.readUTF();
        File f=new File(filename);
            outstr.writeUTF("READY");
            FileInputStream fin=new FileInputStream(f);
            int ch;
            do
            {
                ch=fin.read();
                outstr.writeUTF(String.valueOf(ch));
            }
            while(ch!=-1);    
            fin.close();    
            //outstr.writeUTF("File Receive Successfully");                            
        }
    
    
    void ReceiveFile() throws Exception
    {   System.out.println("Server Recieve Start");
        String filename=inpstr.readUTF();
        System.out.println(filename);
        File f=new File(filename);
        String option;
        System.out.println("Server option");
            option="Y";
            
            if(option.compareTo("Y")==0)
            {   
                FileOutputStream fout=new FileOutputStream(f);
                int ch;
                String temp;
                do
                {
                    temp=inpstr.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                }while(ch!=-1);
				fout.close();
                System.out.println("Server Recieve Start");
                outstr.writeUTF("File Send Successfully");
            }
            
            
    }


    public void run()
    {
        while(true)
        {
            try
            {
            System.out.println("Waiting for Options ...");
            String Command=inpstr.readUTF();
            if(Command.compareTo("GET")==0)
            {
                System.out.println("\tGET Received ...");
                SendFile();
                continue;
            }
            else if(Command.compareTo("SEND")==0)
            {
                System.out.println("\tSEND Received ...");                
                ReceiveFile();
                continue;
            }
            else if(Command.compareTo("LIST")==0)
            { System.out.println("Listing Files");
              SendList();  
            }
            else if(Command.compareTo("DISCONNECT")==0)
            {
                System.out.println("\tExit ");
                System.exit(1);
            }
            }
            catch(Exception e)
            {
            }
        }
    }
void SendList() throws Exception
    {   //outstr.writeUTF("Helelo");
        Runtime rt = Runtime.getRuntime();
            String commands = "ls";
            Process proc = rt.exec(commands);
            BufferedReader stdInput = new BufferedReader(new 
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
            InputStreamReader(proc.getErrorStream()));

// read the output from the command
//System.out.println("Here is the standard output of the command:\n");
String s = null;
String a="";
while ((s = stdInput.readLine()) != null) {
    a=a+"\n"+s;
    //System.out.println(s);
}
outstr.writeUTF(a);
// read any errors from the attempted command
//System.out.println("Here is the standard error of the command (if any):\n");
while ((s = stdError.readLine()) != null) {
    outstr.writeUTF(s);
}      
			                 
    }
}

