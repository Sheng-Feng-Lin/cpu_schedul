import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;


public class OS2_00156150 {
	
	/*****************************************************************
	Description:  �إߩһݪ��ϰ��ܼ�
	Created Date: 2014/05/02 	
	******************************************************************/
	String temp;
	StringTokenizer stoken;
	ArrayList <Process> processdata;
	Process process;
	String FCFS_scheduler, SJF_scheduler, Priority_scheduler, RR_scheduler;
	float[] avg_waiting_time;
	float[] avg_turnaround_time;
	
	//�غc�l  �i���l�Ƴ]�w
	public OS2_00156150() {
		// TODO Auto-generated constructor stub
		temp = null;
		stoken = null;
		processdata = new ArrayList <Process>();
		FCFS_scheduler = "";
		SJF_scheduler = "";
		Priority_scheduler = "";
		RR_scheduler = "";
		avg_waiting_time = new float[4];
		avg_turnaround_time = new float[4];
		
		
	}
	/*
	 * method loadfile                                                                                                   
	* 
	* @param  void 
	* 
	* @return void
	*
	* @throws
	*		���txt�ɪ����e  �B�N���e��marraylist���i��B�z	
	* 
	* Example:
	* Process BrustTime  Priority
		input�G 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		P1 10 3
		P2 1 2
		P3 2 1
	* 
	 */
	
	public void loadfile(){
		//Ū���ɮת���m
		processdata.clear();
		File FileName = new File("C://OSIN.txt");
		//using try....catch...deal with exception 
	    try(FileReader fr =new FileReader(FileName)){
	    	BufferedReader br = new BufferedReader(fr);
	    	//�N�ɮ׸̪���Ƨ���X��
			while( br.ready()){
				process = new Process();//��new �O�����m��Process �ӨѦs��process info
				//�N��X�Ӫ��@�C��mtemp���r�ꤤ
				temp = br.readLine();
				//�ϥ�stringToken�ӰϧO�ɮרC�@�C
				stoken = new StringTokenizer(temp, " ");
				while( stoken.hasMoreTokens()){
					process.process_name = stoken.nextToken();
					process.burst_time = stoken.nextToken();
					process.priority = stoken.nextToken();
					//�N�C�@��process infoŪ�������mprocessdata��arraylist��
					processdata.add(process);
				}
				
			}
			//�ɮ�����
			br.close();
		}	
		catch(FileNotFoundException e){
				System.out.println("�ҥ~�o��,�䤣����ɮ�");
		}
		catch(final IOException e){
				System.out.println("�ҥ~�o��,�ɮצs�����~");
		}			    
	}
	
