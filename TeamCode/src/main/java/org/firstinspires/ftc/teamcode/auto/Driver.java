package org.firstinspires.ftc.teamcode.auto;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Telem;
import static org.firstinspires.ftc.teamcode.Ports.*;

public class Driver {

    //drive function, input speed, distance in cm, and degree angle of the movement
    public static void drive(LinearOpMode opMode, double speed, double cm, double degrees) {

        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        //Determine the multipliers (some quick lookups and a general case if they lookups don't apply)
        if (degrees % 360 == 0) {
            frblMultiplier = 1;
            flbrMultiplier = 1;
        } else if (degrees % 360 == 90) {
            frblMultiplier = 1;
            flbrMultiplier = -1;
        } else if (degrees % 360 == 180) {
            frblMultiplier = -1;
            flbrMultiplier = -1;
        } else if (degrees % 360 == 270) {
            frblMultiplier = -1;
            flbrMultiplier = 1;
        } else {
            //General case to determine the multipliers
            degrees = -degrees;

            double radians = degrees * PI / 180;

            frblMultiplier = cos(radians)-sin(radians);
            flbrMultiplier = cos(radians)+sin(radians);
        }

        // Reset the tick encoders to zero
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Calculate the number of ticks based on the distance and a conversion constant
        int ticks = (int) (cm * 22.233);

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
        int ticks = (int) (degrees * 15.6);

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
}
