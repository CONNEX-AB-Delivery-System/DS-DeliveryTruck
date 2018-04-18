package base;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import ev3dev.sensors.Battery;
import lejos.hardware.port.MotorPort;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;

import lejos.hardware.port.SensorPort;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import ev3dev.sensors.ev3.EV3TouchSensor;

import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * Title: DeliveryTruck
 *
 * Main class for Delivery truck. Does all "dirty" stuff of initializing EV3 Motors and Sensors.
 * Also starts serverSocket thread and respective Socket connections from SCS.
 * After command "run" from SCS starts DTRun thread execution.
 *
 * NOTE: Nothing should be changed in this class.
 */



public class DeliveryTruck {

    //Configuration
    private static int HALF_SECOND = 500;

    //Synchronization variables between threads to allow intra thread communication
    //Main variable for stopping execution
    static boolean isRunning = true;
    //Variables for commands from/to SCS
    static String inputCommandSCS = "";
    static String outputCommandSCS = "none";
    //Variables for controlling task thread
    static boolean runThreadIsStarted = false;
    static boolean runThreadIsExecuted = false;

    //motor for drive forwards and backwards - connected to motor port D
    public static EV3MediumRegulatedMotor motorDrive;
    //motor for steering - connected to motor port C
    public static EV3MediumRegulatedMotor motorSteer;

    //motor for crane lifting - connected multiplexer port M1
    private static EV3LargeRegulatedMotor craneRotation;
    //motor for crane lifting - connected to motor port B
    public static EV3MediumRegulatedMotor craneLift;
    //motor for grabber - connected to motor port A
    public static EV3MediumRegulatedMotor craneGrabber;

    //sensor for proximity - connect to sensor port S1
    public static EV3UltrasonicSensor sensorProximity;
    //sensor for line reading - connected to sensor port S3
    public static LineReaderV2 lineReader;
    //sensor for crane rotation movement detection S4
    public static EV3TouchSensor touchSensor;


    public static void main(final String[] args) {
        DTRun runThread;

        double minVoltage = 7.200;

        //Always check if battery voltage is enougth
        System.out.println("Battery Voltage: " + Battery.getInstance().getVoltage());
        System.out.println("Battery Current: " + Battery.getInstance().getBatteryCurrent());
        if (Battery.getInstance().getVoltage() < minVoltage) {
            System.out.println("Battery voltage to low, shutdown");
            System.out.println("Please change the batteries");
            System.exit(0);
        }

        //initialize all motors here
        motorDrive = new EV3MediumRegulatedMotor(MotorPort.B);
        motorSteer = new EV3MediumRegulatedMotor(MotorPort.C);
        //TODO: Uncomment if you are using this motor
        //craneLift = new EV3MediumRegulatedMotor(MotorPort.B);
        //TODO: Uncomment if you are using this motor
        //craneGrabber = new EV3MediumRegulatedMotor(MotorPort.A);
        System.out.println("Motor initialized");
        //initialize all sensors here
        lineReader = new LineReaderV2(SensorPort.S3);
        //sensorProximity = new EV3UltrasonicSensor(SensorPort.S1);
        //TODO: Uncomment if you are using this sensor
        //touchSensor = new EV3TouchSensor(SensorPort.S4);
        //DeliveryTruck.sensorProximity.enable();
        System.out.println("Sensors initialized");

        //open thread for executing "run" task
        runThread = new DTRun( "RunThread-1");
        //add "run" task and "run executed" flags
        runThreadIsExecuted = false;
        runThreadIsStarted = true;
        runThread.start();

        //wait for some time till run thread is executed
        while (isRunning) {
            if (!runThreadIsExecuted) {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                inputCommandSCS = "";
                runThreadIsStarted = false;
                isRunning = false;
            }
        }

        System.exit(0);

        //open thread for socket server to listen/send commands to SCS
        //DTThreadPooledServer server = new DTThreadPooledServer("ServerThread-1", 8000);
        //server.start();

        /*while (isRunning) {
            //first, check if have received "kill" command from SCS
            if (inputCommandSCS.equals("KILL")) {
                //then stop everything
                //isRunning = false;
            }

            //check if have received "run" command from SCS and have not executed run thread before
            if (inputCommandSCS.equals("RUN") && (!runThreadIsStarted)) {
                //open thread for executing "run" task
                runThread = new DTRun( "RunThread-1");
                //add "run" task and "run executed" flags
                runThreadIsExecuted = false;
                runThreadIsStarted = true;
                runThread.start();
            }

            //wait for some time till run thread is executed
            if (!runThreadIsExecuted) {
                try {
                    Thread.sleep(10 * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                inputCommandSCS = "";
                runThreadIsStarted = false;
            }

            if (DeliveryTruck.outputCommandSCS.equals("FINISHED")) {

                System.out.println("main-FINISHED");
                server.isRunning();

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //Stop server to release socket bind
        System.out.println("Stopping Server");
        server.stopServerSocket(); */

        //System.exit(0);


        /*
        int distanceValue = 0;

        String name;
        //lineReader.wakeUp();
        name = lineReader.getName();
        System.out.println("Sensor name: " + name);

        for(int i = 0; i <= 20; i++) {

            sp = sensorProxmity.getListenMode();
            int sampleSize = sp.sampleSize();
            float[] sample = new float[sampleSize];
            sp.fetchSample(sample, 0);

            Delay.msDelay(1000);

            sp = sensorProxmity.getDistanceMode();
            sampleSize = sp.sampleSize();
            sample = new float[sampleSize];
            sp.fetchSample(sample, 0);

            distanceValue = (int)sample[0];

            System.out.println("Iteration: {}, Distance: {}" + i + " "+ distanceValue);

            //sp2 = lineReader.getRedMode();


            //System.out.println("" + sp2);

        } */


        //TODO:To Stop the motor in case of pkill java for example
       Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                DeliveryTruck.motorDrive.stop();
                DeliveryTruck.motorSteer.stop();

            }
        }));

    }
}
