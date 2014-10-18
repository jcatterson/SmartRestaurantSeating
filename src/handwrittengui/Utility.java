/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author JCatterson
 */
public class Utility
{
    public static ArrayList makeArrayList(Table[] tbls)
    {
        ArrayList ret = new ArrayList();
        for(int i = 0; i < tbls.length; i++)
            ret.add(tbls[i]);
        return ret;
    }
    public static Table[] orderTablesByTime(Table[] tbls)
    {
        for(int i = 0; i < tbls.length; i++)
        {
            int timeInSec;
            try
            {
                String time = tbls[i].getEstimatedWait();
                timeInSec = Integer.parseInt(time.split(":")[0]) * 60;
                timeInSec += Integer.parseInt(time.split(":")[1]);
            }
            catch(Exception e)
            {
                timeInSec = Integer.MAX_VALUE;
            }
            int minIndex = i;
            for(int j = i+1; j < tbls.length; j++)
            {
                int innerTimeInSec;
                try
                {
                    String time = tbls[j].getEstimatedWait();
                    innerTimeInSec = Integer.parseInt(time.split(":")[0])*60;
                    innerTimeInSec += Integer.parseInt(time.split(":")[1]);
                }
                catch(Exception e)
                {
                    innerTimeInSec = Integer.MAX_VALUE;
                }
                if(innerTimeInSec < timeInSec)
                {
                    minIndex = j;
                }
            }
            if(minIndex != i)
            {
                Table temp = tbls[i];
                tbls[i] = tbls[minIndex];
                tbls[minIndex] = temp;
            }
            
        }
        return tbls;
    }
    /* 
     * Returns all the Tables ordered by the number of neighbors
     * The ones that have the equivalent number of tables are stored within the
     * returned ArrayList as a Table ArrayList.
     */
    public static ArrayList orderSameSizedNeighbors(Table[] tbls)
    {
        ArrayList t = orderByNumberNeighbors(tbls);
        ArrayList toReturn = new ArrayList();
        ArrayList sameSize = new ArrayList();
        int currentSize = tbls[(int)t.get(0)].getAllNeighbors().size();
        for(int i = 0; i < t.size(); i++)
        {
            int index = (int)t.get(i);
            Table temp = tbls[index];
            int numNeighbors;
            numNeighbors = temp.getAllNeighbors().size();
            if(currentSize == numNeighbors)
            {
                sameSize.add(temp);
            }
            else //(currentSize < numNeighbors)
            {
                toReturn.add(sameSize);
                sameSize = new ArrayList();
                sameSize.add(temp);
                currentSize = numNeighbors;                
            }
           /* else
            {
               ; // do not know what to do
            }*/
        }
        if(!sameSize.isEmpty())
            toReturn.add(sameSize);
        return toReturn;
    }
    
    private static ArrayList orderByNumberNeighbors(Table[] tbls)
    {        
        ArrayList order = new ArrayList();
        int nextUp;
        int i = 0;
        int leastSat = 0;
        while(true)
        {
            if(order.size() == tbls.length)
                break;
            nextUp = i;
            //if(i != 0)
                leastSat = tbls[i].getAllNeighbors().size();
            if(order.contains(i))
            {
                leastSat = Integer.MAX_VALUE;
            }
            for(int j = i+1; j < tbls.length; j++)//.size(); j++)
            {
                if(tbls[j].getAllNeighbors().size() < leastSat && !order.contains(j))
                {
                    leastSat = tbls[j].getAllNeighbors().size();
                    nextUp = j;
                }
            }
            order.add(nextUp);
            if(i != nextUp)
                i = 0;       
            else
                i++;
        }
        return order;
    }
    
    /*
     * Probably the same problem as orderByNumberNeighbors
     */
    public static ArrayList findAbsoluteLeastConflict(Table[] orderedList)
    {
        ArrayList l = new ArrayList();
        int smallest = orderedList[0].getAllConnectingTableOwners().size();
        l.add(orderedList[0]);
        for(int i = 1; i < orderedList.length;i++)
        {
            int size = orderedList[i].getAllConnectingTableOwners().size();
            if(smallest < size)
                break;
            else
                l.add(orderedList[i]);
        }
        return l;
    }
    public static ArrayList orderHash(HashMap h)
    {
        //int[] o = new int[h.size()];
        Object[] k = h.keySet().toArray();  
        
        
        
        //Server[] s = Utility.makeServerArray(servers);
        ArrayList order = new ArrayList();
        int nextUp;
        int i = 0;
        while(true)
        {
            if(order.size() == h.size())
                break;
            nextUp = (int)k[i];
            double leastSat = (double)h.get(k[i]);//s[i].getTotalServed();
            if(order.contains(i))
            {
                leastSat = Integer.MAX_VALUE;
            }
            for(int j = i+1; j < h.size(); j++)
            {
                if((double)h.get(k[j]) < leastSat && !order.contains((int)k[j]))
                {
                    leastSat = (double)h.get(k[j]);
                    nextUp = (int)k[j];
                }
            }
            order.add(nextUp);
            if(i != nextUp)
                i = 0;       
            else
                i++;
        }
        return order;
        
    }
    public static boolean tableInList(Table toFind, Table[] t)
    {
        for(int i =0; i < t.length; i++)
        {
            if( t[i].equals(toFind) )
            {
                return true;
            }            
        }
        return false;
    }
    
