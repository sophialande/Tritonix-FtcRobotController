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

    double VRF = 0;
    double VLF = 0;
    double HRF = 0;
    double HLF = 0;

    void resetSlides(){
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
    }

    @Override
    public void runOpMode(){
        telemetry.addLine("Initializing ports...");
        Telem.update(this);

        Ports.init(this);

        telemetry.addLine("Resetting linear slides");
        Telem.update(this);

        resetSlides();

        telemetry.addLine("Setup Complete");
        telemetry.addLine("--------------");
        telemetry.addLine("Press Start to begin.");
        Telem.update(this);

        waitForStart();

        boolean calibrationNotFinished = false;

        for(int i = 0; calibrationNotFinished; i++){

            lsv_r.setPower(i*accuracy);
            lsv_l.setPower(i*accuracy);
            lsh_r.setPower(i*accuracy);
            lsh_l.setPower(i*accuracy);

            prevVR = lsv_r.getCurrentPosition();
            prevVL = lsv_l.getCurrentPosition();
            prevHR = lsh_r.getCurrentPosition();
            prevHL = lsh_l.getCurrentPosition();

            sleep(50);

            lsv_r.setPower(0);
            lsv_l.setPower(0);
            lsh_r.setPower(0);
            lsh_l.setPower(0);

            calibrationNotFinished = (lsv_r.getCurrentPosition()-prevVR == 0) &&
                                     (lsv_l.getCurrentPosition()-prevVL == 0) &&
                                     (lsh_r.getCurrentPosition()-prevHR == 0) &&
                                     (lsh_l.getCurrentPosition()-prevHL == 0);

            if(lsv_r.getCurrentPosition()-prevVR != 0 && VRF == 0){
                VRF = i*accuracy;
            }
            if(lsv_l.getCurrentPosition()-prevVL != 0 && VLF == 0){
                VLF = i*accuracy;
            }
            if(lsh_r.getCurrentPosition()-prevHR != 0 && HRF == 0){
                HRF = i*accuracy;
            }
            if(lsh_l.getCurrentPosition()-prevHL != 0 && HLF == 0){
                HLF = i*accuracy;
            }

            telemetry.addData("VRF", VRF);
            telemetry.addData("VLF", VLF);
            telemetry.addData("HRF", HRF);
            telemetry.addData("HLF", HLF);
            telemetry.addLine("It it says 0, it's undefined");
            Telem.update(this);

            resetSlides();

        }

    }

}
