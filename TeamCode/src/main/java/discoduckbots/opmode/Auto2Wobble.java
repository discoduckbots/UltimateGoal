package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.sensors.TensorFlow;
//test
@Disabled
public class Auto2Wobble extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
        private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;

    TensorFlow tensorFlow = null;
    RingStackDetector ringStackDetector = null;

    private static final double AUTONOMOUS_SPEED = 0.65;
    private static final double ROTATION_SPEED = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
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
            distanceForward = 19;
            distanceBack = 0;
        } else if (number == 1) {
            distanceBack = 10;
            distanceForward = 26;
        } else {
            distanceBack = 24;
            distanceForward = 42;
        }
        wobbleMover.grab();
        mecanumDrivetrain.driveByGyro(8, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
        sleep(500);
        mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
        sleep(500);

        if (number == 1) {
            mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
            wobbleMover.drop(this);

        } else {
            wobbleMover.drop(this);
            mecanumDrivetrain.driveByGyro(15, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
            sleep(500);
        }

        if (distanceBack > 0 ) {
            mecanumDrivetrain.driveByGyro(distanceBack, MecanumDrivetrain.DIRECTION_FORWARD, 0.5, 0);
            sleep(500);
        }

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
