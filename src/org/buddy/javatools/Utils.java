/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buddy.javatools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author xinqiyang
 */
public class Utils {
    
    public static StackTraceElement getCallerInfo() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        return trace[3];
    }
    
    public static Log getLog() {
        StackTraceElement callerInfo = getCallerInfo();
        String            className  = callerInfo.getClassName();

        return (Log) LogFactory.getLog(className);
    }
}
