/*
 * HBaseExplorerApp.java
 */

package org.hbaseexplorer;

import org.apache.log4j.PropertyConfigurator;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class HBaseExplorerApp extends SingleFrameApplication {
    
    private static final String LOG4J_CONF_FILE = "./conf/log4j.properties";

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new HBaseExplorerView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
        //Set maxable of the window
        
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of HBaseExplorerApp
     */
    public static HBaseExplorerApp getApplication() {
        return Application.getInstance(HBaseExplorerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(HBaseExplorerApp.class, args);
        //set log info
        PropertyConfigurator.configure(LOG4J_CONF_FILE);
    }
}
