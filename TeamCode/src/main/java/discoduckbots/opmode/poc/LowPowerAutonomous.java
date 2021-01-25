package discoduckbots.opmode.poc;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Gyro Autonomous - LOW BATT", group="Linear Opmode")
public class LowPowerAutonomous extends AutonomousWithGyro{

    private static final double AUTONOMOUS_SPEED = 0.7;
    private static final double ROTATION_SPEED = 0.4;
    private static final double HIGH_GOAL_SHOOT_POWER = 0.6;


    @Override
    public void runOpMode() throws InterruptedException {
        runAutonomous(AUTONOMOUS_SPEED, ROTATION_SPEED, HIGH_GOAL_SHOOT_POWER);
    }

}
