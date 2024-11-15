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
    DcMotor fr;
    DcMotor fl;
    DcMotor br;
    DcMotor bl;

    // Linear slides
    DcMotor lsv_r;
    DcMotor lsv_l;
    DcMotor lsh_r;
    DcMotor lsh_l;

    // Servos
    Servo claw;
    Servo intake;
    Servo outtake;


    //DO INITIALIZATION STEPS HERE
     Ports(LinearOpMode opMode, boolean wheelsActive, boolean slidesActive, boolean clawActive,
           boolean intakeActive, boolean outtakeActive, boolean allActive, boolean frActive,
           boolean flActive, boolean brActive, boolean blActive, boolean lsv_rActive,
           boolean lsv_lActive, boolean lsh_rActive, boolean lsh_lActive, boolean servosActive) {

         if (allActive || wheelsActive || frActive) {
             fr = opMode.hardwareMap.get(DcMotor.class, "FR");
             fr.setDirection(DcMotor.Direction.FORWARD);
         }

         if (allActive || wheelsActive || flActive) {
             fl = opMode.hardwareMap.get(DcMotor.class, "FL");
             fl.setDirection(DcMotor.Direction.REVERSE);
         }

         if (allActive || wheelsActive || brActive) {
             br = opMode.hardwareMap.get(DcMotor.class, "BR");
             br.setDirection(DcMotor.Direction.FORWARD);
         }

         if (allActive || wheelsActive || blActive) {
             bl = opMode.hardwareMap.get(DcMotor.class, "BL");
             bl.setDirection(DcMotor.Direction.REVERSE);
         }

         if (allActive || slidesActive || lsv_rActive) {
             lsv_r = opMode.hardwareMap.get(DcMotor.class, "lsv_r");
         }

         if (allActive || slidesActive || lsv_lActive) {
             lsv_l = opMode.hardwareMap.get(DcMotor.class, "lsv_l");
         }

         if (allActive || slidesActive || lsh_rActive) {
             lsh_r = opMode.hardwareMap.get(DcMotor.class, "lsh_r");
         }

         if (allActive || slidesActive || lsh_lActive) {
             lsh_l = opMode.hardwareMap.get(DcMotor.class, "lsh_l");
         }

         if (allActive || servosActive || clawActive) {
             claw = opMode.hardwareMap.get(Servo.class, "claw");
         }

         if (allActive || servosActive || intakeActive) {
             intake = opMode.hardwareMap.get(Servo.class, "intake");
         }

         if (allActive || servosActive || outtakeActive) {
             outtake = opMode.hardwareMap.get(Servo.class, "outtake");
         }
     }
}