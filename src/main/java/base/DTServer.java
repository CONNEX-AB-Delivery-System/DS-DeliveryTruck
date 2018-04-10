package base;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class DTServer extends Thread {
/*
    private Thread t;
    private String threadName;
    ServerSocket serverSocket;
    Socket clientSocket;
    final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);


    DTServer ( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void interrupt() {
        System.out.println("Close server " +  this.serverSocket.isBound() );
        try {
            if (this.serverSocket.isBound()) {
                this.serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Running " +  threadName );


        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(8000);
                    System.out.println("Waiting for connection...");
                    while (DeliveryTruck.isRunning) {
                        clientSocket = serverSocket.accept();
                        //clientProcessingPool.submit(new ClientTask(clientSocket));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();

        System.out.println("Client socket1: " + clientSocket.isConnected());

        while (DeliveryTruck.isRunning) {
            //System.out.println(isRunning);
            try {
                Thread.sleep(1000);
                System.out.println("Server socket: " + serverSocket.isBound());
                System.out.println("Client socket: " + clientSocket.isConnected());
            } catch (InterruptedException x) {

            }
        }
        /*try {
            this.serverSocket = new ServerSocket(8000);

            System.out.println("Waiting for connection...");
            while (DeliveryTruck.isRunning) {
                this.clientSocket = serverSocket.accept();
                //clientProcessingPool.submit(new ClientTask(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Unable to process client request");
            e.printStackTrace();
        }

        System.out.println("Thread " +  this.threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }


    /*private class ClientTask implements Runnable {

        private Socket socket;

        private BufferedReader reader;
        private BufferedWriter writer;

        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Got a connection !");

            //open input/output streams
            try {
                this.reader = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()));

                String outputValue = this.socket.getLocalSocketAddress().toString();
                this.writer.write(outputValue+"\n"); this.writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            // Do whatever required to process the client's request

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } */
}

