package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.hardware.Ports.*;

import com.acmerobotics.dashboard.config.Config;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;

@Config
@Autonomous(name="PID F Finder")
public class PIDFFinder extends LinearOpMode {

    double accuracy = 0.001;

    int prevVR;
    int prevVL;
    int prevHR;
    int prevHL;

    @Override
    public void runOpMode(){
        telemetry.addLine("Initializing ports...");
        Telem.update(this);

        Ports.init(this);

        telemetry.addLine("Resetting linear slides");
        Telem.update(this);

        lsv_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsv_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsh_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsh_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lsv_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsv_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsh_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsh_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lsv_r.setTargetPosition(0);
        lsv_l.setTargetPosition(0);
        lsh_r.setTargetPosition(0);
        lsh_l.setTargetPosition(0);

        lsv_r.setPower(0.4);
        lsv_l.setPower(0.4);
        lsh_r.setPower(0.4);
        lsh_l.setPower(0.4);

        while(lsv_r.isBusy() || lsv_l.isBusy() || lsh_r.isBusy() || lsh_l.isBusy()){
            if(!lsv_r.isBusy()){
                lsv_r.setPower(0.4);
            } else {
                lsv_r.setPower(0);
            }

            if(!lsv_l.isBusy()){
                lsv_l.setPower(0.4);
            } else {
                lsv_l.setPower(0);

            }
            if(!lsh_r.isBusy()){
                lsh_r.setPower(0.4);
            } else {
                lsh_r.setPower(0);

            }
            if(!lsh_l.isBusy()){
                lsh_l.setPower(0.4);
            } else {
                lsh_l.setPower(0);
            }
        }

        lsv_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lsv_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lsh_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lsh_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Setup Complete");
        telemetry.addLine("--------------");
        telemetry.addLine("Press Start to begin.");
        Telem.update(this);

        waitForStart();

        for(int i = 0; i > 1/accuracy; i++){

            lsv_r.setPower(i*accuracy);
            lsv_l.setPower(i*accuracy);
            lsh_r.setPower(i*accuracy);
            lsh_l.setPower(i*accuracy);

            prevVR = lsv_r.getCurrentPosition();
            prevVL = lsv_l.getCurrentPosition();
            prevHR = lsh_r.getCurrentPosition();
            prevHL = lsh_l.getCurrentPosition();

            sleep(50);

        }

    }

}
