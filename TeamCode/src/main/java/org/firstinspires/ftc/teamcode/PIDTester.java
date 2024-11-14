package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.hardware.Ports.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;

//Create an opmode class
@Config
@Autonomous(name="PID Tester")
public class PIDTester extends LinearOpMode {

    public static double Kp;
    public static double Ki;
    public static double Kd;
    public static double Kf;

    public static PIDController pidController = new PIDController(Kp, Ki, Kd, Kf);
    public static int target;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        DcMotor slide = hardwareMap.get(DcMotor.class, "lsv_r");

        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setDirection(DcMotor.Direction.REVERSE);

        Telemetry dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();



        //wait for the game to start
        waitForStart();
//        slide.setTargetPosition(100);
//
//        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        slide.setPower(0.2);
//
//        while(slide.isBusy()){
//            telemetry.addData("position", slide.getCurrentPosition());
//            telemetry.update();
//        }
//        slide.setPower(0);


        pidController.setup(target-slide.getCurrentPosition());
        while(opModeIsActive()){
            double power = pidController.evaluate(target-slide.getCurrentPosition());
            slide.setPower(power);
            dashboardTelemetry.addData("Power", power);
            dashboardTelemetry.update();
        }

    }
}