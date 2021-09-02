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

    xSpeed = MathUtil.clamp(xSpeed, -1, 1); //Prevents Extranious Input
    zRotation = MathUtil.clamp(zRotation, -1, 1); //Prevents Extranious Input

    xSpeed = applyDeadband(xSpeed, 0.05); //Prevent Unwanted Input
    zRotation = applyDeadband(zRotation, 0.05); //Prevent Unwanted Input

    double[] wheelSpeeds = new double[2]; //Create Motor Power Array

    wheelSpeeds[0] = xSpeed + zRotation; //Left Speed
    wheelSpeeds[1] = -(xSpeed - zRotation); //Right Speed
  
    normalize(wheelSpeeds); //Scale values down while maintaining magnitude

    wheelSpeeds[0] *= 0.5; //Lower Output
    wheelSpeeds[1] *= 0.5; //Lower Output

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
    //kP values
    double kpAim = 0.06;
    double kpDistance = -0.04;

    //I term replacement
    double min_command = 0.1;

    //Limelight Data
    /*
    boolean tv = m_limelight.getIsTargetFound();
    double tx = m_limelight.getdegRotationToTarget();
    double ty = m_limelight.getdegVerticalToTarget();
    */

    boolean tv = true;
    double tx = 0; //+-29.8
    double ty = -12.4; //+-24.85

    //Creates Local Speed Variables
    double xSpeed = 0;
    double zRotation = 0;

    //Go to target if it is found
    if(tv){

      //Error Values
      double horizontalError = -tx; //Turn Error
      double distanceError = ty;

      //Turn left or 
      zRotation = kpAim * horizontalError;

      //Calculate Distance
      xSpeed = kpDistance * distanceError;

      comboDrive(xSpeed, zRotation); //Drive with the calculated parameters 

    }
    //Seek if no target found
    else{

      System.out.println("No Target");

      zRotation = 0.25;
      comboDrive(xSpeed, zRotation); //Turn Slowly

    }

  }

  //Allows external limelght access
  public LimeLight getLimelight(){
    return m_limelight;
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}
}
