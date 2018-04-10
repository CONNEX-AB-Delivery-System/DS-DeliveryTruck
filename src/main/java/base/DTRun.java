package base;

import lejos.hardware.port.MotorPort;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;

import lejos.hardware.port.SensorPort;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;

import lejos.robotics.SampleProvider;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;


class DTRun extends Thread {
    private Thread t;
    private String threadName;



    DTRun ( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        //DeliveryTruck.motorDrive.brake();
        //DeliveryTruck.motorDrive.rotate(720);

        //DeliveryTruck.sensorProximity = new EV3UltrasonicSensor(SensorPort.S3);
        //DeliveryTruck.sensorProximity.enable();

        System.out.println("Current value" + DeliveryTruck.lineReader.getPIDValue());

        //DeliveryTruck.motorSteer.rotate(45);
        //DeliveryTruck.motorSteer.rotate(-45);


        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}