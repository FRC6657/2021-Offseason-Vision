// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.utils.DPadButton;
import frc.robot.utils.DPadButton.Direction;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  //Subsystem Declarations
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Outtake m_outtake = new Outtake();
  private final Intake m_intake = new Intake();
  private final Agitator m_agipotato = new Agitator();

  //Controller Declarations
  private final XboxController m_controller = new XboxController(0);

  //Shuffleboard Declarations
  private final SendableChooser<Command> m_chooser = new SendableChooser<>(); //Auto Chooser

  public RobotContainer() {
    configureButtonBindings();
  }

  /**
   * Autonomous for automatically aiming and shooting at a target
   */
  public class AimShoot extends SequentialCommandGroup {
    public AimShoot(){
      addCommands(  
        new Seek(m_drivetrain),
        new VisionAim(m_drivetrain),
        new cOuttake(m_outtake).withTimeout(2) 
      );
    }
  }

  @SuppressWarnings("unused")
  private void configureButtonBindings() {
    
    //Default Command Declarations
    CommandScheduler.getInstance().setDefaultCommand(m_drivetrain,
      new TeleOp(
      m_drivetrain, //Drivetrain Subsystem
      () -> (m_controller.getRawAxis(XboxController.Axis.kRightTrigger.value) - m_controller.getRawAxis(XboxController.Axis.kLeftTrigger.value)) * (SmartDashboard.getNumber("speed-multiplier", 100)/100),
      () -> m_controller.getRawAxis(XboxController.Axis.kRightX.value) * SmartDashboard.getNumber("speed-multiplier", 100)/100
    ));

    //Every button input on the XBox Controller
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

    //Controller Bindings
    rBumper.whenHeld(new cOuttake(m_outtake));
    lBumper.whenHeld(new cIntake(m_intake, 0.4));
    start.whenHeld(new cIntake(m_intake, -0.4).withTimeout(0.05));
    dPadRight.whenHeld(new cAgitator(m_agipotato, 1.0));
    dPadLeft.whenHeld(new cAgitator(m_agipotato, -1.0));

    //Auto Chooser population
    m_chooser.setDefaultOption("Aim", new AimShoot()); //Aim to target then end

    //Shuffleboard objects
    SmartDashboard.putData("auto-chooser", m_chooser); //Send the Auto Chooser
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected(); //Get the selected auto from the chooser
  }
}
