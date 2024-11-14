package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.*;

/*
 * USAGE GUIDE:
 *
 * Call setup with the current position and then in a loop call evaluate until you reach your
 * desired outcome.
 */

public class PIDController {

    //Define the parameters of the PID controller
    double Kp;
    double Ki;
    double Kd;
    double Kf;

    public PIDController(double Kp, double Ki, double Kd, double Kf){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kf = Kf;
    }

    ElapsedTime elapsedTime = new ElapsedTime();
    double integralSum = 0;

    int lastError;

    void setup(int error){
        lastError = error;
        elapsedTime.reset();
    }

    double evaluate(int error){
        integralSum += error * elapsedTime.time();
        double output = error * Kp + integralSum*Ki + (error - lastError)*Kd/elapsedTime.time() + Kf;
        elapsedTime.reset();
        return(output);
    }
}
