package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.hardware.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Ports;

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
@Autonomous(name="noah auto pushbot left")
public class AutoPushbotLeft extends LinearOpMode {

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        Ports.init(this);

        //wait for the game to start
        waitForStart();

        drive(this, 0.4, 80, 0);
        drive(this, 0.4, 300, 180);

    }
}
