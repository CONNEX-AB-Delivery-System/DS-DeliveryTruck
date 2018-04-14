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

//import lejos.hardware.sensor.MindsensorsLineLeader;
import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;




public class DeliveryTruck {

    //Configuration
    private static int HALF_SECOND = 500;

    //TODO: synhronize isRunning variable between threads
    //Synchronization variables between threads to allow intra thread communication
    //Main variable for stopping execution
    static boolean isRunning = true;
    //Variables for commands from/to SCS
    static String inputCommandSCS = "";
    static String outputCommandSCS = "none";
    //Variables for controlling task thread
    static boolean runThreadIsStarted = false;
    static boolean runThreadIsExecuted = false;

    //System.out.println("Creating Motor A & B");
    //motor for drive forwards and backwards - connected to motor port A
    public static EV3LargeRegulatedMotor motorDrive;
    //motor for steering - connected to motor port B
    public static EV3MediumRegulatedMotor motorSteer;
    //motor for crane rotation connected to motor port C
    //private static EV3LargeRegulatedMotor craneRotation;

    //motor multiplexer connected to sensor port S1
    //motor for crane lifting - multiplexer port M1
    //??
    //motor for grabber - multiplexer port M1
    //??
    //TODO: FIX multiplexer controller..

    //sensor for line reading - connected to sensor port TODO: S2
    public static LineReaderV2 lineReader;
    //sensor for proximity - connect to sensor port TODO: X
    public static EV3UltrasonicSensor sensorProximity;
    //sensor for crane rotation movement detection  
    public static EV3TouchSensor touchSensor;


    public static void main(final String[] args) throws IOException {
        // getting reference to Main thread
        Thread t = Thread.currentThread();

        double minVoltage = 7.200;

        //Always check if battery voltage is enougth
        System.out.println("Battery Voltage: " + Battery.getInstance().getVoltage());
        System.out.println("Battery Current: " + Battery.getInstance().getBatteryCurrent());
        if (Battery.getInstance().getVoltage() < minVoltage) {
            System.out.println("Battery voltage to low, shutdown");
            System.exit(0);
        }

        //initalize all motors here
        motorDrive = new EV3LargeRegulatedMotor(MotorPort.C);
        motorSteer = new EV3MediumRegulatedMotor(MotorPort.A);
        System.out.println("Motor initialized");
        //initalize all sensors here
        lineReader = new LineReaderV2(SensorPort.S1);
        sensorProximity = new EV3UltrasonicSensor(SensorPort.S3);
        //DeliveryTruck.sensorProximity.enable();
        System.out.println("Sensors initialized");

        //open thread for socket server to listen/send commands to SCS
        DTThreadPooledServer server = new DTThreadPooledServer("ServerThread-1", 8000);
        new Thread(server).start();

        while (isRunning) {

            //check if have recieved command from SCS and have not executed run thread before
            if (inputCommandSCS.equals("RUN") && (runThreadIsStarted == false)) {
                //open thread for executing task
                //TODO: start only after SCS has send command
                DTRun runThread = new DTRun( "RunThread-1");
                //runThread.setDaemon(false);
                runThreadIsStarted = true;
                runThread.start();
            }

            //wait till run thread is executed
            if (runThreadIsExecuted == false) {
                //TODO: not sure about this
                try {
                    //stop if no connection from SCS after 30 seconds
                    Thread.sleep(1 * 1000);
                    //DeliveryTruck.isRunning = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Stopping Server");
        server.stop();

        //motorSteer.close();
        //motorDrive.close();

        System.exit(0);


        /*

        String line;
        while (isRunning) {
            line = reader.readLine();
            System.out.println("RECIEVED " + line);

            switch (line) {
                case "LEFT-PRESS":
                    System.out.println("RECIEVED LEFT-PRESS " + line);
                    motorSteer.rotate(180, true);
                    //motorSteer.setAcceleration(50);
                    //motorSteer.backward();
                    break;
                case "LEFT-RELEASE":
                    motorSteer.stop(true);
                    break;
                case "RIGHT-PRESS":
                    motorSteer.rotate(-180, true);
                    //motorSteer.setSpeed(200);
                    //motorSteer.setAcceleration(50);
                    //motorSteer.forward();
                    break;
                case "RIGHT-RELEASE":
                    motorSteer.stop(true);
                    break;
                case "STOP":
                    isRunning = false;
                    outputValue = "shutting down";
                    writer.write(outputValue+"\n");writer.flush();
                    break;
            }
        }



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
       /*Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                DeliveryTruck.motorDrive.stop();
                DeliveryTruck.motorSteer.stop();
                DeliveryTruck.sensorProximity.disable();
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })); */

    }
}
