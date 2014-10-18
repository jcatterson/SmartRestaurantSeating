/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author JCatterson
 */
public class GreeterCommunicator
{
    public void connect()
    {
        try
        {
            requestSocket = new Socket("localhost", 2005);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();            

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void sendMessage(String s)
    {
        try
        {
            out.writeObject(s);
            out.flush();            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
}
