/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author JCatterson
 */
public class Table
{
    enum TableType
    {
        TABLE, BOOTH;
    }
    public Table(String tableName, double maxSet, boolean isBooth)
    {
        this.tableName = tableName;
        this.maxCap = maxSet;
        minCap = 0;
        this.tType = isBooth ? TableType.BOOTH : TableType.TABLE;
        this.isClean = true;
        reservedTo = null;
        tablesCombined = new ArrayList();
        lkOpen = new ReentrantLock();
        neighbors = new ArrayList();
        this.minCap = 1;
        this.estimatedWait = Calendar.getInstance();
        gui = new TableGUI(this);
    }
    public Table(String tableName, double maxSet, boolean isBooth, int highChairAccess, int wheelChairAccess)
    {
        this.tableName = tableName;
        this.maxCap = maxSet;
        minCap = 0;
        this.tType = isBooth ? TableType.BOOTH : TableType.TABLE;
        this.isClean = true;
        this.numHighChairsPossible = highChairAccess;
        this.numWheelChairsPossible= wheelChairAccess;
        reservedTo = null;
        tablesCombined = new ArrayList();
        lkOpen = new ReentrantLock();
        neighbors = new ArrayList();
        this.minCap = 1;
        this.estimatedWait = Calendar.getInstance();
        gui = new TableGUI(this);
    }
    
    public void setWait(WaitList w)
    {
        wait = w;
    }

    public void setMinCap(int min)
    {
        this.minCap = min;
    }
    public void setNeighbors(ArrayList t)
    {
        neighbors = t;
    }
    public boolean isNeighbor(Table t)
    {
        return Utility.tableInList(t, Utility.makeTableArray(neighbors));
    }
    
    public void setReserveTable(Customer c)
    {
        reservedTo = c;
    }
    public Server getCareTaker()
    {
        return careTaker;
    }
    public void setCareTaker(Server c)
    {
        if(tablesCombined.size() > 0)
        {
            Table[] t = Utility.makeTableArray(tablesCombined);
            for(int i = 0; i < tablesCombined.size(); i++)
            {
                t[i].setCareTaker(c);
            }
        }
        careTaker = c;
    }
    
    public Server getTableOwner()
    {
        return tableOwner;
    }
    public void setTableOwner(Server s)
    {
        tableOwner = s;
    }
    
    public void mergeTable(Table t)
    {
        Table[] tbls = {t, this};
        Table combined = this.getTableOwner().pushTables(tbls);
        combined.careTaker = this.careTaker;
    }
    
    public static Table mergeTables(Table[] tbls)
    {
        Table pushedTogether;
        String name = "";
        double maxSize = 0;
        boolean isBooth = true;
        int highChair = 0;
        int wheelChair = 0;
        for(int i = 0; i < tbls.length; i++)
        {
            name += ( tbls[i].getName() + "-");
            isBooth &= tbls[i].isBooth();
            highChair += tbls[i].getNumHighChairsPossible();
            wheelChair += tbls[i].getNumWheelChairsPossible();
            maxSize += tbls[i].getMaxCapacity();
        }
        name = name.substring(0, name.length() - 1);
        pushedTogether = new Table(name,maxSize,isBooth,highChair,wheelChair);//, wait);
        pushedTogether.appendTables( tbls );
        return pushedTogether;
    }
    public ArrayList getAllNeighbors()
    {
        return this.neighbors;
        
    }
    public ArrayList getAllConnectingTableOwners()
    {
        ArrayList owners = new ArrayList();
        Table[] allT = Utility.makeTableArray(this.getTablesCombined());
        if( this.getTablesCombined().size() > 1 )
        {
            for(int i = 0; i < allT.length; i++)
            {
                ArrayList h = allT[i].getAllConnectingTableOwners();
                if(!h.isEmpty())
                { // only add the ones not already in the list
                    for(int b = 0; b < h.size(); b++)
                    {
                        Server differentOwner = (Server)h.get(b);
                        if(!Utility.serverInAList(differentOwner, owners))
                            owners.add(differentOwner);
                    }
                }
            }
        }
        if(this.getTableOwner() != null)
            owners.add(this.getTableOwner());
        return owners;
    }
    
    public ArrayList getTablesCombined()
    {
        ArrayList allTbls = new ArrayList();
        if(!tablesCombined.isEmpty())
        {
            for(int i = 0; i < tablesCombined.size(); i++)
            {
                ArrayList temp = ((Table)tablesCombined.get(i)).getTablesCombined();
                if(temp != null)
                    allTbls.addAll(temp);
            }
            return allTbls;
        }
        else 
        {
            allTbls.add(this);
            return allTbls;
        }
    }
    /*
     * Should only be called by another table!
     */
    public void appendTables(Table[] t)
    {
        for(int i = 0; i < t.length; i++)
        {
            tablesCombined.add(t[i]);
        }        
    }
    
    public ArrayList getAllCleanNeighbors(Table alreadyConnected)
    {
        ArrayList t = new ArrayList();
        
        if(!tablesCombined.isEmpty())
        {
            Table[] tblsCombined = Utility.makeTableArray(this.tablesCombined);
            for(int i = 0; i < tablesCombined.size(); i++)
            {
                ArrayList tmp = tblsCombined[i].getAllCleanNeighbors(alreadyConnected);
                if(tmp != null)
                    t.addAll(tmp);
            }
            return t;
        }
        else if(neighbors == null)
            return null;
        else
        {
            Table[] ne = Utility.makeTableArray(neighbors);
            for(int i = 0; i < this.neighbors.size();  i++ )
            {
                if(ne[i].isClean() && !alreadyConnected.alreadyConnected(ne[i]))
                    t.add(ne[i]);
            }
            return t;
        }
    }
    
