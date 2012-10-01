/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditTableData.java
 *
 * Created on May 21, 2010, 12:26:14 AM
 */

package org.hbaseexplorer.components;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.logging.Log;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.buddy.javatools.Utils;
import org.hbaseexplorer.HBaseExplorerApp;
import org.hbaseexplorer.components.renderers.EditTableCellRenderer;
import org.hbaseexplorer.datamodels.EditTableDataModel;
import org.hbaseexplorer.domain.FilterModel;
import org.hbaseexplorer.domain.HBTriplet;
import org.hbaseexplorer.domain.RowData;
import org.hbaseexplorer.domain.Table;
import org.hbaseexplorer.exception.ExplorerException;
import org.jdesktop.application.Action;
/**
 *
 * @author zaharije
 */
public class EditTableData extends javax.swing.JPanel {

    private Table table;
    private String rowKey;
    private FilterModel filterModel;
    private int Total=0;
    //add row keys
    //private ArrayList allRowKey;

    /** Creates new form EditTableData */
    public EditTableData(Table table) {
        initComponents();
        this.table = table;
        this.filterModel = new FilterModel();

        Log log = Utils.getLog();
        //tableData.setDefaultRenderer(String.class, new EditTableCellRenderer());
        long start =  System.currentTimeMillis();
        showData(0);
        log.info("first show Data" + (System.currentTimeMillis() - start));
        //add by xinqiyang
        //load Rows
        long startLoad =  System.currentTimeMillis();
        loadRows(2000);
        setjListRowListener();
        jLabel1.setText("   ROWS TATAL:"+String.valueOf(this.Total));
        log.info("start load show Data" + (System.currentTimeMillis() - startLoad));
    }
    
