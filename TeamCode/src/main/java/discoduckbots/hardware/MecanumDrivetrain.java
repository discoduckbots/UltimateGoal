package discoduckbots.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.util.NumberUtility;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDrivetrain implements DrivetrainInterface {
    private static final float ENCODER_CLICKS_FORWARD_1_INCH = 19.0f;//18.75487911f;
    private static final float ENCODER_CLICKS_STRAFE_1_INCH = 25.8944908f;

    public static final int DIRECTION_FORWARD = 0;
    public static final int DIRECTION_REVERSE = 1;
    public static final int DIRECTION_STRAFE_RIGHT = 2;
    public static final int DIRECTION_STRAFE_LEFT = 3;

    private DcMotor mFrontLeft;
    private DcMotor mFrontRight;
    private DcMotor mBackLeft;
    private DcMotor mBackRight;
    private Telemetry mTelemetry;
    private LinearOpMode opMode;

    /**
     * Creates a mecanum motor using the 4 individual motors passed in as the arguments
     * @param telemetry : Telemetry to send messages to the Driver Control
     * @param frontLeft : Front left motor
     * @param frontRight : Front right motor
     * @param backLeft : Back left motor
     * @param backRight : Back right motor
     */
    public MecanumDrivetrain(Telemetry telemetry,
                             LinearOpMode opMode,
                             DcMotor frontLeft, DcMotor frontRight,
                             DcMotor backLeft, DcMotor backRight ) {
        mTelemetry = telemetry;
        mFrontLeft = frontLeft;
        mFrontRight = frontRight;
        mBackLeft = backLeft;
        mBackRight = backRight;

        setMotorDirection(DIRECTION_FORWARD);

        mBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void setMotorDirection(int direction){
        if (DIRECTION_REVERSE == direction){
            mBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            mBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
            mFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            mFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else if (DIRECTION_STRAFE_LEFT == direction){
            mBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            mBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
            mFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            mFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else if (DIRECTION_STRAFE_RIGHT == direction){
            mBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            mBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
            mFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            mFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else{
            mBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            mBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
            mFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            mFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    public String getEncoderCounts(){
        return "FL: " + mFrontLeft.getCurrentPosition() +
                "\nFR: " + mFrontRight.getCurrentPosition() +
                "\nBL: " + mBackLeft.getCurrentPosition() +
                "\nBR: " + mBackRight.getCurrentPosition();
    }

    /**
     * Overload of drive method that allows for throttling of power
     * @param speedX - the x value of the joystick controlling strafe
     * @param speedY - the y value of the joystick controlling the forward/backward motion
     * @param rotation - the x value of the joystick controlling the rotation
     * @param throttle - the amount to throttle the power of the motors
     */
    public void drive(double speedX, double speedY, double rotation, double throttle){
        double throttledX = speedX * throttle;
        double throttledY = speedY * throttle;
        double throttledRotation = rotation * throttle;

        drive(throttledX, throttledY, throttledRotation);
    }
    public void turnLeft(LinearOpMode opMode, int degree){
        setMotorDirection(DIRECTION_FORWARD);
        drive(0,0,-.5);
        opMode.sleep((long)(750 * 1));
        stop();
    }

    public void turnRight(LinearOpMode opMode, int degree){
        setMotorDirection(DIRECTION_FORWARD);
        drive(0,0,.5);
        opMode.sleep((long)(750 * 1));
        stop();
    }
    /**
     * This function makes the mecanum motor drive using the joystick
     * @param speedX - the x value of the joystick controlling straf
     * @param speedY - the y value of the joystick controlling the forward/backwards motion
     * @param rotation - the x value of the joystick controlling the rotation
     */
    public void drive(double speedX, double speedY, double rotation) {
        mTelemetry.addData("speedX", speedX);
        mTelemetry.addData("speedY", speedY);
        mTelemetry.addData("rotation", rotation);
        mTelemetry.update();

        double fl = speedX + speedY + rotation;
        double fr = -speedX + speedY - rotation;
        double bl= -speedX + speedY + rotation;
        double br = speedX + speedY - rotation;

        double max = NumberUtility.findMax(fl, fr, bl, br);
        if (max > 1) {
            fl = fl / max;
            fr = fr / max;
            bl = bl / max;
            br = br / max;
        }

        mFrontRight.setPower(fr);
        mFrontLeft.setPower(fl);
        mBackRight.setPower(br);
        mBackLeft.setPower(bl);
    }

    /**
     * This function stops the mecanum motor
     */
    public void stop() {
        mFrontLeft.setPower(0);
        mFrontRight.setPower(0);
        mBackLeft.setPower(0);
        mBackRight.setPower(0);

    }

    /**
     * Method to drive a specified distance using motor encoder functionality
     *
     * @param inches - The Number Of Inches to Move
     * @param direction - The Direction to Move
     *                  - Valid Directions:
     *                  - MecanumDrivetrain.DIRECTION_FORWARD
     *                  - MecanumDrivetrain.DIRECTION_REVERSE
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_LEFT
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_RIGHT
     * @param speed - The desired motor power (most accurate at low powers < 0.25)
     */
    public void driveByDistance(int inches, int direction, double speed){
        setMotorDirection(direction);
        driveByRevolution(convertDistanceToTarget(inches, direction), speed);
    }

    /**
     * Method to drive a specified distance using motor encoder functionality (includes telemetry)
     *
     * @param inches - The Number Of Inches to Move
     * @param direction - The Direction to Move
     *                  - Valid Directions:
     *                  - MecanumDrivetrain.DIRECTION_FORWARD
     *                  - MecanumDrivetrain.DIRECTION_REVERSE
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_LEFT
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_RIGHT
     * @param speed - The desired motor power (most accurate at low powers < 0.25)
     * @param telemetry - The instance of telemetry to output to
     */
    public void driveByDistance(int inches, int direction, double speed, Telemetry telemetry){
        setMotorDirection(direction);

        int targetPosition = convertDistanceToTarget(inches, direction);
        telemetry.addData("Target Position:", targetPosition);
        telemetry.update();

//        driveByRevolution(targetPosition, speed);
        driveByRevolutionWithTolerance(targetPosition, speed, telemetry);

        telemetry.addData("Ending Positions:",
                "target: " + targetPosition +
                " FL: " + mFrontLeft.getCurrentPosition()
                        + " FR: " + mFrontRight.getCurrentPosition()
                        + " BL: " + mBackLeft.getCurrentPosition()
                        + " BR: " + mBackRight.getCurrentPosition());
        telemetry.update();
    }

    public void driveByDistance(int inches, int direction, double baseSpeed, IMU imu, double targetHeading){
        setMotorDirection(direction);

        int targetPosition = convertDistanceToTarget(inches, direction);

        driveByRevolutionWithTolerance(targetPosition, baseSpeed, imu, targetHeading, direction);
    }

    private int convertDistanceToTarget(int inches, int direction){
        float target;

        if (DIRECTION_FORWARD == direction || DIRECTION_REVERSE == direction){
            target = inches * ENCODER_CLICKS_FORWARD_1_INCH;
        }
        else{
            target = inches * ENCODER_CLICKS_STRAFE_1_INCH;
        }

        return Math.round(target);
    }

    /**
     * Returns if atleast one of the wheels is moving
     * @return true if the robot is moving
     */
    public boolean isMoving() {
        return mFrontLeft.isBusy() || mFrontRight.isBusy() || mBackRight.isBusy() || mBackLeft.isBusy();
    }

    private void driveByRevolutionWithTolerance(int revolutions, double basePower, IMU imu, double targetHeading, int direction){
        int tolerance = 10;

        mFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mFrontLeft.setTargetPosition(mFrontLeft.getCurrentPosition() + revolutions);
        mFrontRight.setTargetPosition(mFrontRight.getCurrentPosition() + revolutions);
        mBackLeft.setTargetPosition(mBackLeft.getCurrentPosition() + revolutions);
        mBackRight.setTargetPosition(mBackRight.getCurrentPosition() + revolutions);

        mFrontLeft.setPower(basePower);
        mFrontRight.setPower(basePower);
        mBackLeft.setPower(basePower);
        mBackRight.setPower(basePower);

        int revolutionsRemaining = revolutions;
        double currentPower = basePower;

        ElapsedTime timeoutTimer = new ElapsedTime();

        while (revolutionsRemaining > tolerance && opMode.opModeIsActive() && timeoutTimer.seconds() < 0.75) {

            //Negative Value is Go Right - Positive is Left
            double adjustment = imu.headingAdjustment(targetHeading);

            if (DIRECTION_FORWARD == direction || DIRECTION_REVERSE == direction) {
                mFrontLeft.setPower(basePower - adjustment);
                mBackLeft.setPower(basePower - adjustment);
                mFrontRight.setPower(basePower + adjustment);
                mBackRight.setPower(basePower + adjustment);
            }
            else{
                mBackLeft.setPower(basePower - adjustment);
                mFrontRight.setPower(basePower + adjustment);
            }

            revolutionsRemaining = mFrontLeft.getTargetPosition() - mFrontLeft.getCurrentPosition();
            timeoutTimer.reset();
        }
    }

    private void driveByRevolutionWithTolerance(int revolutions, double power, Telemetry telemetry){
        int tolerance = 10;

        int currentPosition = mFrontLeft.getCurrentPosition();

        mFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mFrontLeft.setTargetPosition(mFrontLeft.getCurrentPosition() + revolutions);
        mFrontRight.setTargetPosition(mFrontRight.getCurrentPosition() + revolutions);
        mBackLeft.setTargetPosition(mBackLeft.getCurrentPosition() + revolutions);
        mBackRight.setTargetPosition(mBackRight.getCurrentPosition() + revolutions);

        mFrontLeft.setPower(power);
        mFrontRight.setPower(power);
        mBackLeft.setPower(power);
        mBackRight.setPower(power);

        ElapsedTime startTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        while ((mFrontLeft.getTargetPosition() > mFrontLeft.getCurrentPosition() + tolerance)
        && startTime.time() < 10){
            telemetry.addData("In Loop Target Position:", mFrontLeft.getTargetPosition());
            telemetry.addData("Current Position: ", mFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        mFrontLeft.setPower(0);
        mFrontRight.setPower(0);
        mBackLeft.setPower(0);
        mBackRight.setPower(0);
    }

    /**
     * Method will motors a specified number of revolutions at the desired power
     * agnostic of direction.
     *
     * @param revolutions - the number of motor encoder ticks to move
     * @param power - the speed at which to move
     */
    private void driveByRevolution(int revolutions, double power){
        mFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mFrontLeft.setTargetPosition(revolutions);
        mFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBackRight.setTargetPosition(revolutions);
        mBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBackLeft.setTargetPosition(revolutions);
        mBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mFrontRight.setTargetPosition(revolutions);
        mFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mFrontLeft.setPower(power);
        mBackRight.setPower(power);
        mBackLeft.setPower(power);
        mFrontRight.setPower(power);
    }

    public void forwardByTime(LinearOpMode opMode, double speed, double time) {
        mBackLeft.setPower(speed);
        mBackRight.setPower(speed);
        mFrontRight.setPower(speed);
        mFrontLeft.setPower(speed);
        opMode.sleep((long)(1000 * time));

    }

    public void strafeRightByTime(LinearOpMode opMode, double speed, double time) {
        mBackLeft.setPower(-speed);
        mBackRight.setPower(speed);
        mFrontLeft.setPower(speed);
        mFrontRight.setPower(-speed);
        opMode.sleep((long)(1000 * time));
    }

    public void strafeLeftByTime(LinearOpMode opMode, double speed, double time) {
        mFrontLeft.setPower(-speed);
        mFrontRight.setPower(speed);
        mBackLeft.setPower(speed);
        mBackRight.setPower(-speed);
        opMode.sleep((long)(1000*time));
    }

    public void backwardByTime(LinearOpMode opMode, double speed, double time) {
        mBackLeft.setPower(-speed);
        mBackRight.setPower(-speed);
        mFrontLeft.setPower(-speed);
        mFrontRight.setPower(-speed);
        opMode.sleep((long)(1000 * time));
    }
}