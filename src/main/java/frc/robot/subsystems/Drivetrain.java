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

  private WPI_TalonSRX m_frontLeft;
  private WPI_VictorSPX m_backLeft;
  private WPI_TalonSRX m_frontRight;
  private WPI_VictorSPX m_backRight;

  private SpeedControllerGroup m_leftmotors;
  private SpeedControllerGroup m_rightmotors;

  private LimeLight m_limelight = new LimeLight();

  public Drivetrain() {

    m_frontLeft = new WPI_TalonSRX(1);
    m_backLeft = new WPI_VictorSPX(2);
    m_frontRight = new WPI_TalonSRX(3);
    m_backRight = new WPI_VictorSPX(4);

    m_frontLeft.setNeutralMode(NeutralMode.Brake);
    m_backLeft.setNeutralMode(NeutralMode.Brake);
    m_frontRight.setNeutralMode(NeutralMode.Brake);
    m_backRight.setNeutralMode(NeutralMode.Brake);
    
  }

  public void comboDrive(double xSpeed, double zRotation) {

    //xSpeed = MathUtil.clamp(xSpeed, -1, 1); //Limits input to an acceptable range
    //zRotation = MathUtil.clamp(zRotation, -1, 1); //Limits input to an acceptable range

    //xSpeed = applyDeadband(xSpeed, 0.05); //Apply a small deadband to inputs
    //zRotation = applyDeadband(xSpeed, 0.05); //Apply a small deadband to inputs

    double[] wheelSpeeds = new double[2]; //Wheel speed array

    wheelSpeeds[0] = xSpeed + zRotation; //Left Speed
    wheelSpeeds[1] = -(xSpeed - zRotation); //Right Speed

    normalize(wheelSpeeds); //Maintain magnitude while staying below an output of 1
  
    SmartDashboard.putNumber("left-motor", wheelSpeeds[0] * 0.5); //Put left motor power on the Dashboard
    SmartDashboard.putNumber("right-motor", wheelSpeeds[1] * 0.5); //Put right motor power on the Dashboard

    //m_leftmotors.set(wheelSpeeds[0] * 0.5); //Set Left Motor Speed
    //m_rightmotors.set(wheelSpeeds[1] * 0.5); //Set Right Motor Speed

  }

  /**
   * @param wheelSpeeds Array of wheel speeds to normalize
   * 
   * This function takes an array of values and normalize them.
   * Keeping all of the values in line with what should be passed
   * to the motors while also preserving the magnitude difference
   * between each values.
   */
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
  private double applyDeadband(double input, double deadband) {
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
   * Autonomous Vision Drive
  */
  public void visionDrive(){

    double kpAim = -0.1;
    double kpDistance = -0.1;

    double min_command = 0.05;

    boolean tv = m_limelight.getIsTargetFound();
    double tx = m_limelight.getdegRotationToTarget();
    double ty = m_limelight.getdegVerticalToTarget();

    double xSpeed = 0;
    double zRotation = 0;

    if(tv){

      double horizontalError = -tx;
      double distanceError = -ty;

      if(tx > 1){
        zRotation = kpAim * horizontalError - min_command; 
      }
      if(tx < 1){
        zRotation = kpAim * horizontalError + min_command; 
      }

      xSpeed = kpDistance * distanceError;

      comboDrive(xSpeed, zRotation);      

    }
    else{

      zRotation = 0.3;

    }

  }

  /*
   * TeleOp Vision Drive
   * 
   * @param xSpeed
   * @param zRotation
   */
  public double[] visionDrive(double xSpeed, double zRotation){

    double kpAim = -0.1;
    double kpDistance = -0.1;

    double min_command = 0.05;

    double tx = m_limelight.getdegRotationToTarget();
    double ty = m_limelight.getdegVerticalToTarget();

    double horizontalError = -tx;
    double distanceError = -ty;

    if(tx > 1){
      zRotation -= kpAim * horizontalError - min_command; 
    }
    if(tx < 1){
      zRotation += kpAim * horizontalError + min_command; 
    }

    xSpeed += kpDistance * distanceError;

    double[] adjustments = {xSpeed,zRotation};

    return adjustments;

  }

  public LimeLight getLimelight(){
    return m_limelight;
  }

  @Override
  public void periodic() {



  }
  @Override
  public void simulationPeriodic() {
  }
}
