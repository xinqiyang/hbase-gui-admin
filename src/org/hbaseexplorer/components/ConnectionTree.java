package org.hbaseexplorer.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.logging.Log;
import org.apache.hadoop.conf.Configuration;
import org.buddy.javatools.Utils;
import org.hbaseexplorer.HBaseExplorerView;
import org.hbaseexplorer.domain.Connection;
import org.hbaseexplorer.domain.Table;

/**
 *
 * @author zaharije
 */
public class ConnectionTree extends JTree {
    private Table table;
    private HBaseExplorerView mainApp;

    public ConnectionTree() {
        super();
        
        // Double click handler
        addMouseListener(
            new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int selRow = getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = getPathForLocation(e.getX(), e.getY());
                    if(selRow != -1) {
                        if(e.getClickCount() == 1) {
                        //    mySingleClick(selRow, selPath);
                        }
                        else if(e.getClickCount() == 2) {
                          //  myDoubleClick(selRow, selPath);
                          doubleClick(selPath);
                        }
                    }
                }
            }
        );
         
    }

    public void createConnection(Configuration conf)  {
        if(conf != null) {
            try {
                Connection conn = new Connection(conf);
                conn.connect();
                addConnectionToTree(conn);
            } catch (Exception ex) {
                Logger.getLogger(ConnectionTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //double click 
    public void doubleClick(TreePath selectionPath) {
        Log log = Utils.getLog();
        long start =  System.currentTimeMillis();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

        Object userObject = selectedNode.getUserObject();
        if (userObject instanceof Table) {
            mainApp.getTabPane().showTable((Table)userObject);
        }
        log.info("double click time time:" + (System.currentTimeMillis() - start));
        
    }


    //add table to list
    private void addConnectionToTree(Connection conn) {
        
        Log log = Utils.getLog();
        
        DefaultTreeModel defTreeModel = (DefaultTreeModel)getModel();

        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)getModel().getRoot();

        //
        log.debug(conn.getName());
        
        DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(conn.getName(), true);
        
        
        DefaultMutableTreeNode tablesNode = new DefaultMutableTreeNode("Tables", true);
        
        nameNode.setUserObject(conn);
        nameNode.add(tablesNode);
        
        for(Table mtable : conn.getTableList()) {
            
            //log.info(mtable);
            
            DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(mtable.getName(), true);
            tablesNode.add(tableNode);
            tableNode.setUserObject(mtable);
        }

        DefaultMutableTreeNode confNode = new DefaultMutableTreeNode("Tables Count:" + tablesNode.getChildCount(), true);
        nameNode.add(confNode);

        
        rootNode.add(nameNode);
        
        defTreeModel.setRoot(rootNode);

        for(int i=0; i<getRowCount(); i++) {
            expandRow(i);
        }
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public HBaseExplorerView getMainApp() {
        return mainApp;
    }

    public void setMainApp(HBaseExplorerView mainApp) {
        this.mainApp = mainApp;
    }
}
