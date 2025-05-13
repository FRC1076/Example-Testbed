package frc.robot.subsystems.testbed;

import frc.robot.Constants.TestbedConstants;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

public class TestbedSubsystem extends SubsystemBase{
    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;
    private final SparkMaxConfig m_motorConfig;

    public TestbedSubsystem() {
        // Initialize motor controller and encoder
        m_motor = new SparkMax(TestbedConstants.kTestbedMotorID, MotorType.kBrushless);
        m_encoder = m_motor.getEncoder();

        // Initialize motor configuration
        m_motorConfig = new SparkMaxConfig();
        m_motorConfig
            .smartCurrentLimit(TestbedConstants.kCurrentLimit)
        .encoder
            .positionConversionFactor(TestbedConstants.kPositionConversionFactor) // For the testbed, this value is 1:1, because there are no gears.
            .velocityConversionFactor(TestbedConstants.kVelocityConversionFactor); // Convert from RPM to rotations per second. If there were gears, you would also multiply by the gear ratio.

        // Apply motor configuration
        m_motor.configure(m_motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void setVoltage(double voltage) {
        m_motor.setVoltage(voltage);
    }

    // Returns the position of the encoder in rotations
    public double getPosition() {
        return m_encoder.getPosition();
    }

    // Returns the velocity of the encoder in rotations per second
    public double getVelocity() {
        return m_encoder.getVelocity();
    }

    /* ########################### */
    /*   Public Command Factories  */
    /* ########################### */

    public Command runForward() {
        return runOnce(() -> setVoltage(6));
    }

    public Command stop() {
        return runOnce(() -> setVoltage(0));
    }
}
