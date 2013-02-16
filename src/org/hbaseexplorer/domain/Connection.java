package org.hbaseexplorer.domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.buddy.javatools.Utils;
import org.hbaseexplorer.exception.ExplorerException;

/**
 *
 * @author zaharije
 */
public class Connection implements Serializable {
    private Configuration hbaseConfiguration;

    private HBaseAdmin hbaseAdmin;

    private ArrayList<Table> tableList;

    public Connection(Configuration configuration) {
        hbaseConfiguration = HBaseConfiguration.create(configuration);
        String resource = "./conf/hbase.xml";
        hbaseConfiguration.addResource(new Path(resource));
        tableList = new ArrayList<Table>();
    }

    public void connect() throws ZooKeeperConnectionException {
        try {
            //
            hbaseAdmin = new HBaseAdmin(hbaseConfiguration);
            /*
            HBaseConfiguration config = new HBaseConfiguration();
            config.clear();
                config.set("hbase.zookeeper.quorum", "192.168.56.56");
                config.set("hbase.zookeeper.property.clientPort","2181");
                config.set("hbase.master", "192.168.56.56:60000");
                //HBaseConfiguration config = HBaseConfiguration.create();
    //config.set("hbase.zookeeper.quorum", "localhost");  // Here we are running zookeeper locally
                HBaseAdmin.checkHBaseAvailable(config);
             * 
             */
            refreshTables();
        }
        catch(MasterNotRunningException me) {
            throw new ExplorerException("Cannot connect to cluster");
        }
    }

    public void refreshTables() {
        try {
            tableList = new ArrayList<Table>();
            HTableDescriptor hTables[] = hbaseAdmin.listTables();
            
            Log log = Utils.getLog();
            log.info("*****table");
            log.info(hTables.length);
            
            for(HTableDescriptor tableDescriptor : hTables) {
                tableList.add(new Table(tableDescriptor, this));
            }
        }
        catch(IOException ioe) {
            throw new ExplorerException("Error while getting table list!");
        }
    }

    public String getName() {
        return hbaseConfiguration.get("hbase.zookeeper.quorum");
    }
    
    public ArrayList<Table> getTableList() {
        return tableList;
    }

    public Configuration getHbaseConfiguration() {
        return hbaseConfiguration;
    }

    public HBaseAdmin getHbaseAdmin() {
        return hbaseAdmin;
    }

    public String toString() {
        return getName();
    }
}
