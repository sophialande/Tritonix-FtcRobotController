package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;


@TeleOp(name = "Teleop Tritonics")
public class TeleopOpMode extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;

    Gamepad prevGamepad1;
    Gamepad prevGamepad2;
    Gamepad currGamepad1;
    Gamepad currGamepad2;

    public static double servoRangeTime = 1;

    boolean intakeInverse = false;
    int handoffStep = 0;

    ElapsedTime elapsedTime = new ElapsedTime();

    PIDController lsv_lController;
    PIDController lsv_rController;

    @Override
    public void runOpMode() {

        builder = new Ports.Builder();
        builder.wheelsActive = true;
        builder.slidesActive = true;
        builder.servosActive = true;
        ports = new Ports(this, builder);

        ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsh_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsh_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ports.fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsv_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsh_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsh_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lsv_lController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_l);
        lsv_rController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_r);

        prevGamepad1 = new Gamepad();
        prevGamepad2 = new Gamepad();
        currGamepad1 = new Gamepad();
        currGamepad2 = new Gamepad();

        waitForStart();

        elapsedTime.reset();

        while (opModeIsActive()) {
            double max;

            prevGamepad1.copy(currGamepad1);
            prevGamepad2.copy(currGamepad2);

            currGamepad1.copy(gamepad1);
            currGamepad2.copy(gamepad2);

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double drive = -currGamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double strafe = gamepad1.left_stick_x;
            double yaw = currGamepad1.right_stick_x;
            double vertical = -currGamepad2.left_stick_y;
            double horizontal = currGamepad2.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double fr = (drive - strafe - yaw) * Math.abs(drive - strafe - yaw);
            double fl = (drive + strafe + yaw) * Math.abs(drive + strafe + yaw);
            double br = (drive + strafe - yaw) * Math.abs(drive + strafe - yaw);
            double bl = (drive - strafe + yaw) * Math.abs(drive - strafe + yaw);

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(fl), Math.abs(fr));
            max = Math.max(max, Math.abs(bl));
            max = Math.max(max, Math.abs(br));

            if (max > 1.0) {
                fl /= max;
                fr /= max;
                bl /= max;
                br /= max;
            }

            if(currGamepad2.y && !prevGamepad2.y) {
                intakeInverse = !intakeInverse;
            }

            ports.fl.setPower(fl);
            ports.fr.setPower(fr);
            ports.bl.setPower(bl);
            ports.br.setPower(br);
            if(ports.lsv_r.getCurrentPosition() < 4330 && ports.lsv_l.getCurrentPosition() < 4330 || vertical < 0) {
                ports.lsv_r.setPower(vertical + 0.06);
                ports.lsv_l.setPower(vertical + 0.06);
            } else {
                ports.lsv_r.setPower(0.06);
                ports.lsv_l.setPower(0.06);
            }
            if(ports.lsh_r.getCurrentPosition() < 2220 && ports.lsh_l.getCurrentPosition() < 2220 || horizontal < 0) {
                ports.lsh_r.setPower(horizontal);
                ports.lsh_l.setPower(horizontal);
            } else {
                ports.lsh_r.setPower(0);
                ports.lsh_l.setPower(0);
            }
            if(currGamepad2.dpad_up){
                ports.outtakePitchL.setPosition(1);
                ports.outtakePitchR.setPosition(0);
            } else if(currGamepad2.dpad_down){
                ports.outtakePitchL.setPosition(0);
                ports.outtakePitchR.setPosition(1);
            }
            if(currGamepad2.dpad_right){
                ports.outtakeClaw.setPosition(0.25);
            } else if(currGamepad2.dpad_left){
                ports.outtakeClaw.setPosition(0);
            }
            if(currGamepad2.x && !prevGamepad2.x){
                if(intakeInverse) {
                    ports.intakeClaw.setPosition(0.05);
                    intakeInverse = false;
                } else {
                    ports.intakeClaw.setPosition(0.03);
                }
            }
            if(currGamepad2.a && !prevGamepad2.a){
                if(intakeInverse){
                    ports.intakePitch.setPosition(0.4);
                    intakeInverse = false;
                } else {
                    ports.intakePitch.setPosition(0.1);
                }
            }
            if(currGamepad2.b) {
                if(intakeInverse){
                    ports.intakeRoll.setPosition(ports.intakeRoll.getPosition() + elapsedTime.seconds());
                } else {
                    ports.intakeRoll.setPosition(ports.intakeRoll.getPosition() - elapsedTime.seconds());
                }
            }
            /* HANDOFF ROUTINE:
             *
             * 1. intakeClaw -> 0.05 & outtakeClaw -> 0.15 & intakePitch -> 0.4 & outtakePitch -> 0 & horizontalSlides -> 1700 if horizontalSlides < 1700 & verticalSlides -> 0
             * 2. horizontalSlides -> 910 & intakeClaw -> 0.04
             * 3. outtakeClaw -> 0.25 & intakeClaw -> 0.03
             * 4. horizontalSlides -> 1300
             */
            if(currGamepad1.dpad_right && !prevGamepad1.dpad_right) {
                handoffStep = 1;
                lsv_lController.setup(-ports.lsv_l.getCurrentPosition());
                lsv_rController.setup(-ports.lsv_r.getCurrentPosition());
            }
            if(handoffStep == 1){
                ports.intakeClaw.setPosition(0.05);
                ports.outtakeClaw.setPosition(0.15);
                ports.intakePitch.setPosition(0.4);
                ports.outtakePitchL.setPosition(0);
                ports.outtakePitchR.setPosition(1);
                if(ports.lsh_l.getCurrentPosition() < 1700 || ports.lsh_r.getCurrentPosition() < 1700){
                    ports.lsh_l.setPower(1);
                    ports.lsh_r.setPower(1);
                } else {
                    ports.lsh_l.setPower(0);
                    ports.lsh_r.setPower(0);
                }
                ports.lsv_l.setPower(lsv_lController.evaluate(-ports.lsv_l.getCurrentPosition()));
                ports.lsv_r.setPower(lsv_rController.evaluate(-ports.lsv_r.getCurrentPosition()));
                if((ports.lsv_l.getCurrentPosition() < 20 || ports.lsv_r.getCurrentPosition() < 20) && (ports.lsh_l.getCurrentPosition() < 1700 || ports.lsh_r.getCurrentPosition() < 1700)){
                    handoffStep = 2;
                }
            }
            if(handoffStep == 2){
                ports.intakeClaw.setPosition(0.04);
                ports.lsh_l.setPower(1);
                ports.lsh_r.setPower(1);
                if(ports.lsh_l.getCurrentPosition() <  940 || ports.lsh_r.getCurrentPosition() < 940){
                    ports.lsh_l.setPower(0);
                    ports.lsh_r.setPower(0);
                    handoffStep = 3;
                }
            }
            if(handoffStep == 3){
                ports.outtakeClaw.setPosition(0.25);
                ports.intakeClaw.setPosition(0.03);
                if(ports.intakeClaw.getPosition() < 0.35){
                    handoffStep = 4;
                }
            }
            if(handoffStep == 4){
                ports.lsh_l.setPower(1);
                ports.lsh_r.setPower(1);
                if(ports.lsh_l.getCurrentPosition() > 1700 || ports.lsh_r.getCurrentPosition() > 1700){
                    ports.lsh_l.setPower(0);
                    ports.lsh_r.setPower(0);
                    handoffStep = 0;
                }
            }


            //TELEMETRY
            telemetry.addLine("Intake Inverse: " + intakeInverse);
            telemetry.addData("Front Left Pow", ports.fl.getPower());
            telemetry.addData("Back Left Pow", ports.bl.getPower());
            telemetry.addData("Front Right Pow", ports.fr.getPower());
            telemetry.addData("Back Right Pow", ports.br.getPower());
            telemetry.addData("Linear Slide Vertical Right Position", ports.lsv_r.getCurrentPosition());
            telemetry.addData("Linear Slide Vertical Left Position", ports.lsv_l.getCurrentPosition());
            telemetry.addData("Linear Slide Horizontal Right Position", ports.lsh_r.getCurrentPosition());
            telemetry.addData("Linear Slide Horizontal Left Position", ports.lsh_l.getCurrentPosition());
            Telem.update(this);

            elapsedTime.reset();
        }
    }
}