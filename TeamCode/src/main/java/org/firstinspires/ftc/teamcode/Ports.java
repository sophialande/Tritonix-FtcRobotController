package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Ports {
    public static DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
    public static DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
    public static DcMotor br = hardwareMap.get(DcMotor.class, "br");
    public static DcMotor bl = hardwareMap.get(DcMotor.class, "bl");
}