    public static boolean serverInAList(Server toFind, ArrayList t)
    {
        for(int i =0; i < t.size(); i++)
        {
            if( ((Server)t.get(i)).equals(toFind) )
            {
                return true;
            }            
        }
        return false;
    }
    
    public static boolean serverInList(Server toFind, Server[] t)
    {
        for(int i =0; i < t.length; i++)
        {
            if( t[i].equals(toFind) )
            {
                return true;
            }            
        }
        return false;
    }
    
    public static Table[] makeTableArray(ArrayList s)
    {
        int j = s.size();
        Table[] tbls = new Table[j];
        for(int i = 0; i < j; i++)
        {
            tbls[i] = (Table)s.get(i);
        }
        return tbls;       
    }
    
    public static Customer[] makeCustomerArray(ArrayList t)
    {
        int j = t.size();
        Customer[] tbls = new Customer[j];
        for(int i = 0; i < j; i++)
        {
            tbls[i] = (Customer)t.get(i);
        }
        return tbls;
    }
    
    public static Server[] makeServerArray(ArrayList s)
    {
        int j = s.size();
        Server[] tbls = new Server[j];
        for(int i = 0; i < j; i++)
        {
            tbls[i] = (Server)s.get(i);
        }
        return tbls;       
    }
    
    public static Server[] makeServerListFromIndicies(ArrayList index, ArrayList servers)
    {
        Server[] s = new Server[servers.size()];
        for(int i = 0; i < servers.size(); i++)
        {
            int z = (int)index.get(i);
            s[i] = (Server)servers.get(z);
        }
        return s;
    }
    public static Table[] makeTableListFromIndicies(ArrayList index, ArrayList tables)
    {
        Table[] s = new Table[tables.size()];
        for(int i = 0; i < tables.size(); i++)
        {
            int z = (int)index.get(i);
            s[i] = (Table)tables.get(z);
        }
        return s;
    }
    public static Table[] makeTableListFromIndicies(ArrayList index, Table[] tables)
    {
        Table[] s = new Table[index.size()];
        for(int i = 0; i < index.size(); i++)
        {
            int z = (int)index.get(i);
            s[i] = (Table)tables[z];
        }
        return s;
    }
    
    /*
     * Returns the INDEX order for the servers
     */
    public static ArrayList serverServiceOrder(ArrayList servers)
    {
        Server[] s = Utility.makeServerArray(servers);
        ArrayList order = new ArrayList();
        int nextUp;
        int i = 0;
        while(true)
        {
            if(order.size() == servers.size())
                break;
            nextUp = i;
            int leastSat = s[i].getTotalServed();
            if(order.contains(i))
            {
                leastSat = Integer.MAX_VALUE;
            }
            for(int j = i+1; j < servers.size(); j++)
            {
                if(s[j].getTotalServed() < leastSat && !order.contains(j))
                {
                    leastSat = s[j].getTotalServed();
                    nextUp = j;
                }
            }
            order.add(nextUp);
            if(i != nextUp)
                i = 0;       
            else
                i++;
        }
        return order;
    }
    
    /*
     * Returns the INDEX order of least conflicting tables
     */
    public static ArrayList orderByLeastConflicting(ArrayList t)
    {
        ArrayList order = new ArrayList();
        int nextUp;
        int i = 0;
        while(true)
        {
            if(order.size() == t.size())
                break;
            nextUp = i;
            ArrayList n = ((Table)t.get(i)).getAllConnectingTableOwners();
            int leastSat = n.size();
            if(order.contains(i))
            {
                leastSat = Integer.MAX_VALUE;
            }
            for(int j = i+1; j < t.size(); j++)
            {
                ArrayList possibleNext = ((Table)t.get(j)).getAllConnectingTableOwners();
                int pNextSize = possibleNext.size();
                if(pNextSize < leastSat && !order.contains(j))
                {
                    leastSat = pNextSize;
                    nextUp = j;
                }
            }
            order.add(nextUp);
            if(i != nextUp)
                i = 0;       
            else
                i++;
        }
        return order;        
        //return t;
    }
    
    /*
     * Returns the ordered INDECIES of the best fit tables
     */
    public static ArrayList orderByBestFit(Table[] t)
    {
        ArrayList order = new ArrayList();
        int nextUp;
        int i = 0;
        while(true)
        {
            if(order.size() == t.length)//.size())
                break;
            nextUp = i;
            double leastSat = t[i].getMaxCapacity();//((Table)t.get(i)).getMaxCapacity();
            if(order.contains(i))
            {
                leastSat = Integer.MAX_VALUE;
            }
            for(int j = i+1; j < t.length; j++)//.size(); j++)
            {
                if(t[j].getMaxCapacity() < leastSat && !order.contains(j))//((Table)t.get(j)).getMaxCapacity() < leastSat && !order.contains(j))
                {
                    leastSat = t[j].getMaxCapacity();//((Table)t.get(j)).getMaxCapacity();
                    nextUp = j;
                }
            }
            order.add(nextUp);
            if(i != nextUp)
                i = 0;       
            else
                i++;
        }
        return order;
    }
}
