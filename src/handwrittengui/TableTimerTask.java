/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.util.TimerTask;
/**
 *
 * @author JCatterson
 */
public class TableTimerTask extends TimerTask
{
    public TableTimerTask(Table s)
    {
        t = s;
    }
    public void run()
    {
        t.refresh();
        System.out.println("I ticked!");
    }
    private Table t;
}
