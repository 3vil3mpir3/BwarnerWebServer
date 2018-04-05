package com.bwarner.utils;

import com.bwarner.enums.Types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.StringTokenizer;

public final class Utils {

    /** returns requested file based on path **/
    public static File getFile(BufferedReader reader, String dir) throws IOException{
        return new File(dir+getFilename(reader));
    }

    /** extracts and returns requested file name **/
    private static String getFilename (BufferedReader reader) throws IOException{
        String requestString = reader.readLine();
        StringTokenizer tokens = new StringTokenizer(requestString);
        tokens.nextToken();
        return tokens.nextToken();
    }

    /** Returns file extension of request. Used to determine content type. **/
    public static String getFileExtension(final File file)
    {
        String extension = file.getName();
        extension= extension.substring(extension.lastIndexOf(".")+1,extension.length()).toUpperCase();
        return Types.valueOf(extension).toString();
    }

    public static byte[] getFileBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }

        in.close();
        return array;
    }
}
