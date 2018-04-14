package base;

import java.io.*;
import java.net.Socket;

/**
 *  Title WorkerRunnable
 *
 *  Implements single socket connection via thread that listens to SCS commands and sends commands to SCS.
 *
 *  NOTE: Nothing should be changed in this class.
 */

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

            String outputValue = clientSocket.getLocalSocketAddress().toString();

            writer.write(outputValue+"\n");writer.flush();

            long time = System.currentTimeMillis();
            /*output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
                    this.serverText + " - " +
                    time +
                    "").getBytes());*/

            String line;
            while (DeliveryTruck.isRunning) {
                line = reader.readLine();
                //System.out.println("RECIEVED " + line);
                switch (line) {
                    case "RUN":
                        DeliveryTruck.inputCommandSCS = line;
                        System.out.println("RECIEVED " + line);
                        break;
                    case "LEFT-PRESS":
                        DeliveryTruck.inputCommandSCS = line;
                        break;
                    case "STOP":
                        DeliveryTruck.inputCommandSCS = line;
                        DeliveryTruck.isRunning = false;
                        break;
                }

                if (!DeliveryTruck.outputCommandSCS.equals("none")) {
                    writer.write(DeliveryTruck.outputCommandSCS+"\n");writer.flush();
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