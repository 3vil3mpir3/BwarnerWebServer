package com.bwarner;

import com.bwarner.request.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebServer extends Thread {

			/** logger **/
			private static Logger bwlog = LogManager.getLogger(WebServer.class);

			/** set defaults **/
			private static final int DEFAULT_PORT = 8080;
			private static final int DEFAULT_THREADS = 5;

			/** set helper text, get params **/
			public static void main(String args[]) {

				if (args.length == 0 || args[0].equals("-help")) {
					System.out.println("Valid usage: java -jar bwarner-Webserver-0.1-jar-with-dependencies.jar <port> 1025-65535 <threads> 1-10");
				}else{
					bwlog.info("Server starting...");
					getParameters(args);
				}

			}

			/** get params, set param limits and return defaults if limits exceeded. I've limited the ports to a specific range and threads to <10 **/
			private static void getParameters(String args[]){
				int portNumber = DEFAULT_PORT;
				int threads = DEFAULT_THREADS;

				if(Integer.parseInt(args[0]) > 1024 && Integer.parseInt(args[0]) < 65535) {
					portNumber = Integer.parseInt(args[0]);
				}else{
					System.out.println("Port number invalid. Defaulting to " + DEFAULT_PORT);
				}

				if(args[1]!=null && Integer.parseInt(args[1])<=10) {
					threads = Integer.parseInt(args[1]);
				}else{
					System.out.println("Upper thread bounds for this exercise is 10. Defaulting to " + DEFAULT_THREADS);
				}

				try {
					new WebServer().startServer(portNumber,threads);
				} catch (Exception e) {
					bwlog.error("Startup Error", e);
				}
			}

			/** start server with valid arguments for port number and fixes the thread pool **/
			private void startServer(int portNumber, int threads) throws IOException {
				ServerSocket socket = new ServerSocket(portNumber);
				System.out.println("Web server working on port " + portNumber + " with " + threads +" threads.");
				ExecutorService executor = Executors.newFixedThreadPool(threads);

				do{
					try{
						long threadId = Thread.currentThread().getId();
						executor.submit(new RequestHandler(socket.accept()));
					} catch (IOException e){
						bwlog.error("Executor Error", e);
					}
				} while(true);

			}
}
