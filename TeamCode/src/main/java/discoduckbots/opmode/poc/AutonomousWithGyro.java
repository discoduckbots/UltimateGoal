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

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        wobbleMover = hardwareStore.getWobbleMover();
        shooter = hardwareStore.getShooter();

        double autonomousSpeed = 0.6;

        // wait for start
        waitForStart();
        runtime.reset();

        wobbleMover.grab();
        mecanumDrivetrain.driveByGyro(6, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed,0);
//        mecanumDrivetrain.driveWithColorSensor(MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed, 0);
        mecanumDrivetrain.driveByGyro(22, MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed, 0);
        wobbleMover.drop(this);
        mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, autonomousSpeed, 0);
        mecanumDrivetrain.turnLeft(this, 190);
        mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed, 90);
        shooter.shoot();

        sleep(1000);

        for (int i = 0; i<4; i++){
            sleep(1500);
            shooter.pushRing();
            sleep(1500);
            shooter.resetPusher();
        }

        mecanumDrivetrain.stop();
        mecanumDrivetrain.driveByGyro(1, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed, 90);
        mecanumDrivetrain.stop();
    }
}
