package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Ports {

    //CREATE HARDWARE VARIABLES HERE

    //Motors
    public static DcMotor fr;
    public static DcMotor fl;
    public static DcMotor br;
    public static DcMotor bl;

    //DO INITIALIZATION STEPS HERE
    public static void init(LinearOpMode opMode){

        //Set the motor variables to their respective motors
        fr = opMode.hardwareMap.get(DcMotor.class, "FR");
        fl = opMode.hardwareMap.get(DcMotor.class, "FL");
        br = opMode.hardwareMap.get(DcMotor.class, "BR");
        bl = opMode.hardwareMap.get(DcMotor.class, "BL");

        //Set the motors to run using the encoder
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
    }
}
