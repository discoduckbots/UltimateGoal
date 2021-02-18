package discoduckbots.opmode.poc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import discoduckbots.hardware.IMU;

@TeleOp(name = "Gyroscope Test Op", group = "Sensor")
@Disabled
public class GyroscopeTestOp extends LinearOpMode {

    private IMU imu;
    private BNO055IMU gyro;
    NormalizedColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
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
