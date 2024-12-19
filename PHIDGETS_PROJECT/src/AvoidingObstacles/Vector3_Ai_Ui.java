
package AvoidingObstacles;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Cursor;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Box;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSlider;

public class Vector3_Ai_Ui extends Vector3_Ai_System 
{

	protected static JCheckBox ObjectInView;
	protected static JLabel ObjectDistance_Information;
	protected static JProgressBar progressBar;
	protected static JButton Toggle_Ai;
	protected static JSlider ControlSpeed_Value;
	protected static JLabel AiHumanControl_Information;
	private JFrame frame;

	public void MainRun() throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vector3_Ai_Ui window = new Vector3_Ai_Ui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	public Vector3_Ai_Ui() throws InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 */
	private void initialize() throws InterruptedException {
		frame = new JFrame();
		frame.setAutoRequestFocus(true);
		frame.setBounds(100, 100, 1324, 908);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel InformationPannel = new JPanel();
		InformationPannel.setDoubleBuffered(false);
		InformationPannel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		InformationPannel.setBackground(new Color(0, 64, 0));
		InformationPannel.setBounds(0, 0, 1131, 869);
		frame.getContentPane().add(InformationPannel);
		InformationPannel.setLayout(null);
		
		ObjectInView = new JCheckBox("Infront of Object");
		ObjectInView.setBorderPainted(true);
		ObjectInView.setIconTextGap(5);
		ObjectInView.setFocusPainted(false);
		ObjectInView.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		ObjectInView.setVerifyInputWhenFocusTarget(false);
		ObjectInView.setRolloverEnabled(false);
		ObjectInView.setRequestFocusEnabled(false);
		ObjectInView.setFocusable(false);
		ObjectInView.setForeground(new Color(255, 255, 255));
		ObjectInView.setBackground(new Color(0, 128, 0));
		ObjectInView.setFont(new Font("OCR A Extended", Font.PLAIN, 22));
		ObjectInView.setBounds(6, 99, 440, 33);
		InformationPannel.add(ObjectInView);
		
		JPanel ObjectDistanceTab = new JPanel();
		ObjectDistanceTab.setBackground(new Color(0, 128, 0));
		ObjectDistanceTab.setBounds(6, 139, 440, 33);
		InformationPannel.add(ObjectDistanceTab);
		ObjectDistanceTab.setLayout(null);
		
		JLabel ObjectDistance_Title = new JLabel("Distance:");
		ObjectDistance_Title.setForeground(new Color(255, 255, 255));
		ObjectDistance_Title.setBounds(0, 0, 101, 33);
		ObjectDistanceTab.add(ObjectDistance_Title);
		ObjectDistance_Title.setHorizontalAlignment(SwingConstants.LEFT);
		ObjectDistance_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		
		ObjectDistance_Information = new JLabel("0 mm");
		ObjectDistance_Information.setForeground(new Color(255, 255, 255));
		ObjectDistance_Information.setBounds(98, 0, 342, 33);
		ObjectDistanceTab.add(ObjectDistance_Information);
		ObjectDistance_Information.setHorizontalAlignment(SwingConstants.LEFT);
		ObjectDistance_Information.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		
		JPanel ControlPannel = new JPanel();
		ControlPannel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		ControlPannel.setBackground(new Color(0, 128, 64));
		ControlPannel.setBounds(1131, 0, 177, 869);
		frame.getContentPane().add(ControlPannel);
		ControlPannel.setLayout(null);
		
		Toggle_Ai = new JButton("Start Vector");
		Toggle_Ai.setFocusable(false);
		Toggle_Ai.setVerifyInputWhenFocusTarget(false);
		Toggle_Ai.setRequestFocusEnabled(false);
		Toggle_Ai.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
		Toggle_Ai.setForeground(new Color(255, 255, 255));
		Toggle_Ai.setBackground(new Color(0, 64, 0));
		Toggle_Ai.setBounds(10, 11, 157, 28);
		ControlPannel.add(Toggle_Ai);
		
		Toggle_Ai.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Vector3_Ai_System.Ai_Active = !Vector3_Ai_System.Ai_Active;
				
				if (!Vector3_Ai_System.Ai_Active) 
				{
					Toggle_Ai.setBackground(new Color(0, 64, 0));
					Toggle_Ai.setText("Start Vector3");
				}
				else 
				{
					Toggle_Ai.setBackground(new Color(64, 0, 0));
					Toggle_Ai.setText("Stop Vector3");
				}
			}
		});
		
		JButton L_Spin_Ai = new JButton("L Spin");
		L_Spin_Ai.setFocusable(false);
		L_Spin_Ai.setRequestFocusEnabled(false);
		L_Spin_Ai.setForeground(Color.WHITE);
		L_Spin_Ai.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
		L_Spin_Ai.setBackground(new Color(128, 128, 128));
		L_Spin_Ai.setBounds(10, 50, 78, 28);
		ControlPannel.add(L_Spin_Ai);
		
		JButton R_Spin_Ai = new JButton("R Spin");
		R_Spin_Ai.setFocusable(false);
		R_Spin_Ai.setRequestFocusEnabled(false);
		R_Spin_Ai.setForeground(Color.WHITE);
		R_Spin_Ai.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
		R_Spin_Ai.setBackground(Color.GRAY);
		R_Spin_Ai.setBounds(89, 50, 78, 28);
		ControlPannel.add(R_Spin_Ai);
		
		JButton Toggle_ObjectAvoidance = new JButton("Avoid Objects");
		Toggle_ObjectAvoidance.setVerifyInputWhenFocusTarget(false);
		Toggle_ObjectAvoidance.setRequestFocusEnabled(false);
		Toggle_ObjectAvoidance.setForeground(Color.WHITE);
		Toggle_ObjectAvoidance.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
		Toggle_ObjectAvoidance.setFocusable(false);
		Toggle_ObjectAvoidance.setBackground(new Color(0, 64, 0));
		Toggle_ObjectAvoidance.setBounds(10, 162, 157, 28);
		ControlPannel.add(Toggle_ObjectAvoidance);
		
		JButton StopSpin_Ai = new JButton("Stop Spin");
		StopSpin_Ai.setRequestFocusEnabled(false);
		StopSpin_Ai.setForeground(Color.WHITE);
		StopSpin_Ai.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
		StopSpin_Ai.setFocusable(false);
		StopSpin_Ai.setBackground(Color.GRAY);
		StopSpin_Ai.setBounds(10, 83, 157, 28);
		ControlPannel.add(StopSpin_Ai);
		
		JPanel ControlSpeed_Ai = new JPanel();
		ControlSpeed_Ai.setLayout(null);
		ControlSpeed_Ai.setBackground(new Color(128, 128, 128));
		ControlSpeed_Ai.setBounds(10, 201, 157, 49);
		ControlPannel.add(ControlSpeed_Ai);
		
		JLabel ControlSpeed_Title = new JLabel("Set Speed");
		ControlSpeed_Title.setHorizontalTextPosition(SwingConstants.CENTER);
		ControlSpeed_Title.setHorizontalAlignment(SwingConstants.CENTER);
		ControlSpeed_Title.setForeground(Color.WHITE);
		ControlSpeed_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		ControlSpeed_Title.setBounds(0, 0, 157, 26);
		ControlSpeed_Ai.add(ControlSpeed_Title);
		
		ControlSpeed_Value = new JSlider();
		ControlSpeed_Value.setValue(70);
		ControlSpeed_Value.setSnapToTicks(true);
		ControlSpeed_Value.setMinorTickSpacing(1);
		ControlSpeed_Value.setForeground(Color.LIGHT_GRAY);
		ControlSpeed_Value.setFont(new Font("OCR A Extended", Font.PLAIN, 11));
		ControlSpeed_Value.setFocusable(false);
		ControlSpeed_Value.setBackground(Color.GRAY);
		ControlSpeed_Value.setBounds(0, 20, 157, 26);
		ControlSpeed_Ai.add(ControlSpeed_Value);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(255, 255, 255));
		progressBar.setForeground(new Color(0, 64, 0));
		progressBar.setBounds(302, 11, 128, 14);
		ObjectDistanceTab.add(progressBar);
		
		JPanel Title = new JPanel();
		Title.setLayout(null);
		Title.setBackground(new Color(128, 64, 0));
		Title.setBounds(6, 11, 1115, 33);
		InformationPannel.add(Title);
		
		JLabel Title_Text = new JLabel("Ai Survival System");
		Title_Text.setForeground(new Color(255, 255, 255));
		Title_Text.setHorizontalAlignment(SwingConstants.CENTER);
		Title_Text.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 17));
		Title_Text.setBounds(0, 0, 1118, 33);
		Title.add(Title_Text);
		
		JPanel AiMood = new JPanel();
		AiMood.setLayout(null);
		AiMood.setBackground(new Color(0, 128, 0));
		AiMood.setBounds(6, 234, 440, 33);
		InformationPannel.add(AiMood);
		
		JLabel AiMood_Title = new JLabel("Current Mode:");
		AiMood_Title.setHorizontalAlignment(SwingConstants.LEFT);
		AiMood_Title.setForeground(Color.WHITE);
		AiMood_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		AiMood_Title.setBounds(0, 0, 141, 33);
		AiMood.add(AiMood_Title);
		
		JLabel AiMood_Information = new JLabel("Idle");
		AiMood_Information.setHorizontalAlignment(SwingConstants.LEFT);
		AiMood_Information.setForeground(Color.WHITE);
		AiMood_Information.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiMood_Information.setBounds(140, 0, 300, 33);
		AiMood.add(AiMood_Information);
		
		JPanel Hunger = new JPanel();
		Hunger.setLayout(null);
		Hunger.setBackground(new Color(0, 128, 0));
		Hunger.setBounds(6, 278, 440, 33);
		InformationPannel.add(Hunger);
		
		JLabel AiHunger_Title = new JLabel("Hunger:");
		AiHunger_Title.setHorizontalAlignment(SwingConstants.LEFT);
		AiHunger_Title.setForeground(Color.WHITE);
		AiHunger_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		AiHunger_Title.setBounds(0, 0, 141, 33);
		Hunger.add(AiHunger_Title);
		
		JLabel AiHunger_Information = new JLabel("0%");
		AiHunger_Information.setHorizontalAlignment(SwingConstants.LEFT);
		AiHunger_Information.setForeground(Color.WHITE);
		AiHunger_Information.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiHunger_Information.setBounds(79, 0, 361, 33);
		Hunger.add(AiHunger_Information);
		
		JPanel ObjectSection_Title = new JPanel();
		ObjectSection_Title.setLayout(null);
		ObjectSection_Title.setBackground(new Color(100, 174, 74));
		ObjectSection_Title.setBounds(6, 55, 440, 33);
		InformationPannel.add(ObjectSection_Title);
		
		JLabel ObjectSection_Text = new JLabel("OBJECT");
		ObjectSection_Text.setBackground(new Color(100, 174, 74));
		ObjectSection_Text.setHorizontalAlignment(SwingConstants.CENTER);
		ObjectSection_Text.setForeground(Color.WHITE);
		ObjectSection_Text.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 17));
		ObjectSection_Text.setBounds(0, 0, 440, 33);
		ObjectSection_Title.add(ObjectSection_Text);
		
		JPanel StateSection_Title = new JPanel();
		StateSection_Title.setLayout(null);
		StateSection_Title.setBackground(new Color(100, 174, 74));
		StateSection_Title.setBounds(6, 190, 440, 33);
		InformationPannel.add(StateSection_Title);
		
		JLabel StateSection_Text = new JLabel("STATES");
		StateSection_Text.setHorizontalAlignment(SwingConstants.CENTER);
		StateSection_Text.setForeground(Color.WHITE);
		StateSection_Text.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 17));
		StateSection_Text.setBackground(new Color(100, 174, 74));
		StateSection_Text.setBounds(0, 0, 440, 33);
		StateSection_Title.add(StateSection_Text);
		
		JPanel AiStamina = new JPanel();
		AiStamina.setLayout(null);
		AiStamina.setBackground(new Color(0, 128, 0));
		AiStamina.setBounds(6, 322, 440, 33);
		InformationPannel.add(AiStamina);
		
		JLabel AiStamina_Title = new JLabel("Stamina:");
		AiStamina_Title.setHorizontalAlignment(SwingConstants.LEFT);
		AiStamina_Title.setForeground(Color.WHITE);
		AiStamina_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		AiStamina_Title.setBounds(0, 0, 141, 33);
		AiStamina.add(AiStamina_Title);
		
		JLabel AiStamina_Information = new JLabel("100%");
		AiStamina_Information.setHorizontalAlignment(SwingConstants.LEFT);
		AiStamina_Information.setForeground(Color.WHITE);
		AiStamina_Information.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiStamina_Information.setBounds(89, 0, 351, 33);
		AiStamina.add(AiStamina_Information);
		
		JPanel LocationSection_Title = new JPanel();
		LocationSection_Title.setLayout(null);
		LocationSection_Title.setBackground(new Color(100, 174, 74));
		LocationSection_Title.setBounds(681, 55, 440, 33);
		InformationPannel.add(LocationSection_Title);
		
		JLabel LocationSection_Text = new JLabel("OBJECT");
		LocationSection_Text.setHorizontalAlignment(SwingConstants.CENTER);
		LocationSection_Text.setForeground(Color.WHITE);
		LocationSection_Text.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 17));
		LocationSection_Text.setBackground(new Color(100, 174, 74));
		LocationSection_Text.setBounds(0, 0, 440, 33);
		LocationSection_Title.add(LocationSection_Text);
		
		JPanel AiCoordinates = new JPanel();
		AiCoordinates.setLayout(null);
		AiCoordinates.setBackground(new Color(0, 128, 0));
		AiCoordinates.setBounds(681, 99, 440, 90);
		InformationPannel.add(AiCoordinates);
		
		JLabel AiCoordinates_Title = new JLabel("Current Coordinates:");
		AiCoordinates_Title.setHorizontalAlignment(SwingConstants.LEFT);
		AiCoordinates_Title.setForeground(Color.WHITE);
		AiCoordinates_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		AiCoordinates_Title.setBounds(0, 0, 440, 33);
		AiCoordinates.add(AiCoordinates_Title);
		
		JLabel AiCoordinates_Information_X = new JLabel("X: 0");
		AiCoordinates_Information_X.setHorizontalAlignment(SwingConstants.LEFT);
		AiCoordinates_Information_X.setForeground(Color.WHITE);
		AiCoordinates_Information_X.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiCoordinates_Information_X.setBounds(20, 29, 280, 33);
		AiCoordinates.add(AiCoordinates_Information_X);
		
		JLabel AiCoordinates_Information_Y = new JLabel("Y: 0");
		AiCoordinates_Information_Y.setHorizontalAlignment(SwingConstants.LEFT);
		AiCoordinates_Information_Y.setForeground(Color.WHITE);
		AiCoordinates_Information_Y.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiCoordinates_Information_Y.setBounds(20, 56, 280, 33);
		AiCoordinates.add(AiCoordinates_Information_Y);
		
		JButton Human_Control_Module = new JButton("Human Controller");
		Human_Control_Module.setFocusTraversalPolicyProvider(true);
		Human_Control_Module.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		Human_Control_Module.setVerifyInputWhenFocusTarget(false);
		Human_Control_Module.setForeground(Color.WHITE);
		Human_Control_Module.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
		Human_Control_Module.setBackground(new Color(255, 128, 0));
		Human_Control_Module.setBounds(10, 652, 206, 206);
		InformationPannel.add(Human_Control_Module);
		
		Human_Control_Module.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (Human_Control_Module.isFocusOwner()) 
				{
					if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 'w' || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP) 
					{
						Vector3_Ai_System.Human_Forward = true;
					}
					
					if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT) 
					{
						Vector3_Ai_System.Human_Left = true;
					}
					
					if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT) 
					{
						Vector3_Ai_System.Human_Right = true;
					}
					
					if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 's' || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN) 
					{
						Vector3_Ai_System.Human_Backward = true;
					}
					
					if (e.getKeyCode() == KeyEvent.VK_ENTER) 
					{
						Vector3_Ai_System.Human_Control_Active = !Vector3_Ai_System.Human_Control_Active;
						System.out.println("Human Control: " + Vector3_Ai_System.Human_Control_Active);
						
						Vector3_Ai_System.Human_Forward = false;
						Vector3_Ai_System.Human_Left = false;
						Vector3_Ai_System.Human_Right = false;
						Vector3_Ai_System.Human_Backward = false;
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 'w' || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP) 
				{
					Vector3_Ai_System.Human_Forward = false;
				}
				if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT) 
				{
					Vector3_Ai_System.Human_Left = false;
				}
				if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT) 
				{
					Vector3_Ai_System.Human_Right = false;
				}
				if ((e.getKeyChar() + "").toLowerCase().charAt(0) == 's' || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN) 
				{
					Vector3_Ai_System.Human_Backward = false;
				}
			}
		});
		
		JPanel AiHumanControl = new JPanel();
		AiHumanControl.setLayout(null);
		AiHumanControl.setBackground(new Color(0, 128, 0));
		AiHumanControl.setBounds(6, 366, 440, 33);
		InformationPannel.add(AiHumanControl);
		
		JLabel AiHumanControl_Title = new JLabel("Control Type:");
		AiHumanControl_Title.setHorizontalAlignment(SwingConstants.LEFT);
		AiHumanControl_Title.setForeground(Color.WHITE);
		AiHumanControl_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		AiHumanControl_Title.setBounds(0, 0, 141, 33);
		AiHumanControl.add(AiHumanControl_Title);
		
		AiHumanControl_Information = new JLabel("Ai");
		AiHumanControl_Information.setHorizontalAlignment(SwingConstants.LEFT);
		AiHumanControl_Information.setForeground(Color.WHITE);
		AiHumanControl_Information.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiHumanControl_Information.setBounds(140, 0, 300, 33);
		AiHumanControl.add(AiHumanControl_Information);
		
		JPanel AiObstacleAvoidance = new JPanel();
		AiObstacleAvoidance.setLayout(null);
		AiObstacleAvoidance.setBackground(new Color(0, 128, 0));
		AiObstacleAvoidance.setBounds(6, 410, 440, 33);
		InformationPannel.add(AiObstacleAvoidance);
		
		JLabel AiObstacleAvoidance_Title = new JLabel("Obstacle Avoidance:");
		AiObstacleAvoidance_Title.setHorizontalAlignment(SwingConstants.LEFT);
		AiObstacleAvoidance_Title.setForeground(Color.WHITE);
		AiObstacleAvoidance_Title.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		AiObstacleAvoidance_Title.setBounds(0, 0, 210, 33);
		AiObstacleAvoidance.add(AiObstacleAvoidance_Title);
		
		JLabel AiObstacleAvoidance_Information = new JLabel("Active");
		AiObstacleAvoidance_Information.setHorizontalAlignment(SwingConstants.LEFT);
		AiObstacleAvoidance_Information.setForeground(Color.WHITE);
		AiObstacleAvoidance_Information.setFont(new Font("OCR A Extended", Font.PLAIN, 17));
		AiObstacleAvoidance_Information.setBounds(204, 0, 236, 33);
		AiObstacleAvoidance.add(AiObstacleAvoidance_Information);
	}
}

