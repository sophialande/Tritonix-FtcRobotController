package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.hardware.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.classes.PIDController;
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
@Autonomous(name="Auto Pushbot Adaptive")
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

    PIDController lsv_lController;
    PIDController lsv_rController;

    // CONTROL FUNCTIONS FOR DASHBOARD
    public static int delayNum; // 0 = 0, 3 = 3, 5 = 5
    public static int teamColor;
    public static int parkVsHang;
    public static int startingPos;



    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        builder = new Ports.Builder();
        builder.wheelsActive = true;
        builder.servosActive = true;
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
        allianceColor = null;
        scoringVersion = null;

        lsv_lController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_l);
        lsv_rController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_r);


        // This loop will only end once the op mode has been configured or when it's been stopped
//
//        while(!isStopRequested() && !isConfigured && !delaySet){
//
//            // Set the value of prevGamepad1 to the value of from the previous loop
//            prevGamepad1.copy(currentGamepad1);
//
//            // Set the value of currentGamepad1 to the current state of the gamepad
//            currentGamepad1.copy(gamepad1);
//
//            telemetry.addLine("CONFIGURE AUTONOMOUS");
//            telemetry.addLine("--------------------");
//            telemetry.addLine("");
//
//            // If there isn't a starting position, as for one to be inputted and record it
//            if(startingPosition == null){
//                telemetry.addLine("Select a starting position");
//                telemetry.addLine("Press (X/□) for LEFT, (B/○) for RIGHT");
//
//                // if the x key has been pressed, set the position as left
//                if(currentGamepad1.x && !prevGamepad1.x){
//                    startingPosition = StartingPosition.LEFT;
//                } else if (currentGamepad1.b && !prevGamepad1.b) {
//                    startingPosition = StartingPosition.RIGHT;
//                }
//            } else{
//                // If there is a starting position, say that the opmode has been configured
//                isConfigured = true;
//            }
//            telemetry.update();
//

//        while(!isStopRequested() && !isConfigured && !delaySet){
//            telemetry.addLine("hi");
//            telemetry.update();
//
//            // Set the value of prevGamepad1 to the value of from the previous loop
//            prevGamepad1.copy(currentGamepad1);
//
//            // Set the value of currentGamepad1 to the current state of the gamepad
//            currentGamepad1.copy(gamepad1);
//
//            telemetry.addLine("CONFIGURE AUTONOMOUS");
//            telemetry.addLine("--------------------");
//            telemetry.addLine("");
//
//            // If there isn't a starting position, as for one to be inputted and record it
//            if(startingPosition == null){
//                telemetry.addLine("Select a starting position:");
//                telemetry.addLine("Press (X/□) for LEFT, (B/○) for RIGHT");
//
//                // if the x key has been pressed, set the position as left
//                if(currentGamepad1.x && !prevGamepad1.x){
//                    startingPosition = StartingPosition.LEFT;
//                } else if (currentGamepad1.b && !prevGamepad1.b) {
//                    startingPosition = StartingPosition.RIGHT;
//                }
//                telemetry.update();
//            }
//            if(allianceColor == null && startingPosition != null){
//                telemetry.addLine("Select your alliance color:");
//                telemetry.addLine("Press (X/□) for BLUE, (B/○) for RED");
//
//                // if the x key has been pressed, set the color to be blue
//                if(currentGamepad1.x && !prevGamepad1.x){
//                    allianceColor = AllianceColor.BLUE;
//                } else if (currentGamepad1.b && !prevGamepad1.b) {
//                    allianceColor = AllianceColor.RED;
//                }
//                telemetry.update();
//            }
//            if(scoringVersion == null && allianceColor != null && startingPosition != null){
//                telemetry.addLine("Select the version of scoring:");
//                telemetry.addLine("Press (X/□) for PARK ONLY, (B/○) for HANG SPECIMEN AND PARK");
//
//                // if the x key has been pressed, set the color to be blue
//                if(currentGamepad1.x && !prevGamepad1.x){
//                    scoringVersion = ScoringVersion.PARK;
//                } else if (currentGamepad1.b && !prevGamepad1.b) {
//                    scoringVersion = ScoringVersion.HANG;
//                }
//                telemetry.update();
//            }
//
//            if(delaySet == false && scoringVersion != null && allianceColor != null && startingPosition != null){
//                telemetry.addLine("Select how long to wait before beginning autonomous:");
//                telemetry.addLine("Press (X/□) for 0 SECONDS, (Y/triangle) for 3 SECONDS, OR (B/○) for 5 SECONDS");
//
//                // if the x key has been pressed, set the color to be blue
//                if(currentGamepad1.x && !prevGamepad1.x){
//                    delay = 0;
//                    delaySet = true;
//                }
//                if (currentGamepad1.y && !prevGamepad1.y){
//                    delay = 3;
//                    delaySet = true;
//                } else if (currentGamepad1.b && !prevGamepad1.b) {
//                    delay = 5;
//                    delaySet = true;
//                }
//                telemetry.update();
//            }
//            if (startingPosition != null && allianceColor != null && scoringVersion != null && delaySet != false){
//                // If opmode has been configured
//                isConfigured = true;
//            }
//            telemetry.update();
////
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
//        telemetry.addLine("CONFIGURATION COMPLETE");
//        telemetry.addLine("----------------------");
//        telemetry.addData("Starting Position: ", startingPosition);
//        telemetry.addData("Alliance Color: ", allianceColor);
//        telemetry.addData("Scoring version; ", scoringVersion);
//        telemetry.addData("Delay: ", delay);
//        telemetry.update();

        //wait for the game to start
