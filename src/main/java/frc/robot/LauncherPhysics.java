package frc.robot;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class LauncherPhysics {
    /**
     * Give it your distance from the edge of the robot, it will return an RPM value that is accurate.    
     * 
     * Distance in inches, RPM is actually in percent output because we are lazy for now
     */
    private InterpolatingDoubleTreeMap shooterTable = new InterpolatingDoubleTreeMap();

    public LauncherPhysics(){
        shooterTable.put(0.1, 0.1); // left number is the distance, right number is the rpm
        shooterTable.put(2.0, 0.55); //just add more numbers to the table as you test
    }


    public double getRPM(double distance){
        return shooterTable.get(distance);
    }

}
