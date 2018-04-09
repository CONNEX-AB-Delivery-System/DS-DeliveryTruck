# Package Handling System / Delivery Truck project with Gradle

## About this project

This repository stores a template project about `Delivery Truck`. You need to use this project to get started.

## Getting Started

Once you download in your computer the project, open Java IDE [IntelliJ](https://www.jetbrains.com/idea/))
to import this [Gradle](https://gradle.org/) project. The project includes latest dependencies and
an example ready to be deployed on Delivery Truck using the `Delivery Truck` library from `CONNEX-AB-Delivery-System`.

The project includes some tasks to reduce the time to deploy on your robot.

Review the IP of your Brick and update the file `deploy.gradle`:

```
remotes {
    ev3dev {
        host = '192.168.1.180'
        user = 'robot'
        password = 'maker'
    }
}
```

The tasks associated to deploy on your robot are:

- deploy (The project deliver a FatJar to your Brick)
- remoteRun (Ejecute a jar deployed on your Brick)
- deployAndRun (Deploy & Execute from your Computer the program that you configured on the file: MANIFEST.MF)

You can use the Java IDE to launch the task or execute them from the terminal

```
./gradlew deployAndRun
```

# Modify the example

In order to modify the example, current APIs are:

http://ev3dev-lang-java.github.io/docs/api/latest/index.html

# Examples

Exist several examples ready to use here:

https://github.com/ev3dev-lang-java/examples


# General help info

## Getting Started

LEGO brick is running on Debian-based operating system ev3dev: https://github.com/ev3dev (for more info see ev3dev links).

AND programmed in JAVA: http://ev3dev-lang-java.github.io/#/. JAVA programms are deployed on brick by using Gradle, to see how it is done, follow this link: http://ev3dev-lang-java.github.io/docs/support/getting_started/create-your-first-project.html. (also Git repo for example source code available here: https://github.com/ev3dev-lang-java/template_project_gradle). You can see other examples here: https://github.com/ev3dev-lang-java/examples and Java class documentation here: http://ev3dev-lang-java.github.io/docs/api/latest/index.html


For sensor and motor capabilities you can learn here: http://docs.ev3dev.org/en/ev3dev-jessie/ and   http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html and http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/motor_data.html

Hint: value0 -> value(0) in Java.

If necessary, to set-up wifi, you can access robot through ssh and then use "connman", described here:  https://wiki.archlinux.org/index.php/ConnMan#Connecting_to_eduroam_.28802.1X.29
