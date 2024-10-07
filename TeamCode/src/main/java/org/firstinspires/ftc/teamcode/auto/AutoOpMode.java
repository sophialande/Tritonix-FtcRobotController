package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.auto.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name="auto")
public class AutoOpMode extends LinearOpMode {

    @Override
    public void runOpMode(){
        drive(1, 12, 0);
        drive(1, 24, 180);
        drive(1, 16.971, -45);
        drive(1, 24, 90);
        drive(1, 12,  270);
        rotate(1, 180);
        drive(1, 12, 180);
        drive(1, 24, 0);
        drive(1, 16.971, 45);
        drive(1, 24, 270);
        drive(1, 12,  90);
        rotate(1, 180);
    }
}
