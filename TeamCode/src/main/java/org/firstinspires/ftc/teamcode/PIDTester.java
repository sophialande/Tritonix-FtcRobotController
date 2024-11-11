package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;

//Create an opmode class
@Config
@Autonomous(name="PID Tester")
public class PIDTester extends LinearOpMode {

    Gamepad prevGamepad1;

    PIDController pidController = new PIDController();

    // Changes the speed you change the constants at
    double adjustmentConstant = 0.01;

    // 0 = Kp, 1 = Ki, 2 = Kd, 3 = Kf
    // Yes, I already hear it, this will be easier I promise
    int selectedConstant = 0;

    void increaseConstant(int v, int multiplier){
        if(v == 0){
            pidController.Kp += multiplier*adjustmentConstant;
        } else if (v == 1){
            pidController.Ki += multiplier*adjustmentConstant;
        } else if (v == 2){
            pidController.Kd += multiplier*adjustmentConstant;
        } else {
            pidController.Kf += multiplier*adjustmentConstant;
        }
    }

    //Give the slide you want to tune here
    DcMotor slide;
    //and the target number of ticks here
    int target;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        Ports.init(this);

        //wait for the game to start
        waitForStart();

        while(!isStopRequested()){
            if(gamepad1 != prevGamepad1){
                if(!gamepad1.dpad_right && prevGamepad1.dpad_right){
                    selectedConstant++;
                } else if (!gamepad1.dpad_left && prevGamepad1.dpad_left){
                    selectedConstant--;
                }

                if(!gamepad1.dpad_up && prevGamepad1.dpad_up){
                    increaseConstant(selectedConstant, 1);
                } else if (!gamepad1.dpad_down && prevGamepad1.dpad_down){
                    increaseConstant(selectedConstant, -1);
                }

                if(!gamepad1.x && prevGamepad1.x){
                    pidController.Setup(slide.getCurrentPosition()-target);
                    ElapsedTime timeElapsed = new ElapsedTime();
                    while(gamepad1.x){
                        slide.setPower(pidController.Evaluate(slide.getCurrentPosition()-target));
                        telemetry.addData("Current Position", slide.getCurrentPosition());
                        telemetry.addData("Time After Start", timeElapsed.time());
                    }
                } else {
                    telemetry.addData("Kp(0)", pidController.Kp);
                    telemetry.addData("Ki(1)", pidController.Ki);
                    telemetry.addData("Kd(2)", pidController.Kd);
                    telemetry.addData("Kf(3)", pidController.Kf);
                    telemetry.addLine();
                    telemetry.addData("Currently Editing (index)", selectedConstant);
                    telemetry.addLine("Change constant to edit with left & right.");
                    telemetry.addLine("Change constant with up and down.");
                    telemetry.addLine("Press x to start the trial");
                }
                Telem.update(this);
            }
        }
    }
}