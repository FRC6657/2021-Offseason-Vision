// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
