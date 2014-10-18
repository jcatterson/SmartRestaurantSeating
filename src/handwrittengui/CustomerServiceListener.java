/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author JCatterson
 */
public class CustomerServiceListener implements Runnable
{
    public CustomerServiceListener(ArrayList<Server> s, Table[] t, WaitList w)
    {
        servers = s;  
        wait = w;
    }
    
    @Override
    public void run()
    {
        try
        {
            ServerSocket s = new ServerSocket(2005, 10);
            System.out.println("waiting for CustomerGUI");
            Socket connection = s.accept();            
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            in = new ObjectInputStream(connection.getInputStream());
            while(true)
            {
                String message = (String)in.readObject();            
                String[] sa = message.split("-");
                int numGuests = Integer.parseInt(sa[0]);
                int numWheel = Integer.parseInt(sa[1]);
                int numHC = Integer.parseInt(sa[2]);
                String preference = sa[3];
                Customer c = new Customer(numGuests, numWheel, numHC, preference.charAt(0));
                ArrayList order = Utility.serverServiceOrder(this.servers);
                Table tbl = null;
                for(int i = 0; i < order.size(); i++)
                {
                    if( !wait.isRunning() )
                    {
                        int nextUp = (int)order.get(i);
                        tbl = ((Server)servers.get(nextUp)).seat(c);
                        Server svr = ((Server)servers.get(nextUp));
                        if( tbl != null)
                        {
                            System.out.println("Customer got sat at table " + tbl.getName());
                            System.out.println("The server is " + svr.getServerName());
                            break;
                        }
                    }
                    else
                        break;
                }
                
                if(tbl == null)
                {
                    boolean b = wait.tryTableConnection(c);
                    if(!b)
                    {
                        System.out.println("the customer is going on the wait");
                        
                        
                        wait.addCustomer(c);
                        wait.run();
                        wait.display();
                    }
                    /*
                    if(!wait.isRunning())
                    {
                        //wait = new WaitList(servers);
                        //wait.start();
                        wait.display();
                        
                    }
                    // this should not be here just temporary
                    Table t = ((Server)servers.get(0)).getOwnedTables()[0];
                    c.setReservedTable(t);
                    c.getGraphic().reEvaluate();
                    wait.addCustomer(c);*/
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    private Table[] tbls;
    private WaitList wait;
    ArrayList servers;
    private ObjectOutputStream out;
    private ObjectInputStream in;    
}
