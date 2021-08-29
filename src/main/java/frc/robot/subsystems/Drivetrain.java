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

  public void visionDrive(){



  }

  @Override
  public void periodic() {
  }
  @Override
  public void simulationPeriodic() {
  }
}
