package base;

//import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
//import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.Battery;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.SensorPort;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
//import lejos.hardware.sensor.MindsensorsLineLeader;
//import lejos.hardware.port.MotorPort;
import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;


public class DeliveryTruck {

    //System.out.println("Creating Motor A & B");
    //motor for steering
    //final EV3MediumRegulatedMotor motorLeft = new EV3MediumRegulatedMotor(MotorPort.A);
    //motor for driving
    //final EV3MediumRegulatedMotor motorRight = new EV3MediumRegulatedMotor(MotorPort.B);
    //motor for crane rotation
    //motor for crane lifting
    //motor for grabber

    //sensor for line reading
    //sensor for proximity
    private static EV3UltrasonicSensor sensorProxmity = new EV3UltrasonicSensor(SensorPort.S3);
    //private static MindsensorsLineLeader lineReader = new MindsensorsLineLeader(SensorPort.S4);
    private static BaseSensor lineReader = new BaseSensor(SensorPort.S4, "RAW", "ms-line-leader 0x01");

    //Configuration
    private static int HALF_SECOND = 500;

    public static void main(final String[] args){

        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        SampleProvider sp;

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

        }

        //lineReader.sleep();

        //System.out.println(Battery.getInstance().getVoltage());


        //To Stop the motor in case of pkill java for example
       /* Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                motorLeft.stop();
                motorRight.stop();
            }
        }));

        System.out.println("Defining the Stop mode");
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

        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());

        System.exit(0);
    }
}
