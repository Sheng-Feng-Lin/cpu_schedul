//建立Process 的類別 用來存放process name and burst time and priority 
public class Process {
	
	String process_name, burst_time, priority;
	float FCFS_waiting_time, FCFS_turnaround_time, SJF_waiting_time, SJF_turnaround_time,Priority_waiting_time, Priority_turnaround_time
		,RR_waiting_time, RR_turnaround_time;
	
	
	
	/*****************************************************************
	Description:  建構子, 對新建的process 做初始化值的動作
	Created Date: 2014/05/02 	
	******************************************************************/
	public Process() {
		// TODO Auto-generated constructor stub
		process_name = null;
		burst_time = null;
		priority = null;
		FCFS_waiting_time= 0 ;
		FCFS_turnaround_time = 0;
		SJF_waiting_time= 0 ;
		SJF_turnaround_time = 0;
		Priority_waiting_time= 0 ;
		Priority_turnaround_time = 0;
		RR_waiting_time= 0 ;
		RR_turnaround_time = 0;
		
	}

}
