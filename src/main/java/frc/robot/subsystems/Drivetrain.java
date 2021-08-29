// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  private WPI_TalonSRX m_frontLeft;
  private WPI_VictorSPX m_backLeft;
  private WPI_TalonSRX m_frontRight;
  private WPI_VictorSPX m_backRight;

  private SpeedControllerGroup m_leftmotors;
  private SpeedControllerGroup m_rightmotors;

  private NetworkTableInstance m_limelight = NetworkTableInstance.getDefault();
  private NetworkTable m_visionData = m_limelight.getTable("limelight");

  public Drivetrain() {

    m_frontLeft = new WPI_TalonSRX(1);
    m_frontRight = new WPI_TalonSRX(2);
    m_backLeft = new WPI_VictorSPX(3);
    m_backRight = new WPI_VictorSPX(4);

    m_frontLeft.setNeutralMode(NeutralMode.Coast);
    m_backLeft.setNeutralMode(NeutralMode.Coast);
    m_frontRight.setNeutralMode(NeutralMode.Coast);
    m_backRight.setNeutralMode(NeutralMode.Coast);
    
  }

  public void comboDrive(double xSpeed, double zRotation) {

    double leftPower = xSpeed + zRotation;
    double rightPower = -(xSpeed - zRotation);
  
    m_leftmotors.set(leftPower);
    m_rightmotors.set(rightPower);

  }

  /**
   * Autonomous Vision Drive
   */
  public void visionDrive(){

    double kpAim = -0.1;
    double kpDistance = -0.1;

    double min_command = 0.05;

    double tv = m_visionData.getEntry("tv").getDouble(0.0); //Does the limelight have a target 1 or 0
    double tx = m_visionData.getEntry("tx").getDouble(0.0); //Horizontal Offset from crosshair
    double ty = m_visionData.getEntry("ty").getDouble(0.0); //Vertical Offset from crosshair

    double xSpeed = 0;
    double zRotation = 0;

    if(tv != 0){

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

  /**
   * TeleOp Vision Drive
   * 
   * @param xSpeed
   * @param zRotation
   */
  public double[] visionDrive(double xSpeed, double zRotation){

    double kpAim = -0.1;
    double kpDistance = -0.1;

    double min_command = 0.05;

    double tv = m_visionData.getEntry("tv").getDouble(0.0); //Does the limelight have a target 1 or 0
    double tx = m_visionData.getEntry("tx").getDouble(0.0); //Horizontal Offset from crosshair
    double ty = m_visionData.getEntry("ty").getDouble(0.0); //Vertical Offset from crosshair

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

  public NetworkTable visionData(){
    return m_visionData;
  }

  @Override
  public void periodic() {
  }
  @Override
  public void simulationPeriodic() {
  }
}
