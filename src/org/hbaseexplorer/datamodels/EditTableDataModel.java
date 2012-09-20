/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hbaseexplorer.datamodels;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.table.AbstractTableModel;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.hbaseexplorer.domain.FilterModel;
import org.hbaseexplorer.domain.HBTriplet;
import org.hbaseexplorer.domain.RowData;
import org.hbaseexplorer.domain.Table;
import org.hbaseexplorer.exception.ExplorerException;

/**
 *
 * @author zaharije
 */
public class EditTableDataModel extends AbstractTableModel {

    public String rowKey;
    private Table table;
//    private ArrayList<HBTriplet> data;
    private Integer skip;
    private boolean dirty = false;
    private FilterModel filterModel;
    private RowData rowData;

    private int loadCount =0;
    
    public EditTableDataModel(Table table, Integer skip, String rowKey, FilterModel filterModel) {
        this.table = table;
        this.rowData = null;
        this.skip = skip;
        this.rowKey = rowKey;
        this.filterModel = filterModel;
        refreshData(skip);
    }

    private void refreshData(int skip) {
        if (rowData == null) {
            Scan scan;
            if (rowKey == null) {
                scan = new Scan();
            } else {
                scan = new Scan(rowKey.getBytes());
            }

            if (!filterModel.isEmpty()) {
                SingleColumnValueFilter filter = new SingleColumnValueFilter(
                        filterModel.getFamily().getBytes(),
                        filterModel.getColumn().getBytes(),
                        CompareFilter.CompareOp.EQUAL,
                        filterModel.getValue().getBytes());
                filter.setFilterIfMissing(true);
                scan.setFilter(filter);
            }

            try {
                ResultScanner resultScanner = table.getHTable().getScanner(scan);
                while (skip > 0) {
                    resultScanner.next();
                    skip--;
                }

                Result result = resultScanner.next();

                rowData = new RowData();
                if (result != null) {
                    rowData.setRowKey(result.getRow());

                    //get the rowKey
                    rowKey = rowData.getRowKeyString();
                    //get rows data
                    for (KeyValue kv : result.list()) {
                        rowData.add(new HBTriplet(kv.getFamily(), kv.getQualifier(), kv.getValue()));
                    }
                }
            } catch (IOException ioe) {
                throw new ExplorerException("Error getting data from table " + table.getFullName());
            }
        }


    }

    public int getRowCount() {
        refreshData(skip);
        return rowData.size();
    }

    //add by xinqiyang
    //return the rowData of the table
    //it will wast time here , the num max set 1000
    public DefaultListModel getRowData(int num) {
        DefaultListModel alist = new DefaultListModel();

        try {
            //load first,scan class have a limit setting????
            Scan scan = new Scan();
            //if set the stop row,it return null.....
            /*
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(buf);
            out.writeInt(num);
            byte[] stop = buf.toByteArray();
            out.close();
            buf.close();
            scan.setStopRow(stop);
            */
            ResultScanner resultScanner = table.getHTable().getScanner(scan);
            while (num > 0) {
                Result result = resultScanner.next();
                if (result != null) {
                    rowData = new RowData();
                    rowData.setRowKey(result.getRow());
                    alist.addElement(rowData.getRowKeyString());
                    this.loadCount++;
                }
                num--;
            }
            return alist;
        } catch (IOException ex) {
            Logger.getLogger(EditTableDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alist;
    }

    public int getColumnCount() {
        return 3;
    }
    
    public int getRowsTotal()
    {
        return this.loadCount;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        refreshData(skip);
        HBTriplet triplet = rowData.get(rowIndex);
        switch (columnIndex) {
            //case 0: return triplet.getRowKeyString();
            case 0:
                try {
                    return triplet.getFamilyString();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(EditTableDataModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            case 1:
                try {
                    return triplet.getQualifierString();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(EditTableDataModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            case 2:
                try {
                    //code change  
                    return triplet.getValueString();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(EditTableDataModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
        }
        return "<empty>";
    }

    public boolean isChanged(int row) {
        return rowData.get(row).isChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 2) {
            try {
                // check if value is changed
                if (!rowData.get(row).getValueString().equals(((String) value))) {
                    rowData.get(row).setValue(((String) value).getBytes());
                    rowData.get(row).setIsChanged(true);
                    dirty = true;
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EditTableDataModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 2;
    }

    public RowData getData() {
        return rowData;
    }

    public void setData(RowData rowData) {
        this.rowData = rowData;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
