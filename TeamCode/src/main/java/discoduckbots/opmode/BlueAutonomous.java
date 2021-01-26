package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.Intake;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.sensors.TensorFlow;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueStoneSide-TF", group="Linear Opmode")
public class BlueAutonomous extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;
    TensorFlow tensorFlow = null;
    RingStackDetector ringStackDetector = null;
    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        shooter = hardwareStore.getShooter();
        wobbleMover = hardwareStore.getWobbleMover();

        tensorFlow = new TensorFlow();
        ringStackDetector = new RingStackDetector();
        tensorFlow.initTensorflow(telemetry, hardwareMap);

        // wait for start
        waitForStart();
        runtime.reset();

        wobbleMover.grab();
        autonomousByEncoder();
    }


    private void autonomousByEncoder() {
        double autonomousSpeed = 0.6;
        int distanceToStrafe = 0;
        boolean tensorFlowDetection = true;
        if (tensorFlowDetection) {
            int distanceToMoveBack = 0;

            telemetry.addData("init tensor flow", "");
            telemetry.update();

            int number = ringStackDetector.getSlot(tensorFlow.detect(), telemetry);
        }
          /*
            //int number = 0;
            if (number == 0){
                distanceToMoveBack = 0;
                distanceToStrafe = 13;
                mecanumDrivetrain.driveByDistanceWithTolerance(6, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed);

                     telemetry.addData("Strafing Done","");
                     telemetry.update();

                mecanumDrivetrain.driveByDistanceWithTolerance(22,  MecanumDrivetrain.DIRECTION_REVERSE,autonomousSpeed);

                telemetry.addData("Moving Forward Done","");
                telemetry.update();

            }else if (number == 1){

                distanceToMoveBack = 30;
                mecanumDrivetrain.driveByDistanceWithTolerance(6,MecanumDrivetrain.DIRECTION_STRAFE_LEFT,autonomousSpeed);
                mecanumDrivetrain.driveByDistanceWithTolerance(29,MecanumDrivetrain.DIRECTION_REVERSE,autonomousSpeed);
                mecanumDrivetrain.driveByDistanceWithTolerance(10,MecanumDrivetrain.DIRECTION_STRAFE_RIGHT,autonomousSpeed);
            }else if (number == 4){
                distanceToMoveBack = 40;
                distanceToStrafe = 10;
                mecanumDrivetrain.driveByDistanceWithTolerance(6,MecanumDrivetrain.DIRECTION_STRAFE_LEFT,autonomousSpeed);
                mecanumDrivetrain.driveByDistanceWithTolerance(10,MecanumDrivetrain.DIRECTION_REVERSE,autonomousSpeed);
            }
            telemetry.addData("Starting wobble mover", "");
            telemetry.update();
            wobbleMover.drop(this);
            telemetry.addData("finished wobble mover", "");
            telemetry.update();
            if (distanceToMoveBack > 0) {
                mecanumDrivetrain.driveByDistanceWithTolerance(distanceToMoveBack, MecanumDrivetrain.DIRECTION_FORWARD, autonomousSpeed);
            }
        } else {
            //sleep(25000);
            mecanumDrivetrain.driveByDistanceWithTolerance(DISTANCE_TO_LAUNCH_LINE, MecanumDrivetrain.DIRECTION_FORWARD, autonomousSpeed);
        }

        if (distanceToStrafe > 0) {
            mecanumDrivetrain.driveByDistanceWithTolerance(distanceToStrafe, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, autonomousSpeed);
        }

        shoot();

        telemetry.addData("Finished Shooting", "");
        telemetry.update();

        mecanumDrivetrain.stop();
        mecanumDrivetrain.driveByDistanceWithTolerance(1, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed);
        mecanumDrivetrain.stop();
        */

    }

    private void shoot() {
        double autonomousSpeed = 0.6;
        mecanumDrivetrain.turnLeft(this, 190);

        mecanumDrivetrain.driveByDistanceWithTolerance(10, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed);

        shooter.shoot();
        sleep((long)(1000 * 3));

        for (int index = 0; index < 4 ; index++) {
            telemetry.addData("shoot ", index);
            telemetry.update();
            sleep((long)(1500 * 1));
            shooter.pushRing();
            sleep((long)(1500 * 1));
            shooter.resetPusher();
        }
    }
}
