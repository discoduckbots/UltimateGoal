package discoduckbots.opmode.poc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import discoduckbots.hardware.IMU;

@TeleOp(name = "Gyroscope Test Op", group = "Sensor")
public class GyroscopeTestOp extends LinearOpMode {

    private IMU imu;
    private BNO055IMU gyro;

    @Override
    public void runOpMode() throws InterruptedException {

        gyro = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new IMU(gyro);
        imu.initialize();

        waitForStart();

        while (opModeIsActive()){
            double heading = imu.getIMUHeading();
            telemetry.addData("Heading", heading);
            telemetry.update();
        }
    }
}
