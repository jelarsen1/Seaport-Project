// CMSC335Project 1
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Main GUI 
// Creates a new World and adds various subclasses of Thing from by reading a text file
// People are assigned to ports.  A ship has a list of jobs that need to be processed.
// The progress bars represent the jobs being processed.
// If a ship is at a dock, and the people with the required skills are available, the job will start.
// Threads are used to represent the jobs with synchronization locks in order to prevent deadlock.
// When all the jobs for a ship are done, the ship can leave the dock and the next ship in queue at the port is added to the dock.
// The GUI also includes search fields and a visual representation of the various classes in the world.


package cmsc.pkg335.project.pkg1;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;


public class CMSC335Project1 extends JFrame{

    
    public static void main(String[] args) {
        
        String[] sortBy = {"Ships in Queue by Weight", "Ships in Queue by Length", "Ships in Queue by Width", 
            "Ships in Queue by Draft", "All Ships by Name", "People by Name", "Ports by Name",
            "Docks by Name"};
        
        JFrame window = new JFrame("SeaPorts Project");
        JTextArea text = new JTextArea(20, 50);
        JTextArea searchText = new JTextArea();
        JTextArea resultsText = new JTextArea(5, 20);
        JTextArea sortedText = new JTextArea();
        JTextArea resourcesText = new JTextArea(15, 35);
        text.setEditable(false);
        resultsText.setEditable(false);
        sortedText.setEditable(false);
        JPanel progressBarPanel = new JPanel();
        JPanel resourcesPanel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panelSouth = new JPanel();
        JPanel searchPanel = new JPanel();
        JPanel radioButtonPanel = new JPanel();
        JPanel sortPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel threadsPanel = new JPanel();
        JScrollPane jPBPane = new JScrollPane(progressBarPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollPane = new JScrollPane(text);
        JScrollPane resultsScrollPane = new JScrollPane(resultsText);
        JScrollPane sortedScrollPane = new JScrollPane(sortedText);
        JScrollPane resourcesScrollPane = new JScrollPane(resourcesText);
        
        JButton chooseFileButton = new JButton("Choose a file");
        JButton searchButton = new JButton("Search");
        JButton sortButton = new JButton("Sort");
        JComboBox sortList = new JComboBox(sortBy);
        TitledBorder radioBorder = new TitledBorder("Search Options");
        TitledBorder searchBorder = new TitledBorder("Search Field");
        TitledBorder resultsBorder = new TitledBorder("Search Results");
        TitledBorder sortBorder = new TitledBorder("Sort");
        TitledBorder treeBorder = new TitledBorder("JTree");
        TitledBorder progressBarBorder = new TitledBorder("Progress Bars: Job Name:Parent");
        TitledBorder resourcesBorder = new TitledBorder("Threads and resources:>>Job:Thread.isAlive():Resources Aquired");
        
        panel1.setMinimumSize(new Dimension(150,30));
        centerPanel.setMinimumSize(new Dimension(700,300));
        panelSouth.setMinimumSize(new Dimension(500,150));
        threadsPanel.setMinimumSize(new Dimension(700,300));
        
        searchPanel.setLayout(new GridLayout(2, 1));
        searchPanel.setBorder(searchBorder);
        searchPanel.add(searchText);
        searchPanel.add(searchButton);
        
        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(scrollPane);
        
        resultsScrollPane.setBorder(resultsBorder);
        
        JRadioButton indexRadioButton = new JRadioButton("Ship by Index");
        JRadioButton nameRadioButton = new JRadioButton("Ship by name");
        JRadioButton personRadioButton = new JRadioButton("Person by skill");
        ButtonGroup group = new ButtonGroup();
        group.add(indexRadioButton);
        group.add(nameRadioButton);
        group.add(personRadioButton);
        
        radioButtonPanel.setBorder(radioBorder);
        radioButtonPanel.setLayout(new GridLayout(3, 1));
        radioButtonPanel.add(indexRadioButton);
        radioButtonPanel.add(nameRadioButton);
        radioButtonPanel.add(personRadioButton);
        indexRadioButton.setSelected(true);
        
        sortPanel.setLayout(new GridLayout(2, 1));
        sortPanel.add(sortList);
        sortPanel.add(sortButton);
                
        panel1.setLayout(new GridLayout(1, 3));
        panel1.add(chooseFileButton);
        
        panel2.setLayout(new GridLayout(1, 3));
        panel2.add(radioButtonPanel);
        panel2.add(searchPanel);
        panel2.add(resultsScrollPane);
        
        panel3.setLayout(new GridLayout(1,2));
        panel3.setBorder(sortBorder);
        panel3.add(sortPanel);
        panel3.add(sortedScrollPane);
                
        panelSouth.setLayout(new GridLayout(2,1));
        panelSouth.add(panel2);
        panelSouth.add(panel3);
        
        jPBPane.setBorder(progressBarBorder);
        resourcesScrollPane.setBorder(resourcesBorder);
        
        threadsPanel.setLayout(new GridLayout(1,2));
        threadsPanel.add(jPBPane);
        threadsPanel.add(resourcesScrollPane);
        
        window.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        window.add(panel1, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        window.add(centerPanel, c);
        
        c.gridy = 2;
        window.add(panelSouth, c);
        
        c.gridy = 3;
        window.add(threadsPanel,c);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.pack();
        
        JFileChooser jfc = new JFileChooser(".");
        
        Scanner sc = new Scanner("World 00000 0");
        World world = new World(sc);
        
        
        //When the Choose File button is pressed and an appropriate file is selected
        //Populates the World with all of the subclasses.  Creates the JProgressBars
        //and threads for the Jobs and updates them.
        chooseFileButton.addActionListener((ActionEvent e) -> {
            int rV = jfc.showOpenDialog(null);
            if(rV == JFileChooser.APPROVE_OPTION){
                File selectedFile = jfc.getSelectedFile();
                try {
                    FileReader reader  = new FileReader(selectedFile);
                    BufferedReader textReader = new BufferedReader(reader);
                    Scanner sc1 = new Scanner(textReader);
                    
                    while(sc1.hasNextLine()){
                        String st = sc1.nextLine();
                        Scanner sc2 = new Scanner(st);
                        System.out.println("Processing >"+st+"<");
                        
                        if(!st.isEmpty()){
                            
                            switch (sc2.next()){
                                case "\\" : break;
                                case "port" :
                                    String temp = sc2.nextLine();
                                    SeaPort tempPort = new SeaPort(new Scanner(temp));
                                    world.addPort(tempPort);
                                    break;
                                case "dock" :
                                    Dock tempDock = new Dock(new Scanner(sc2.nextLine()));
                                    world.searchPortByIndex(tempDock.parent).addDock(tempDock);
                                    break;
                                case "person" :
                                    Person tempPerson = new Person(new Scanner(sc2.nextLine()));
                                    world.searchPortByIndex(tempPerson.parent).addPerson(tempPerson);
                                    break;
                                case "pship" :
                                    PassengerShip tempPShip = new PassengerShip(new Scanner(sc2.nextLine()));
                                    world.addShip(tempPShip);
                                    break;
                                case "cship" :
                                    CargoShip tempCShip = new CargoShip(new Scanner(sc2.nextLine()));
                                    world.addShip(tempCShip);
                                    break;
                                case "job" :
                                    Job tempJob = new Job(new Scanner(sc2.nextLine()));
                                    world.addJob(tempJob);
                                default: break;
                            }
                        }
                    }
                    
                    text.setText(world.toString());
                    progressBarPanel.setLayout(new GridLayout(0,2));
                    ArrayList<Job> jobLabels = world.getJobs();
                    int i = 0;
                    
                    for(JProgressBar jp:world.getJPs()){ 
                        String s = jobLabels.get(i).name+":"+jobLabels.get(i).parent;
                        progressBarPanel.add(new JLabel(s));
                        jp.setPreferredSize(new Dimension(140, 20));
                        progressBarPanel.add(jp);
                        i++;
                    }
                    
                    threadsPanel.revalidate();
                    
                    JTree tree = new JTree(world.getTree());
                    JScrollPane treeScrollPane = new JScrollPane(tree);
                    treeScrollPane.setBorder(treeBorder);
                    centerPanel.add(treeScrollPane);
                    centerPanel.revalidate ();
                    window.revalidate ();
                    window.repaint();
                    new Thread(new Runnable(){
                        public void run() {
                            world.startThread();
                        }
                    }).start();
                    
                    new SwingWorker<Void, String>(){
                        
                        @Override
                        protected void process(List<String> chunks){
                            String s = chunks.get(chunks.size()-1);
                            resourcesText.setText(s);
                        }
                        
                        @Override
                        protected Void doInBackground() throws Exception {
                            while(!world.jobsDone()){
                                publish(world.getResources());
                            }
                            
                            System.out.println("Jobs done from GUI");
                            return null;
                        }
                    
                    }.execute();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CMSC335Project1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        searchButton.addActionListener((ActionEvent e) -> {
            String output = "";
            if(indexRadioButton.isSelected()){
                ArrayList<Ship> ships = world.searchShipIndex(Integer.parseInt(searchText.getText()));
                for(Ship i:ships) output+=i+"\n";
                resultsText.setText(output);
            }
            
            if(nameRadioButton.isSelected()){
                ArrayList<Ship> ships = world.searchShipName(searchText.getText());
                for(Ship i:ships) output+=i+"\n";
                resultsText.setText(output);
            }
            
            if(personRadioButton.isSelected()){
                ArrayList<Person> people = world.searchPersonSkill(searchText.getText());
                for(Person p:people) output+=p+"\n";
                resultsText.setText(output);
            }          
        });
        
        sortButton.addActionListener((ActionEvent e) -> {
            int selected = sortList.getSelectedIndex();
            switch(selected){
                case 0: sortedText.setText(world.sortShipsByWeight());
                break;
                case 1: sortedText.setText(world.sortShipsByLength());
                break;
                case 2: sortedText.setText(world.sortShipsByWidth());
                break;
                case 3: sortedText.setText(world.sortShipsByDraft());
                break;
                case 4: sortedText.setText(world.sortShipsByName());
                break;
                case 5: sortedText.setText(world.sortPeople());
                break;
                case 6: sortedText.setText(world.sortPorts());
                break;
                case 7: sortedText.setText(world.sortDocks());
                break;
            }        
        });
        
    }
}
