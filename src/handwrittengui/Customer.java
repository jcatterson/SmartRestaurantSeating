/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.util.Calendar;
/**
 *
 * @author JCatterson
 */
public class Customer
{
    enum TableType
    {
        TABLE, BOOTH;
    }
    public void setTableType(char t)
    {
        if(t == 'T')
            this.typeTable = TableType.TABLE;
        else if(t == 'B')
            this.typeTable = TableType.BOOTH;
        else
            ; // leave it null
    }
    
     public Customer(int guests, int numWheel, int numHC, char tableType)
    {
        this.numGuest = guests;
        this.numWheelChairs = numWheel;
        this.numHighChairs = numHC;
        setTableType(tableType);
        this.timeArrived = Calendar.getInstance();
        cg = new CustomerGraphic(this);
        //cg.paint();
    }
    public Customer(int guests, int numWheel, int numHC)
    {
        this.numGuest = guests;
        this.numWheelChairs = numWheel;
        this.numHighChairs = numHC;
        this.timeArrived = Calendar.getInstance();
        cg = new CustomerGraphic(this);
        //cg.paint();
    }
    public Customer(String name, int guests, TableType tType, Calendar resTime)
    {
        this.name = name;
        this.numGuest = guests;
        this.typeTable = tType;
        this.reservationTime = resTime;
        this.timeArrived = Calendar.getInstance();
        cg = new CustomerGraphic(this);
    }
    public Customer(String name, int guests, Calendar resTime)
    {
        this.name = name;
        this.numGuest = guests;
        this.typeTable = null;
        this.reservationTime = resTime;
        this.timeArrived = Calendar.getInstance();
        cg = new CustomerGraphic(this);
    }
    public Customer(String name, int guests, TableType tType)
    {
        this.name = name;
        this.numGuest = guests;
        this.timeArrived = Calendar.getInstance();
        this.typeTable = tType;
        this.reservationTime = null;
        cg = new CustomerGraphic(this);
    }
    public Customer(String name, int guests)
    {
        this.name = name;
        this.numGuest = guests;
        this.timeArrived = Calendar.getInstance();
        this.typeTable = null;
        this.reservationTime = null;
        cg = new CustomerGraphic(this);
    }
    public void setNumGuest(int s)
    {
        numGuest = s;
    }
    
    public int getNumGuests()
    {
        return numGuest;
    }
    public void setNumHighChairs(int s)
    {
        numHighChairs = s;
    }
    public int getNumHighChairs()
    {
        return numHighChairs;
    }
    public int getNumWheelChairs()
    {
        return numWheelChairs;
    }
    public void setNumWheelChairs(int s)
    {
        numWheelChairs = s;
    }
    public CustomerGraphic getGraphic()
    {
        return cg;
    }
    public void addWaitTicket(int tickNum)
    {
        ticketNumber = tickNum;
    }
    public int getWaitTicket()
    {
        return ticketNumber;
    }
    /*public void setClamiedTable(String s)
    {
        claimedTable = s;
    }
    public String getClaimedTable()
    {
        return claimedTable;
    }*/
    public TableType getTableType()
    {
        return typeTable;
    }
    
    public void setReservedTable(Table t)
    {
        reservedTable = t;
        t.setReserved(this);
    }
    public Table getReservedTable()
    {
        return reservedTable;
    }
    public Calendar getTimeArrived()
    {
        return timeArrived;        
    }
    private Table reservedTable;
    private String claimedTable;
    private int ticketNumber;
    private CustomerGraphic cg;
    private int numHighChairs;
    private int numWheelChairs;
    private String name;
    private Calendar timeArrived;
    private int numGuest;
    private TableType typeTable;
    private Calendar reservationTime;
    
}
