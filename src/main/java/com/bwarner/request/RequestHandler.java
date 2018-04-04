package com.bwarner.request;

import java.io.*;
import java.net.Socket;

import org.apache.commons.httpclient.HttpStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bwarner.utils.Utils;
import com.bwarner.response.ResponseBuilder;

public class RequestHandler implements Runnable{

    private static Logger bwlog = LogManager.getLogger(RequestHandler.class);

    private Socket assignedsocket;

    private final static String ROOT = "../www/";
    private final static File FOUR_OH_FOUR = new File (ROOT+"404.html");

    public RequestHandler(Socket socket) {
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
        InputStream istream = assignedsocket.getInputStream();
        OutputStream ostream = assignedsocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
        File requestedfile = Utils.getFile(reader, ROOT);
        if(requestedfile.exists()){
            bwlog.info("Serving request for " + requestedfile.getName() + " from " + ROOT);
            ResponseBuilder.processOutput(ostream, HttpStatus.SC_OK, requestedfile);
        }else{
            ResponseBuilder.processOutput(ostream, HttpStatus.SC_NOT_FOUND, FOUR_OH_FOUR);
            bwlog.info("Request for " + requestedfile.getName() + " from " + ROOT + " failed.  Serving 404 page.");
        }

    }
}