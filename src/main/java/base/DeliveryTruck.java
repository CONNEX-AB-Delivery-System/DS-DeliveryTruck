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

    static boolean isRunning = true;

    //System.out.println("Creating Motor A & B");
    //motor for drive forwards and backwards - connected to motor port A
    static EV3MediumRegulatedMotor motorDrive;
    //motor for steering - connected to motor port B
    static EV3MediumRegulatedMotor motorSteer;
    //motor for crane rotation connected to motor port C
    static EV3LargeRegulatedMotor craneRotation;

    //motor multiplexer connected to sensor port S1
    //motor for crane lifting - multiplexer port M1
    //??
    //motor for grabber - multiplexer port M1
    //??

    //sensor for line reading - connected to sensor port S2
    //private static MindsensorsLineLeader lineReader = new MindsensorsLineLeader(SensorPort.S4);
    //private static BaseSensor lineReader = new BaseSensor(SensorPort.S4, "RAW", "ms-line-leader 0x01");
    //sensor for proximity
    static EV3UltrasonicSensor sensorProximity;


    public static void main(final String[] args) throws IOException {

        //DeliveryTruck.motorDrive = new EV3MediumRegulatedMotor(MotorPort.C);
        DeliveryTruck.motorSteer = new EV3MediumRegulatedMotor(MotorPort.B);
        //DeliveryTruck.craneRotation = new EV3LargeRegulatedMotor(MotorPort.C);
        System.out.println("Motors initialized");

        //DeliveryTruck.sensorProximity = new EV3UltrasonicSensor(SensorPort.S3);
        //DeliveryTruck.sensorProximity.enable();
        LineReaderV2 lineReader = new LineReaderV2(SensorPort.S1);

        int value = lineReader.getPIDValue();

        System.out.println("Current value" + value);


        System.out.println("Sensors initialized");

        System.out.println("Voltage: " + Battery.getInstance().getVoltage());


        //https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
        ServerSocket serv = new ServerSocket(19231);

        Socket socket = serv.accept();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()));

        String outputValue = socket.getLocalSocketAddress().toString();

        writer.write(outputValue+"\n");writer.flush();

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

        //normal shutdown
        socket.close();

        DeliveryTruck.motorDrive.stop();
        DeliveryTruck.motorSteer.stop();
        DeliveryTruck.sensorProximity.disable();

        System.out.println("Checking Battery before shutdown");
        System.out.println("Voltage: " + Battery.getInstance().getVoltage());

        System.exit(0);

        //final int motorSpeed = 500;
        //DeliveryTruck.motorDrive.setSpeed(motorSpeed);

        //Delay.msDelay(3000);

        //DeliveryTruck.motorDrive.forward();

        //Delay.msDelay(3000);

        //DeliveryTruck.motorDrive.stop();

        /*SampleProvider sp;

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

        //lineReader.sleep();

        //System.out.println(Battery.getInstance().getVoltage());


        //To Stop the motor in case of pkill java for example
       Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
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
        }));

        /*System.out.println("Defining the Stop mode");
        motorLeft.brake();
        motorRight.brake();

        System.out.println("Defining motor speed");
        final int rotateTo = 10;
        final int motorSpeed = 300;
        //motorLeft.rotateTo(rotateTo);
        motorLeft.setSpeed(motorSpeed);
        motorRight.setSpeed(motorSpeed);

        motorLeft.getSpeed()

        if (motorLeft.getSpeed() > 200) then {motorLeft.stop()} else {}


        {
            motorLeft.forward();

        }
        while (sensorX.getValue() > 200)

        System.out.println("Go Forward with the motors");
        motorLeft.forward();
        //motorRight.backward();

        Delay.msDelay(2000);

        System.out.println("Stop motors");
        motorLeft.stop();
        motorRight.stop();

        System.out.println("Go Backward with the motors");
        motorLeft.backward();
        //motorRight.forward();

        Delay.msDelay(2000);

        System.out.println("Stop motors");
        motorLeft.stop();
        motorRight.stop(); */

        //System.out.println("Checking Battery");
        //System.out.println("Voltage: " + Battery.getInstance().getVoltage());
    }
}
