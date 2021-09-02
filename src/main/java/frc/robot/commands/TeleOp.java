// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TeleOp extends CommandBase {

  Drivetrain m_drivetrain;
  DoubleSupplier xSpeed;
  DoubleSupplier zRotation;

  public TeleOp(Drivetrain m_drivetrain, DoubleSupplier xSpeed, DoubleSupplier zRotation) {
    
    this.m_drivetrain = m_drivetrain;
    this.xSpeed = xSpeed;
    this.zRotation = zRotation;

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {
    System.out.println("TelOp Init");
  }

  @Override
  public void execute() {
    m_drivetrain.comboDrive(-xSpeed.getAsDouble(), zRotation.getAsDouble());
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
