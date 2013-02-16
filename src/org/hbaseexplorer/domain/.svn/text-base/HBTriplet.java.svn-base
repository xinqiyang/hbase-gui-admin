package org.hbaseexplorer.domain;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zaharije
 */
public class HBTriplet {
    private byte[] family;
    private byte[] qualifier;
    private byte[] value;
    private boolean isChanged;

    public HBTriplet() {
    }

    public HBTriplet(byte[] family, byte[] qualifier, byte[] value) {
        this.qualifier = qualifier;
        this.value = value;
        this.family = family;
        this.isChanged = false;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public String getQualifierString() throws UnsupportedEncodingException {
        return byte2String(qualifier);
    }

    public void setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
    }

    public byte[] getValue() {
        return value;
    }

    public String getValueString() throws UnsupportedEncodingException {
        return byte2String(value);
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public byte[] getFamily() {
        return family;
    }

    public String getFamilyString() throws UnsupportedEncodingException {
        return byte2String(family);
    }

    public void setFamily(byte[] family) {
        this.family = family;
    }

    public static String byte2String(byte[] data) throws UnsupportedEncodingException {
        return new String(data,"UTF-8");
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    @Override
    public String toString() {
        try {
            return getFamilyString() + ":" + getQualifierString() + "=" + getValueString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HBTriplet.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
