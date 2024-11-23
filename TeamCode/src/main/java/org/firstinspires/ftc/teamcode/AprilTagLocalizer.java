package org.firstinspires.ftc.teamcode;

import android.util.Size;

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

    double tagYaw;
    double xRelative;
    double yRelative;
    double yaw;
    double x;
    double y;

    @Override
    public void runOpMode() {
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        myVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();
        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        FtcDashboard.getInstance().startCameraStream(myVisionPortal, 30);

        waitForStart();

        while(opModeIsActive()){
            aprilTagDetections = aprilTagProcessor.getDetections();

            for (AprilTagDetection aprilTagDetection : aprilTagDetections) {

                if (aprilTagDetection.metadata != null) {
                    tagYaw = Math.toDegrees(Math.atan2(2 * (aprilTagDetection.metadata.fieldOrientation.w * aprilTagDetection.metadata.fieldOrientation.z + aprilTagDetection.metadata.fieldOrientation.x * aprilTagDetection.metadata.fieldOrientation.y), 1 - 2 * (Math.pow(aprilTagDetection.metadata.fieldOrientation.y, 2) + Math.pow(aprilTagDetection.metadata.fieldOrientation.z, 2))));
                    xRelative = aprilTagDetection.ftcPose.range*Math.sin(Math.toRadians(90-aprilTagDetection.ftcPose.bearing-aprilTagDetection.ftcPose.yaw));
                    yRelative = aprilTagDetection.ftcPose.range*Math.cos(Math.toRadians(90-aprilTagDetection.ftcPose.bearing-aprilTagDetection.ftcPose.yaw));
                    yaw = aprilTagDetection.ftcPose.yaw + tagYaw;
                    if (tagYaw == 0) {
                        yaw += 180;
                        x = -aprilTagDetection.metadata.fieldPosition.get(0) + xRelative;
                        y = -aprilTagDetection.metadata.fieldPosition.get(1) + yRelative;
                    } else if (tagYaw == 90) {
                        x = -aprilTagDetection.metadata.fieldPosition.get(0) + yRelative;
                        y = -aprilTagDetection.metadata.fieldPosition.get(1) + xRelative;
                    } else if (tagYaw == 180) {
                        yaw -= 180;
                        x = -aprilTagDetection.metadata.fieldPosition.get(0) - yRelative;
                        y = -aprilTagDetection.metadata.fieldPosition.get(1) - xRelative;
                    } else if (tagYaw == -90) {
                        x = -aprilTagDetection.metadata.fieldPosition.get(0) + yRelative;
                        y = -aprilTagDetection.metadata.fieldPosition.get(1) - xRelative;
                    } else {
                        dashboardTelemetry.addLine("Invalid Tag");
                    }
                    dashboardTelemetry.addData("X", x);
                    dashboardTelemetry.addData("Y", y);
                    dashboardTelemetry.addData("Yaw", yaw);
                    dashboardTelemetry.addData("tagYaw", tagYaw);
                    dashboardTelemetry.addData("xRelative", xRelative);
                    dashboardTelemetry.addData("yRelative", yRelative);
                    dashboardTelemetry.addLine();
                } else {
                    dashboardTelemetry.addLine("Tag is null");
                }
            }
            dashboardTelemetry.update();
        }
    }
}
