/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.UUID;

/**
 *
 * @author thelastlaugh
 */
/*class FTPClient {
  public FTPClient() throws Exception//send user object as argument
    {
        Socket soc=new Socket("localhost",5217);
        transferfileClient t=new transferfileClient();
        
        
    }
  
}*/
class transferfileClient
{
    Socket ClientSoc;

    DataInputStream inpstr;
    DataOutputStream outstr;
    BufferedReader br;
    transferfileClient()//send user object here
    {
        try
        {
            ClientSoc=new Socket("localhost",5217);
            inpstr=new DataInputStream(ClientSoc.getInputStream());
            outstr=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        catch(Exception ex)
        {
        }        
    }
    public String SendFile(String path,String name) throws Exception
    {        
        outstr.writeUTF("SEND");
        String filename=name;
        //System.out.print("Enter File Name Existing In Same Directory:");
        //filename=br.readLine();
            
        File f=new File(path);   
        outstr.writeUTF(filename);
        //String msgFromServer=inpstr.readUTF();
        //if(msgFromServer.compareTo("File Already Exists")==0)
        
            String Option;
            //System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
            Option="Y";            
            if(Option=="Y")    
            {
                //outstr.writeUTF("Y");
            }
          
        
        System.out.println("Sending File ...");
        FileInputStream fin=new FileInputStream(f);
			BufferedReader brfile = new BufferedReader(new InputStreamReader(fin));
			String line;
                        int ch;
			do
            {
                ch=fin.read();
                outstr.writeUTF(String.valueOf(ch));
            }
            while(ch!=-1);
        fin.close();
        System.out.println(inpstr.readUTF());
        return name+" File Sent to Server";
    }
    
    public String ReceiveFile(String a) throws Exception
            {outstr.writeUTF("GET");
        String fileName,result;
        result="Not Set";
        //System.out.print("Enter File Name :");
        fileName=a;
        outstr.writeUTF(fileName);
        String msgFromServer=inpstr.readUTF();
         if(msgFromServer.compareTo("READY")==0)
        {
            //System.out.println("Receiving File ...");
            String uuid=UUID.randomUUID().toString();
            uuid=uuid.substring(0, 6);
            result=uuid+fileName+" is stored in Data folder";
            fileName="Data"+File.separator+uuid+fileName;
            File f=new File(fileName);
            if(f.exists())
            {
                String Option;
                //System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                Option="Y";            
                if(Option=="N")    
                {
                    outstr.flush();
                    return result;    
                }                
            }
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
            //System.out.println(inpstr.readUTF());
                
        }
        
       return result; 
    }
    
       

    public void showmenu() throws Exception
    {
        while(true)
        {    
            System.out.println("*******************MENU********************");
            System.out.println("1. Send File");
            System.out.println("2. Receive File");
            System.out.println("3. List Files");
            System.out.println("4. Exit");
            System.out.print("\nSelect Any Option :");
            int choice;
            choice=Integer.parseInt(br.readLine());
            if(choice==1)
            {
                outstr.writeUTF("SEND");
                SendFile("","");
            }
            else if(choice==2)
            {
                outstr.writeUTF("GET");
                ReceiveFile("hi");
            }
            else if(choice==3)
            {
               outstr.writeUTF("LIST");
               ReceiveList();
            }
            else
            {
                outstr.writeUTF("DISCONNECT");
                System.exit(1);
            }
        }
    }
    public String ReceiveList() throws Exception
        {  //String lista;
            //while((lista=inpstr.readUTF())!=null)
                outstr.writeUTF("LIST");
                  String res=inpstr.readUTF();
                  return res;
        }
}


