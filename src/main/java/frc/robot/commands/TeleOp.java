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

  /**
   * This runs the drivetrain in the TeleOp period
   * <p>
   *
   * @param m_drivetrain the drivetrain subsystem
   * @param xSpeed the desired forward speed input
   * @param zRotation the desired turning speed input
   * 
   * @see m_drivetrain
   * 
   * @author Andrew Card
   */

  public TeleOp(Drivetrain m_drivetrain, DoubleSupplier xSpeed, DoubleSupplier zRotation) {
    
    this.m_drivetrain = m_drivetrain;
    this.xSpeed = xSpeed;
    this.zRotation = zRotation;

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {
    System.out.println("TelOp Init"); //Print to verify TeleOp is running
  }

  @Override
  public void execute() {
    m_drivetrain.teleComboDrive(xSpeed.getAsDouble(), zRotation.getAsDouble()); //Runs the drivetrain with the controller suppplied doubles
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.comboDrive(0, 0); //Stops the drivetrain when the command ends
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
