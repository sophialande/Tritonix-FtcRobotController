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
    public DcMotor fr;
    public DcMotor fl;
    public DcMotor br;
    public DcMotor bl;

    // Linear slides
    public DcMotor lsv_r;
    public DcMotor lsv_l;
    public DcMotor lsh_r;
    public DcMotor lsh_l;

    // Servos
    public Servo claw;
    public Servo intake;
    public Servo outtake;


    //DO INITIALIZATION STEPS HERE
     public Ports(LinearOpMode opMode, Builder builder) {

         if (builder.allActive || builder.wheelsActive || builder.frActive) {
             fr = opMode.hardwareMap.get(DcMotor.class, "FR");
             fr.setDirection(DcMotor.Direction.FORWARD);
         }

         if (builder.allActive || builder.wheelsActive || builder.flActive) {
             fl = opMode.hardwareMap.get(DcMotor.class, "FL");
             fl.setDirection(DcMotor.Direction.REVERSE);
         }

         if (builder.allActive || builder.wheelsActive || builder.brActive) {
             br = opMode.hardwareMap.get(DcMotor.class, "BR");
             br.setDirection(DcMotor.Direction.FORWARD);
         }

         if (builder.allActive || builder.wheelsActive || builder.blActive) {
             bl = opMode.hardwareMap.get(DcMotor.class, "BL");
             bl.setDirection(DcMotor.Direction.REVERSE);
         }

         if (builder.allActive || builder.slidesActive || builder.lsv_rActive) {
             lsv_r = opMode.hardwareMap.get(DcMotor.class, "lsv_r");
         }

         if (builder.allActive || builder.slidesActive || builder.lsv_lActive) {
             lsv_l = opMode.hardwareMap.get(DcMotor.class, "lsv_l");
         }

         if (builder.allActive || builder.slidesActive || builder.lsh_rActive) {
             lsh_r = opMode.hardwareMap.get(DcMotor.class, "lsh_r");
         }

         if (builder.allActive || builder.slidesActive || builder.lsh_lActive) {
             lsh_l = opMode.hardwareMap.get(DcMotor.class, "lsh_l");
         }

         if (builder.allActive || builder.servosActive || builder.clawActive) {
             claw = opMode.hardwareMap.get(Servo.class, "claw");
         }

         if (builder.allActive || builder.servosActive || builder.intakeActive) {
             intake = opMode.hardwareMap.get(Servo.class, "intake");
         }

         if (builder.allActive || builder.servosActive || builder.outtakeActive) {
             outtake = opMode.hardwareMap.get(Servo.class, "outtake");
         }
     }

     public class Builder {
         public boolean wheelsActive = false;
         public boolean slidesActive = false;
         public boolean clawActive = false;
         public boolean intakeActive = false;
         public boolean outtakeActive = false;
         public boolean allActive = false;
         public boolean frActive = false;
         public boolean flActive = false;
         public boolean brActive = false;
         public boolean blActive = false;
         public boolean lsv_rActive = false;
         public boolean lsv_lActive = false;
         public boolean lsh_rActive = false;
         public boolean lsh_lActive = false;
         public boolean servosActive = false;
     }
}