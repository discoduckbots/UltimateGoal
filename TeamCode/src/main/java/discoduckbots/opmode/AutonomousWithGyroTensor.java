package discoduckbots.opmode;

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
    private static final double STRAFE_SPEED = 0.5;
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
        mecanumDrivetrain.driveByGyro(19,MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
        sleep(1000);

        for (int i = 0; i<3; i++){
            sleep(1000);
            shooter.pushRing();
            sleep(1000);
            shooter.resetPusher();
        }

        if (number == 0) {
            mecanumDrivetrain.driveByGyro(20, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.drop(this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(33, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
           sleep(500);
            mecanumDrivetrain.driveByGyro(9, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grab();
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(18, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.release();
            sleep(500);
            mecanumDrivetrain.driveByGyro(3, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);

        } else if (number== 1){
            mecanumDrivetrain.driveByGyro(17, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.drop(this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(49, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(750);
            wobbleMover.grab();
            sleep(500);
            mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(33, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.release();
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(7, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);

           /* mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.drop(this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceBack,MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(2, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            wobbleMover.grab();
            sleep(500);
            mecanumDrivetrain.driveByGyro(4, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(26, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.release();
            sleep(500);
            mecanumDrivetrain.driveByGyro(3, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            sleep(500);*/
        } else {
            mecanumDrivetrain.driveByGyro(20, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.drop(this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(13, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(58, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(9, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grab();
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(43, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(750);
            wobbleMover.release();
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(17, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);

           /*mecanumDrivetrain.driveByGyro(8, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.drop(this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.release();
            sleep(500);
            mecanumDrivetrain.driveByGyro(8, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceBack, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(2, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grab();
            sleep(500);
            mecanumDrivetrain.driveByGyro(7, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(37, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.release();
            sleep(500);
            mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);*/
        }

        mecanumDrivetrain.stop();    }
}