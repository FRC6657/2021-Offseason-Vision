/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Agitator extends SubsystemBase {

  private WPI_TalonSRX m_agitator;
  /**
   * Agitator Subsystem
   */
  public Agitator() {  
      m_agitator = new WPI_TalonSRX(9);
  }
  //Spins the agitator
  public void agipotate(double speed){
    m_agitator.set(speed);
  }
}
