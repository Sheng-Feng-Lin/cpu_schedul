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
	Description:  建立所需的區域變數
	Created Date: 2014/05/02 	
	******************************************************************/
	String temp;
	StringTokenizer stoken;
	ArrayList <Process> processdata;
	Process process;
	String FCFS_scheduler, SJF_scheduler, Priority_scheduler, RR_scheduler;
	float[] avg_waiting_time;
	float[] avg_turnaround_time;
	
	//建構子  進行初始化設定
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
	*		抓取txt檔的內容  且將內容放置arraylist中進行處理	
	* 
	* Example:
	* Process BrustTime  Priority
		input： 
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
		//讀取檔案的位置
		processdata.clear();
		File FileName = new File("C://OSIN.txt");
		//using try....catch...deal with exception 
	    try(FileReader fr =new FileReader(FileName)){
	    	BufferedReader br = new BufferedReader(fr);
	    	//將檔案裡的資料抓取出來
			while( br.ready()){
				process = new Process();//需new 記憶體位置給Process 來供存放process info
				//將抓出來的一列放置temp此字串中
				temp = br.readLine();
				//使用stringToken來區別檔案每一列
				stoken = new StringTokenizer(temp, " ");
				while( stoken.hasMoreTokens()){
					process.process_name = stoken.nextToken();
					process.burst_time = stoken.nextToken();
					process.priority = stoken.nextToken();
					//將每一個process info讀取完後放置processdata此arraylist中
					processdata.add(process);
				}
				
			}
			//檔案關閉
			br.close();
		}	
		catch(FileNotFoundException e){
				System.out.println("例外發生,找不到該檔案");
		}
		catch(final IOException e){
				System.out.println("例外發生,檔案存取錯誤");
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
	*		 由process的到達順序來決定誰要先執行,如同時到達以process name 最小的優先進行執行	
	* 
	* Example:
	* Process BrustTime  Priority
		input： 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
	  	Algorithms: FCFS
	  	執行順序:P1 P2 P3 
		waiting time : P1:0.0 P2:10.0 P3:11.0 
		Avg waiting time : 7.0
		turnaround_time time : P1:10.0 P2:11.0 P3:13.0 
		Avg turnaround_time time : 11.333333
	* 
	 */
	public String FCFS(){
		String FCFS_Info = ""; //用於將FCFS的INFO計算出來後存放於字串, 且將此字串show 在 Textarea上
		float waiting_time=0, turnaround_time=0; 
		
		//將processdata 淺層複製至FCFS這個arraylist中
		ArrayList<Process> FCFS = new ArrayList<Process> (processdata);
		
		//先將turnaround time 先歸0
		for(int i = 0; i < FCFS.size(); i++)
			FCFS.get(i).FCFS_turnaround_time = 0;
	
		FCFS_Info += "Algorithms: FCFS\n執行順序:";
		for(int i = 0; i < FCFS.size(); i++){
				//turnaround time 為做完的時間, 而這個process做完時間會是前面的process的turnaround time + 這個process 的 burst time故須使用累加方式,
				turnaround_time +=Float.parseFloat(FCFS.get(i).burst_time)-0; 
				FCFS.get(i).FCFS_turnaround_time+=turnaround_time;
				//waiting time = turnaroundtime - burst_time 
				FCFS.get(i).FCFS_waiting_time = FCFS.get(i).FCFS_turnaround_time-Float.parseFloat(FCFS.get(i).burst_time); 
				FCFS_Info += FCFS.get(i).process_name+" ";
				FCFS_scheduler += FCFS.get(i).process_name+" ";
		}
		
		//計算FCFS 平均等待時間
		turnaround_time=0;
		FCFS_Info += "\nwaiting time : ";
		for(int i = 0; i < FCFS.size(); i++){
			//累加waitingtime 來計算總共所花的waiting time 
			waiting_time +=FCFS.get(i).FCFS_waiting_time;
			FCFS_Info += FCFS.get(i).process_name + ":"+ FCFS.get(i).FCFS_waiting_time+" ";
		}
		//計算平均時間
		FCFS_Info += "\nAvg waiting time : "+ waiting_time/FCFS.size();
		avg_waiting_time[0] = waiting_time/FCFS.size();
		
		
		//計算FCFS 平均turnaround time 
		FCFS_Info += "\nturnaround_time time : ";
		for(int i = 0; i < FCFS.size(); i++){
			//累加turnaroundtime 來計算總共所花的turnaround time
			turnaround_time+= FCFS.get(i).FCFS_turnaround_time;
			FCFS_Info += FCFS.get(i).process_name + ":"+ FCFS.get(i).FCFS_turnaround_time+" ";
		}
		//計算平均時間
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
	*		 由process的burst time最小來決定誰要先執行,如同有相同的burst time以process name 最小的優先進行執行	
	* 
	* Example:
	* Process BrustTime  Priority
		input： 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		Algorithms: SJF
		執行順序:P2 P3 P1 
		waiting time : P2:0.0 P3:1.0 P1:3.0 
		Avg waiting time : 1.3333334
		turnaround_time time : P2:1.0 P3:3.0 P1:13.0 
		Avg turnaround_time time : 5.6666665
	* 
	 */
	public String SJF(){
		String SJF_Info = ""; //用於將SJF的INFO計算出來後存放於字串, 且將此字串show 在 Textarea上
		float waiting_time=0, turnaround_time=0; 
		
		//將processdata 淺層複製至SJF這個arraylist中
		ArrayList<Process> SJF = new ArrayList<Process> (processdata);
		
		//先將turnaround time 先歸0
		for(int i = 0; i < SJF.size(); i++)
			SJF.get(i).SJF_turnaround_time = 0;
		
		//使用collection 對arraylist中burst_time項目來進行排序
		Collections.sort(SJF, new Comparator<Process>(){
			 @Override
			 public int compare(Process p1, Process p2) {
					 return (int) (Float.parseFloat(p1.burst_time) - Float.parseFloat(p2.burst_time));
			 }   
		});
		
		//排序好後開始進行排班執行
		SJF_Info += "Algorithms: SJF\n執行順序:";
		for(int i = 0; i < SJF.size(); i++){
				//turnaround time 為做完的時間, 而這個process做完時間會是前面的process的turnaround time + 這個process 的 burst time故須使用累加方式,
				turnaround_time +=Float.parseFloat(SJF.get(i).burst_time)-0; 
				SJF.get(i).SJF_turnaround_time+=turnaround_time;
				//waiting time = turnaroundtime - burst_time 
				SJF.get(i).SJF_waiting_time = SJF.get(i).SJF_turnaround_time-Float.parseFloat(SJF.get(i).burst_time); 
				SJF_Info += SJF.get(i).process_name+" ";
				SJF_scheduler += SJF.get(i).process_name+" ";
		}
		
		//計算SJF 平均等待時間
		turnaround_time=0;
		SJF_Info += "\nwaiting time : ";
		for(int i = 0; i < SJF.size(); i++){
			//累加waitingtime 來計算總共所花的turnaround time
			waiting_time += SJF.get(i).SJF_waiting_time;
			SJF_Info += SJF.get(i).process_name + ":"+ SJF.get(i).SJF_waiting_time+" ";
		}
		SJF_Info += "\nAvg waiting time : "+ waiting_time/SJF.size();
		avg_waiting_time[1] = waiting_time/SJF.size();
		
		//計算SJF 平均turnaround 時間
		SJF_Info += "\nturnaround_time time : ";
		for(int i = 0; i < SJF.size(); i++){
			//累加turnaroundtime 來計算總共所花的turnaround time
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
	*		 由process的Priority最小來決定誰要先執行,如同有相同的Priority以process name 最小的優先進行執行	
	* 
	* Example:
	* Process BrustTime  Priority
		input： 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		Algorithms: Priority
		執行順序:P3 P2 P1 
		waiting time : P3:0.0 P2:2.0 P1:3.0 
		Avg waiting time : 1.6666666
		turnaround_time time : P3:2.0 P2:3.0 P1:13.0 
		Avg turnaround_time time : 6.0
	* 
	 */
	public String Priority(){
		String Priority_Info = "";//用於將Priority的INFO計算出來後存放於字串, 且將此字串show 在 Textarea上
		float waiting_time=0, turnaround_time=0; 
		
		//將processdata 淺層複製至Priority這個arraylist中
		ArrayList<Process> Priority = new ArrayList<Process> (processdata);
		
		//先將turnaround time 先歸0
		for(int i = 0; i < Priority.size(); i++)
			Priority.get(i).Priority_turnaround_time = 0;
		
		//使用collection 對arraylist中priority項目來進行排序
		Collections.sort(Priority, new Comparator<Process>(){
			 @Override
			 public int compare(Process p1, Process p2) {
					 return (int) (Float.parseFloat(p1.priority) - Float.parseFloat(p2.priority));
			 }   
		});
		
		//排序好後開始進行排班執行
		Priority_Info += "Algorithms: Priority\n執行順序:";
		for(int i = 0; i < Priority.size(); i++){
				//turnaround time 為做完的時間, 而這個process做完時間會是前面的process的turnaround time + 這個process 的 burst time故須使用累加方式,
				turnaround_time +=Float.parseFloat(Priority.get(i).burst_time)-0; 
				Priority.get(i).Priority_turnaround_time+=turnaround_time;
				//waiting time = turnaroundtime - burst_time 
				Priority.get(i).Priority_waiting_time = Priority.get(i).Priority_turnaround_time-Float.parseFloat(Priority.get(i).burst_time); 
				Priority_Info += Priority.get(i).process_name+" ";	
				Priority_scheduler += Priority.get(i).process_name+" ";
		}
		
		//計算Priority 平均等待時間
		turnaround_time=0;
		Priority_Info += "\nwaiting time : ";
		for(int i = 0; i < Priority.size(); i++){
			//累加waitingtime 來計算總共所花的waitingtime
			waiting_time += Priority.get(i).Priority_waiting_time;
			Priority_Info += Priority.get(i).process_name + ":"+ Priority.get(i).Priority_waiting_time+" ";
		}
		
		Priority_Info += "\nAvg waiting time : "+ waiting_time/Priority.size();
		avg_waiting_time[2] = waiting_time/Priority.size();
		
		//計算Priority 平均turnaround時間
		Priority_Info += "\nturnaround_time time : ";
		for(int i = 0; i < Priority.size(); i++){
			//累加turnaroundtime 來計算總共所花的turnaround time
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
	*		每個procees都會先輪到一次 , 而 time Quantum 為決定每一次process所能執行的時間 	
	* 
	* Example:
	* Process BrustTime  Priority
		input： 
		P1 		10 			3
		P2 		1 			2
		P3 		2 			1
		
		output:
		Algorithms: RR
		執行順序:P1 P2 P3 P1 P3 P1 
		waiting time : P1:3.0 P2:1.0 P3:3.0 
		Avg waiting time : 2.3333333
		turnaround_time time : P1:13.0 P2:2.0 P3:5.0 
		Avg turnaround_time time : 6.6666665
	* 
	 */
	public String RR(){
		String RR_Info = "";//用於將RR的INFO計算出來後存放於字串, 且將此字串show 在 Textarea上
		float waiting_time=0, turnaround_time=0;
		int count=0;
		
		//將processdata 淺層複製至RR這個arraylist中
		ArrayList<Process> RR = new ArrayList<Process> (processdata);
		int[] RR_temp = new int [RR.size()];
		
		//將burst time 放置其他陣列中, 避免接下來動作會影響至原本的burst time value 
		for(int i = 0; i < RR.size(); i++)
			RR_temp[i] = Integer.parseInt(RR.get(i).burst_time);
		
		RR_Info += "Algorithms: RR\n執行順序:";
		
		//當count 不等於 process總共size 表還有process尚未做完
		while(count != RR.size()){
			for(int i = 0; i < RR.size(); i++){
				// RR_temp 是用來check 這個process burst_time 不等於0 表示還沒做完需繼續執行
				if(RR_temp[i] != 0){
					//當count累加為總共process數量-1 代表只剩一個process須執行, 故也不需進行切換動作, 所以就一次做完
					if(count == RR.size()-1){
						turnaround_time +=RR_temp[i];
						RR_temp[i] -= RR_temp[i];	
					}
					//否則會根據time Quantum 來決定一次能做多少
					else{
						//表示 time Quantum 為1 , 當改變time Quantum下面這兩個變數需一起更動
						turnaround_time ++; 
						RR_temp[i] -= 1;	
					}
					
					//如果RR_temp 扣掉 time Quantum 等於0 , 表示這個process已經做完, 可以 set這個process的 turnaround time and waiting time,且將count++  
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
		
		//計算RR 平均等待時間
		turnaround_time=0;
		RR_Info += "\nwaiting time : ";
		for(int i = 0; i <RR.size(); i++){
			//累加waitingtime 來計算總共所花的waiting time
			waiting_time += RR.get(i).RR_waiting_time;
			RR_Info += RR.get(i).process_name + ":"+ RR.get(i).RR_waiting_time+" ";
		}
		RR_Info += "\nAvg waiting time : "+ waiting_time/RR.size();
		avg_waiting_time[3] = waiting_time/RR.size();
		
		//計算RR 平均turnaround時間
		RR_Info += "\nturnaround_time time : ";
		for(int i = 0; i < RR.size(); i++){
			//累加turnaroundtime 來計算總共所花的turnaround time
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
		//show alogrithms 執行順序
		System.out.println("algorithms\t執行順序");
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
