package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.util.*;

/*
 * USAGE GUIDE:
 *
 * Call setup with the current position and then in a loop call evaluate until you reach your
 * desired outcome.
 */

public class PIDController {

    //Define the parameters of the PID controller
    double Kp = 0;
    double Ki = 0;
    double Kd = 0;
    double Kf = 0;

    ElapsedTime elapsedTime = new ElapsedTime();
    double integralSum = 0;

    int lastPosition;

    void Setup(int position){
        lastPosition = position;
        elapsedTime.reset();
    }

    double Evaluate(int position){

        integralSum += position*elapsedTime.time();
        double output = position*Kp + integralSum*Ki + (position-lastPosition)*Kd/elapsedTime.time() + Kf;
        elapsedTime.reset();
        return(output);

    }
}