    public boolean alreadyConnected(Table t)
    {
        return Utility.tableInList(t, Utility.makeTableArray(this.getTablesCombined()));
    }
    
    public void setReserved(Customer c)
    {
         reservedTo = c;
    }
    
    public boolean isReserved()
    {
        return reservedTo != null;
    }
    
    public boolean couldAccomodate(Customer c)
    {
            double max = getMaxCapacity();
            int min = getMinCapacity();
            int tot = c.getNumGuests();
            boolean sameType = sameTableTypes(c.getTableType());
            if ( max >= tot && min <= tot && sameType && (reservedTo == null || reservedTo.equals(c)) ) // will need to check if has already been reservered
            {
                return true;
            }        
            else
                return false;
    }
    
    public boolean tableAccomodates(Customer c)
    {
        if(isClean() )//&& ( (!isReserved) || c.getReservedTable().equals(this)))
        {
            // should check table type matches request
            // also check wheel chair and high chair
            double max = getMaxCapacity();
            int min = getMinCapacity();
            int tot = c.getNumGuests();
            boolean sameType = sameTableTypes(c.getTableType());
            if ( max >= tot && min <= tot && isClean() && sameType)
            {
                return true;
            }
        }
        return false;
    }
    public boolean sameTableTypes(Customer.TableType t)
    {
        if(t != null)
        {
            String prefenceType = t.toString();
            String tableType = this.tType.toString();
            return prefenceType == tableType;
        }
        return true;
    }
    public String getName()
    {
        return tableName;
    }
    public double getMaxCapacity()
    {
        return maxCap;
    }
    public int getMinCapacity()
    {
        return minCap;
    }
    public boolean isBooth()
    {
        return TableType.BOOTH == tType;
    }
    public boolean isClean()
    {
        while(lkOpen.isLocked())
        {
            try
            {
                System.out.println("Sleeping");
                Thread.sleep(800);
            }
            catch (Exception e){}
        }
        return isClean;
    }
    public void splitTables()
    {
        try
        {
            lkOpen.lock();
            if(this.getTablesCombined().size() > 1)
            {
                Table[] tbl = Utility.makeTableArray(tablesCombined);
                for(int i = 0; i < tbl.length; i++)
                    tbl[i].splitTables();
                careTaker.returnTable(this);
            }
            else
            {
                careTaker.returnTable(this);
                if(tableOwner != null)
                {
                    this.setCareTaker(tableOwner);
                    tableOwner.getTableBack(this);
                }

                System.out.println("Table is split " + this.getName());
            }
        }
        catch(Exception e){}
        finally
        {
            lkOpen.unlock();
            refresh();
        }
    }    
    
    public String getEstimatedWait()
    {
        String time;
        Calendar now = Calendar.getInstance();
        Calendar temp = (Calendar)estimatedWait.clone();
        if(now.before(temp))
        {
            temp.add(Calendar.HOUR, -now.get(Calendar.HOUR));
            temp.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));        
            int hr = temp.get(Calendar.HOUR);
            int min = temp.get(Calendar.MINUTE);
            String sMin = min >= 10 ? Integer.toString(min) : "0" + Integer.toString(min); 
            time = Integer.toString(hr) + ":" + sMin;
            return time; 
        }
        else
            return "0:00";
    }
    
    public Calendar getEstimatedCalendarTime()
    {
        return estimatedWait;
    }
    
    public void setEstimatedWait(int hrs, int min)
    {
        Calendar t = Calendar.getInstance();
        t.add(Calendar.HOUR, hrs);
        t.add(Calendar.MINUTE, min);
        estimatedWait = t;
        wait.run();
    }
    
    public void addToEstimatedWait(int hrs, int min)
    {
        estimatedWait.add(Calendar.HOUR, hrs);
        estimatedWait.add(Calendar.MINUTE, min);
    }
    
    public void setCleanStatus(boolean b)
    {
        try
        {
            lkOpen.lock();
            isClean = b;
            if(this.getTablesCombined().size() > 1)
            {
                Table[] tbl = Utility.makeTableArray(tablesCombined);
                for(int i = 0; i < tbl.length; i++)
                    tbl[i].setCleanStatus(b);
                if(b == true)
                {
                    careTaker.returnTable(this);
                    //refresh();
                }
            }
            else if(b == true)
            {
                careTaker.returnTable(this);

                if(tableOwner != null)
                {
                    this.setCareTaker(tableOwner);
                    tableOwner.getTableBack(this);
                }

                System.out.println("Table is clean " + this.getName());
            }
            else
            {
                System.out.println("DIRTY TABLE");
            }        
            
        }
        catch(Exception e){}
        finally
        {
            lkOpen.unlock();
            if(b)
                wait.checkSeating();
            refresh();
        }
    }
    public int getNumHighChairsPossible()
    {
        return this.numHighChairsPossible;
    }
    public int getNumWheelChairsPossible()
    {
        return this.numWheelChairsPossible;
    }

    public void changeStatus()
    {
        setCleanStatus(!this.isClean());
        this.refresh();
    }
    public void refresh()
    {
        gui.removeAll();
        gui.paint();
    }
    public TableGUI getTableGUI()
    {
        return gui;
    }
    private ReentrantLock lkOpen;
    private Calendar estimatedWait;
    private TableGUI gui;
    private Server careTaker;
    private Server tableOwner;
    private Customer reservedTo;
    private String tableName;
    private double maxCap;
    private int minCap = 1;
    private boolean isClean;
    private int numWheelChairsPossible;
    private int numHighChairsPossible;
    private TableType tType;
    private ArrayList tablesCombined;
    private ArrayList neighbors;
    private WaitList wait;
}
