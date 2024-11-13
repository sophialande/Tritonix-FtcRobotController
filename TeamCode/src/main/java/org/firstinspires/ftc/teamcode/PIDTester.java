package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.hardware.Ports.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Ports;

//Create an opmode class
@Config
@Autonomous(name="PID Tester")
public class PIDTester extends LinearOpMode {

    public static PIDController pidController = new PIDController();
    public static int target = 2000;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        Ports.init(this);

        DcMotor slide = lsv_l;

        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slide.setTargetPosition(0);

        while(slide.isBusy()){
            slide.setPower(-0.8);
        }
        slide.setPower(0);

        //wait for the game to start
        waitForStart();

        pidController.Setup(target-slide.getCurrentPosition());
        while(opModeIsActive()){
            slide.setPower(pidController.Evaluate(target-slide.getCurrentPosition()));
        }
        slide.setPower(0);

    }
}