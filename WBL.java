package shiyan8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WBL extends JFrame {

	private JPanel contentPane;
	private JTextField showtime;//显示烹饪时间
	private JTextField text1;//灯的开闭情况,默认关闭
	private boolean working=false;//默认不工作
	private boolean stove_door=false;//炉门默认关闭
	private int time;//烹饪时间
	private JLabel status;//状态显示
	private Thread Time;
	private boolean die;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WBL frame = new WBL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WBL() {
		setTitle("微波炉系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u663E\u793A\u5668", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(5, 5, 608, 169);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("灯光");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 23));
		lblNewLabel.setBounds(81, 53, 65, 44);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("烹饪时间");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 23));
		lblNewLabel_1.setBounds(274, 53, 96, 44);
		panel.add(lblNewLabel_1);
		
		showtime = new JTextField();
		showtime.setEditable(false);
		showtime.setFont(new Font("宋体", Font.PLAIN, 23));
		showtime.setBounds(392, 53, 138, 36);
		panel.add(showtime);
		showtime.setColumns(10);
		
		text1 = new JTextField();
		text1.setEditable(false);
		text1.setFont(new Font("宋体", Font.PLAIN, 23));
		text1.setColumns(10);
		text1.setBounds(137, 53, 110, 36);
		panel.add(text1);
//		默认显示灯关闭
		text1.setText("暗暗暗");
		
		JLabel lblNewLabel_2 = new JLabel("当前状态：");
		lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 19));
		lblNewLabel_2.setBounds(47, 123, 101, 36);
		panel.add(lblNewLabel_2);
		
		status = new JLabel("");
		status.setForeground(new Color(0, 0, 255));
		status.setFont(new Font("宋体", Font.BOLD, 19));
		status.setBounds(156, 128, 374, 23);
		panel.add(status);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 171, 608, 224);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		   
		
		JButton openButton = new JButton("开炉");
		openButton.setFont(new Font("宋体", Font.BOLD, 17));
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				打开炉门,灯要凉
				if (stove_door!=true) {
					stove_door=true;
					status.setText("炉门已经打开！！");
					text1.setText("亮亮亮");
					text1.setForeground(Color.green);
					
				}
			}
		});
		openButton.setBounds(61, 32, 178, 42);
		panel_1.add(openButton);
		
		JButton closeButton = new JButton("关炉");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//关闭炉门
				if (stove_door!=false) {
					stove_door=false;
					status.setText("炉门已经关闭！！！");
				}
				init();
			}
		});
		closeButton.setFont(new Font("宋体", Font.BOLD, 17));
		closeButton.setBounds(340, 32, 178, 42);
		panel_1.add(closeButton);
		
		JButton fireButton = new JButton("烹饪");
		fireButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				炉门关闭
				if (stove_door==false) {
					//启动初始化烹饪
					//亮灯
					working=true;
					text1.setText("亮亮亮");
					text1.setForeground(Color.green);
					//每点击一次，时间加60秒
					time+=60;
					status.setText("工作中...");
					showtime.setText(Integer.toString(time));
					timeSub(time);
				}
//				炉门未关闭
				if (stove_door==true) {
					status.setText("炉门未关闭！，滴一声！！！");
				}
				
			}
		});
		fireButton.setFont(new Font("宋体", Font.BOLD, 17));
		fireButton.setBounds(61, 114, 178, 42);
		panel_1.add(fireButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(working);
//				判断是否在工作
				if (working==false) {
					status.setText("微波炉未启动，滴一声!!!");
					
				}
				if(working==true) {
					//中断，关灯并清除剩余时间
					status.setText("非正常中断！滴一声!!!");
					text1.setText("暗暗暗");
					text1.setForeground(Color.black);
					showtime.setText("");
				}
			}
		});
		cancelButton.setFont(new Font("宋体", Font.BOLD, 17));
		cancelButton.setBounds(340, 114, 178, 42);
		panel_1.add(cancelButton);
		
		
	}
	
	/**
	 * 初始化烹饪工作
	 */
	public void init() {
		//确认灯是关闭的
		text1.setText("暗暗暗");
		text1.setForeground(Color.black);
		//烹饪时间调零
		showtime.setText("");
		time=0;
		//提示
		status.setText("准备完毕，等待启动！");
	}
	/**
	 * 烹饪时间倒计
	 * @param time
	 */
	public void timeSub(int time) {
		while(time>0) {
			 time--;
			 try {
				 Thread.sleep(100);
				 System.out.println(time);
			 }catch(InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
		if (time==0) {
			System.out.println("时间到！");
			//关闭灯，并鸣叫三声
			text1.setForeground(Color.black);
			status.setText("烹饪完成，滴滴滴~（三声）");
			text1.setText("暗暗暗");
		}
	}


		
	
	
}
