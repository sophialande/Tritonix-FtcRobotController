package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.auto.Driver.*;
import org.firstinspires.ftc.teamcode.Ports;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//Create an opmode class
@Config
@Autonomous(name="noah auto test")
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
