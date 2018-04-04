package com.bwarner.response;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.bwarner.utils.Utils;

public final class ResponseBuilder {
    private static Logger bwlog = LogManager.getLogger(ResponseBuilder.class);

    private final static String CRLF = "\r\n";
    private static final String VERSION = HttpVersion.HTTP_1_1.toString();

    private static List<String> writeHeaders(int status, String contenttype){
        List<String> headers = new ArrayList<String>();
        headers.add(VERSION + " " + status + " " + HttpStatus.getStatusText(status)+CRLF);
        headers.add("Server: bwarner test server"+ CRLF);
        headers.add("Content-Type: " +contenttype+ CRLF);
        return headers;
    }

    public static byte[] respond(byte[] response) {
        return response;
    }
    private static byte[] respond(String response) {
        return response.getBytes();
    }

    public static void processOutput(OutputStream os, int status, File file) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        String type =  Utils.getMimeType(file);
        List<String> headers = writeHeaders(status, type);
        
        byte[] output = respond(Utils.getFileBytes(file));

        for (String header : headers) {
            dos.writeBytes(header);
        }
        dos.writeBytes(CRLF);
        if (output != null) {
            bwlog.info("Serving request for " + file.getName() + " with type " + type + "and headers: "+headers);
            dos.write(output);
        }
        dos.flush();
    }

}
