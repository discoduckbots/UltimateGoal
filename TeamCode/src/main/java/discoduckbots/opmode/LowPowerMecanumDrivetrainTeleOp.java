package discoduckbots.opmode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mecanum OpMode - LOW BATT", group="Linear Opmode")
public class LowPowerMecanumDrivetrainTeleOp extends MecanumDrivetrainTeleOp {
    private static final double HIGH_GOAL_SHOOTER_POWER = 0.75;
    private static final double POWER_SHOT_SHOOTER_POWER = 0.7;

    @Override
    public void runOpMode() {
        runTeleOp(HIGH_GOAL_SHOOTER_POWER, POWER_SHOT_SHOOTER_POWER);
    }
}
