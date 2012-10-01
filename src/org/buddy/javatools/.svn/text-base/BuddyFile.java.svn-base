/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buddy.javatools;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Buddy IO class 
 * @author xinqiyang
 */
public class BuddyFile {

    
    public static void write(String fileName,String content,boolean append) throws IOException
    {
        try {
            File file = new File(fileName);
            FileWriter filewriter = new FileWriter(file,append);
            filewriter.write(content);
            filewriter.close();
        }
        catch(IOException e) {
            
        }
    }
    
    public static String get(String fileName) throws FileNotFoundException, IOException
    {
        String s="";
        String readLine = "";
        File file = new File(fileName);
        if(file.exists()) {
            FileReader fileread = new FileReader(file);
            BufferedReader br = new BufferedReader(fileread);

            while((s = br.readLine()) != null)
            {
                readLine = readLine + s;
            }
        }
        return readLine;
    }
    
    



}