	/*
	 * method  FCFS                                                                                                    
	* 
	* @param  void 
	* 
	* @return String FCFS_Info
	*
	* @throws
	*		 ��process����F���ǨӨM�w�֭n������,�p�P�ɨ�F�Hprocess name �̤p���u���i�����	
	* 
	* Example:
	* Process BrustTime  Priority
		input�G 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
	  	Algorithms: FCFS
	  	���涶��:P1 P2 P3 
		waiting time : P1:0.0 P2:10.0 P3:11.0 
		Avg waiting time : 7.0
		turnaround_time time : P1:10.0 P2:11.0 P3:13.0 
		Avg turnaround_time time : 11.333333
	* 
	 */
	public String FCFS(){
		String FCFS_Info = ""; //�Ω�NFCFS��INFO�p��X�ӫ�s���r��, �B�N���r��show �b Textarea�W
		float waiting_time=0, turnaround_time=0; 
		
		//�Nprocessdata �L�h�ƻs��FCFS�o��arraylist��
		ArrayList<Process> FCFS = new ArrayList<Process> (processdata);
		
		//���Nturnaround time ���k0
		for(int i = 0; i < FCFS.size(); i++)
			FCFS.get(i).FCFS_turnaround_time = 0;
	
		FCFS_Info += "Algorithms: FCFS\n���涶��:";
		for(int i = 0; i < FCFS.size(); i++){
				//turnaround time ���������ɶ�, �ӳo��process�����ɶ��|�O�e����process��turnaround time + �o��process �� burst time�G���ϥβ֥[�覡,
				turnaround_time +=Float.parseFloat(FCFS.get(i).burst_time)-0; 
				FCFS.get(i).FCFS_turnaround_time+=turnaround_time;
				//waiting time = turnaroundtime - burst_time 
				FCFS.get(i).FCFS_waiting_time = FCFS.get(i).FCFS_turnaround_time-Float.parseFloat(FCFS.get(i).burst_time); 
				FCFS_Info += FCFS.get(i).process_name+" ";
				FCFS_scheduler += FCFS.get(i).process_name+" ";
		}
		
		//�p��FCFS �������ݮɶ�
		turnaround_time=0;
		FCFS_Info += "\nwaiting time : ";
		for(int i = 0; i < FCFS.size(); i++){
			//�֥[waitingtime �ӭp���`�@�Ҫ᪺waiting time 
			waiting_time +=FCFS.get(i).FCFS_waiting_time;
			FCFS_Info += FCFS.get(i).process_name + ":"+ FCFS.get(i).FCFS_waiting_time+" ";
		}
		//�p�⥭���ɶ�
		FCFS_Info += "\nAvg waiting time : "+ waiting_time/FCFS.size();
		avg_waiting_time[0] = waiting_time/FCFS.size();
		
		
		//�p��FCFS ����turnaround time 
		FCFS_Info += "\nturnaround_time time : ";
		for(int i = 0; i < FCFS.size(); i++){
			//�֥[turnaroundtime �ӭp���`�@�Ҫ᪺turnaround time
			turnaround_time+= FCFS.get(i).FCFS_turnaround_time;
			FCFS_Info += FCFS.get(i).process_name + ":"+ FCFS.get(i).FCFS_turnaround_time+" ";
		}
		//�p�⥭���ɶ�
		FCFS_Info += "\nAvg turnaround_time time : "+ turnaround_time/FCFS.size();
		avg_turnaround_time[0] = turnaround_time/FCFS.size();
		
		return FCFS_Info;
	}
	
