
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;


public class MainFrame extends JFrame {
	/*****************************************************************
	Description: 建立所需變數
	Created Date: 2014/05/02 	
	******************************************************************/
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static OS2_00156150 cpu_scheduler = new  OS2_00156150();
	private String process_info = "";
	private JTextArea textArea;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	/*****************************************************************
	Description:主程式
	Created Date: 2014/05/02 	
	******************************************************************/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//使用try...catch...做例外的處理
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					//將內容print 至 console中
					cpu_scheduler.console_print();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		
		setTitle("HW2 CPU scheduler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 696, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//宣告TextArea 用來存放執行完排班演算法後的結果
		textArea = new JTextArea();
		textArea.setForeground(Color.GRAY);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textArea.setTabSize(25);
		textArea.setBounds(10, 98, 660, 354);
		contentPane.add(textArea);
		
		
		//Load file button 
		btnNewButton = new JButton("Load File ");
		btnNewButton.setBounds(10, 10, 100, 59);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cpu_scheduler.loadfile();	//excute load file 
				process_info = "";
				// show 檔案的內容
				for(int i = 0; i < cpu_scheduler.processdata.size(); i++){
					process_info += cpu_scheduler.processdata.get(i).process_name + " " + 
							cpu_scheduler.processdata.get(i).burst_time+ " " + 
							cpu_scheduler.processdata.get(i).priority + "\n";
				}	
				textArea.setText(process_info);	
			}
		});
		

		// FCFS button 
		btnNewButton_1 = new JButton("FCFS");
		btnNewButton_1.setBounds(150, 10, 100, 59);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 將執行完FCFS function 將 return string set 至 textArea
				textArea.setText(cpu_scheduler.FCFS());
			}
		});
		
		// SJF button
		btnNewButton_2 = new JButton("SJF");
		btnNewButton_2.setBounds(290, 10, 100, 59);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 將執行完SJF function 將 return string set 至 textArea
				textArea.setText(cpu_scheduler.SJF());
			}
		});
		
		// Priority button
		btnNewButton_3 = new JButton("Priority");
		btnNewButton_3.setBounds(430, 10, 100, 59);
		contentPane.add(btnNewButton_3);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 將執行完 Priority function 將 return string set 至 textArea
				textArea.setText(cpu_scheduler.Priority());
			}
		});
		
		// RR button 
		btnNewButton_4 = new JButton("RR");
		btnNewButton_4.setBounds(570, 10, 100, 59);
		contentPane.add(btnNewButton_4);
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 將執行完RR function 將 return string set 至 textArea
				textArea.setText(cpu_scheduler.RR());
			}
		});

	}
}
