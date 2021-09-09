// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.utils.DPadButton;
import frc.robot.utils.DPadButton.Direction;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain(); //Drivetrain Subsystem
  private final Outtake m_outtake = new Outtake();
  private final Intake m_intake = new Intake();
  private final Agipotato m_agipotato = new Agipotato();

  private final XboxController m_controller = new XboxController(0); //Controller
  private final SendableChooser<Command> m_chooser = new SendableChooser<>(); //Auto Chooser
  private final AimBot m_aimbot = new AimBot(m_drivetrain); //Aimbot Command
  private final MinCommandTesting m_mintest = new MinCommandTesting(m_drivetrain, 0.1);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    
    CommandScheduler.getInstance().setDefaultCommand(m_drivetrain,
      new TeleOp(
      m_drivetrain, //Drivetrain Subsystem
      () -> (m_controller.getRawAxis(XboxController.Axis.kRightTrigger.value) - m_controller.getRawAxis(XboxController.Axis.kLeftTrigger.value)) * SmartDashboard.getNumber("speed-multiplier", 25)/100,
      () -> m_controller.getRawAxis(XboxController.Axis.kRightX.value) * SmartDashboard.getNumber("speed-multiplier", 25)/100
    ));

    final JoystickButton a = new JoystickButton(m_controller, XboxController.Button.kA.value);
    final JoystickButton b = new JoystickButton(m_controller, XboxController.Button.kB.value);
    final JoystickButton x = new JoystickButton(m_controller, XboxController.Button.kX.value);
    final JoystickButton y = new JoystickButton(m_controller, XboxController.Button.kY.value);

    final DPadButton dPadUp = new DPadButton(m_controller, Direction.UP);
    final DPadButton dPadDown = new DPadButton(m_controller, Direction.DOWN);
    final DPadButton dPadLeft = new DPadButton(m_controller, Direction.LEFT);
    final DPadButton dPadRight = new DPadButton(m_controller, Direction.RIGHT);

    final JoystickButton rBumper = new JoystickButton(m_controller, XboxController.Button.kBumperRight.value);
    final JoystickButton lBumper = new JoystickButton(m_controller, XboxController.Button.kBumperLeft.value);

    final JoystickButton start = new JoystickButton(m_controller, XboxController.Button.kStart.value);
    final JoystickButton back = new JoystickButton(m_controller, XboxController.Button.kBack.value);

    rBumper.whenHeld(new cOuttake(m_outtake));
    lBumper.whenHeld(new cIntake(m_intake, 0.4));
    start.whenHeld(new cIntake(m_intake, -0.4).withTimeout(0.05));
    dPadRight.whenHeld(new Agipotate(m_agipotato, 1.0));
    dPadLeft.whenHeld(new Agipotate(m_agipotato, -1.0));

    m_chooser.setDefaultOption("Aim", m_aimbot); //Aim to target then end
    m_chooser.addOption("MinTesting", m_mintest);

    SmartDashboard.putData("auto-chooser", m_chooser); //Send the Auto Chooser
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected(); //Get the selected auto from the chooser
  }
}
