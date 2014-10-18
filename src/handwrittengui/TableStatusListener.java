/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.net.*;
import java.io.*;
/**
 *
 * @author JCatterson
 */
public class TableStatusListener implements Runnable 
{   
    public TableStatusListener(Table[] t)
    {
        tables = t;
    }
    
    public void run()
    {
        try
        {
            ServerSocket s = new ServerSocket(2004, 10);
            System.out.println("waiting");
            Socket connection = s.accept();            
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            in = new ObjectInputStream(connection.getInputStream());
            while(true)
            {
                String message = (String)in.readObject();            
                String[] sa = message.split("-");
                Table tStatus = findTable(sa[1]);
                if( sa[0].equalsIgnoreCase("D"))
                {
                    tStatus.setCleanStatus(false);
                    //tStatus.refresh();
                }
                else if( sa[0].equalsIgnoreCase("C"))
                {
                    tStatus.setCleanStatus(true);
                    //tStatus.refresh();
                }
            }        
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public Table findTable(String tableName)
    {
        int s = tables.length;
        for(int i = 0; i < s; i++)
        {
            String n = tables[i].getName();
            if(n.equalsIgnoreCase(tableName))
                return tables[i];
        }
        return null;
    }
    
    private Table[] tables;
    private ObjectOutputStream out;
    private ObjectInputStream in;
}