	/*
	 * method SJF                                                                                                   
	* 
	* @param  void 
	* 
	* @return String SJF_Info
	*
	* @throws
	*		 ��process��burst time�̤p�ӨM�w�֭n������,�p�P���ۦP��burst time�Hprocess name �̤p���u���i�����	
	* 
	* Example:
	* Process BrustTime  Priority
		input�G 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		Algorithms: SJF
		���涶��:P2 P3 P1 
		waiting time : P2:0.0 P3:1.0 P1:3.0 
		Avg waiting time : 1.3333334
		turnaround_time time : P2:1.0 P3:3.0 P1:13.0 
		Avg turnaround_time time : 5.6666665
	* 
	 */
	public String SJF(){
		String SJF_Info = ""; //�Ω�NSJF��INFO�p��X�ӫ�s���r��, �B�N���r��show �b Textarea�W
		float waiting_time=0, turnaround_time=0; 
		
		//�Nprocessdata �L�h�ƻs��SJF�o��arraylist��
		ArrayList<Process> SJF = new ArrayList<Process> (processdata);
		
		//���Nturnaround time ���k0
		for(int i = 0; i < SJF.size(); i++)
			SJF.get(i).SJF_turnaround_time = 0;
		
		//�ϥ�collection ��arraylist��burst_time���بӶi��Ƨ�
		Collections.sort(SJF, new Comparator<Process>(){
			 @Override
			 public int compare(Process p1, Process p2) {
					 return (int) (Float.parseFloat(p1.burst_time) - Float.parseFloat(p2.burst_time));
			 }   
		});
		
		//�ƧǦn��}�l�i��ƯZ����
		SJF_Info += "Algorithms: SJF\n���涶��:";
		for(int i = 0; i < SJF.size(); i++){
				//turnaround time ���������ɶ�, �ӳo��process�����ɶ��|�O�e����process��turnaround time + �o��process �� burst time�G���ϥβ֥[�覡,
				turnaround_time +=Float.parseFloat(SJF.get(i).burst_time)-0; 
				SJF.get(i).SJF_turnaround_time+=turnaround_time;
				//waiting time = turnaroundtime - burst_time 
				SJF.get(i).SJF_waiting_time = SJF.get(i).SJF_turnaround_time-Float.parseFloat(SJF.get(i).burst_time); 
				SJF_Info += SJF.get(i).process_name+" ";
				SJF_scheduler += SJF.get(i).process_name+" ";
		}
		
		//�p��SJF �������ݮɶ�
		turnaround_time=0;
		SJF_Info += "\nwaiting time : ";
		for(int i = 0; i < SJF.size(); i++){
			//�֥[waitingtime �ӭp���`�@�Ҫ᪺turnaround time
			waiting_time += SJF.get(i).SJF_waiting_time;
			SJF_Info += SJF.get(i).process_name + ":"+ SJF.get(i).SJF_waiting_time+" ";
		}
		SJF_Info += "\nAvg waiting time : "+ waiting_time/SJF.size();
		avg_waiting_time[1] = waiting_time/SJF.size();
		
		//�p��SJF ����turnaround �ɶ�
		SJF_Info += "\nturnaround_time time : ";
		for(int i = 0; i < SJF.size(); i++){
			//�֥[turnaroundtime �ӭp���`�@�Ҫ᪺turnaround time
			turnaround_time+=SJF.get(i).SJF_turnaround_time;
			SJF_Info += SJF.get(i).process_name + ":"+ SJF.get(i).SJF_turnaround_time+" ";
		}
		SJF_Info += "\nAvg turnaround_time time : "+ turnaround_time/SJF.size();
		avg_turnaround_time[1] = turnaround_time/SJF.size();
		
		return SJF_Info;
	}
	
	/*
	 * method Priority                                                                                                  
	* 
	* @param  void 
	* 
	* @return String Priority_Info
	*
	* @throws
	*		 ��process��Priority�̤p�ӨM�w�֭n������,�p�P���ۦP��Priority�Hprocess name �̤p���u���i�����	
	* 
	* Example:
	* Process BrustTime  Priority
		input�G 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		Algorithms: Priority
		���涶��:P3 P2 P1 
		waiting time : P3:0.0 P2:2.0 P1:3.0 
		Avg waiting time : 1.6666666
		turnaround_time time : P3:2.0 P2:3.0 P1:13.0 
		Avg turnaround_time time : 6.0
	* 
	 */
	public String Priority(){
		String Priority_Info = "";//�Ω�NPriority��INFO�p��X�ӫ�s���r��, �B�N���r��show �b Textarea�W
		float waiting_time=0, turnaround_time=0; 
		
		//�Nprocessdata �L�h�ƻs��Priority�o��arraylist��
		ArrayList<Process> Priority = new ArrayList<Process> (processdata);
		
		//���Nturnaround time ���k0
		for(int i = 0; i < Priority.size(); i++)
			Priority.get(i).Priority_turnaround_time = 0;
		
		//�ϥ�collection ��arraylist��priority���بӶi��Ƨ�
		Collections.sort(Priority, new Comparator<Process>(){
			 @Override
			 public int compare(Process p1, Process p2) {
					 return (int) (Float.parseFloat(p1.priority) - Float.parseFloat(p2.priority));
			 }   
		});
		
		//�ƧǦn��}�l�i��ƯZ����
		Priority_Info += "Algorithms: Priority\n���涶��:";
		for(int i = 0; i < Priority.size(); i++){
				//turnaround time ���������ɶ�, �ӳo��process�����ɶ��|�O�e����process��turnaround time + �o��process �� burst time�G���ϥβ֥[�覡,
				turnaround_time +=Float.parseFloat(Priority.get(i).burst_time)-0; 
				Priority.get(i).Priority_turnaround_time+=turnaround_time;
				//waiting time = turnaroundtime - burst_time 
				Priority.get(i).Priority_waiting_time = Priority.get(i).Priority_turnaround_time-Float.parseFloat(Priority.get(i).burst_time); 
				Priority_Info += Priority.get(i).process_name+" ";	
				Priority_scheduler += Priority.get(i).process_name+" ";
		}
		
		//�p��Priority �������ݮɶ�
		turnaround_time=0;
		Priority_Info += "\nwaiting time : ";
		for(int i = 0; i < Priority.size(); i++){
			//�֥[waitingtime �ӭp���`�@�Ҫ᪺waitingtime
			waiting_time += Priority.get(i).Priority_waiting_time;
			Priority_Info += Priority.get(i).process_name + ":"+ Priority.get(i).Priority_waiting_time+" ";
		}
		
		Priority_Info += "\nAvg waiting time : "+ waiting_time/Priority.size();
		avg_waiting_time[2] = waiting_time/Priority.size();
		
		//�p��Priority ����turnaround�ɶ�
		Priority_Info += "\nturnaround_time time : ";
		for(int i = 0; i < Priority.size(); i++){
			//�֥[turnaroundtime �ӭp���`�@�Ҫ᪺turnaround time
			turnaround_time+=Priority.get(i).Priority_turnaround_time;
			Priority_Info += Priority.get(i).process_name + ":"+ Priority.get(i).Priority_turnaround_time+" ";
		}
		Priority_Info += "\nAvg turnaround_time time : "+ turnaround_time/Priority.size();
		avg_turnaround_time[2] = turnaround_time/Priority.size();
		
		return Priority_Info;
	}
	
