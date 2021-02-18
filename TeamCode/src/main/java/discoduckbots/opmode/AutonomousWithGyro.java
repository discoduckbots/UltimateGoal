package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Gyro Autonomous", group="Linear Opmode")
@Disabled
public class AutonomousWithGyro extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;

    private static final double AUTONOMOUS_SPEED = 0.65;
    private static final double ROTATION_SPEED = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        wobbleMover = hardwareStore.getWobbleMover();
        shooter = hardwareStore.getShooter();

        // wait for start
        waitForStart();
        runtime.reset();

        wobbleMover.grab();
        mecanumDrivetrain.driveByGyro(6, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
        sleep(500);
        mecanumDrivetrain.driveByGyro(20, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
        sleep(500);
        wobbleMover.drop(this);
        mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);

        mecanumDrivetrain.gyroTurn(-80, ROTATION_SPEED, this);
        sleep(500);
        shooter.setPowerForHighGoal();

        sleep(1000);

        for (int i = 0; i<4; i++){
            sleep(1500);
            shooter.pushRing();
            sleep(1500);
            shooter.resetPusher();
        }

        mecanumDrivetrain.stop();
        mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 270);
        mecanumDrivetrain.stop();
    }
}