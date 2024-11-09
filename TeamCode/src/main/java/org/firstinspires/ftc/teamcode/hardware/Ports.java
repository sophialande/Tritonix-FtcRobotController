package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

/*
 * USAGE GUIDE:
 *
 * The "Ports" class is designed to make it easier to access hardware. The first section can be used
 * to declare the hardware variables, without attaching them to anything. The second section
 * (within the init function) is for attaching those variables to their various hardware and running
 * other initialization steps. This is run before the challenge starts, so no need to worry about
 * efficiency.
 */

public class Ports {

    //CREATE HARDWARE VARIABLES HERE

    //Motors
    public static DcMotor fr;
    public static DcMotor fl;
    public static DcMotor br;
    public static DcMotor bl;

    // Linear slides
    public static DcMotor lsv_r;
    public static DcMotor lsv_l;
    public static DcMotor lsh_r;
    public static DcMotor lsh_l;

    // Servos
    public static Servo claw;
    public static Servo intake;
    public static Servo outtake;


    //DO INITIALIZATION STEPS HERE
    public static void init(LinearOpMode opMode){

        //Set the motor variables to their respective motors
        fr = opMode.hardwareMap.get(DcMotor.class, "FR");
        fl = opMode.hardwareMap.get(DcMotor.class, "FL");
        br = opMode.hardwareMap.get(DcMotor.class, "BR");
        bl = opMode.hardwareMap.get(DcMotor.class, "BL");

        //Set the motors to run in the right direction
        fr.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);

        lsv_r = opMode.hardwareMap.get(DcMotor.class, "lsv_r");
        lsv_l = opMode.hardwareMap.get(DcMotor.class, "lsv_l");

        lsh_r = opMode.hardwareMap.get(DcMotor.class, "lsh_r");
        lsh_l = opMode.hardwareMap.get(DcMotor.class, "lsh_l");

        claw = opMode.hardwareMap.get(Servo.class, "claw");
        intake = opMode.hardwareMap.get(Servo.class, "intake");
        outtake = opMode.hardwareMap.get(Servo.class, "outtake");
    }
}