	/*
	 * method RR                                                                                                 
	* 
	* @param  void 
	* 
	* @return String RR_Info
	*
	* @throws
	*		�C��procees���|������@�� , �� time Quantum ���M�w�C�@��process�ү���檺�ɶ� 	
	* 
	* Example:
	* Process BrustTime  Priority
		input�G 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		Algorithms: RR
		���涶��:P1 P2 P3 P1 P3 P1 
		waiting time : P1:3.0 P2:1.0 P3:3.0 
		Avg waiting time : 2.3333333
		turnaround_time time : P1:13.0 P2:2.0 P3:5.0 
		Avg turnaround_time time : 6.6666665
	* 
	 */
	public String RR(){
		String RR_Info = "";//�Ω�NRR��INFO�p��X�ӫ�s���r��, �B�N���r��show �b Textarea�W
		float waiting_time=0, turnaround_time=0;
		int count=0;
		
		//�Nprocessdata �L�h�ƻs��RR�o��arraylist��
		ArrayList<Process> RR = new ArrayList<Process> (processdata);
		int[] RR_temp = new int [RR.size()];
		
		//�Nburst time ��m��L�}�C��, �קK���U�Ӱʧ@�|�v�T�ܭ쥻��burst time value 
		for(int i = 0; i < RR.size(); i++)
			RR_temp[i] = Integer.parseInt(RR.get(i).burst_time);
		
		RR_Info += "Algorithms: RR\n���涶��:";
		
		//��count ������ process�`�@size ���٦�process�|������
		while(count != RR.size()){
			for(int i = 0; i < RR.size(); i++){
				// RR_temp �O�Ψ�check �o��process burst_time ������0 ����٨S�������~�����
				if(RR_temp[i] != 0){
					//��count�֥[���`�@process�ƶq-1 �N��u�Ѥ@��process������, �G�]���ݶi������ʧ@, �ҥH�N�@������
					if(count == RR.size()-1){
						turnaround_time +=RR_temp[i];
						RR_temp[i] -= RR_temp[i];	
					}
					//�_�h�|�ھ�time Quantum �ӨM�w�@���వ�h��
					else{
						//��� time Quantum ��1 , �����time Quantum�U���o����ܼƻݤ@�_���
						turnaround_time ++; 
						RR_temp[i] -= 1;	
					}
					
					//�p�GRR_temp ���� time Quantum ����0 , ��ܳo��process�w�g����, �i�H set�o��process�� turnaround time and waiting time,�B�Ncount++  
					if(RR_temp[i] ==0){
						RR.get(i).RR_turnaround_time =turnaround_time;
						RR.get(i).RR_waiting_time = RR.get(i).RR_turnaround_time-Float.parseFloat(RR.get(i).burst_time);
						count++;
					}
					RR_Info += RR.get(i).process_name+" ";	
					RR_scheduler += RR.get(i).process_name+" ";
				}
			}	
		}
		
		//�p��RR �������ݮɶ�
		turnaround_time=0;
		RR_Info += "\nwaiting time : ";
		for(int i = 0; i <RR.size(); i++){
			//�֥[waitingtime �ӭp���`�@�Ҫ᪺waiting time
			waiting_time += RR.get(i).RR_waiting_time;
			RR_Info += RR.get(i).process_name + ":"+ RR.get(i).RR_waiting_time+" ";
		}
		RR_Info += "\nAvg waiting time : "+ waiting_time/RR.size();
		avg_waiting_time[3] = waiting_time/RR.size();
		
		//�p��RR ����turnaround�ɶ�
		RR_Info += "\nturnaround_time time : ";
		for(int i = 0; i < RR.size(); i++){
			//�֥[turnaroundtime �ӭp���`�@�Ҫ᪺turnaround time
			turnaround_time+=RR.get(i).RR_turnaround_time;
			RR_Info += RR.get(i).process_name + ":"+ RR.get(i).RR_turnaround_time+" ";
		}
		RR_Info += "\nAvg turnaround_time time : "+ turnaround_time/RR.size();
		avg_turnaround_time[3] = turnaround_time/RR.size();
		
		
		return RR_Info;
	}
	
	
	public void console_print(){
		loadfile();
		FCFS();
		SJF();
		Priority();
		RR();
		//show alogrithms ���涶��
		System.out.println("algorithms\t���涶��");
		System.out.println("FCFS\t\t"+FCFS_scheduler);
		System.out.println("SJF\t\t"+SJF_scheduler);
		System.out.println("Priority\t"+Priority_scheduler);
		System.out.println("RR\t\t"+RR_scheduler);
		
		//show turnaround time 
		System.out.println("\nTurnaround time\t\tFCFS\tSJF\tPriority"+"   "+"RR\n");
		for(int i= 0; i < processdata.size();i++)
			System.out.println(processdata.get(i).process_name + "\t\t\t" + processdata.get(i).FCFS_turnaround_time
					+"\t"+ processdata.get(i).SJF_turnaround_time+"\t"+ processdata.get(i).Priority_turnaround_time
					+"\t   "+  processdata.get(i).RR_turnaround_time);
		//show avg waiting time 
		System.out.println("Average turnaround time\t"+avg_turnaround_time[0]+"\t"+avg_turnaround_time[1]
				+"\t"+avg_turnaround_time[2]+"\t   "+avg_turnaround_time[3]);
	
		
		//show waiting time 
		System.out.println("\nWaiting time\t\tFCFS\tSJF\tPriority"+"   "+"RR\n");
		for(int i= 0; i < processdata.size();i++)
			System.out.println(processdata.get(i).process_name + "\t\t\t" + processdata.get(i).FCFS_waiting_time
					+"\t"+ processdata.get(i).SJF_waiting_time+"\t"+ processdata.get(i).Priority_waiting_time
					+"\t   "+  processdata.get(i).RR_waiting_time);
				
		//show avg waiting time 		
		System.out.print("Average waiting time\t"+avg_waiting_time[0]+"\t"+avg_waiting_time[1]
				+"\t"+avg_waiting_time[2]+"\t   "+avg_waiting_time[3]);
			
		
	}
	
	
}
