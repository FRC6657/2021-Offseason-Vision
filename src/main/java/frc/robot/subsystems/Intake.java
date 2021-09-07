/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private WPI_TalonSRX Intake;
  /**
   * 
   * Intake Subsystem
   * 
   */
  public Intake() {

      Intake = new WPI_TalonSRX(5);

      Intake.setNeutralMode(NeutralMode.Brake);

  }
  //Runs intake
  public void intake(double speed){

    Intake.set(speed);

  }

  @Override
  public void periodic() {}
}
