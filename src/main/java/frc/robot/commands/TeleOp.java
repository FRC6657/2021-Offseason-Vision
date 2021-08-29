// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TeleOp extends CommandBase {

  Drivetrain m_drivetrain;
  double xSpeed;
  double zRotation;
  boolean aimbot;

  public TeleOp(Drivetrain m_drivetrain, double xSpeed, double zRotation, boolean aimbot) {
    
    this.m_drivetrain = m_drivetrain;
    this.xSpeed = xSpeed;
    this.zRotation = zRotation;
    this.aimbot = aimbot;

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {
    System.out.println("TelOp Init");
  }

  @Override
  public void execute() {

    if(aimbot){

      double[] adjustments = m_drivetrain.visionDrive(xSpeed, zRotation);
      m_drivetrain.comboDrive(xSpeed-adjustments[0], zRotation-adjustments[1]);

    }
    else{

      m_drivetrain.comboDrive(xSpeed, zRotation);

    }
  }

  @Override
  public void end(boolean interrupted) {

    m_drivetrain.comboDrive(0, 0);

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
