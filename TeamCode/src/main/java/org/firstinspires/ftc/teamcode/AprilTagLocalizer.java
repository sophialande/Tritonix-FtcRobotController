package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Config
@TeleOp(name = "April Tag Test Script" )
public class AprilTagLocalizer extends LinearOpMode {
    AprilTagProcessor aprilTagProcessor;
    VisionPortal myVisionPortal;
    Telemetry dashboardTelemetry;
    List<AprilTagDetection> aprilTagDetections;

    @Override
    public void runOpMode() {
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTagProcessor);
        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        waitForStart();

        while(opModeIsActive()){
            aprilTagDetections = aprilTagProcessor.getDetections();

            for (AprilTagDetection aprilTagDetection : aprilTagDetections) {

                if (aprilTagDetection.metadata != null) {
                    dashboardTelemetry.addData("ID", aprilTagDetection.id);
                    dashboardTelemetry.addData("X", aprilTagDetection.ftcPose.x);
                    dashboardTelemetry.addData("Y", aprilTagDetection.ftcPose.y);
                    dashboardTelemetry.addData("Z", aprilTagDetection.ftcPose.z);
                    dashboardTelemetry.addData("Pitch", aprilTagDetection.ftcPose.pitch);
                    dashboardTelemetry.addData("Roll", aprilTagDetection.ftcPose.roll);
                    dashboardTelemetry.addData("Yaw", aprilTagDetection.ftcPose.yaw);
                    dashboardTelemetry.addLine();
                }
            }
            dashboardTelemetry.update();
        }
    }
}
