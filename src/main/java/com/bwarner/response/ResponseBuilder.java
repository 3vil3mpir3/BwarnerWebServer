package com.bwarner.response;

import com.bwarner.utils.Utils;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;

public final class ResponseBuilder {

    /** Constants for \r\n and http version number **/
    private final static String CRLF = "\r\n";
    private static final String VERSION = HttpVersion.HTTP_1_1.toString();

    /** Grab output stream, load the headers and send off to output. Accepts string as content **/
    public static void preprocessOutput(OutputStream os, int status, String content) throws IOException{
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        List<String> headers = writeHeaders(status, "");
        writeOutput( dos, content.getBytes(), headers);
    }

    /** Grab output stream, load the headers and send off to output. Accepts file as content **/
    public static void preprocessOutput(OutputStream os, int status, File file) throws IOException{
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        String type =  Utils.getFileExtension(file);
        List<String> headers = writeHeaders(status, type);
        byte[] output = Utils.getFileBytes(file);
        writeOutput( dos, output, headers);
    }

    /** writes header array, returns to processOutput **/
    private static List<String> writeHeaders(int status, String contenttype){
        List<String> headers = new ArrayList<String>();
        headers.add(VERSION + " " + status + " " + HttpStatus.getStatusText(status)+CRLF);
        headers.add("Server: Bwarner"+ CRLF);
        headers.add(contenttype+ CRLF);
        return headers;
    }

    /** Write formed output for headers and requestor **/
    public static void writeOutput(DataOutputStream dos, byte[] output, List<String> headers) throws IOException{
        for (String header : headers) {
            dos.writeBytes(header);
        }

        dos.writeBytes(CRLF);

        if (output != null) {
            dos.write(output);
        }

        dos.flush();
        dos.close();
    }

}