//        waitForStart();

//        this.sleep((long)1000*delay);

        // BASIC VERSION, NO LOCALIZATION FUNCTIONS
//        if(allianceColor == AllianceColor.RED){
//            if(startingPosition == StartingPosition.LEFT){
//                if(scoringVersion == ScoringVersion.PARK){
//                    // Red, Left pos, only parking
//                    telemetry.addLine("Red, Left pos, only parking");
//                }
//                else if(scoringVersion == ScoringVersion.HANG){
//                    // Red, Left pos, hanging specimen, then parking
//                    telemetry.addLine("Red, Left pos, hanging specimen, then parking");
//                }
//            }
//            else if(startingPosition == StartingPosition.RIGHT){
//                if(scoringVersion == ScoringVersion.PARK){
//                    // Red, Right pos, only parking
//                    telemetry.addLine("Red, right pos, only parking");
//                }
//                else if(scoringVersion == ScoringVersion.HANG){
//                    // Red, Right pos, hanging specimen, then parking
//
//                    telemetry.addLine("Red, Right pos, hanging specimen, then parking");
//
//                }
//            }
//
//        }

//        }

//        }


        waitForStart();

        if(teamColor==0){
            if(startingPos==0){
                if(parkVsHang==0){
                    if(delayNum==0){
                        // COLOR BLUE, POSITION LEFT, PARKING ONLY, 0 SECOND DELAY

                    } else if (delayNum==3){
                        // COLOR BLUE, POSITION LEFT, PARKING ONLY, 3 SECOND DELAY
                        sleep(3000);
                    } else if (delayNum==5){
                        // COLOR BLUE, POSITION LEFT, PARKING ONLY, 5 SECOND DELAY
                        sleep(5000);
                    }
                } else if (parkVsHang==1){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                }
            } else if (startingPos==1){
                if(parkVsHang==0){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                } else if (parkVsHang==1){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                }
            }
        } else if (teamColor==1){
            if(startingPos==0){
                if(parkVsHang==0){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                } else if (parkVsHang==1){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                }
            } else if (startingPos==1){
                if(parkVsHang==0){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                } else if (parkVsHang==1){
                    if(delayNum==0){

                    } else if (delayNum==3){

                    } else if (delayNum==5){

                    }
                }
            }
        }

        telemetry.update();
        sleep(10000);

    }
}