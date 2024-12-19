package AvoidingObstacles;
import com.phidget22.PhidgetException;
public class Vector3_Ai_Information extends Vector3_Ai_Ui {
	public Vector3_Ai_Information() throws InterruptedException
	{
		super();
	}
	public void MainRun() throws InterruptedException
	{
		try
		{
			while (!Vector3_Ai_System.VectorSetup)
			{
				Thread.sleep(10);
			}
			
			UpdateUi();
		}
		catch (InterruptedException | PhidgetException e)
		{
			System.err.println("[VectorInfo]: Failed to setup information.");
			System.out.println("");
			e.printStackTrace();
		}
	}
	
	public static void UpdateUi() throws InterruptedException, PhidgetException
	{
		Vector3_Ai_System.sonar.setDataInterval(100);
		Vector3_Ai_System.leftMotors.setDataInterval(100);
		Vector3_Ai_System.rightMotors.setDataInterval(100);
		Vector3_Ai_System.leftMotors.setAcceleration(leftMotors.getMaxAcceleration());
		Vector3_Ai_System.rightMotors.setAcceleration(rightMotors.getMaxAcceleration());
		
		while (true)
		{
			ObjectDistance_Information.setText("" + (Vector3_Ai_System.sonar != null ? "" + Vector3_Ai_System.sonar.getDistance() + " mm" : "Unable to Detect Distance."));
			progressBar.setValue((int) (Vector3_Ai_System.sonar != null ? Vector3_Ai_System.sonar.getDistance() / Vector3_Ai_System.DistanceFromObject : 0));
			
			ObjectInView.setSelected((Vector3_Ai_System.sonar != null ? Vector3_Ai_System.sonar.getDistance() > Vector3_Ai_System.DistanceFromObject : false));
			AiHumanControl_Information.setText(Vector3_Ai_System.Human_Control_Active ? "Human" : "Ai");
			Vector3_Ai_System.SetSpeed = ControlSpeed_Value.getValue();
			
			Thread.sleep(5);
		}
	}
}
