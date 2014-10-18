/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.*;
import java.util.Calendar;
/**
 *
 * @author JCatterson
 */
public class CustomerGraphic extends JPanel
{
    public CustomerGraphic(Customer c)
    {
        cust = c;
        paint();
        
    }
    public void paint()
    {
        layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        lblName = new JLabel("BOBBBBB");
        lblNumGuests = new JLabel(Integer.toString(cust.getNumGuests()));
        lblNumHC = new JLabel(Integer.toString(cust.getNumHighChairs()));
        lblNumWC = new JLabel(Integer.toString(cust.getNumWheelChairs()));
        lblPreference = new JLabel("BOOTH");
        lblTimeEntered = new JLabel("ENTERED");
        if(cust.getReservedTable() !=null)
        {
            String t;
            try
            {
                t = cust.getReservedTable().getEstimatedWait();
            }
            catch(Exception e)
            {
                t = "0:00";
            }
            
            /*if(t == null)
                t = "0:00";*/
            //int hr = t.get(Calendar.HOUR);
            //int min = t.get(Calendar.MINUTE);
            lblEstimatedWaitLeft = new JLabel(t);
            lblTableReserved = new JLabel(cust.getReservedTable().getName());
        }
        else
        {
            lblEstimatedWaitLeft = new JLabel("???");
            lblTableReserved = new JLabel("???");
        }
        
        hGroup = layout.createSequentialGroup();
        vGroup = layout.createParallelGroup();
        this.setLayout(layout);
        layout.setHorizontalGroup(hGroup
                    .addComponent(lblName)
                    .addComponent(lblNumGuests)
                    .addComponent(lblNumHC)
                    .addComponent(lblNumWC)
                    .addComponent(lblPreference)
                    .addComponent(lblTimeEntered)
                    .addComponent(lblEstimatedWaitLeft)
                    .addComponent(lblTableReserved)
                );
        layout.setVerticalGroup(vGroup
                    .addComponent(lblName)
                    .addComponent(lblNumGuests)
                    .addComponent(lblNumHC)
                    .addComponent(lblNumWC)
                    .addComponent(lblPreference)
                    .addComponent(lblTimeEntered)
                    .addComponent(lblEstimatedWaitLeft)
                    .addComponent(lblTableReserved)
                );
    }
    
    public void reEvaluate()
    {
        this.removeAll();
        this.paint();
    }
    private Customer cust;
    private GroupLayout layout;
    private SequentialGroup hGroup;
    private ParallelGroup vGroup;
    private JLabel lblName;
    private JLabel lblNumGuests;
    private JLabel lblNumHC;
    private JLabel lblNumWC;
    private JLabel lblPreference;
    private JLabel lblTimeEntered;
    private JLabel lblEstimatedWaitLeft;
    private JLabel lblTableReserved;
}
