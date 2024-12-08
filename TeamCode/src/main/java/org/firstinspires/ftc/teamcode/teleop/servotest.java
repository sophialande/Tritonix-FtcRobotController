package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Ports;

@TeleOp(name = "servo test")
public class servotest extends LinearOpMode {
    Ports ports;
    Ports.Builder builder;

    @Override
    public void runOpMode() {
        builder = new Ports.Builder();
        builder.servosActive = true;
        ports = new Ports(this, builder);


        waitForStart();
        while (opModeIsActive()) {
            ports.intakePitch.setPosition(0);
        }
    }
}
