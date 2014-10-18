/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 *
 * @author JCatterson
 */
public class Server
{
    public Server(String name, Table[] tables)
    {
        this.name = name;
        this.tables = tables;
        lastSat = Calendar.getInstance();
        lastSat.add(Calendar.DAY_OF_YEAR, -1);
        claimTables();
        gui = new ServerGUI(this);
    }
    public Server(Server t)
    {
        
    }
    
    private void claimTables()
    {
        for(int i = 0; i < tables.length; i++)
        {
            tables[i].setTableOwner(this);
            tables[i].setCareTaker(this);
        }
    }
    
    public Table pushTables(Table[] tbls)
    {
        Table pushedTogether = Table.mergeTables(tbls);
        ArrayList serversTableTaken = new ArrayList();
        Server[] list;
        for(int i = 0; i < tbls.length; i++)
        {
            Server s = tbls[i].getCareTaker();
            if(s != null)
            {
                s.looseTable(tbls[i]);
                serversTableTaken.add(s);
            }
        }
        receiveTable(pushedTogether);
        list = Utility.makeServerArray(serversTableTaken);
        for(int i = 0; i < list.length; i++)
        {
            list[i].getGUI().refresh();
        }
        return pushedTogether;
    }
    
    public void returnTable(Table t)
    {
        looseTable(t);
        gui.refresh();
    }
    
    public void getTableBack(Table t)
    {
        receiveTable(t);
        gui.refresh();
    }
    
    private void receiveTable(Table t)
    {
        Table[] oldList = tables.clone();
        tables = new Table[oldList.length + 1];
        for(int i = 0; i < oldList.length; i++)
        {
            tables[i] = oldList[i];
        }
        tables[oldList.length] = t;
        //gui.paint();
    }
    
    private boolean looseTable(Table t)
    {
        boolean lost = false;
        Table[] oldList = tables.clone();
        ArrayList tbls = new ArrayList();
        //tables = new Table[oldList.length - 1];
        int number = 0;
        for(int i = 0; i < oldList.length; i++)
        {
            if(t == oldList[i])
            {                
                lost = true;
            }
            else
            {
                tbls.add(oldList[i]);
            }
        }
        tables = Utility.makeTableArray(tbls);
        return lost;
    }
    
    public void splitTable(Table tbl)
    {
        Table[] tbls = Utility.makeTableArray(tbl.getTablesCombined());
        Table[] oldList = tables.clone();
        tables = new Table[oldList.length + tbls.length-1];
        for(int i = 0; i < oldList.length; i++)
        {
            if(!Utility.tableInList(tbl, tbls))
                tables[i] = oldList[i];
        }
        for(int i = 0; i < tbls.length; i++)
        {
            tables[i + oldList.length-1] = tbls[i];
        }
        gui.paint();
    }
    
    public int getNumTablesOwned()
    {
        return tables.length;
    }
    
    public Table[] getOwnedTables()
    {
        return tables;
    }
    
    public void updateEstimatedTableService()
    {
        for(int i = 0; i < this.tables.length; i++)
        {
            if(!tables[i].isReserved())
            {
                tables[i].addToEstimatedWait(0,5);
            }
        }
    }
    
    public void seat(Table t)
    {
        Table[] tbls = Utility.makeTableArray(t.getTablesCombined());
        for(int i = 0; i < tbls.length; i++)
        {
            tbls[i].setEstimatedWait(1, 5);
            
        }
        t.setCareTaker(this);
        t.setCleanStatus(false);
        //t.setCleanStatus(false);
        //t.setCleanStatus(false);
        totalServed++;
        setLastSetTime();
        gui.refresh();        
    }

