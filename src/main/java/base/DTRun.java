package base;

import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

//import static base.DeliveryTruck.motorSteer;
//import static base.DeliveryTruck.motorDrive;

/**
 *  Title: DTRun thread
 *
 *  This is thread where all truck logic for task execution should be implemented.
 *  Use function method to do that (it can be extended with other functions).
 */

class DTRun extends Thread {
    private Thread t;
    private String threadName;


    DTRun ( String threadName) {
        this.threadName = threadName;
        System.out.println("Creating " +  this.threadName );
    }

    private boolean runMotors() {

        try {

            while (DeliveryTruck.isRunning && !DeliveryTruck.runThreadIsExecuted) {

                //DeliveryTruck.isRunning should be checked as often as possible
                //to allow stop from SCS

                //TODO: YOUR CODE HERE
                //TODO: CHECK THIS DOCUMENTATION TO UNDERSTAND HOW TO RUN THIS TRUCK
                //TODO: AND HOW TO WRITE CODE:
                //https://github.com/CONNEX-AB-Delivery-System/DS-DeliveryTruck/blob/master/README.md

                //System.out.println("LineReader value" + DeliveryTruck.lineReader.getPIDValue());

                //System.out.println("Rotate motor " +  this.threadName );
                DeliveryTruck.motorDrive.setSpeed(300);
                DeliveryTruck.motorDrive.forward();

                Thread.sleep(2000);

                DeliveryTruck.motorDrive.stop(true);


                DeliveryTruck.runThreadIsExecuted = true;
                DeliveryTruck.outputCommandSCS = "FINISHED";
                System.out.println("Task executed");

            }

        } catch (InterruptedException e) {
            System.out.println("Thread " +  this.threadName + " interrupted.");
        }

        return true;
    }

    public void run() {
        System.out.println("Running " +  this.threadName );

        this.runMotors();

        System.out.println("Thread " +  this.threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  this.threadName );
        if (t == null) {
            t = new Thread (this, this.threadName);
            t.start ();
        }
    }
}