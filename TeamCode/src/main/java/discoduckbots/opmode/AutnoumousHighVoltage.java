package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.Intake;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.sensors.TensorFlow;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="HighVoltageAuto", group="Linear Opmode")
public class AutnoumousHighVoltage extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;
    private Intake intake = null;

    TensorFlow tensorFlow = null;
    RingStackDetector ringStackDetector = null;

    private static final double AUTONOMOUS_SPEED = 0.65;
    private static final double STRAFE_SPEED = 0.5;
    private static final double ROTATION_SPEED = 0.4;
    private static final int WOBBLE_GRABBER_REVOLUTIONS = 4832;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        wobbleMover = hardwareStore.getWobbleMover();
        shooter = hardwareStore.getShooter();
        intake = hardwareStore.getIntake();

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
        sleep(1000);
        shooter.setPowerForAutonomous();
        //sleep(500);
        mecanumDrivetrain.driveByGyro(22.5,MecanumDrivetrain.DIRECTION_REVERSE, .45, 0);
        sleep(650);

        for (int i = 0; i<3; i++){
            sleep(380);
            shooter.pushRing();
            sleep(380);
            shooter.resetPusher();
        }

        if (number == 0) {
            mecanumDrivetrain.driveByGyro(9, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS);
            sleep(500);
            mecanumDrivetrain.driveByGyro(11, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(30, MecanumDrivetrain.DIRECTION_FORWARD, .3,0);
            sleep(1000);
            mecanumDrivetrain.driveByGyro(8.7, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grabAndLiftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/4, this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(17, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.release();
            //wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/3);
            sleep(500);
            mecanumDrivetrain.driveByGyro(8, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);

        } else if (number== 1){
            mecanumDrivetrain.driveByGyro(12, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS);
            sleep(500);
            wobbleMover.liftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/3);
            sleep(500);
            wobbleMover.grab();
            sleep(500);
            mecanumDrivetrain.driveByGyro(21, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(6.5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(250);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/4);
            sleep(250);
            intake.outtake();
            sleep(500);
            mecanumDrivetrain.driveByGyro(2.75, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(3.5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(6.25, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grabAndLiftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/4, this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(10.25, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(11, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(250);
            shooter.setPowerForPowerShot();
            sleep(500);
            for (int i = 0; i<3; i++){
                sleep(325);
                intake.pushRing();
                sleep(325);
                intake.resetPusher();
            }
            sleep(300);
            shooter.pushRing();
            sleep(300);
            shooter.resetPusher();
            mecanumDrivetrain.driveByGyro(14, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(250);
            //mecanumDrivetrain.driveByGyro(1, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            wobbleMover.release();
            //wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2);
            sleep(350);
            mecanumDrivetrain.driveByGyro(2, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);

        } else {
            mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(distanceForward, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS);
            sleep(500);
            mecanumDrivetrain.driveByGyro(12, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(54, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED/2,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(9.25, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            sleep(500);
            wobbleMover.grabAndLiftByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2+150, this);
            sleep(500);
            mecanumDrivetrain.driveByGyro(3.75, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED,0);
            sleep(500);
            mecanumDrivetrain.driveByGyro(43, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(2.5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(750);
            wobbleMover.release();
            //wobbleMover.dropByEncoder(WOBBLE_GRABBER_REVOLUTIONS/2);
            sleep(750);
            mecanumDrivetrain.driveByGyro(5, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            sleep(250);
            mecanumDrivetrain.driveByGyro(12, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
        }

        mecanumDrivetrain.stop();    }
}