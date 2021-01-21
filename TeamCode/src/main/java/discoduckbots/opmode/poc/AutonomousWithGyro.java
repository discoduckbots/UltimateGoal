package discoduckbots.opmode.poc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Gyro Autonomous", group="Linear Opmode")
public class AutonomousWithGyro extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;

    private static final double AUTONOMOUS_SPEED = 0.65;
    private static final double ROTATION_SPEED = 0.4;
    private static final double HIGH_GOAL_SHOOT_POWER = 0.55;

    @Override
    public void runOpMode() throws InterruptedException {
        runAutonomous(AUTONOMOUS_SPEED, ROTATION_SPEED, HIGH_GOAL_SHOOT_POWER);
    }

    protected void runAutonomous(double autonomousSpeed, double rotationSpeed, double highGoalShootPower){
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        wobbleMover = hardwareStore.getWobbleMover();
        shooter = hardwareStore.getShooter();

        // wait for start
        waitForStart();
        runtime.reset();

        wobbleMover.grab();
        mecanumDrivetrain.driveByGyro(6, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed,0);
        sleep(500);
        mecanumDrivetrain.driveByGyro(20, MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed, 0);
        sleep(500);
        wobbleMover.drop(this);
        mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, autonomousSpeed,0);

        mecanumDrivetrain.gyroTurn(-80, rotationSpeed, this);
        sleep(500);
        shooter.shoot(highGoalShootPower);

        sleep(1000);

        for (int i = 0; i<4; i++){
            sleep(1500);
            shooter.pushRing();
            sleep(1500);
            shooter.resetPusher();
        }

        mecanumDrivetrain.stop();
        mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed, 270);
        mecanumDrivetrain.stop();
    }
}