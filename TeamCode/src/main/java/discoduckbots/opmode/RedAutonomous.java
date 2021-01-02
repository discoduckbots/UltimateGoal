package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

public class RedAutonomous extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    private Shooter shooter = null;
    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        shooter = hardwareStore.getShooter();
        wobbleMover = hardwareStore.getWobbleMover();

        // wait for start
        waitForStart();
        runtime.reset();

        autonomousByEncoder();
    }


    private void autonomousByEncoder() {
        double autonomousSpeed = 0.10;
        sleep(25000);
        mecanumDrivetrain.driveByDistance(DISTANCE_TO_LAUNCH_LINE, MecanumDrivetrain.DIRECTION_FORWARD, autonomousSpeed);
        while (opModeIsActive() && mecanumDrivetrain.isMoving()) {
            telemetry.addData("Step 6", "Strafe left 55\"");
            telemetry.update();
        }
        mecanumDrivetrain.stop();
    }

}
