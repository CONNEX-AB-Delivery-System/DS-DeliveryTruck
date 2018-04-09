package base;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class DTServer extends Thread {
    private Thread t;
    private String threadName;

    DTServer ( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Waiting for connection...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                //clientProcessingPool.submit(new ClientTask(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Unable to process client request");
            e.printStackTrace();
        }

    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}