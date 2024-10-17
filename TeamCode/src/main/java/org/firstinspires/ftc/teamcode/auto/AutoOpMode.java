package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.auto.Driver.*;
import org.firstinspires.ftc.teamcode.Ports;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
 * USAGE GUIDE:
 *
 * Here is the big function: all of the code in here is the code that will run on the day of the
 * competition. Everything before the waitForStart() command will be run as initialization after the
 * robot gets turned on but before the match starts. Everything after waitForStart() will be run
 * during the match.
 */

//Create an opmode class
@Config
@Autonomous(name="tritonics auto test")
public class AutoOpMode extends LinearOpMode {

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        Ports.init(this);

        //wait for the game to start
        waitForStart();

        double speed = 1;
        drive(this, speed, 200, 0);
        drive(this, speed, 200, 270);
        drive(this, speed, 200, 180);
        drive(this, speed, 200, 90);
        //do a little test dance.
        /*
        drive(this, speed, 15, 0);
        drive(this, speed, 30, 180);
        drive(this, speed, 21.213, -45);
        drive(this, speed, 30, 90);
        drive(this, speed, 15, -90);
        rotate(this, speed,180);
        drive(this, speed, 15, 0);
        drive(this, speed, 30, 180);
        drive(this, speed, 21.213, -45);
        drive(this, speed, 30, 90);
        drive(this, speed, 15, -90);
        rotate(this, speed,180);
         */

    }
}
