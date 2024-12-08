package org.firstinspires.ftc.teamcode.classes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.hardware.Ports;

public class Localization {

    Pose3D robotPosition;
    Ports ports;
    AprilTagLocalizer aprilTagLocalizer;
    MecanumLocalizer mecanumLocalizer;

    Pose3D mecanumVelocityEstimate;
    Pose3D mecanumPositionEstimate;
    Pose3D aprilTagEstimate;

    Position aprilTagPos;
    YawPitchRollAngles aprilTagRot;

    double radianYaw;

    public static Pose3D cameraRelativePos;
    LinearOpMode opMode;

    public Localization(LinearOpMode opMode, Ports ports, Pose3D startingPosition) {
        robotPosition = startingPosition;
        this.ports = ports;
        aprilTagLocalizer = new AprilTagLocalizer(opMode);
        mecanumLocalizer = new MecanumLocalizer(opMode, ports);
        this.opMode = opMode;
    }

    public Pose3D getRobotPosition(){
        return robotPosition;
    }

    public void loop() {
//        aprilTagEstimate = aprilTagLocalizer.run();
//        aprilTagPos = aprilTagEstimate.getPosition();
//        aprilTagRot = aprilTagEstimate.getOrientation();

        mecanumVelocityEstimate = mecanumLocalizer.loop();

//        if (!(aprilTagPos.x == 0 && aprilTagPos.y == 0 && aprilTagPos.z == 0 && aprilTagRot.getRoll() == 0)){
//            robotPosition = new Pose3D(new Position(DistanceUnit.INCH,
//                    aprilTagPos.x - cameraRelativePos.getPosition().x,
//                    aprilTagPos.y - cameraRelativePos.getPosition().y,
//                    0, aprilTagPos.acquisitionTime), new YawPitchRollAngles(AngleUnit.DEGREES,
//                    aprilTagRot.getYaw() - cameraRelativePos.getOrientation().getYaw(),
//                    0, 0, aprilTagRot.getAcquisitionTime()));
//            mecanumPositionEstimate = robotPosition;
//        } else {
        // FORMULA: X position = Robot.XCor + (Estimated.XCor*Cos(Estimated.Yaw) - Estimated.YCor*sin(Estimated.Yaw)) * aquisitionTime
        // FORMULA: Y position = Robot.YCor + (Estimated.XCor*Sin(Estimated.Yaw) - Estimated.YCor*cos(Estimated.Yaw)) * aquisitionTime

            mecanumPositionEstimate = new Pose3D(robotPosition.getPosition(), new YawPitchRollAngles(AngleUnit.DEGREES, robotPosition.getOrientation().getYaw() + (mecanumVelocityEstimate.getOrientation().getYaw() * mecanumLocalizer.motorTimeDiff)/1000, 0, 0, 0));
            radianYaw = Math.toRadians(-mecanumPositionEstimate.getOrientation().getYaw());
            mecanumPositionEstimate = new Pose3D(new Position(DistanceUnit.INCH,
                    robotPosition.getPosition().x+((mecanumVelocityEstimate.getPosition().x*Math.cos(radianYaw)-mecanumVelocityEstimate.getPosition().y*Math.sin(radianYaw)) * mecanumLocalizer.motorTimeDiff)/1000,//*mecanumVelocityEstimate.getOrientation().getAcquisitionTime(),
                    robotPosition.getPosition().y+((mecanumVelocityEstimate.getPosition().x*Math.sin(radianYaw)+mecanumVelocityEstimate.getPosition().y*Math.cos(radianYaw)) * mecanumLocalizer.motorTimeDiff)/1000,//*mecanumVelocityEstimate.getOrientation().getAcquisitionTime(),
                    0, 0), mecanumPositionEstimate.getOrientation());
            opMode.telemetry.addData("position", mecanumPositionEstimate);
            robotPosition = mecanumPositionEstimate;
//        }
    }

}
