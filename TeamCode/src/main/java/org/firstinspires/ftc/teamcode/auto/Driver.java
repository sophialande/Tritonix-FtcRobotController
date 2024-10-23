package org.firstinspires.ftc.teamcode.auto;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Telem;
import static org.firstinspires.ftc.teamcode.Ports.*;

/*
 * USAGE GUIDE:
 *
 * The Driver class allows you to actually move to robot around using the wheels, there are two
 * functions in the driver class: drive and rotate.
 *
 * Driver.drive(a, b, c, d) can move the robot in any direction you want for any distance you want
 * at any speed you want. The first parameter (a) is the opMode. If you are calling this from the
 * opMode simply pass in "this" (without the quote marks), if you are calling this from somewhere
 * else that already had the opMode passed into it, pass "opMode" (without the quote marks). Next is
 * the speed (b), which can be anywhere from -1 to 1 and specifies how fast the robot moves, the
 * faster you go, though, the less precise you are. Third is distance in cm (c), this tells the
 * robot how far to travel at that speed, if the speed is anywhere above ~0.5 it might drift a
 * little at the end. Finally the angle you want to robot to move at (d); input any degree value and
 * that is the direction the robot will move (counterclockwise) without rotating!
 *
 * Driver.rotate(a, b, c) just like in the previous function a is the opMode, bass in this or opMode
 * depending on your situation. Next is the speed (b) that you would like it to rotate at, this is
 * again a scale from -1 to 1. Finally the number of degrees you would like to rotate (c) in the
 * clockwise direction. Remember that if you set the speed to high you might overshoot the rotation.
 */

public class Driver {

    //drive function, input speed, distance in cm, and degree angle of the movement
    public static void drive(LinearOpMode opMode, double speed, double cm, double degrees) {

        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        double radians = degrees * PI / 180;

        frblMultiplier = cos(radians)-sin(radians);
        flbrMultiplier = cos(radians)+sin(radians);

        //Debug (if power level caps)
        if (speed*frblMultiplier > 1 || speed*flbrMultiplier > 1) {
            Telem.add("Drive Status", "The set speed and direction has maxed out the speed of one of the wheels. Direction and speed may not be accurate");
        }

        // Reset the tick encoders to zero
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Calculate the number of ticks based on the distance and a conversion constant
        int ticks = (int) (cm * 17.467);

        //Calculate the number of ticks required for each motor to move
        int frblTicks = (int) (ticks * frblMultiplier);
        int flbrTicks = (int) (ticks * flbrMultiplier);

        // set the target position
        fr.setTargetPosition(frblTicks);
        fl.setTargetPosition(flbrTicks);
        br.setTargetPosition(flbrTicks);
        bl.setTargetPosition(frblTicks);

        // Change the mode to spin until reaching the position
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // make the motors run at the given speed
        fr.setPower(speed * frblMultiplier);
        fl.setPower(speed * flbrMultiplier);
        br.setPower(speed * flbrMultiplier);
        bl.setPower(speed * frblMultiplier);

        // Run while the motors are moving
        while (fr.isBusy()) {

            // Update the telem data
            opMode.telemetry.addData("Running to", "Font Right and Back Left: " + frblTicks + " | Front Left and Back Right: " + flbrTicks);
            opMode.telemetry.addData("Current pos", "Front Right: " + fr.getCurrentPosition() + " | Front Left: " + fl.getCurrentPosition() + " | Back Right: " + br.getCurrentPosition() + " | Back Left: " + bl.getCurrentPosition());
            Telem.update(opMode);
        }

        Telem.remove("Drive Status");

        // Stop the motors
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);

    }

    public static void rotate(LinearOpMode opMode, double speed, double degrees){

        // Reset the tick encoders to zero
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert from degrees to ticks
        int ticks = (int) (degrees*12.4);

        // set the target position
        fr.setTargetPosition(-ticks);
        fl.setTargetPosition(ticks);
        br.setTargetPosition(-ticks);
        bl.setTargetPosition(ticks);

        // Change the mode to spin until reaching the position
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // make the motors run at the given speed
        fr.setPower(-speed);
        fl.setPower(-speed);
        br.setPower(-speed);
        bl.setPower(-speed);

        // Run while both motors are moving
        while (fr.isBusy()) {
            // Update the telem data
            opMode.telemetry.addData("Running to", "Left " + ticks + " | Right: " + -ticks);
            opMode.telemetry.addData("Current pos", "Front Right: " + fr.getCurrentPosition() + " | Front Left: " + fl.getCurrentPosition() + " | Back Right: " + br.getCurrentPosition() + " | Back Left: " + bl.getCurrentPosition());
            Telem.update(opMode);
        }

        // Stop the motors
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public static void traject(LinearOpMode opMode, double speed, double angle, double degrees, double rotSpeed) {

        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        double radians = degrees * PI / 180;

        frblMultiplier = cos(radians)-sin(radians);
        flbrMultiplier = cos(radians)+sin(radians);

        //Debug (if power level caps)
        if (speed*frblMultiplier > 1 || speed*flbrMultiplier > 1) {
            Telem.add("Drive Status", "The set speed and direction has maxed out the speed of one of the wheels. Direction and speed may not be accurate");
        }

        // Change the mode to spin until reaching the position
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // make the motors run at the given speed
        fr.setPower(speed * frblMultiplier);
        fl.setPower(speed * flbrMultiplier);
        br.setPower(speed * flbrMultiplier);
        bl.setPower(speed * frblMultiplier);

        double radianAngle = angle * PI / 180;
        double radiansInconstant = radians;

        // Run while the motors are moving
        while (radiansInconstant <= radianAngle + radians) {

            // Update the telem data
            //opMode.telemetry.addData("Running to", "Font Right and Back Left: " + frblTicks + " | Front Left and Back Right: " + flbrTicks);
            //opMode.telemetry.addData("Current pos", "Front Right: " + fr.getCurrentPosition() + " | Front Left: " + fl.getCurrentPosition() + " | Back Right: " + br.getCurrentPosition() + " | Back Left: " + bl.getCurrentPosition());
            //Telem.update(opMode);

            //Circle
            frblMultiplier = cos(radiansInconstant)-sin(radiansInconstant);
            flbrMultiplier = cos(radiansInconstant)+sin(radiansInconstant);
            radiansInconstant += 0.000000001;
            fr.setPower(speed * frblMultiplier);
            fl.setPower(speed * flbrMultiplier);
            br.setPower(speed * flbrMultiplier);
            bl.setPower(speed * frblMultiplier);
        }

        Telem.remove("Drive Status");

        // Stop the motors
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);

    }

}
