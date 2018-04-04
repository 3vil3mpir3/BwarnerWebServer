package com.bwarner.utils;

import com.bwarner.enums.Types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Utils {

    private static Logger bwlog = LogManager.getLogger(Utils.class);

    public static File getFile(BufferedReader reader, String dir) throws IOException{
        return new File(dir+getFilename(reader));
    }

    private static String getFilename (BufferedReader reader) throws IOException{
        String requeststring = reader.readLine();
        StringTokenizer tokens = new StringTokenizer(requeststring);
        tokens.nextToken();
        return tokens.nextToken();
    }

    public static String getMimeType(final File file)
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
