// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import oi.limelightvision.limelight.frc.LimeLight;

public class Drivetrain extends SubsystemBase {

  //Motor Declaration
  private WPI_TalonSRX m_frontLeft;
  private WPI_VictorSPX m_backLeft;
  private WPI_TalonSRX m_frontRight;
  private WPI_VictorSPX m_backRight;

  //Motor Groups
  private SpeedControllerGroup m_leftmotors;
  private SpeedControllerGroup m_rightmotors;

  //Limelight 🙏
  private LimeLight m_limelight = new LimeLight();

  public Drivetrain() {

    //Motor CAN Assignments
    m_frontLeft = new WPI_TalonSRX(1);
    m_backLeft = new WPI_VictorSPX(2);
    m_frontRight = new WPI_TalonSRX(3);
    m_backRight = new WPI_VictorSPX(4);

    //Enable Auto Brake
    m_frontLeft.setNeutralMode(NeutralMode.Brake);
    m_backLeft.setNeutralMode(NeutralMode.Brake);
    m_frontRight.setNeutralMode(NeutralMode.Brake);
    m_backRight.setNeutralMode(NeutralMode.Brake);

    //Assign motors to groups
    m_leftmotors = new SpeedControllerGroup(m_frontLeft, m_backLeft);
    m_rightmotors = new SpeedControllerGroup(m_frontRight, m_backRight);
    
  }

  public void comboDrive(double xSpeed, double zRotation) {
    //Prevents Extranious Input
    xSpeed = MathUtil.clamp(xSpeed, -1, 1);
    zRotation = MathUtil.clamp(zRotation, -1, 1);
  
    //Apply Deadband
    xSpeed = cubicScaledDeadband(xSpeed, 0.05, 1);
    zRotation = cubicScaledDeadband(zRotation, 0.05, 1);

    double[] wheelSpeeds = new double[2]; //Create Motor Power Array

    wheelSpeeds[0] = xSpeed + zRotation; //Left Speed
    wheelSpeeds[1] = -(xSpeed - zRotation); //Right Speed
  
    normalize(wheelSpeeds); //Scale values down while maintaining magnitude

    m_leftmotors.set(wheelSpeeds[0]); //Set Left Motor Powers
    m_rightmotors.set(wheelSpeeds[1]); //Set Right Motor Powers

    SmartDashboard.putNumber("left-motor",  m_leftmotors.get()); //Put left motor power on the Dashboard
    SmartDashboard.putNumber("right-motor", m_rightmotors.get()); //Put right motor power on the Dashboard

  }

  /**
   * @param wheelSpeeds Array of wheel speeds to normalize
   * 
   * This function takes an array of values and normalize them.
   * Keeping all of the values in line with what should be passed
   * to the motors while also preserving the magnitude difference
   * between each values.
   */
  @SuppressWarnings("unused")
  private void normalize(double[] wheelSpeeds) {
    double maxMagnitude = Math.abs(wheelSpeeds[0]);
    for (int i = 1; i < wheelSpeeds.length; i++) {
      double temp = Math.abs(wheelSpeeds[i]);
      if (maxMagnitude < temp) {
        maxMagnitude = temp;
      }
    }
    if (maxMagnitude > 1.0) {
      for (int i = 0; i < wheelSpeeds.length; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
      }
    }
  }

  /**
   * @param input    Input in need of a deadband
   * @param deadband Deadband threshold
   */
  @SuppressWarnings("unused")
  private double linearDeadband(double input, double deadband) {
    if (Math.abs(input) > deadband) {
      if (input > 0.0) {
        return (input - deadband) / (1.0 - deadband);
      } else {
        return (input + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

    /**
   * @param input    Input in need of a deadband
   * @param deadband Deadband threshold
   * @param weight   Weight of the curve
   */
  private double cubicScaledDeadband(double input, double deadband, double weight){

    double w = weight;
    double d = deadband;
    double x = input;

    double output = 0;

    if(Math.abs(x) > deadband){
      output = (((w * (x * x * x) + 1*(1 - w) * x) - (Math.abs(x)) / x * (w * (d * d * d) + (1 - w) * d)) / (1 - (w * (d * d * d) + (1 - w) * d)));
    }
    else{
      output=0;
    }
    return output;

  }
  
  /**
   * Autonomous Vision Drive
  */
  public void visionDrive(){
    //kP values
    //These are tuned so that the maximum combined output is 0.5
    double kpAim = -0.0083892617 * 1;
    double kpDistance = -0.0100603621 * 1;

    //I term replacement [set negative to disable]
    double min_command = -0.05;

    //Limelight Data
    boolean tv = m_limelight.getIsTargetFound();
    double tx = m_limelight.getdegRotationToTarget();
    double ty = m_limelight.getdegVerticalToTarget();
    
    //boolean tv = true;
    //double tx = 29.8;
    //double ty = 0;
    
    //Creates Local Speed Variables
    double xSpeed = 0;
    double zRotation = 0;

    //Go to target if it is found
    if(tv){

      //Error Values
      double horizontalError = -tx; //Turn Error
      double distanceError = ty;

      zRotation = kpAim * horizontalError;
      xSpeed = kpDistance * distanceError;

      if(Math.abs(horizontalError) < 0.5){
        horizontalError = 0;
      }
      if(Math.abs(distanceError) < 0.5){
        distanceError = 0;
      }

      if((zRotation > 0) && (zRotation < min_command)){
        zRotation += min_command;
      }
      else if((zRotation < 0) && (zRotation > -min_command)){
          zRotation -= min_command;
      }

      if((xSpeed > 0) && (xSpeed < min_command)){
          xSpeed += min_command;
      }
      else if((xSpeed < 0) && (xSpeed > -min_command)){
          xSpeed -= min_command;
      }

      comboDrive(xSpeed, zRotation); //Drive with the calculated parameters 

    }
    //Seek if no target found
    else{

      System.out.println("No Target");

      zRotation = 0.4;
      comboDrive(xSpeed, zRotation); //Turn Slowly

    }

  }

  //Allows external limelght access
  public LimeLight getLimelight(){
    return m_limelight;
  }

  public double[] getMotorValues(){
    double[] motorValues = new double[1];
    motorValues[0] = m_leftmotors.get();
    motorValues[1] = m_rightmotors.get();
    return motorValues;
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}
}
