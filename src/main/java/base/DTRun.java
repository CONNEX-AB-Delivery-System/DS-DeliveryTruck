package base;

import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
//TODO: Do we need those imports?

//import static base.DeliveryTruck.motorSteer;
//import static base.DeliveryTruck.motorDrive;
//TODO: Do we need those imports?


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

                System.out.println("Current value" + DeliveryTruck.lineReader.getPIDValue());

                System.out.println( "DTRun cmd " +  DeliveryTruck.inputCommandSCS );
                Thread.sleep(500);

                System.out.println("Rotate motor " +  this.threadName );
                DeliveryTruck.motorSteer.rotateTo(15, true);
                Thread.sleep(500);
                System.out.println("Rotate motor 2 " +  this.threadName );
                DeliveryTruck.motorSteer.rotateTo(-20, true);
                Thread.sleep(100);

                /*DeliveryTruck.motorSteer.brake();
                System.out.println("Steer right " +  this.threadName );
                DeliveryTruck.motorSteer.setSpeed(100);
                DeliveryTruck.motorSteer.forward();
                Thread.sleep(200);
                DeliveryTruck.motorSteer.stop();

                System.out.println("Steer left " +  this.threadName );
                DeliveryTruck.motorSteer.setSpeed(100);
                DeliveryTruck.motorSteer.backward();
                Thread.sleep(200);
                DeliveryTruck.motorSteer.stop();

                System.out.println("Drive motor " +  this.threadName );
                DeliveryTruck.motorDrive.setSpeed(300);
                DeliveryTruck.motorDrive.backward();
                Thread.sleep(500);
                DeliveryTruck.motorDrive.stop();*/

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