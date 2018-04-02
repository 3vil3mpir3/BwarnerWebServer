package com.bwarner;

import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**  **/

public class ConnectionHandler implements Runnable{

    /** logger **/
    private static Logger bwlog = LogManager.getLogger(ConnectionHandler.class);


    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }


    public void run(){
        try {

        } catch (Exception e) {
            bwlog.error("Runtime Error", e);
        }
    }

}
