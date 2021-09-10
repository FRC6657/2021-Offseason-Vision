/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class cIntake extends CommandBase {

  private final Intake m_intake;
  private final double speed;

  /**
   * This command runs the Intake when the command is executing
   *
   * @param m_intake the Intake Subsystem
   * @param speed the speed at which the intake is told to run
   * @see Intake
   * 
   * @author Andrew Card
   */

  public cIntake(Intake m_intake, double speed) {
   
    this.m_intake = m_intake;
    this.speed = speed;

    addRequirements(m_intake);

  }

  @Override
  public void execute() {
    m_intake.intake(speed); //Run the intake
  }

  @Override
  public void end(boolean interrupted) {
    m_intake.intake(0); //Stop the intake
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
