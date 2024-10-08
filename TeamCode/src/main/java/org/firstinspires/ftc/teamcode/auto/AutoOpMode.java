package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.auto.Driver.*;
import org.firstinspires.ftc.teamcode.Ports;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Create an opmode class
@Config
@Autonomous(name="noah auto test")
public class AutoOpMode extends LinearOpMode {

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        Ports.init();

        //wait for the game to start
        waitForStart();

        //do a little test dance.
        drive(1, 30, 0);
        drive(1, 60, 180);
        drive(1, 42.426, -45);
        drive(1, 60, 90);
        drive(1, 30,  270);
        rotate(1, 180);
        drive(1, 30, 180);
        drive(1, 60, 0);
        drive(1, 42.426, 45);
        drive(1, 60, 270);
        drive(1, 30,  90);
        rotate(1, 180);
    }
}
