package discoduckbots.opmode.poc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.opmode.RingStackDetector;
import discoduckbots.sensors.TensorFlow;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="TfGyro Autonomous", group="Linear Opmode")
public class AutonomousWithGyroTensor extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;

    TensorFlow tensorFlow = null;
    RingStackDetector ringStackDetector = null;

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

        tensorFlow = new TensorFlow();
        ringStackDetector = new RingStackDetector();
        tensorFlow.initTensorflow(telemetry, hardwareMap);

        // wait for start
        waitForStart();
        runtime.reset();

        int number = ringStackDetector.getSlot(tensorFlow.detect(), telemetry);
        int distanceForward;
        int distanceBack;
        if (number == 0) {
            distanceForward = 20;
            distanceBack = 0;
        } else if (number == 1) {
            distanceBack = 11;
            distanceForward = 29;
        } else {
            distanceBack = 24;
            distanceForward = 42;
        }
        wobbleMover.grab();
        mecanumDrivetrain.driveByGyro(8, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed,0);
        sleep(500);
        mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed, 0);
        sleep(500);

        if (number == 1) {
            mecanumDrivetrain.driveByGyro(16, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, autonomousSpeed,0);
            wobbleMover.drop(this);

        } else {
            wobbleMover.drop(this);
            mecanumDrivetrain.driveByGyro(16, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, autonomousSpeed,0);
            sleep(500);
        }

        if (distanceBack > 0 ) {
            mecanumDrivetrain.driveByGyro(distanceBack, MecanumDrivetrain.DIRECTION_FORWARD, 0.5, 0);
            sleep(500);
        }

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