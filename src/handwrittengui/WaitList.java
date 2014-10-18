/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.Hashtable;
import java.util.Calendar;
/**
 *
 * @author JCatterson
 */
public class WaitList extends Thread
{
    public WaitList(ArrayList s)
    {
        System.out.println("We are on a wait");
        image = new WaitListGUI();
        waitList = new ArrayList();
        servers = s;
        this.refreshTimer = new Timer();
        scheduleTimer();
    }
    
    public void display()
    {
        image.paint();
    }
    
    /*
     * This will be called once a table has been cleaned
     * If a table is clean, and a customer could be accomodated
     * we will be able to take the customer off the list
     */
    public void checkSeating()
    {
        Customer[] customers = Utility.makeCustomerArray(waitList);
        ArrayList s = Utility.serverServiceOrder(this.servers);
        Server[] server = Utility.makeServerArray(this.servers);
        Table tableSat = null;
        for(int i = 0; i < customers.length; i++)
        {
            for(int j = 0; j < servers.size(); j++)
            {
                int nextUp = (int)s.get(j);
                tableSat = server[nextUp].seat(customers[i]);
                if(tableSat != null)
                {
                    break;
                }                
            }
            if(tableSat != null)
            {
                System.out.println("Customer taken off the wait list!");
                removeCustomer(customers[i]);
                break;
            }
            else
            { // can we push some tables together to seat someone?
                tryTableConnection(customers[i]); 
            }
        }
    }
    
    public String findTimeDifference(Calendar earlier, Calendar later)
    {
        String time;
        Calendar temp = (Calendar)later.clone();
        temp.add(Calendar.HOUR, -earlier.get(Calendar.HOUR));
        temp.add(Calendar.MINUTE, -earlier.get(Calendar.MINUTE));
        int hr = temp.get(Calendar.HOUR);
        int min = temp.get(Calendar.MINUTE);
        String sMin = min >= 10 ? Integer.toString(min) : "0" + Integer.toString(min); 
        time = Integer.toString(hr) + ":" + sMin;
        return time;
    }
    
