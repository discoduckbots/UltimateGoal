package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.sensors.TensorFlow;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="TfSGyro Autonomous", group="Linear Opmode")
public class AutonomousWithGyroTensorSpeed extends LinearOpMode {

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
            sleep(500);

        } else {
            wobbleMover.drop(this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(16, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
        }
        shooter.setPowerForHighGoal();
        if (distanceBack > 0 ) {
            mecanumDrivetrain.driveByGyro(distanceBack, MecanumDrivetrain.DIRECTION_FORWARD, 0.5, 0);
            sleep(500);
        }


        mecanumDrivetrain.gyroTurn(-80, ROTATION_SPEED, this);
      //  sleep(500);


        for (int i = 0; i<4; i++){
            shooter.pushRing();
            sleep(500);
            shooter.resetPusher();
            sleep(500);
        }
        // move to the left to align with the second wobble goal
        mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
        // turn to go back to pick up wobble goal two.
        mecanumDrivetrain.gyroTurn(-80, ROTATION_SPEED, this);
        mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
        sleep(100);
        // pick up second wobble goal
        wobbleMover.grab();

        if (number == 0) {
            mecanumDrivetrain.driveByGyro(19, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
           // mecanumDrivetrain.driveByGyro(16, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            //wobbleMover.release();
        } else if (number == 1) {
            mecanumDrivetrain.driveByGyro(26, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            //wobbleMover.release();
        } else  {
            mecanumDrivetrain.driveByGyro(19, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            //mecanumDrivetrain.driveByGyro(16, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            //mecanumDrivetrain.gyroTurn(180, ROTATION_SPEED, this);
            //wobbleMover.release();
        }

        mecanumDrivetrain.stop();
        mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 270);
        mecanumDrivetrain.stop();
    }
}