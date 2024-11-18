package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Config
@Autonomous(name = "April Tag Test Tritonics")
public class AprilTagTest extends LinearOpMode {

    AprilTagProcessor aprilTagProcessor;
    List<AprilTagDetection> aprilTagDetections;
    private static final boolean USE_WEBCAM = true;

    @Override
    void runOpMode(){
        Telemetry dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();
        aprilTagProcessor = new AprilTagProcessor.Builder();

        waitForStart();

        while(opModeIsActive()) {
            aprilTagDetections = aprilTagProcessor.getDetections();
            for (AprilTagDetection aprilTagDetection : aprilTagDetections) {
                if (aprilTagDetection.metadata != null) {
                    dashboardTelemetry.addData("April Tag ID", aprilTagDetection.id);
                }
            }
            dashboardTelemetry.update();
        }
    }
}