    public void run()
    {
        try
        {
            if(isRunning())
            {
                Table[] allTbls = getAllTables();
                allTbls = Utility.orderTablesByTime(allTbls);
                ArrayList orderedTbls = Utility.makeArrayList(allTbls);
                HashMap tables = new HashMap();
                HashMap customerTimes = new HashMap();
                for(int i = 0; i < orderedTbls.size(); i++)
                {
                    Table t = (Table)orderedTbls.get(i);
                    tables.put(t, customerTimes);
                    for(int j = 0; j < waitList.size(); j++)
                    {
                        Customer c = (Customer)waitList.get(j);
                        if(t.couldAccomodate(c))
                        {
                            Calendar timeOpen = t.getEstimatedCalendarTime();
                            Calendar timeA = c.getTimeArrived();
                            String time;
                            if(timeOpen != null)
                            {
                                time = findTimeDifference(timeA, timeOpen);
                                customerTimes.put(c, time);
                            }
                            else
                            {
                                time = "0:00";
                                customerTimes.put(c, time);
                            }
                        }
                    }
                    customerTimes = new HashMap();            
                }
                while( !orderedTbls.isEmpty() )//for(int i = 0; i < orderedTbls.size(); i++)
                {
                    Table tblToFindCust = (Table)orderedTbls.get(0);
                    HashMap custs = (HashMap)tables.get(tblToFindCust);
                    for(int j = 0; j < waitList.size(); j++)
                    {
                        Customer c = (Customer)waitList.get(j);
                        if(custs.containsKey(c) && c.getReservedTable() == null)
                        {
                            c.setReservedTable(tblToFindCust);
                            tblToFindCust.getTableOwner().updateEstimatedTableService();
                            c.getGraphic().reEvaluate();
                            orderedTbls.remove(tblToFindCust);
                            Table[] temp = Utility.makeTableArray(orderedTbls);
                            temp = Utility.orderTablesByTime(temp);
                            orderedTbls = Utility.makeArrayList(temp);
                            break;
                        }
                    }
                    orderedTbls.remove(tblToFindCust);
                    
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        // now loop through the customers in order
        //    loop thorough the tables in order
        //    if the table contains the customer then
        //       compare to other customers rating
        //       if a customer contains a number within the acceptable range then
        //           reserve the table for the customer within range
        //           reset and re-run
        //       else
        //           reserve the table for the first customer
        
        
        
        
        
        
        // get all the tables
        // Make an array of tables for each customer that could work along with an associated time it would take to seat the table
        // do this for each customer
        
        // once we have this, we will begin to reserve tables
        // if the table is not a 'perfect fit' for the customer, then 
        //     compare with other customers that are 'perfect fits'
        //     if the customer is within a specific rating range, then
        //         give the customer that table 
        //         re-run the solution
        
       
        
    }
    public boolean tryTableConnection(Customer c)
    {
        Table[] tbls = findAllCleanTables();
        ArrayList possibilities = findAllPossibilities(tbls, c);
        if(possibilities != null && possibilities.size() > 0)
        {    
            Server[] elgibleServer = findElgibleServerOrder(possibilities);
            ArrayList lstConflict = findElgibleTables(possibilities);
            seatBestOption(c, elgibleServer, lstConflict);
            return true;
        }
        return false;
    }
    
    public Table[] getAllTables()
    {
        ArrayList t = new ArrayList();
        for(int i = 0; i < servers.size(); i++)
        {
            Server s = (Server)servers.get(i);
            for(int j = 0; j < s.getOwnedTables().length; j++)
            {
                t.addAll(s.getOwnedTables()[j].getTablesCombined());
                //t.add(s.getOwnedTables()[j]);
            }
        }
        return Utility.makeTableArray(t);
    }
    
    public ArrayList findElgibleTables(ArrayList possibilities)
    {
        ArrayList leastConflictingOrder = Utility.orderByLeastConflicting(possibilities);
        Table[] leastConflictTOrder = Utility.makeTableListFromIndicies(leastConflictingOrder, possibilities);
        ArrayList lstConflict = Utility.findAbsoluteLeastConflict(leastConflictTOrder);    
        return lstConflict;
    }
    
    public Server[] findElgibleServerOrder(ArrayList possibilities)
    {
        ArrayList serverOrder =  Utility.serverServiceOrder(servers);
        Server[] svrOrder = Utility.makeServerListFromIndicies(serverOrder, servers);
        Server[] potentialServers = getAllSevers(possibilities);
        Server[] elgibleServer = orderEldgibleServers(svrOrder,potentialServers);
        return elgibleServer;
    }
    
    /*
     * This is expecting to have a list of servers that will have a common table in lstConflict
     * If lstConflict and elgibleServer do not have a table in common.  This will throw a null pointer exception
     * This will seat the first table that is the intersection between elgibleServer and lstConflict
     */
    public void seatBestOption(Customer c, Server[] elgibleServer, ArrayList lstConflict)
    {
        Server toSeatServer = null;
        Table toSeatTable = null;
        for(int i = 0; i < elgibleServer.length; i++)
        {
            for(int j = 0; j < lstConflict.size(); j++) // this loop is the list of Connected tables
            {
                toSeatTable = (Table)lstConflict.get(j);
                for(int z = 0; z < toSeatTable.getTablesCombined().size(); z++)
                {
                    ArrayList temp = toSeatTable.getAllConnectingTableOwners();
                    if(temp.contains(elgibleServer[i]))
                    {
                        toSeatServer = elgibleServer[i];
                        break;
                    }
                }
                if(toSeatServer != null)
                    break;
            }
            if(toSeatServer != null)
                break;
        }
        ArrayList n = toSeatTable.getTablesCombined();
        Table t = toSeatServer.pushTables(Utility.makeTableArray(n));
        toSeatServer.seat(t);
        removeCustomer(c);
    }
    
    public ArrayList orderByBestTable(ArrayList fitOrder, ArrayList leastConflictOrder)
    {
        //int[] order = new int[fitOrder.size()];
        double[] avg = new double[fitOrder.size()];
        HashMap mp = new HashMap();
        for(int i = 0; i < fitOrder.size(); i++)
        {
            mp.put((int)fitOrder.get(i), (double)i);
        }
        for(int i = 0; i < leastConflictOrder.size(); i++)
        {
            int index = (int)leastConflictOrder.get(i);
            double t = (double)mp.get(index);
            t += (double)i;
            t /= 2;
            mp.remove(index);
            mp.put(index, t);
        }
        
        return Utility.orderHash(mp);
    }
    
    public Server[] orderEldgibleServers(Server[] serverServiceOrder, Server[] potentialServers)
    {
        Server[] s = new Server[potentialServers.length];
        int num = 0;
        for(int i = 0; i < serverServiceOrder.length; i++)
        {
            if(potentialServers != null)
            {
                if( Utility.serverInList(serverServiceOrder[i], potentialServers) )
                {
                    s[num++] = serverServiceOrder[i];
                }
            }
        }
        return s;
    }
    
    public Server[] getAllSevers(ArrayList possible)
    {
        ArrayList allSrvs = new ArrayList();
        Table[] allTbls = Utility.makeTableArray(possible);
        for(int i = 0; i < allTbls.length; i++)
        {
            ArrayList sTbls = allTbls[i].getAllConnectingTableOwners();
            if(!allSrvs.containsAll(servers))
            {
                Server[] s = Utility.makeServerArray(sTbls);
                for(int j = 0; j < s.length; j++)
                {
                    if(!allSrvs.contains(s[j]) && s[j] != null && !s[j].isBusy())
                    {
                        allSrvs.add(s[j]);
                    }
                }
            }
        }
        return Utility.makeServerArray(allSrvs);
    }
    
    /* 
     * Looks at all the empty tables and finds all the tables that once
     * Pushed together, could accomodate the customer
     * 
     */
    public ArrayList findAllPossibilities(Table[] t, Customer c)
    {
        ArrayList possible = new ArrayList();
        for(int i = 0; i < t.length; i++)
        {
            // get a list of all clean neighbors
            ArrayList cleanNeigh = t[i].getAllCleanNeighbors(t[i]);
            if(cleanNeigh != null)
            {
                Table[] cleanNeighbors = Utility.makeTableArray(cleanNeigh);
                // loop through the tables and connect them
                if(cleanNeighbors != null)
                {
                    for(int j = 0; j < cleanNeighbors.length; j++)
                    {
                        Table[] toConnect = { t[i], cleanNeighbors[j] };
                        Table temp = Table.mergeTables(toConnect);
                        // check if the temp table is big enough to accomodate
                        if(temp.tableAccomodates(c))
                            possible.add(temp);
                        // If it isn't, try connecting more tables
                        else
                        {
                            Table[] bigTable = {temp};
                            ArrayList biggerT = findAllPossibilities(bigTable,c);
                            if(biggerT != null) // had possible
                                possible.addAll(biggerT);
                        }
                    }  
                }
            }
        }
        return possible;
    }
    
    public Table[] findAllCleanTables()
    {
        ArrayList t = new ArrayList();
        Server[] svs = Utility.makeServerArray(servers);
        for(int i = 0; i < servers.size(); i++)
        {
            t.addAll(svs[i].getCleanTablesAsTables());
        }
        return Utility.makeTableArray(t);
    }
    
    public void addCustomer(Customer c)
    {
        runningT = this;
        c.addWaitTicket(waitList.size());
        waitList.add(c);
        image.addCustomer(c);
    }
    public void removeCustomer(Customer c)
    {
        waitList.remove(c);
        Customer[] custs = Utility.makeCustomerArray(waitList); 
        image.paintNewCustomers(custs);
        if(waitList.isEmpty())
        {
            image.dispose();
            stopT();
        }
            
    }

    
    public void scheduleTimer()
    {
        refreshTimer.schedule(new java.util.TimerTask()
        {
            public void run()
            {
                refresh();
                System.out.println("I ticked!");
            }
        }
                
                                                            , 60000);
    }
    private void refresh()
    {
        System.out.println("I got here!");
        this.refreshTimer.cancel();
        //if(runningT != null)
        //{
            Customer[] c = Utility.makeCustomerArray(waitList);
            for(int i = 0; i < c.length; i++)
            {
                c[i].getGraphic().reEvaluate();
            }
            image.paintNewCustomers(c);
        //}
        refreshTimer = new Timer();
        scheduleTimer();
    }
    
    public boolean isRunning()
    {
        return runningT != null;
    }
    private void stopT()
    {
        System.out.println("Stopped running!");
        runningT = null;
    }
    private Timer refreshTimer;
    private volatile Thread runningT;
    private ArrayList servers;
    private WaitListGUI image;
    private ArrayList waitList;
    private Table[] tbls;
    
}
