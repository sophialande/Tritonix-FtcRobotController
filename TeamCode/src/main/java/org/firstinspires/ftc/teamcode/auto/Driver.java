package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Telem;

public class Driver {

    static DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
    static DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
    static DcMotor br = hardwareMap.get(DcMotor.class, "br");
    static DcMotor bl = hardwareMap.get(DcMotor.class, "bl");

    public static void drive(double speed, double cm, double degrees) {

        degrees = -degrees;

        // Reset the tick encoders to zero
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int ticks = (int) (cm * 22.233);

        double radians = degrees * PI / 180;

        double frblMultiplier = cos(radians)-sin(radians);
        double flbrMultiplier = cos(radians)+sin(radians);

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

        // Run while both motors are moving
        while (fr.isBusy()) {

            // Update the telem data
            telemetry.addData("Running to", "Font Right and Back Left: " + frblTicks + " | Front Left and Back Right: " + flbrTicks);
            telemetry.addData("Current pos", "Front Right: " + fr.getCurrentPosition() + " | Front Left: " + fl.getCurrentPosition() + " | Back Right: " + br.getCurrentPosition() + " | Back Left: " + bl.getCurrentPosition());
            Telem.update();
        }

        // Stop the motors
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);

    }

    public static void rotate(double speed, double degrees){

        // Reset the tick encoders to zero
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
            telemetry.addData("Running to", "Left " + ticks + " | Right: " + -ticks);
            telemetry.addData("Current pos", "Front Right: " + fr.getCurrentPosition() + " | Front Left: " + fl.getCurrentPosition() + " | Back Right: " + br.getCurrentPosition() + " | Back Left: " + bl.getCurrentPosition());
            Telem.update();
        }

        // Stop the motors
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }
}
