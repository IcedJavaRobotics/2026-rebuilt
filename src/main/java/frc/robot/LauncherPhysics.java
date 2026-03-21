package frc.robot;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class LauncherPhysics {
    /**
     * Give it your distance from the edge of the robot, it will return an RPM value that is accurate.    
     * 
     * Distance in inches, RPM is actually in percent output because we are lazy for now
     */
    private InterpolatingDoubleTreeMap shooterTable = new InterpolatingDoubleTreeMap();

    private InterpolatingDoubleTreeMap shooterConversionTable = new InterpolatingDoubleTreeMap();

    public LauncherPhysics(){
        initializeShooterTable();
        initializeShooterConversionTable();
    }

    private void initializeShooterTable(){
        shooterTable.put(0.1, 0.1); // left number is the distance, right number is the rpm
        shooterTable.put(2.0, 0.55); //just add more numbers to the table as you test
        shooterTable.put(80.0, 0.69);
        shooterTable.put(40.0, 0.54);
        shooterTable.put(50.0, 0.56);
        shooterTable.put(70.0, 0.6); //0.2125 TA 6.2 TY
    }

    private void initializeShooterConversionTable(){
        shooterConversionTable.put(0.0, 0.0);
        shooterConversionTable.put(0.65, 55.0);
        shooterConversionTable.put(0.67, 60.0);
        shooterConversionTable.put(1.0, 500.0);
    }

    public double getRPM(double distance){
        return shooterTable.get(distance);
    }

    public double getVelocity(double percentOutput){
        return shooterConversionTable.get(percentOutput);
    }

}
