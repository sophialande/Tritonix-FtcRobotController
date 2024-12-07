package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.hardware.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;

/*
 *
 * Pushbot: Autonomous Adaptive
 *
 * Scores 5 points
 * In this OpMode the driver selects the starting position using the game
 * pad.
 *
 * If the robot is in the LEFT starting position it should be set-up with
 * the front facing the basket and a sample on the left of the front
 * If the robot is in the RIGHT starting position it should be set-up with
 * the front facing the basket and a sample on the left of the front
 */

//Create an opmode class
@Config
@Autonomous(name="noah auto pushbot adaptive")
public class AutoPushbotAdaptive extends LinearOpMode {

    // this gamepad object will measure the current gamepad state
    Gamepad currentGamepad1;

    // this gamepad object will measure the previous gamepad state
    Gamepad prevGamepad1;

    // this will only return true once the op mode has been configured
    private Boolean isConfigured;

    // This object class will store the starting position that has been entered for the robot

    // things that can be changed
    // blue vs red alliance
    // left vs right starting position
    // park vs specimen hanging scoring sequence
    // wait 0, 3, 5 seconds
    private enum StartingPosition {
        LEFT,
        RIGHT
    }

    private enum AllianceColor {
        BLUE,
        RED
    }

    private enum ScoringVersion {
        PARK,
        HANG
    }


    private final double speed = 0.4;

    private int delay = 0;
    private Boolean delaySet = false;

    // This object will store the starting position that has been entered for the robot
    private StartingPosition startingPosition;
    private AllianceColor allianceColor;
    private ScoringVersion scoringVersion;


    Ports ports;
    Ports.Builder builder;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        builder.wheelsActive = true;
        ports = new Ports(this, builder);

        ports.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ports.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initialize the gampepad objects
        prevGamepad1 = new Gamepad();
        currentGamepad1 = new Gamepad();

        // requires isConfigured variable to start false (figure out why yourself)
        isConfigured = false;

        // init the starting pos var as null
        startingPosition = null;

