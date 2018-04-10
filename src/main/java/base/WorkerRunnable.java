package base;

import java.io.*;
import java.net.Socket;


public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    clientSocket.getOutputStream()));


            //System.out.println(reader.readLine());
            String outputValue = clientSocket.getLocalSocketAddress().toString();

            writer.write(outputValue+"\n");writer.flush();
            /*InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream(); */
            long time = System.currentTimeMillis();
            /*output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
                    this.serverText + " - " +
                    time +
                    "").getBytes());
            output.close();
            input.close(); */


            String line;
            while (DeliveryTruck.isRunning) {
                line = reader.readLine();
                switch (line) {
                    case "LEFT-PRESS":
                        System.out.println("RECIEVED " + line);
                        break;
                    case "STOP":
                        System.out.println("RECIEVED " + line);
                        //outputValue = "shutting down";
                        //writer.write(outputValue+"\n");writer.flush();
                        DeliveryTruck.isRunning = false;
                        break;
                }
            }
            reader.close();
            writer.close();
            System.out.println("Request processed: " + time);

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}