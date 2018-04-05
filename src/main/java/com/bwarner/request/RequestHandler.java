package com.bwarner.request;

import com.bwarner.enums.Methods;
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

    /** Constant for web directory **/
    private final static String ROOT = "../www/";

    public RequestHandler(Socket socket) {
        this.assignedSocket = socket;
    }

    /** Get input and output streams from socket, send contents to processOutput **/
    private void processRequest() throws IOException{
        InputStream istream = assignedSocket.getInputStream();
        OutputStream ostream = assignedSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(istream));

        String[] requestHeader = Utils.getBufferLines(reader);
        File requestedFile = Utils.getFile(requestHeader[1], ROOT);
        Methods method =  Utils.getMethod(requestHeader[0]);

        switch (method){
            case GET:
                if(requestedFile.exists()){
                    bwlog.info("Serving: "+ requestedFile.getName() + " lfrom " + ROOT);
                    ResponseBuilder.preprocessOutput(ostream, HttpStatus.SC_OK, requestedFile);
                }else{
                    bwlog.error("Request for " + requestedFile.getName() + " from " + ROOT + " failed.  Serving 404 page.");
                    ResponseBuilder.preprocessOutput(ostream, HttpStatus.SC_NOT_FOUND, Utils.getErrorMessage(requestedFile.getName()));
                }
                break;
            case HEAD:
                ResponseBuilder.preprocessOutput(ostream, HttpStatus.SC_OK, "");
                break;
            case TRACE:
                ResponseBuilder.preprocessOutput(ostream, HttpStatus.SC_OK, reader.readLine());
                break;
            case UNRECOGNIZED:
                ResponseBuilder.preprocessOutput(ostream, HttpStatus.SC_BAD_REQUEST, Utils.getErrorMessage(requestedFile.getName()));
                break;
            default:
                ResponseBuilder.preprocessOutput(ostream, HttpStatus.SC_NOT_IMPLEMENTED, "");
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