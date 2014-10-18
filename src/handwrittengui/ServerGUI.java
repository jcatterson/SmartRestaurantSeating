/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;

import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Timer;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author JCatterson
 */
public class ServerGUI extends JPanel
{
    public ServerGUI(Server s)
    {
        this.refreshTimer = new Timer();
        thisServer = s;
        scheduleTimer();
        setMouseListener();
        paint();
    }
    
    private void setMouseListener()
    {
        this.addMouseListener(new java.awt.event.MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                requestFocus();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                
            }
        });
    
    }
    
    public void refresh()
    {
        this.refreshTimer.cancel();
        this.removeAll();
        this.paint();
        refreshTimer = new Timer();
        scheduleTimer();
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
  
    public void paint()
    {
        
        lblName = new JLabel(thisServer.getServerName());
        lblTotServ = new JLabel(Integer.toString(thisServer.getTotalServed()));
        lblCurrServ = new JLabel(Integer.toString(thisServer.getCurrentlyServing()));
        //Calendar temp = thisServer.secondsFromLastSat();
        lblLastSat = new JLabel("temp");//thisServer.printTime(temp));
        
        layout = new GroupLayout(this);
        createTableGroups();
        
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                      .addComponent(lblName, 80, 80, 80)
                      .addComponent(lblTotServ,10,10,10)
                      .addGroup(hTableGroup)
                      .addComponent(lblCurrServ,10,10,10)
                      .addComponent(lblLastSat,40,40,40)
                
                );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblName)
                        .addComponent(lblTotServ)
                        .addGroup(vTableGroup)
                        .addComponent(lblCurrServ)
                        .addComponent(lblLastSat))
                );
    }
    
    private void createTableGroups()
    {
        vTableGroup = layout.createParallelGroup();
        hTableGroup = layout.createSequentialGroup();
        //int size = thisServer.getNumTablesOwned();
        Table[] tables = thisServer.getOwnedTables();
        for(int i = 0; i < tables.length; i++)
        {
            vTableGroup.addComponent(tables[i].getTableGUI());
            hTableGroup.addComponent(tables[i].getTableGUI());
        }
    }
    
    private GroupLayout layout;
    private GroupLayout.Group vTableGroup;
    private GroupLayout.Group hTableGroup;
    private JLabel lblName;
    private JLabel lblLastSat;
    private JLabel lblTotServ;
    private JLabel lblCurrServ;
    private Timer refreshTimer;
    private Server thisServer;
}
