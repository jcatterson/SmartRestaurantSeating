/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittengui;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.FocusEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.PopupMenuEvent;

/**
 *
 * @author JCatterson
 */
public class TableGUI extends JPanel
{
    public TableGUI(Table t)
    {
        tbl = t;
        this.addFocusListener(new java.awt.event.FocusListener() 
        {

            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                popup.setVisible(false);
                draggedOne = null;
                isDragged = false;
            }
        });
        initialize();
        paint();
    }
    public void paint()
    {
        if(!tbl.isClean())
        {
            this.setBackground(Color.red);
        }
        else
            this.setBackground(Color.green); 
        layout = new GroupLayout(this);
        lblTable = new JLabel(tbl.getName());
        createLabelListener();           
        LineBorder b = new LineBorder(Color.BLACK,3);
        
        this.setBorder(b);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addComponent(lblTable)
                );
        layout.setVerticalGroup(
                    layout.createParallelGroup()
                        .addComponent(lblTable)
                );  
        //super.repaint();
    }
    
    private void showPopUp(int x, int y)
    {
        popup.setLocation(x,y);
        this.add(popup);        
        popup.setVisible(true);
        popup.setRequestFocusEnabled(true);
    }    

    
    private void initialize()
    {        
        popup = new JPopupMenu();
        itmSplit = new JMenuItem("Split Tables");
        itmMerge = new JMenuItem("Merge Tables");
        popup.add(itmSplit);
        popup.add(itmMerge);
        addMouseListenerSplit();
        addMouseListenerMerge();
    }
    
    private void addMouseListenerMerge()
    {

        itmMerge.addMouseListener(new java.awt.event.MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                draggedOne = tbl;
                isDragged = true;  
                popup.setVisible(false);
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    
    private void addMouseListenerSplit()
    {
        itmSplit.addMouseListener(new java.awt.event.MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                popup.setVisible(false);
                tbl.splitTables();
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void createLabelListener()
    { 
        lblTable.addMouseListener(
        new java.awt.event.MouseListener()
        {   
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(!isDragged)
                {
                    requestFocus();
                    if(e.getButton() == MouseEvent.BUTTON1)
                        tbl.changeStatus();
                    else if(e.getButton() == MouseEvent.BUTTON3)
                    {       
                        showPopUp(e.getXOnScreen(), e.getYOnScreen());
                    }
                }
                else
                {
                    tbl.mergeTable(draggedOne);
                    draggedOne = null;
                    isDragged = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
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
            public void mouseExited(MouseEvent e) {
                
            }
        }
                                                                        ); 
    }
    
    private static volatile Table draggedOne;
    private static volatile boolean isDragged = false;
    private JPopupMenu popup;
    private JMenuItem itmMerge;
    private JMenuItem itmSplit;
    private Table tbl;
    private GroupLayout layout;
    private JLabel lblTable;
}
