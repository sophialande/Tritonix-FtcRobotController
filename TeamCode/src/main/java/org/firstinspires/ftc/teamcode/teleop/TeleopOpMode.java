package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.hardware.Ports.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;


@TeleOp(name = "Teleop Tritonics")
public class TeleopOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {

        Ports.init(this);

        waitForStart();

        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

            fl.setPower(leftFrontPower);
            fr.setPower(rightFrontPower);
            bl.setPower(leftBackPower);
            br.setPower(rightBackPower);
        }

        // VERTICAL LINEAR SLIDES (UP AND DOWN)
        if (gamepad1.y) {
            Driver.linearSlidesVUp(1, 3200);
        }
        else {
            Driver.linearSlidesVUp(0, 3200);
        }

        if (gamepad1.a) {
            Driver.linearSlidesVDown(-1, -1300);
        }
        else {
            Driver.linearSlidesVDown(0, -1300);
        }

        // HORIZONTAL LINEAR SLIDES (IN AND OUT)
        if (gamepad1.x) {
            Driver.linearSlidesHUp(1, 3200);
        }
        else {
            Driver.linearSlidesHUp(0, 3200);
        }

        if (gamepad1.b) {
            Driver.linearSlidesHDown(-1, -1300);
        }
        else {
            Driver.linearSlidesHDown(0, -1300);
        }

        // CLAW
        if (gamepad1.dpad_right) {
            Driver.claw(1);
        }
        if (gamepad1.dpad_left) {
            Driver.claw(-1);
        }
        //change

        // INTAKE/OUTTAKE (DO LATER BECAUSE THEY HAVEN'T DECIDED YET

        // HANGING MECHANISM (DO LATER BECAUSE THEY HAVEN'T DECIDED YET

        //TELEMETRY
        telemetry.addData("Front Left Pow", fl.getPower());
        telemetry.addData("Back Left Pow", bl.getPower());
        telemetry.addData("Front Right Pow", fr.getPower());
        telemetry.addData("Back Right Pow", br.getPower());
        telemetry.addData("Linear Slide Vertical Right Position", lsv_r.getCurrentPosition());
        telemetry.addData("Linear Slide Vertical Left Position", lsv_l.getCurrentPosition());
        telemetry.addData("Linear Slide Horizontal Right Position", lsh_r.getCurrentPosition());
        telemetry.addData("Linear Slide Horizontal Left Position", lsh_l.getCurrentPosition());
        //telemetry.addData("Claw position", claw.getCurrentPosition());
        telemetry.update();

    }
}






