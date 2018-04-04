package com.bwarner.response;

import com.bwarner.utils.Utils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public final class ResponseBuilder {

    private final static String CRLF = "\r\n";
    private static final String VERSION = HttpVersion.HTTP_1_1.toString();
    private static List<String> headers = new ArrayList<String>();

    private static List<String> writeHeaders(int status, String contenttype){
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
        DataOutputStream dos = new DataOutputStream(os);
        writeHeaders(status, Utils.getMimeType(file));
        byte[] output = respond(Utils.getFileBytes(file));

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
