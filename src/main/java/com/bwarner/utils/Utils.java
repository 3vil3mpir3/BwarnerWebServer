package com.bwarner.utils;

import com.bwarner.enums.Types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public final class Utils {

    /** String var for the bufferedreader first readline. hacky solution to resetting. **/
    public static String[] thisRead;

    /** 404 message. **/
    public static String getErrorMessage(String page){
        return "<!DOCTYPE html><html><head><title>Page not found</title></head><body><h1>" + page + " was not found.</h1></body></html>";
    }

    /** returns requested file based on path **/
    public static File getFile(BufferedReader reader, String dir) throws IOException{
        if(thisRead==null){
            setRequestString(reader);
        }
        return new File(dir+thisRead[1]);
    }

    /** returns file bytes **/
    public static byte[] getFileBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream inStream = new FileInputStream(file);
        int offset = 0;
        
        while (offset < length) {
            int count = inStream.read(array, offset, (length - offset));
            offset += count;
        }

        inStream.close();
        return array;
    }

    /** Returns file extension of request. Used to determine content type. **/
    public static String getFileExtension(final File file)
    {
        String extension = file.getName();
        extension= extension.substring(extension.lastIndexOf(".")+1,extension.length()).toUpperCase();
        return Types.valueOf(extension).toString();
    }

    /** returns requested file based on path **/
    public static String getMethod(BufferedReader reader) throws IOException{
        if(thisRead==null){
            setRequestString(reader);
        }
        return thisRead[0];
    }

    /** End of hacky buffered reader hacky solution. blah. **/
    public static void resetReader(){
        thisRead=null;
    }

    /** extracts and returns requested file name **/
    private static void setRequestString (BufferedReader reader) throws IOException{
        thisRead = reader.readLine().split("\\s+");
    }

}
