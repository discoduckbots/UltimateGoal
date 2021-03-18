package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.opmode.RingStackDetector;
import discoduckbots.sensors.TensorFlow;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="LowVoltageAuto", group="Linear Opmode")
public class AutonomousLowVoltage extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;

    TensorFlow tensorFlow = null;
    RingStackDetector ringStackDetector = null;

    private static final double AUTONOMOUS_SPEED = 0.65;
    private static final double STRAFE_SPEED = 0.5;
    private static final double ROTATION_SPEED = 0.4;
    private static final int WOBBLE_GRABBER_REVOLUTIONS = 6250;

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
        telemetry.addData("Tensorflow saw " + number + " rings", number);
        telemetry.update();

        int distanceForward;
        int distanceBack;
        if (number == 0) {
            distanceForward = 5;
            distanceBack = 19;
        } else if (number == 1) {
            distanceBack = 26;
            distanceForward = 10;
        } else {
            distanceBack = 37;
            distanceForward = 23;
        }
        wobbleMover.grab();
        shooter.setPowerForHighGoal();
        //sleep(500);
        mecanumDrivetrain.driveByGyro(23,MecanumDrivetrain.DIRECTION_REVERSE, .45, 0);
        sleep(1000);

        for (int i = 0; i<3; i++){
            sleep(380);
            shooter.pushRing();
            sleep(380);
            shooter.resetPusher();
        }

        if (number == 0) {
            mecanumDrivetrain.driveByGyro(11, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS);
            sleep(500);
            mecanumDrivetrain.driveByGyro(11, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(29, MecanumDrivetrain.DIRECTION_FORWARD, .3,0);
            sleep(1000);
            mecanumDrivetrain.driveByGyro(8, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grabAndLiftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2, this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(17, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2);
            sleep(500);
            mecanumDrivetrain.driveByGyro(3, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);

        } else if (number== 1){
            mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(1, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS);
            sleep(500);
            mecanumDrivetrain.driveByGyro(6, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(41, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(750);
            wobbleMover.grabAndLiftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2, this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(7, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(31, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2);
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);

        } else {
            mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS);
            sleep(500);
            mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(53, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(9, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grabAndLiftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2, this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(43, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2);
            sleep(750);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(11, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
        }

        mecanumDrivetrain.stop();    }
}