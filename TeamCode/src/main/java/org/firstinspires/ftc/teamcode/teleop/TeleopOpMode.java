package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.Ports.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Ports;


@TeleOp(name = "Teleop Tritonics")
public class TeleopOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {

        Ports.init(this);

        waitForStart();

        while (opModeIsActive()) {

            double frblMultiplier = 0.707107*gamepad1.left_stick_x+0.707107*gamepad1.left_stick_y;
            double flbrMultiplier = 0.707107*gamepad1.left_stick_y-0.707107*gamepad1.left_stick_x;

            // make the motors run at the given speed
            fr.setPower(frblMultiplier);
            fl.setPower(flbrMultiplier);
            br.setPower(flbrMultiplier);
            bl.setPower(frblMultiplier);

        }
    }
}






