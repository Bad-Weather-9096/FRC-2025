package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    public enum Position {
        TRANSPORT(32.0, 90.0),
        ALGAE_RELEASE(20.0, 215.0),
        CORAL_INTAKE(24.0, 135.0),
        LOWER_ALGAE_INTAKE(32.0, 180.0),
        UPPER_ALGAE_INTAKE(48.0, 180.0),
        LOWER_CORAL_RELEASE(40.0, 215.0),
        UPPER_CORAL_RELEASE(56.0, 215.0);

        private final double elevatorExtension; // inches
        private final double endEffectorAngle; // degrees

        Position(double elevatorExtension, double endEffectorAngle) {
            this.elevatorExtension = elevatorExtension;
            this.endEffectorAngle = endEffectorAngle;
        }
    }

    private SparkMax elevatorController;
    private SparkMax endEffectorController;

    private Position position = null;

    private static final int ELEVATOR_CAN_ID = 9;
    private static final int END_EFFECTOR_CAN_ID = 10;

    private static final double ELEVATOR_DISTANCE_PER_ROTATION = 4.0; // inches

    private static final double ELEVATOR_SPEED = 0.05; // percent
    private static final double MAXIMUM_ELEVATOR_EXTENSION = 72.0; // inches

    private static final double END_EFFECTOR_SPEED = 0.05; // percent
    private static final double MAXIMUM_END_EFFECTOR_ROTATION = 225.0; // degrees

    public ElevatorSubsystem() {
        elevatorController = new SparkMax(ELEVATOR_CAN_ID, SparkLowLevel.MotorType.kBrushless);

        var elevatorConfig = new SparkMaxConfig();

        elevatorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).smartCurrentLimit(40);

        elevatorController.configure(elevatorConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters);

        elevatorController.getEncoder().setPosition(0.0);

        endEffectorController = new SparkMax(END_EFFECTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless);

        var endEffectorConfig = new SparkMaxConfig();

        endEffectorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).smartCurrentLimit(40);

        endEffectorController.configure(endEffectorConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters);

        endEffectorController.getEncoder().setPosition(0.0);
    }

    public double getElevatorExtension() {
        var position = elevatorController.getEncoder().getPosition();

        SmartDashboard.putNumber("elevator-position", position);

        return position * ELEVATOR_DISTANCE_PER_ROTATION;
    }

    public void raiseElevator() {
        adjustPosition(null);

        elevatorController.set(getElevatorExtension() < MAXIMUM_ELEVATOR_EXTENSION ? ELEVATOR_SPEED : 0.0);
    }

    public void lowerElevator() {
        adjustPosition(null);

        elevatorController.set(getElevatorExtension() > 0 ? -ELEVATOR_SPEED : 0.0);
    }

    public void stopElevator() {
        if (position == null) {
            elevatorController.set(0.0);
        }
    }

    public double getEndEffectorAngle() {
        var position = endEffectorController.getEncoder().getPosition();

        SmartDashboard.putNumber("end-effector-position", position);

        return position * 360.0;
    }

    public void raiseEndEffector() {
        adjustPosition(null);

        endEffectorController.set(getEndEffectorAngle() > 0 ? -END_EFFECTOR_SPEED : 0.0);
    }

    public void lowerEndEffector() {
        adjustPosition(null);

        endEffectorController.set(getEndEffectorAngle() < MAXIMUM_END_EFFECTOR_ROTATION ? END_EFFECTOR_SPEED : 0.0);
    }

    public void stopEndEffector() {
        if (position == null) {
            endEffectorController.set(0.0);
        }
    }

    public void adjustPosition(Position position) {
        this.position = position;
    }

    @Override
    public void periodic() {
        var elevatorExtension = getElevatorExtension();

        if (position != null) {
            var elevatorExtensionDelta = position.elevatorExtension - elevatorExtension;
            var elevatorSpeed = Math.signum(elevatorExtensionDelta) * ELEVATOR_SPEED;

            endEffectorController.set(elevatorSpeed);
        }

        SmartDashboard.putNumber("elevator-extension", elevatorExtension);

        var endEffectorAngle = getEndEffectorAngle();

        if (position != null) {
            var endEffectorAngleDelta = position.endEffectorAngle - endEffectorAngle;
            var endEffectorSpeed = Math.signum(endEffectorAngleDelta) * END_EFFECTOR_SPEED;

            endEffectorController.set(endEffectorSpeed);
        }

        SmartDashboard.putNumber("end-effector-angle", endEffectorAngle);
    }
}
