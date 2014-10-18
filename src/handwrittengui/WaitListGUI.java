/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.*;
import javax.swing.JLabel;
/**
 *
 * @author JCatterson
 */
public class WaitListGUI extends JFrame
{
    public  WaitListGUI()
    {
        layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateGaps(true);
        this.getContentPane().setLayout(layout);
        layout.layoutContainer(this.getContentPane());
        vCustomers = layout.createSequentialGroup();
        hCustomers = layout.createParallelGroup();
        lblName = new JLabel("Name");
        lblGuests = new JLabel("Num Guests");
        lblNumHC = new JLabel("Num HC");
        lblNumWC = new JLabel("Num WC");
        lblPreference = new JLabel("Preference");
        lblTimeEntered = new JLabel("te");
        lblWaitTime = new JLabel("te");
        this.refreshTimer = new Timer();
        //paint();
        
    }

    public void paint()
    {
        layout.setHorizontalGroup(
                    layout.createParallelGroup()
                        .addGroup(
                            layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addComponent(lblGuests)
                                .addComponent(lblNumHC)
                                .addComponent(lblNumWC)
                                .addComponent(lblPreference)
                                .addComponent(lblTimeEntered)
                                .addComponent(lblWaitTime)
                                )
                    .addGroup(hCustomers) 
                );
        layout.setVerticalGroup(
                    layout.createSequentialGroup()
                    .addGroup(
                        layout.createParallelGroup()
                        .addComponent(lblName)
                        .addComponent(lblGuests)
                        .addComponent(lblNumHC)
                        .addComponent(lblNumWC)
                        .addComponent(lblPreference)
                        .addComponent(lblTimeEntered)
                        .addComponent(lblWaitTime) )
                    .addGroup(layout.createSequentialGroup() 
                        .addGroup(vCustomers) )
                    
                );
        pack();
        setVisible(true);
    }
    
    public void addCustomer(Customer c)
    {        
        hCustomers.addComponent(c.getGraphic());
        vCustomers.addComponent(c.getGraphic());
        paint();
    }
    public void paintNewCustomers(Customer[] c)
    {
        
        this.getContentPane().removeAll();
        for(int i = 0; i < c.length; i++)
        {
            hCustomers.addComponent(c[i].getGraphic());
            vCustomers.addComponent(c[i].getGraphic());
        }
        paint();
    }
    public static void runGUI()
    {
                java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new WaitListGUI();//.setVisible(true);
            }
        });        
    }

    private Timer refreshTimer;
    private JLabel lblName;
    private JLabel lblGuests;
    private JLabel lblNumHC;
    private JLabel lblNumWC;
    private JLabel lblPreference;
    private JLabel lblTimeEntered;
    private JLabel lblWaitTime;
    private GroupLayout layout;
    private ParallelGroup hCustomers;
    private SequentialGroup vCustomers;
}
