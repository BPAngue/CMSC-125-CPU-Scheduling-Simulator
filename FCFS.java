import java.util.*;

public class FCFS {
	Map<String, int[]> processValues = new HashMap<>();
	
	public void insertProcesses(String processID, int arrivalTime, int burstTime) {
		processValues.put(processID, new int[]{arrivalTime, burstTime});
	}
	
	public void FCFSAlgo() {
		// convert hashmap to list and sort by arrival time
		List<Map.Entry<String, int[]>> sortedProcesses = new ArrayList<>(processValues.entrySet());
		sortedProcesses.sort(Comparator.comparingInt(entry -> entry.getValue()[0])); // sort by arrival time
		
		int currentTime = 0;
		int completionTime = 0;
		int turnAroundtime = 0;
		int waitingTime = 0;
		
		System.out.println("\nProcesses Execution Order (FCFS):");
		System.out.println("Process | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time |");
		
		for (Map.Entry<String, int[]> entry : sortedProcesses) {
			String processID = entry.getKey();
			int arrivalTime = entry.getValue()[0];
			int burstTime = entry.getValue()[1];
			
			if (currentTime < arrivalTime) {
				currentTime = arrivalTime;
			}
			
			currentTime = currentTime + burstTime;
			completionTime = currentTime;
			turnAroundtime = completionTime - arrivalTime;
			waitingTime = turnAroundtime - burstTime;
				
			System.out.printf("%7s | %12d | %10d | %15d | %15d | %12d |\n", processID, arrivalTime, burstTime, completionTime, turnAroundtime, waitingTime);
		}
	}
	
	public void displayProcesses() {
		for (Map.Entry<String, int[]> entry : processValues.entrySet()) {
			String key = entry.getKey();
			int[] values = entry.getValue();
			System.out.println(key + " -> [" + values[0] + ", " + values[1] + "]");
		}
	}
	
	public static void main(String[] args) {
		FCFS fcfs = new FCFS();
		fcfs.insertProcesses("P1", 0, 2);
		fcfs.insertProcesses("P2", 1, 2);
		fcfs.insertProcesses("P3", 5, 3);
		fcfs.insertProcesses("P4", 6, 4);
		System.out.println("Inserted Processes\n");
		fcfs.displayProcesses();
		fcfs.FCFSAlgo();
	}
	
}