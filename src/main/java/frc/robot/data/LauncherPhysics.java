package frc.robot.data;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class LauncherPhysics {
    /**
     * Give it your distance from the edge of the robot, it will return an RPM value that is accurate.    
     * 
     * Distance in inches, RPM is actually in percent output because we are lazy for now
     */
    private InterpolatingDoubleTreeMap shooterTable = new InterpolatingDoubleTreeMap();

    public LauncherPhysics(){
        initializeShooterTable();
    }

    private void initializeShooterTable(){
        shooterTable.put(0.1, 0.1); // left number is the distance in meters, right number is the rps velocity
    }

    public double getShootingVelocity(double distance){
        return shooterTable.get(distance);
    }


}
