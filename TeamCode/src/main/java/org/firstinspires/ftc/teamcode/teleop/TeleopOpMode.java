package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;


@TeleOp(name = "Teleop Tritonics")
public class TeleopOpMode extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;

    @Override
    public void runOpMode() {

        builder.allActive = true;
        ports = new Ports(this, builder);

        waitForStart();

        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double drive = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double strafe = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double fr = drive - strafe - yaw;
            double fl = drive + strafe + yaw;
            double br = drive + strafe - yaw;
            double bl = drive - strafe + yaw;

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

            ports.fl.setPower(fl);
            ports.fr.setPower(fr);
            ports.bl.setPower(bl);
            ports.br.setPower(br);


            // VERTICAL LINEAR SLIDES (UP AND DOWN)
            if (gamepad1.y) {
                Driver.linearSlidesVUp(ports, 1, 3200);
            } else {
                Driver.linearSlidesVUp(ports, 0, 3200);
            }

            if (gamepad1.a) {
                Driver.linearSlidesVDown(ports, -1, -1300);
            } else {
                Driver.linearSlidesVDown(ports, 0, -1300);
            }

            // HORIZONTAL LINEAR SLIDES (IN AND OUT)
            if (gamepad1.x) {
                Driver.linearSlidesHUp(ports, 1, 3200);
            } else {
                Driver.linearSlidesHUp(ports, 0, 3200);
            }

            if (gamepad1.b) {
                Driver.linearSlidesHDown(ports, -1, -1300);
            } else {
                Driver.linearSlidesHDown(ports, 0, -1300);
            }

            // CLAW
            if (gamepad1.dpad_right) {
                Driver.claw(ports, 1);
            }
            if (gamepad1.dpad_left) {
                Driver.claw(ports, -1);
            }
            //change

            // INTAKE/OUTTAKE (DO LATER BECAUSE THEY HAVEN'T DECIDED YET

            // HANGING MECHANISM (DO LATER BECAUSE THEY HAVEN'T DECIDED YET

            //TELEMETRY
            telemetry.addData("Front Left Pow", ports.fl.getPower());
            telemetry.addData("Back Left Pow", ports.bl.getPower());
            telemetry.addData("Front Right Pow", ports.fr.getPower());
            telemetry.addData("Back Right Pow", ports.br.getPower());
            telemetry.addData("Linear Slide Vertical Right Position", ports.lsv_r.getCurrentPosition());
            telemetry.addData("Linear Slide Vertical Left Position", ports.lsv_l.getCurrentPosition());
            telemetry.addData("Linear Slide Horizontal Right Position", ports.lsh_r.getCurrentPosition());
            telemetry.addData("Linear Slide Horizontal Left Position", ports.lsh_l.getCurrentPosition());
            //telemetry.addData("Claw position", Ports.claw.getCurrentPosition());
            Telem.update(this);
        }
    }
}