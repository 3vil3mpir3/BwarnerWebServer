package com.bwarner.response;

import com.bwarner.utils.Utils;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ResponseBuilder {

    /** Logger **/
    private static Logger bwlog = LogManager.getLogger(ResponseBuilder.class);

    /** Constants for \r\n and http version number **/
    private final static String CRLF = "\r\n";
    private static final String VERSION = HttpVersion.HTTP_1_1.toString();

    /** writes header and output to requestor **/
    public static void processOutput(OutputStream os, int status, File file) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        String type =  Utils.getFileExtension(file);
        List<String> headers = writeHeaders(status, type);
        byte[] output = Utils.getFileBytes(file);

        for (String header : headers) {
            dos.writeBytes(header);
        }

        dos.writeBytes(CRLF);
        
        if (output != null) {
            bwlog.info("Serving request for " + file.getName() + " with type " + type + "and headers: "+headers);
            dos.write(output);
        }

        dos.flush();
        dos.close();
    }

    /** writes header array, returns to processOutput **/
    private static List<String> writeHeaders(int status, String contenttype){
        List<String> headers = new ArrayList<String>();
        headers.add(VERSION + " " + status + " " + HttpStatus.getStatusText(status)+CRLF);
        headers.add("Server: bwarner test server"+ CRLF);
        headers.add(contenttype+ CRLF);
        return headers;
    }

}
