package com.bwarner;

import java.io.*;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//import org.apache.http.HttpStatus;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.http.entity.ContentType;
import com.google.common.net.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.Version;


/**  **/

    public class ConnectionHandler implements Runnable{
    /** logger **/
    private static Logger bwlog = LogManager.getLogger(ConnectionHandler.class);
    
    private Socket assignedsocket;

    private final static String CRLF = "\r\n";
    private final static String ROOT = "../www/";

    private static final String VERSION = HttpVersion.HTTP_1_1.toString();
    private List<String> headers = new ArrayList<String>();
    private byte[] output;


    private ConnectionHandler(Socket socket) {
        this.assignedsocket = socket;
    }

    public void run(){
        try {
            processRequest();
            assignedsocket.close();
        } catch (Exception e) {
            bwlog.error("Runtime Error", e);
        }
    }

    private void processRequest() throws IOException{
        InputStream inputstream = assignedsocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
        String requeststring = reader.readLine();

        StringTokenizer tokens = new StringTokenizer(requeststring);
        tokens.nextToken();
        String fileName = tokens.nextToken();

        File file = new File(ROOT+fileName);
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }
        writeHeaders();
        respond(getBytes(file));

        processOutput(assignedsocket.getOutputStream());
    }

    private void respond(byte[] response) {
        output = response;
    }

    private void writeHeaders(){
        headers.add(VERSION + " " + HttpStatus.SC_OK + " " + HttpStatus.getStatusText(HttpStatus.SC_OK)+CRLF);
        headers.add("Server: bwarner test server"+ CRLF);
        headers.add("Content-Type: " +ContentType.TEXT_HTML.toString()+ CRLF);
    }

    private void getStatus(){

    }


    private void processOutput(OutputStream os) throws IOException{
        DataOutputStream dos = new DataOutputStream(os);
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

    private byte[] getBytes(File file) throws IOException {
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

    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception
    {
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while((bytes = fis.read(buffer)) != -1 )
        {
            os.write(buffer, 0, bytes);
        }
    }

     /*
    public void close() {
        try {
            theServer.close();
            executor.shutdown();
        } catch (IOException e) {
            System.err.println("Error when shutdown: " + e.getMessage());
        }
    }     */

}