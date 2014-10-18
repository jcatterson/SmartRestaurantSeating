/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.lang.Number;

/**
 *
 * @author JCatterson
 */
public class Greeter extends JFrame // JPanel
{
    public Greeter()
    {
        
        gc = new GreeterCommunicator();
        gc.connect();
        initComponents();
        paint();
    }

    public void paint()
    {
        layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateGaps(true);
        this.getContentPane().setLayout(layout);
        int size = this.lblBooth.getPreferredSize().height + this.numGuests.getPreferredSize().height;
        layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                        .addGroup(
                                    layout.createParallelGroup()
                                    .addComponent(lblNumGuests)
                                    .addComponent(numGuests)
                                  )
                        .addGroup(
                                    layout.createParallelGroup()
                                    .addComponent(lblNumHC)
                                    .addComponent(numHC)
                                  )
                        .addGroup(
                                    layout.createParallelGroup()
                                    .addComponent(lblNumWC)
                                    .addComponent(numWC)
                                  )
                        .addComponent(btnAddGroup, 0, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup( 
                                    layout.createParallelGroup()
                                    .addComponent(cBoxBooth)
                                    .addComponent(cBoxTable)
                                 )
                        .addGroup( 
                                    layout.createParallelGroup()
                                    .addComponent(lblBooth)
                                    .addComponent(lblTable)
                                 )
                );
        layout.setVerticalGroup(
                    layout.createParallelGroup()
                        .addGroup(
                                    layout.createSequentialGroup()
                                    .addComponent(lblNumGuests)
                                    .addComponent(numGuests)
                                )
                        .addGroup( 
                                     layout.createSequentialGroup()
                                     .addComponent(lblNumHC)
                                     .addComponent(numHC)
                                  )
                        .addGroup( 
                                     layout.createSequentialGroup()
                                     .addComponent(lblNumWC)
                                     .addComponent(numWC)
                                  )
                        .addComponent(btnAddGroup, GroupLayout.Alignment.LEADING, 0, size, size)
                        .addGroup( 
                                     layout.createSequentialGroup()
                                     .addComponent(cBoxBooth)
                                     .addComponent(cBoxTable)
                                  )
                        .addGroup( 
                                     layout.createSequentialGroup()
                                     .addComponent(lblBooth)
                                     .addComponent(lblTable)
                                  )
                );
        pack();
        numGuests.requestFocusInWindow();
        this.setVisible(true);
    }
    private void initComponents()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        lblNumGuests = new JLabel("Num Guests");
        lblNumHC = new JLabel("Num HC");
        lblNumWC = new JLabel("Num WC");
        lblBooth = new JLabel("Booth");
        lblTable = new JLabel("Table");
        btnAddGroup = new JButton("Add Guest(s)");
        createAddBtnMouseActions();
        numGuests = new JSpinner();
        numHC = new JSpinner();
        numWC = new JSpinner();
        cBoxBooth = new JCheckBox();
        createBoothCkListener();
        cBoxTable = new JCheckBox();
        createTableCkListener();
        
        //numGuests.setFocusable(true);
        //numGuests.requestFocusInWindow();
        
        /*this.addWindowFocusListener(new java.awt.event.WindowAdapter() {
    public void windowGainedFocus(java.awt.event.WindowEvent e) {
        numGuests.requestFocusInWindow();
    }
});*/
        
    }
    private void addBtnClicked()
    {
        int guests = ((Number)numGuests.getValue()).intValue();
        int hc = ((Number)numHC.getValue()).intValue();
        int wc = ((Number)numWC.getValue()).intValue();
        char booth = cBoxBooth.isSelected() ? 'T' : 'F';
        char table = cBoxTable.isSelected() ? 'T' : 'F';    
        String preference;
        if( cBoxBooth.isSelected() )
            preference = "B";
        else if( cBoxTable.isSelected() )
            preference = "T";
        else
            preference = "N";
        gc.sendMessage(Integer.toString(guests) + "-" + Integer.toString(hc) + "-" + Integer.toString(wc) + "-" + preference);
        
    }
    private void createTableCkListener()
    {
        cBoxTable.addMouseListener(new java.awt.event.MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if( cBoxBooth.isSelected() )
                    cBoxBooth.doClick();
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    private void createBoothCkListener()
    {
        cBoxBooth.addMouseListener(new java.awt.event.MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if( cBoxTable.isSelected() )
                    cBoxTable.doClick();
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    private void createAddBtnMouseActions()
    {
        btnAddGroup.addMouseListener(new java.awt.event.MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                addBtnClicked();
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
    
    private GroupLayout layout;
    
    private JLabel lblNumGuests;
    private JLabel lblNumHC;
    private JLabel lblNumWC;
    private JLabel lblBooth;
    private JLabel lblTable;
    
    private JButton btnAddGroup;
    
    private JSpinner numGuests;
    private JSpinner numWC;
    private JSpinner numHC;
    
    private JCheckBox cBoxBooth;
    private JCheckBox cBoxTable;
    
    private GreeterCommunicator gc;
}
