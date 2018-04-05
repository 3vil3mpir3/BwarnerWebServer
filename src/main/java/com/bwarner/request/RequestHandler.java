package com.bwarner.request;

import com.bwarner.response.ResponseBuilder;
import com.bwarner.utils.Utils;

import java.io.*;
import java.net.Socket;

import org.apache.commons.httpclient.HttpStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHandler implements Runnable{

    /** Logger **/
    private static Logger bwlog = LogManager.getLogger(RequestHandler.class);

    /** Socket **/
    private Socket assignedSocket;

    /** Constants for web root and 404 location page **/
    private final static String ROOT = "../www/";
    private final static File FOUR_OH_FOUR = new File (ROOT+"404.html");

    public RequestHandler(Socket socket) {
        this.assignedSocket = socket;
    }

    /** Get input and output streams from socket, send contents to processOutput **/
    private void processRequest() throws IOException{
        InputStream istream = assignedSocket.getInputStream();
        OutputStream ostream = assignedSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
        File requestedFile = Utils.getFile(reader, ROOT);

        if(requestedFile.exists()){
            ResponseBuilder.processOutput(ostream, HttpStatus.SC_OK, requestedFile);
        }else{
            ResponseBuilder.processOutput(ostream, HttpStatus.SC_NOT_FOUND, FOUR_OH_FOUR);
            bwlog.info("Request for " + requestedFile.getName() + " from " + ROOT + " failed.  Serving 404 page.");
        }

    }

    /** Run **/
    public void run(){
        
        try {
            processRequest();
            assignedSocket.close();
        } catch (Exception e) {
            bwlog.error("Runtime Error", e);
        }

    }

}