        // This loop will only end once the op mode has been configured or when it's been stopped
        while(!isStopRequested() && !isConfigured && !delaySet){

            // Set the value of prevGamepad1 to the value of from the previous loop
            prevGamepad1.copy(currentGamepad1);

            // Set the value of currentGamepad1 to the current state of the gamepad
            currentGamepad1.copy(gamepad1);

            telemetry.addLine("CONFIGURE AUTONOMOUS");
            telemetry.addLine("--------------------");
            telemetry.addLine("");

            // If there isn't a starting position, as for one to be inputted and record it
            if(startingPosition == null){
                telemetry.addLine("Select a starting position:");
                telemetry.addLine("Press (X/□) for LEFT, (B/○) for RIGHT");

                // if the x key has been pressed, set the position as left
                if(currentGamepad1.x && !prevGamepad1.x){
                    startingPosition = StartingPosition.LEFT;
                } else if (currentGamepad1.b && !prevGamepad1.b) {
                    startingPosition = StartingPosition.RIGHT;
                }
            }
            if(allianceColor == null && startingPosition != null){
                telemetry.addLine("Select your alliance color:");
                telemetry.addLine("Press (X/□) for BLUE, (B/○) for RED");

                // if the x key has been pressed, set the color to be blue
                if(currentGamepad1.x && !prevGamepad1.x){
                    allianceColor = AllianceColor.BLUE;
                } else if (currentGamepad1.b && !prevGamepad1.b) {
                    allianceColor = AllianceColor.RED;
                }
            }
            if(scoringVersion == null && allianceColor != null && startingPosition != null){
                telemetry.addLine("Select the version of scoring:");
                telemetry.addLine("Press (X/□) for PARK ONLY, (B/○) for HANG SPECIMEN AND PARK");

                // if the x key has been pressed, set the color to be blue
                if(currentGamepad1.x && !prevGamepad1.x){
                    scoringVersion = ScoringVersion.PARK;
                } else if (currentGamepad1.b && !prevGamepad1.b) {
                    scoringVersion = ScoringVersion.HANG;
                }
            }

            if(delaySet == false && scoringVersion != null && allianceColor != null && startingPosition != null){
                telemetry.addLine("Select how long to wait before beginning autonomous:");
                telemetry.addLine("Press (X/□) for 0 SECONDS, (Y/triangle) for 3 SECONDS, OR (B/○) for 5 SECONDS");

                // if the x key has been pressed, set the color to be blue
                if(currentGamepad1.x && !prevGamepad1.x){
                    delay = 0;
                    delaySet = true;
                }
                if (currentGamepad1.y && !prevGamepad1.y){
                    delay = 3;
                    delaySet = true;
                } else if (currentGamepad1.b && !prevGamepad1.b) {
                    delay = 5;
                    delaySet = true;
                }
            }
            if (startingPosition != null && allianceColor != null && scoringVersion != null && delaySet != false){
                // If opmode has been configured
                isConfigured = true;
            }
            telemetry.update();
//
//            if(!delaySet && isConfigured) {
//                telemetry.addLine("DELAY SETTING");
//                telemetry.addLine("-------------");
//                telemetry.addLine("");
//                telemetry.addLine("Select a Delay using the d-pad UP and DOWN buttons");
//                telemetry.addData("Current Delay (s)", delay);
//
//                if(currentGamepad1.dpad_up && !prevGamepad1.dpad_up) {
//                    delay++;
//                } else if (currentGamepad1.dpad_down && !prevGamepad1.dpad_down) {
//                    delay--;
//                }
//
//                if(currentGamepad1.a && !prevGamepad1.a){
//                    delaySet = true;
//                }
//            }
//            telemetry.update();
//        }

        // inform the driver that the setup is complete
        telemetry.addLine("CONFIGURATION COMPLETE");
        telemetry.addLine("----------------------");
        telemetry.addData("Starting Position: ", startingPosition);
        telemetry.addData("Alliance Color: ", allianceColor);
        telemetry.addData("Scoring version; ", scoringVersion);
        telemetry.addData("Delay: ", delay);
        telemetry.update();

        //wait for the game to start
        waitForStart();

        this.sleep((long)1000*delay);

        // BASIC VERSION, NO LOCALIZATION FUNCTIONS
        if(allianceColor == AllianceColor.RED){
            if(startingPosition == StartingPosition.LEFT){
                if(scoringVersion == ScoringVersion.PARK){
                    // Red, Left pos, only parking
                    Driver.drive(this, ports, 2, 200, 0);
                }
                else if(scoringVersion == ScoringVersion.HANG){
                    // Red, Left pos, hanging specimen, then parking
                    continue;
                }
            }
            else if(startingPosition == StartingPosition.RIGHT){
                if(scoringVersion == ScoringVersion.PARK){
                    // Red, Right pos, only parking
                    Driver.drive(this, ports, 2, 85, 0);
                }
                else if(scoringVersion == ScoringVersion.HANG){
                    // Red, Right pos, hanging specimen, then parking

                    // STARTS WITH A SPECIMEN IN THE OUTTAKE CLAW
//                    
//
//
//                    // HANGING SEQUENCE
//                    Driver.intakeClaw(ports, 0);  // INTAKE CLAW -  it's either pos 0 or pos 0.85 so change if it's wrong
//                    Driver.outtakeClaw(ports, 0.25); // OUTTAKE CLAW, pos either this or 0 to open it
//
//
//                    Driver.drive(this, ports, 2, 85, 90);
//                    Driver.drive(this, ports, 2, 52, 180);
//
//                    Driver.linearSlidesVUp(ports, 2, 1600); // if this isn't working then maybe go into driver class and change one slide motor power to be negative and one positive
//                    Driver.outtakeClawUp(ports);
//
//                    Driver.linearSlidesVUp(ports, 2, 1385);
//                    Driver.drive(this, ports, 2, 20, 270);
//
//                    Driver.linearSlidesVDown(ports, 2, 0);
//                    Driver.drive(this, ports, 2, 65, 180);
//
//
                }
            }
        }

        }



//        if(startingPosition == StartingPosition.LEFT){
//            drive(this, ports, speed, 80, 0);
//            drive(this, ports, speed, 300, 180);
//        } else if (startingPosition == StartingPosition.RIGHT) {
//            drive(this, ports, speed, 84.853, 45);
//            drive(this, ports, speed, 120, 0);
//            rotate(this, ports, speed, -45);
//            drive(this, ports, speed, 70, 0);
//            drive(this, ports, speed, 70, 180);
//            rotate(this, ports, speed, 45);
//            drive(this, ports, speed, 200, 180);
//            drive(this, ports, speed, 70, -135);
//        }
    }
}