    public Table seat(Customer c)
    {
        
        if ( ! isBusy() )
        {
            ArrayList cleanTbls = getCleanTables();
            if(cleanTbls.isEmpty())
                return null;
            Table[] tbls = Utility.makeTableListFromIndicies(cleanTbls, this.tables);
            
            // I WANT TO PRIORITIZE SEATING BY TABLE NEIGHBORS
            // THE LEAST AMOUNT OF NEIGHBORS
            
            // THEN ORDER THAT LIST BY BEST FIT, weighted by table neighbors
            ArrayList orderedByNeigh = Utility.orderSameSizedNeighbors(tbls);
            ArrayList allOrdered = new ArrayList();
            for(int i = 0; i < orderedByNeigh.size(); i++)
            {
                ArrayList temp = (ArrayList)orderedByNeigh.get(i);
                Table[] ordered = Utility.makeTableArray(temp);
                ArrayList orderedCleanTbls = Utility.orderByBestFit(ordered);
                for(int j = 0; j < orderedCleanTbls.size(); j++)
                {
                    Table t = ordered[ (int)orderedCleanTbls.get(j) ];
                    allOrdered.add(t);
                }
            }
            
            // to do this I will use an ArrayList of strings 
            // the string will contain all the equal Table names seperated by commas
            // split the commas, make the string names into the tables
            // order this Table[] by best fit into an ArrayList           
          
            
            int numClean = allOrdered.size();
            // repeat until all the least table neighbors have been filled
            // adding all to the big list
            
            for(int i = 0; i < numClean; i++)
            {
                Table t = (Table)allOrdered.get(i);
                //int tempIndex = (int)allOrdered.get(i);
                //int index = (int)cleanTbls.get(tempIndex);
                if( numClean >= 1 && t.tableAccomodates(c) )
                {
                    totalServed++;
                    setLastSetTime();
                    t.setEstimatedWait(1, 5);
                    t.setCareTaker(this);
                    t.setCleanStatus(false);
                    gui.refresh();
                    return tables[i];
                }
            }
        }
        return null;
    }
    
    public ArrayList getCleanTablesAsTables()
    {
        ArrayList tbl = new ArrayList();
        for(int i = 0; i < tables.length; i++)
        {
            if(tables[i].isClean())
                tbl.add(tables[i]);
        }
        return tbl;        
    }
    
    /*
     * Returns the INDEX of each of the tables in an array list
     */
    public ArrayList getCleanTables()
    {
        ArrayList tblIndex = new ArrayList();
        for(int i = 0; i < tables.length; i++)
        {
            if(tables[i].isClean())
                tblIndex.add(i);
        }
        return tblIndex;
    }

    public boolean isBusy()
    {
        long millis = secondsFromLastSat();
        /*int millis;
        int milliHours = d.get(Calendar.HOUR) * 60 * 60 * 1000;
        int milliMin = d.get(Calendar.MINUTE) * 60 * 1000;
        int milliSec = d.get(Calendar.SECOND) * 1000;
        millis = milliHours + milliMin + milliSec;*/
        return millis < minSecondsNextSeat;
    }
    
    public long secondsFromLastSat()
    {
        Calendar t = Calendar.getInstance();//(Calendar)lastSat.clone();
        long millis;
        long milliHour = ( t.get(Calendar.HOUR) - lastSat.get(Calendar.HOUR) ) * 3600000;
        long milliMin = ( t.get(Calendar.MINUTE) - lastSat.get(Calendar.MINUTE) ) * 60000;
        long milliSec = ( t.get(Calendar.SECOND) - lastSat.get(Calendar.SECOND) ) * 1000;
        millis =  milliHour + milliMin + milliSec;
        return millis;
        /*t.add(t.get(Calendar.HOUR), -lastSat.get(Calendar.HOUR));
        t.add(t.get(Calendar.MINUTE), -lastSat.get(Calendar.MINUTE));
        t.add(t.get(Calendar.SECOND), -lastSat.get(Calendar.SECOND));*/
        //return t;
    }
    
    private void setLastSetTime()
    {
        lastSat = Calendar.getInstance();
    }
    
    public String getServerName()
    {
        return name;
    }
    
    public int getTotalServed()
    {
        return totalServed;
    }
    
    public String printTime(Calendar t)
    {
        
        String time = "";
        time += Integer.toString(t.get(Calendar.HOUR));
        time += ":";
        time += Integer.toString(t.get(Calendar.MINUTE));
        return time;
    }
    
    public int getCurrentlyServing()
    {
        return currentlyServing;
    }
    
    public ServerGUI getGUI()
    {
        return gui;
    }

    private Calendar availableIn;
    private ServerGUI gui;
    private String name;
    private Calendar lastSat;
    private long minSecondsNextSeat = 1000;
    private Table[] tables;
    private int currentlyServing = 0;
    private int totalServed = 0;
}
