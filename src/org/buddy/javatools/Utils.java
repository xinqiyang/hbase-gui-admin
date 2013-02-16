/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.buddy.javatools;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author xinqiyang
 */
public class Utils {
    
    private static final String UTF_8 = "UTF-8";
    
    public static StackTraceElement getCallerInfo() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        return trace[3];
    }
    
    public static Log getLog() {
        StackTraceElement callerInfo = getCallerInfo();
        String            className  = callerInfo.getClassName();

        return (Log) LogFactory.getLog(className);
    }
    
    public static boolean isPrintableData(byte[] data, long maxPrintableDetectCnt) {
        for (int i = 0; i < data.length; i++) {
            if (i == maxPrintableDetectCnt) {
                break;
            }

            byte b = data[i];

            if (!isPrintableChar((char) b)) {
                return false;
            }
        }

        return true;
    }
    
    private static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
               c != KeyEvent.CHAR_UNDEFINED &&
               block != null &&
               block != Character.UnicodeBlock.SPECIALS;
    }

    // regexp

    public static boolean isMatch(String target, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    
    public static String bytes2str(byte[] bytes, int offset, int length)
    throws UnsupportedEncodingException {
        return new String(bytes, offset, length, UTF_8);
    }

    public static String getHexStringBase(byte[] bytes, int length, boolean show0x) {
        StringBuffer stringBuffer = new StringBuffer();

        length = Math.min(length, bytes.length);

        for (int i = 0; i < length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);

            if (show0x) {
                stringBuffer.append((i == 0 ? "" : " ") + "0x");
            }

            if (hex.length() == 1) {
                stringBuffer.append('0');
            }

            stringBuffer.append(hex);
        }

        if (length < bytes.length) {
            stringBuffer.append(" ...");
        }

        return stringBuffer.toString();
    }
    
    public final static short getShort(byte[] buf, boolean asc, int len) {  
        short r = 0;  
        if (asc)  
          for (int i = len - 1; i >= 0; i--) {  
            r <<= 8;  
            r |= (buf[i] & 0x00ff);  
          }  
        else  
          for (int i = 0; i < len; i++) {  
            r <<= 8;  
            r |= (buf[i] & 0x00ff);  
          }  
          
        return r;  
    }
    
    /* byte[] -> long */  
      public final static long getLong(byte[] buf, boolean asc) {  
        if (buf == null) {  
          throw new IllegalArgumentException("byte array is null!");  
        }  
        if (buf.length > 8) {  
          throw new IllegalArgumentException("byte array size > 8 !");  
        }  
        long r = 0;  
        if (asc)  
          for (int i = buf.length - 1; i >= 0; i--) {  
            r <<= 8;  
            r |= (buf[i] & 0x00000000000000ff);  
          }  
        else  
          for (int i = 0; i < buf.length; i++) {  
            r <<= 8;  
            r |= (buf[i] & 0x00000000000000ff);  
          }  
        return r;  
      }  
}
