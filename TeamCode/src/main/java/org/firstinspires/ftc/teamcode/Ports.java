package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Ports {

    //CREATE HARDWARE VARIABLES HERE

    //Motors
    public static DcMotor fr;
    public static DcMotor fl;
    public static DcMotor br;
    public static DcMotor bl;

    //DO INITIALIZATION STEPS HERE
    public static void init(){

        //Set the motor variables to their respective motors
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fr");
        br = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "fr");

        //Set the motors to run using the encoder
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
