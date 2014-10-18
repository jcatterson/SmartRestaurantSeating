/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author JCatterson
 */
public class HandWrittenGUI extends javax.swing.JFrame
{
    public HandWrittenGUI()
    {
        //this.addWindowListener(new java.awt.event.WindowAdapter() {});
        
        layout = new GroupLayout(getContentPane());
        serverList = layout.createParallelGroup();
        vServerList = layout.createSequentialGroup();
        tables = new Table[50];
        servers = new ArrayList();
        readTableInput(); 
        readTableNeighbors();
        readServerInput();
        wait = new WaitList(servers);
        setWaitListForAll();
        Thread customerService = new Thread(new CustomerServiceListener(servers, tables, wait));
        customerService.start();
        new Greeter();
        initComponent();
        //test();
    }
    
    private void setWaitListForAll()
    {
        for(int i = 0; i < tables.length; i++)
        {
            if(tables[i] == null)
                break;
            tables[i].setWait(wait);
        }
    }
    private void test()
    {
        
        try
        {
            GreeterCommunicator gc = new GreeterCommunicator();
            gc.connect();
            //gc.sendMessage(Integer.toString(guests) + "-" + Integer.toString(hc) + "-" + Integer.toString(wc) + "-" + preference);
            Thread.sleep(800);
            gc.sendMessage(4 + "-" + 0 + "-" + 0 + "-" + "N"); //11
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //13           
            Thread.sleep(60000);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //15
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //31
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //51
            Thread.sleep(800);
            gc.sendMessage(9 + "-" + 0 + "-" + 0 + "-" + "N"); //62
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //53
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //74
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //12
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //14
            Thread.sleep(800);
            gc.sendMessage(8 + "-" + 0 + "-" + 0 + "-" + "N"); //63
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //36
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //52
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //54
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //71
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //21
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //23
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //25
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //41
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //44
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //73
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); //22
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 24
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 42
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 43
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 72
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 35 & 34
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 32 & 33
            Thread.sleep(1200);
            gc.sendMessage(4 + "-" + 0 + "-" + 0 + "-" + "N"); // 61
            //Thread.sleep(800);
            //Thread.sleep(180000);
            // WE HAVE MADE IT TO A FULL RESTAURANT
            Thread.sleep(1200);
            
            
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N");
            
            
            Thread.sleep(1200);
            gc.sendMessage(4 + "-" + 0 + "-" + 0 + "-" + "N");
            
            
            /*Thread.sleep(5000);
            Thread.sleep(800);
            gc.sendMessage(6 + "-" + 0 + "-" + 0 + "-" + "N");
            /*
            
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 43
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 72
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 35 & 34
            Thread.sleep(800);
            gc.sendMessage(3 + "-" + 0 + "-" + 0 + "-" + "N"); // 32 & 33
            Thread.sleep(800);
            gc.sendMessage(4 + "-" + 0 + "-" + 0 + "-" + "N"); // 61*/
            
            //System.out.println("should be updating soon");
            //tables[8].setEstimatedWait(0, 2);
            
            
        }
        catch(Exception e)
        {
            System.out.println("what broke? " + e.toString());
        }
    }
    private void initComponent()
    {   
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(serverList);
        layout.setVerticalGroup(vServerList);
        pack();
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        HandWrittenGUI h = new HandWrittenGUI();
    }
    
    private void readTableNeighbors()
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\JCatterson\\Documents\\NetBeansProjects\\handWrittenGUI\\tableNeighbors.txt"));
            //int number = 0;
            while(in.ready())
            {
                String s = in.readLine();
                System.out.println(s);
                String[] str = s.split("-");
                String tableName = str[0];
                if(str[1].startsWith("{") && str[1].endsWith("}"))
                {
                    str[1] = str[1].substring(1, str[1].length()-1);
                    String[] stringNeighbors = str[1].split(",");
                    ArrayList neighbors = new ArrayList();//Table[stringNeighbors.length];
                    for(int i = 0; i < stringNeighbors.length; i++)
                    {
                        neighbors.add(this.findTable(stringNeighbors[i]));
                    }
                    Table t = this.findTable(tableName);
                    t.setNeighbors(neighbors);
                }
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }        
    }

    private void readTableInput()
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\JCatterson\\Documents\\NetBeansProjects\\handWrittenGUI\\tableAttr.txt"));
            int number = 0;
            while(in.ready())
            {
                String s = in.readLine();
                System.out.println(s);
                String[] str = s.split("-");
                boolean b = str[2].equals("B") ? true : false;
                tables[number] = new Table(str[0], Double.parseDouble(str[1]), b);
                if(str.length > 3)
                {
                    tables[number].setMinCap(Integer.parseInt(str[3]) );
                }
                number++;
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    private void readServerInput()
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\JCatterson\\Documents\\NetBeansProjects\\handWrittenGUI\\serverAttr.txt"));
            int st = 0;
            while(in.ready())
            {
                String s = in.readLine();
                System.out.println(s);
                
                String[] str = s.split("-");
                String serverName = str[0];
                Table[] tbls = new Table[0];
                if(str[1].startsWith("{") && str[1].endsWith("}"))
                {
                    str[1] = str[1].substring(1, str[1].length()-1);
                    String[] stringNeighbors = str[1].split(",");
                    tbls = new Table[stringNeighbors.length];
                    for(int i = 0; i < stringNeighbors.length; i++)
                    {
                        tbls[i] = this.findTable(stringNeighbors[i]);
                    }
                }
                
                Table[] t = tbls;
                servers.add(new Server(serverName, t));
                Server item = (Server)servers.get(st);
                serverList.addComponent(item.getGUI());
                vServerList.addComponent(item.getGUI());
                st++;
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
    private WaitList wait;
    private GroupLayout layout;
    private GroupLayout.Group vServerList;
    private GroupLayout.Group serverList;
    private  Table[] tables;
    private  ArrayList<Server> servers;
}