    private void loadRows(int num)
    {
        EditTableDataModel model = new EditTableDataModel(table, num);
        jListRow.setModel(model.getRowData(num));
        this.Total = model.getRowsTotal();
        //jLabel1.setText(String.valueOf(model.getRowsTotal()));
        
    }
    
    
    private void setjListRowListener()
    {
        jListRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                //JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 1) {          // Double-click
                    // Get item index
                    //int index = list.locationToIndex(evt.getPoint());
                    String itemKey = (String)jListRow.getSelectedValue();
                    //JOptionPane.showMessageDialog(null,index+"  "+itemKey);
                    txtFieldRowKey.setText(itemKey);
                    btnGoClickAction();
                }
            }
        });
    }

    //get row from list
    private void showData(int skip) {
        EditTableDataModel model = new EditTableDataModel(table, skip, rowKey, filterModel);
        autoResizeColWidth(tableData, model);

        Log log = Utils.getLog();
        long start =  System.currentTimeMillis();
        TableColumnModel columnModel = tableData.getColumnModel();
        for (int i = 0; i < tableData.getColumnCount(); i++) {
            //edit
            columnModel.getColumn(i).setCellRenderer(new EditTableCellRenderer());
        }
        log.info("end time:" + (System.currentTimeMillis() - start));
        rowKey = model.getRowKey();
        //set the first rowkey
        txtFieldRowKey.setText(rowKey);
        
        //get all Row keys.
        //allRowKey = model.getRowKey();
        if(skip == 0){
            
        }
    }
    
    
    

    public EditTableDataModel getTableModel() {
        return (EditTableDataModel)tableData.getModel();
    }


    private JTable autoResizeColWidth(JTable table, EditTableDataModel model) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(model);

        int margin = 5;

        for (int i = 0; i < table.getColumnCount(); i++) {
            int                     vColIndex = i;
            DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
            TableColumn             col       = colModel.getColumn(vColIndex);
            int                     width     = 0;

            // Get width of column header
            TableCellRenderer renderer = col.getHeaderRenderer();

            if (renderer == null) {
                renderer = table.getTableHeader().getDefaultRenderer();
            }

            Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

            width = comp.getPreferredSize().width;

            // Get maximum width of column data
            for (int r = 0; r < table.getRowCount(); r++) {
                renderer = table.getCellRenderer(r, vColIndex);
                comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                        r, vColIndex);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            // Add margin
            width += 2 * margin;

            // Set the width
            col.setPreferredWidth(width);
        }

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
            SwingConstants.LEFT);

        // table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        return table;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        txtFieldRowKey = new javax.swing.JTextField();
        btnGo = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        btnAddColumn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnAddRow = new javax.swing.JButton();
        btnRemoveRow = new javax.swing.JButton();
        checkBoxFilter = new javax.swing.JCheckBox();
        btnFilter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListRow = new javax.swing.JList();

        setName("Form"); // NOI18N

        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.hbaseexplorer.HBaseExplorerApp.class).getContext().getActionMap(EditTableData.class, this);
        btnFirst.setAction(actionMap.get("fristBtnClickAction")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.hbaseexplorer.HBaseExplorerApp.class).getContext().getResourceMap(EditTableData.class);
        btnFirst.setText(resourceMap.getString("btnFirst.text")); // NOI18N
        btnFirst.setFocusable(false);
        btnFirst.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFirst.setName("btnFirst"); // NOI18N
        btnFirst.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnFirst);

        btnNext.setAction(actionMap.get("nextBtnClickAction")); // NOI18N
        btnNext.setText(resourceMap.getString("btnNext.text")); // NOI18N
        btnNext.setFocusable(false);
        btnNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNext.setName("btnNext"); // NOI18N
        btnNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnNext);

        txtFieldRowKey.setFont(resourceMap.getFont("txtFieldRowKey.font")); // NOI18N
        txtFieldRowKey.setText(resourceMap.getString("txtFieldRowKey.text")); // NOI18N
        txtFieldRowKey.setName("txtFieldRowKey"); // NOI18N
        jToolBar1.add(txtFieldRowKey);

        btnGo.setAction(actionMap.get("btnGoClickAction")); // NOI18N
        btnGo.setText(resourceMap.getString("btnGo.text")); // NOI18N
        btnGo.setFocusable(false);
        btnGo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGo.setName("btnGo"); // NOI18N
        btnGo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnGo);

        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        jButton1.setAction(actionMap.get("btnSaveClickAction")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton1);

        btnAddColumn.setAction(actionMap.get("btnAddColumnClickAction")); // NOI18N
        btnAddColumn.setText(resourceMap.getString("btnAddColumn.text")); // NOI18N
        btnAddColumn.setFocusable(false);
        btnAddColumn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddColumn.setName("btnAddColumn"); // NOI18N
        btnAddColumn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(btnAddColumn);

        jButton2.setAction(actionMap.get("btnDeleteColumnActionClick")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton2);

        btnAddRow.setText(resourceMap.getString("btnAddRow.text")); // NOI18N
        btnAddRow.setEnabled(false);
        btnAddRow.setFocusable(false);
        btnAddRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddRow.setName("btnAddRow"); // NOI18N
        btnAddRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(btnAddRow);

        btnRemoveRow.setText(resourceMap.getString("btnRemoveRow.text")); // NOI18N
        btnRemoveRow.setEnabled(false);
        btnRemoveRow.setFocusable(false);
        btnRemoveRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveRow.setName("btnRemoveRow"); // NOI18N
        btnRemoveRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(btnRemoveRow);

        checkBoxFilter.setAction(actionMap.get("checkBoxFilter")); // NOI18N
        checkBoxFilter.setText(resourceMap.getString("checkBoxFilter.text")); // NOI18N
        checkBoxFilter.setFocusable(false);
        checkBoxFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        checkBoxFilter.setName("checkBoxFilter"); // NOI18N
        checkBoxFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(checkBoxFilter);

        btnFilter.setAction(actionMap.get("btnFilterClickAction")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setFocusable(false);
        btnFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(btnFilter);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar2.add(jLabel1);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableData.setFont(resourceMap.getFont("tableData.font")); // NOI18N
        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableData.setName("tableData"); // NOI18N
        jScrollPane1.setViewportView(tableData);

        jSplitPane1.setRightComponent(jScrollPane1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jListRow.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListRow.setName("jListRow"); // NOI18N
        jScrollPane2.setViewportView(jListRow);

        jSplitPane1.setLeftComponent(jScrollPane2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .add(jToolBar2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void fristBtnClickAction() {
        rowKey = null;
        showData(0);
    }

    @Action
    public void nextBtnClickAction() {
        showData(1);
    }

    @Action
    public void btnSaveClickAction() {
        RowData changedData = getTableModel().getData().getChangedData();
        if (changedData.size() > 0) {
            try {
                Put put = changedData.convertToPut();
                table.getHTable().put(put);
                showData(0);
            } catch (IOException ex) {
                throw new ExplorerException("Error saving to table " + table.getFullName());
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Nothing to save.");
        }
    }

    @Action
    public void btnAddColumnClickAction() {
        JFrame mainFrame = HBaseExplorerApp.getApplication().getMainFrame();
        NewColumnDialog dialog = new NewColumnDialog(mainFrame, table);
        dialog.setLocationRelativeTo(mainFrame);
        HBaseExplorerApp.getApplication().show(dialog);

        HBTriplet triplet = dialog.getTriplet();
        if (triplet != null) {
            try {
                Put put = new Put(rowKey.getBytes());
                put.add(triplet.getFamily(), triplet.getQualifier(), triplet.getValue());
                table.getHTable().put(put);
                showData(0);
            } catch (IOException ex) {
                throw new ExplorerException("Error adding column : " + triplet);
            }
        }
        
    }

    @Action
    public void btnDeleteColumnActionClick() {
        int rowId = tableData.getSelectedRow();
        if (rowId != -1) {
            String family = tableData.getModel().getValueAt(rowId, 0).toString();
            String column = tableData.getModel().getValueAt(rowId, 1).toString();

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you wanna to delete\n" + rowKey +"\n" + family + ":" + column,
                    "Delete column",
                    JOptionPane.YES_NO_OPTION
                   );
            if (result == JOptionPane.YES_OPTION) {
                Delete delete = new Delete(rowKey.getBytes());
                delete.deleteColumns(family.getBytes(), column.getBytes());
                try {
                    table.getHTable().delete(delete);
                } catch (IOException ex) {
                    throw new ExplorerException("Error deleting " + rowKey + "@" + family + ":" + column);
                }
                showData(0);
            }
        }
    }

    @Action
    public void btnGoClickAction() {
        rowKey = txtFieldRowKey.getText();
        showData(0);
    }
    
    public void jListItemClickAction() {
        
    }

    @Action
    public void checkBoxFilter() {
        btnFilter.setEnabled(checkBoxFilter.isSelected());
        filterModel.setEnabled(checkBoxFilter.isSelected());
        showData(0);
    }

    @Action
    public void btnFilterClickAction() {
        JFrame mainFrame = HBaseExplorerApp.getApplication().getMainFrame();
        FilterDialog dialog = new FilterDialog(mainFrame, table);
        dialog.setFilterModel(filterModel);
        dialog.setLocationRelativeTo(mainFrame);
        HBaseExplorerApp.getApplication().show(dialog);
        filterModel = dialog.getFilterModel();

        showData(0);
    }
    
    
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddColumn;
    private javax.swing.JButton btnAddRow;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnGo;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnRemoveRow;
    private javax.swing.JCheckBox checkBoxFilter;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListRow;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable tableData;
    private javax.swing.JTextField txtFieldRowKey;
    // End of variables declaration//GEN-END:variables

}
