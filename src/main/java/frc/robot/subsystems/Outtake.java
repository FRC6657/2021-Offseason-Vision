/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Outtake extends SubsystemBase {

  //Motor Declaration
  private WPI_TalonSRX outtakeLeft;
  private WPI_TalonSRX outtakeRight;

  //Servo Declaration
  private Servo gate;

  public Outtake() {

    //Motor CAN Assignment
    outtakeLeft = new WPI_TalonSRX(7);
    outtakeRight = new WPI_TalonSRX(8);

    //Servo PWM Assignment
    gate = new Servo(9);

    //Disables brake mode
    outtakeLeft.setNeutralMode(NeutralMode.Coast);
    outtakeRight.setNeutralMode(NeutralMode.Coast);

  }
  //Outtakes
  public void outtake(double speed) {

    //I have no clue who the speeds both need to be negative
    outtakeLeft.set(-speed);
    outtakeRight.set(-speed);

  }
  //Sets the Hopper Gate Servo angle
  public void setServoAngle(double angle) {

    gate.setAngle(angle);

  }

  @Override
  public void periodic() {

  }
}
