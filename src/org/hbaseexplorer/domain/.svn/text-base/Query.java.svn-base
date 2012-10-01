/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hbaseexplorer.domain;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 *
 * @author xinqiyang
 */
public class Query {

    private HBaseAdmin hbaseAdmin;
    private String queryS;

    public Query(Configuration conf, String queryStr) throws MasterNotRunningException {

        Connection con = new Connection(conf);
        this.hbaseAdmin = con.getHbaseAdmin();
        this.queryS = queryStr;
    }


    public String runQuery() throws IOException {
        
        String keys[] = new String[4];
        keys[0] = "create";
        keys[1] = "disable";
        keys[2] = "enable";
        keys[3] = "drop";
        
        String valRet = "";
        String head = "";
        
        String query =this.queryS;
        query = query.replaceAll("\\s{1,}", " ");
        if (query != null && query.length() > 0) {
            List<String> list = Arrays.asList(query.split(" "));
            if (!list.isEmpty()) {
                head = list.get(0);
                head = head.toLowerCase();
                String table = list.get(1);
                if(table != null && !table.isEmpty()){
                    for (int i = 0; i < 4; i++) {
                        if (head.equals(keys[i])) {
                            //run logic then return
                            if(i==0){
                                //run create
                                
                                
                            }

                            if(i==1){
                                this.hbaseAdmin.disableTable(table);
                                valRet = table + "  disable table ok";
                            }

                            if(i==2){
                                this.hbaseAdmin.enableTable(table);
                                valRet = "enable table ok";
                            }

                            if(i==3){
                                this.hbaseAdmin.disableTable(table);
                                this.hbaseAdmin.deleteTable(table); 
                                valRet = "drop table ok";
                            }

                            //valRet = head;
                            break;
                        }
                    }
                }
            }
        }
        if (!valRet.isEmpty()) {
            return valRet;
        }
        return "run error,please check!";
    }
}
