package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class CPUScheduling extends Panels {
    
    // variables for simulator
    private ArrayList<Process> processList;
    private Simulator simulator;
    private ArrayList<String> ganttChartLabels = new ArrayList<>();
    private ArrayList<Integer> ganttChartTimes = new ArrayList<>();
    private JPanel header, midPanel, cpuPanel, readyQueuePanel, outputPanel;
    private GanttChartPanel ganttChartPanel;
    private JLabel logoLabel;
    private JScrollPane processScrollPane, ganttChartScrollPane;
    public JButton backButton, randomButton, clearButton, runButton;
    public Color gray, black, transparent, white, navyBlue;
    public JLabel cpuLabel, readyQueueLabel, processLabel1, processLabel2, algorithmLabel;
    public Timer timer;
    private Object currentSimulator;
    
    public CPUScheduling(Simulator simulator) {
        this.simulator = simulator;
        processList = this.simulator.getProcesses();
        
        // color variables
        gray = Color.GRAY;
        black = Color.BLACK;
        white = Color.WHITE;
        transparent = new Color(0,0,0,0);
        navyBlue = new Color(4, 3, 93);
        
        archivoblack = importFont("archivoblack");
        
        // header Panel
        header = new JPanel();
        header.setBorder(BorderFactory.createEmptyBorder(10,60,0,50));
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(950, 100));
        
        algorithmLabel = new JLabel();
        algorithmLabel.setForeground(white);
        algorithmLabel.setFont(archivoblack.deriveFont(30f));
        algorithmLabel.setPreferredSize(new Dimension(300, 100));
        algorithmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        logo = getImg("/img/logo_small.png");
        logoIcon = new ImageIcon(logo);
        
        logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));
        
        backButton = createButton("BACK");
        backButton.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        header.add(logoLabel);
        header.add(algorithmLabel);
        header.add(backButton);
        
        // ganttChartPanel
        ganttChartPanel = new GanttChartPanel(ganttChartLabels, ganttChartTimes);
        ganttChartPanel.setBackground(white);
        ganttChartPanel.setPreferredSize(new Dimension(10000, 100));
        ganttChartScrollPane = new JScrollPane(ganttChartPanel);
        ganttChartScrollPane.setPreferredSize(new Dimension(950, 120));
        
        // midpanel
        midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        midPanel.setBackground(navyBlue);
        midPanel.setPreferredSize(new Dimension(950, 100));
        
        cpuPanel = new JPanel();
        cpuPanel.setBackground(navyBlue);
        cpuPanel.setPreferredSize(new Dimension(475, 100));
        
        cpuLabel = new JLabel();
        cpuLabel.setFont(archivoblack.deriveFont(20f));
        cpuLabel.setForeground(Color.WHITE);
        cpuLabel.setText("CPU: ");
        processLabel2 = new JLabel("Idle");
        processLabel2.setFont(archivoblack.deriveFont(20f));
        processLabel2.setForeground(Color.YELLOW);
        cpuPanel.add(cpuLabel);
        cpuPanel.add(processLabel2);
        midPanel.add(cpuPanel);
        
        readyQueuePanel = new JPanel();
        readyQueuePanel.setBackground(navyBlue);
        readyQueuePanel.setPreferredSize(new Dimension(475, 100));
        
        readyQueueLabel = new JLabel();
        readyQueueLabel.setFont(archivoblack.deriveFont(20f));
        readyQueueLabel.setForeground(Color.WHITE);
        readyQueueLabel.setText("Ready Queue: ");
        processLabel1 = new JLabel("No processes in ready queue");
        processLabel1.setFont(archivoblack.deriveFont(20f));
        processLabel1.setForeground(Color.YELLOW);
        readyQueuePanel.add(readyQueueLabel);
        readyQueuePanel.add(processLabel1);
        midPanel.add(readyQueuePanel);
       
        // output panel
        outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        outputPanel.setBackground(navyBlue);
        outputPanel.setPreferredSize(new Dimension(930, 850));
        processScrollPane = new JScrollPane(outputPanel);
        processScrollPane.setPreferredSize(new Dimension(950, 350));
        
        outputPanel.add(createPanel(gray, black, "PROCESS ID"));
        outputPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        outputPanel.add(createPanel(gray, black, "BURST TIME")); 
        outputPanel.add(createPanel(gray, black, "PRIORITY")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>COMPLETION<br>TIME</body></html>")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>TURNAROUND<br>TIME (TT)</body></html>")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>WAITING<br>TIME (WT)</body></html>")); 
        outputPanel.add(createPanel(gray, black, "AVG. TT")); 
        outputPanel.add(createPanel(gray, black, "AVG. WT")); 
        
        add(header);
        add(ganttChartScrollPane);
        add(midPanel);
        add(processScrollPane);
    }
    
    public void startSimulation(int algorithm) {
        System.out.println(algorithm);
        if (processList.isEmpty()) {
            // for debugging
            System.out.println("No processes to simulate");
            return;
        } 
        stopCurrentSimulation();
        
        switch(algorithm) {
            case 0: // FCFS
                System.out.println("FCFS Simulator");
                algorithmLabel.setText("FCFS");
                currentSimulator = new FCFS(processList, ganttChartPanel, ganttChartLabels, ganttChartTimes, outputPanel, cpuPanel, readyQueuePanel, timer);
                ((FCFS) currentSimulator).startSimulation();
                break;
            case 1: // Round Robin
                System.out.println("Round Robin Simulator");
                algorithmLabel.setText("Round Robin");
                currentSimulator = new RoundRobin(processList, ganttChartPanel, ganttChartLabels, ganttChartTimes, outputPanel, cpuPanel, readyQueuePanel, timer);
                ((RoundRobin) currentSimulator).startSimulation(simulator.getQuantumTime());
                break;
            case 2: // SJF (non-preemptive)
                System.out.println("SJF Simulator");
                algorithmLabel.setText("SJF");
                currentSimulator = new SJFNP(processList, ganttChartPanel, ganttChartLabels, ganttChartTimes, outputPanel, cpuPanel, readyQueuePanel, timer);
                ((SJFNP) currentSimulator).startSimulation();
                break;
            case 3: // SRTF 
                System.out.println("SRTF Simulator");
                algorithmLabel.setText("SRTF");
                currentSimulator = new SJFP(processList, ganttChartPanel, ganttChartLabels, ganttChartTimes, outputPanel, cpuPanel, readyQueuePanel, timer);
                ((SJFP) currentSimulator).startSimulation();
                break;
            case 4: // Priority (non-preemptive)
                System.out.println("Priority non-preemptive Simulator");
                algorithmLabel.setText("Priority (NP)");
                currentSimulator = new PriorityNP(processList, ganttChartPanel, ganttChartLabels, ganttChartTimes, outputPanel, cpuPanel, readyQueuePanel, timer);
                ((PriorityNP) currentSimulator).startSimulation(simulator.getPriorityLevel());
                break;
            case 5: // Priority (preemptive)
                System.out.println("Priority preemptive Simulator");
                algorithmLabel.setText("Priority (P)");
                currentSimulator = new PriorityP(processList, ganttChartPanel, ganttChartLabels, ganttChartTimes, outputPanel, cpuPanel, readyQueuePanel, timer);
                ((PriorityP) currentSimulator).startSimulation(simulator.getPriorityLevel());
                break;
        }
    }  
    
    @Override
    public JButton createButton(String text) {
        archivoblack = importFont("archivoblack");
        JButton button = new JButton(text);
        button.setBackground(new Color(118,130,138));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(archivoblack.deriveFont(Font.BOLD, 20));
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public final JPanel createPanel(Color background, Color foreground, String label){
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel jlabel = new JLabel();
        jpanel.setPreferredSize(new Dimension (103, 40));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jpanel.setBackground(background);
        jlabel.setForeground(foreground);
        jlabel.setText(label);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setFont(archivoblack.deriveFont(10f));
        jpanel.add(jlabel, BorderLayout.CENTER);
        
        return jpanel;
    }
    
    public void clearGanttChartPanel() {
        ganttChartPanel.removeAll(); // Remove all components
        ganttChartPanel.revalidate(); // Revalidate the panel
        ganttChartPanel.repaint(); // Repaint to reflect changes
        ganttChartLabels.clear(); // Clear stored Gantt chart labels
        ganttChartTimes.clear(); // Clear stored Gantt chart times
    }
    
    public void stopCurrentSimulation() {
        if(currentSimulator instanceof FCFS fcfs) {
            fcfs.stopSimulation();
            fcfs.clearOutputPanel();
        } else if(currentSimulator instanceof RoundRobin roundRobin) {
            roundRobin.stopSimulation();
            roundRobin.clearOutputPanel();
        } else if(currentSimulator instanceof SJFNP sJFNonPreemptive) {
            sJFNonPreemptive.stopSimulation();
            sJFNonPreemptive.clearOutputPanel();
        } else if(currentSimulator instanceof SJFP sJFPreemptive) {
            sJFPreemptive.stopSimulation();
            sJFPreemptive.clearOutputPanel();
        } else if(currentSimulator instanceof PriorityNP priorityNonPreemptive) {
            priorityNonPreemptive.stopSimulation();
            priorityNonPreemptive.clearOutputPanel();
        } else if(currentSimulator instanceof PriorityP priorityPreemptive) {
            priorityPreemptive.stopSimulation();
            priorityPreemptive.clearOutputPanel();
        }
    }
}
