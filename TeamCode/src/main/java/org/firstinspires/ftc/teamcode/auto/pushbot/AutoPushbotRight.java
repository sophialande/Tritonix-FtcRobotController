package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.auto.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Ports;

/*
 *
 * Pushbot: Autonomous Left
 *
 * Scores 5 points
 * In this OpMode the robot starts on the LEFT, scores a sample in the
 * NET ZONE, and parks in the OBSERVATION ZONE.
 *
 * The robot should start facing the NET ZONE with a SAMPLE touching
 * the front cross bar of the robot so it can push it straight into
 * the NET ZONE.
 */

//Create an opmode class
@Config
@Autonomous(name="noah auto pushbot right")
public class AutoPushbotRight extends LinearOpMode {

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        Ports.init(this);

        //wait for the game to start
        waitForStart();

        drive(this, 0.4, 84.853, 45);
        drive(this, 0.4, 120, 0);
        rotate(this, 0.4, -45);
        drive(this, 0.4, 70, 0);
        drive(this, 0.4, 70, 180);
        rotate(this, 0.4, 45);
        drive(this, 0.4, 200, 180);
        drive(this, 0.4, 70, -135);

    }
}
