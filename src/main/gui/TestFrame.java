package main.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import main.domain.Node;


public class TestFrame extends JFrame implements Listener {
	
	class OverviewOfTiles extends JPanel {
		private static final long serialVersionUID = 1L;				
		@Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);	 
	        g.drawImage(displayedImage,0,0,null);	                
	        g.dispose();
	    }
	} 	
	
	private static final long serialVersionUID = 1690046530696444005L;
	private int width = 2000;
	private int height = 1040;	
	private JTextArea textArea = new JTextArea();
	private JTable resourceOverview= new JTable();
	private JTable supplyOverview= new JTable();
	private JTable producerOverview = new JTable();
	private JTable transportOverview= new JTable();
	private JTable tileOverview= new JTable();
	private JTable switchOverview= new JTable();
	private JPanel optionsPanel = new JPanel(new GridLayout(2,3));
	private BufferedImage displayedImage = null;
	private JRadioButton transportButton = new JRadioButton("Transport");
	private JRadioButton resourceButton = new JRadioButton("Resource");
	private JRadioButton noneButton = new JRadioButton("None");	
	private JRadioButton switchButton = new JRadioButton("Switch");	
	private List<Observer> observers = new ArrayList<Observer>();

	public TestFrame(Controller controller) {
		super();		
		initScreen();
		initElements(controller);	
		initGraphics();
		addButtons();
		initPauze();
		textArea.setBounds(805,50,200,800);
		add(textArea);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setDisplayImage(controller.getNodes());		
	}
	
	private void addButtons() {
		JPanel panel = new JPanel(new GridLayout(2,2));        
	    ButtonGroup bG = new ButtonGroup();
	    bG.add(transportButton);
	    bG.add(resourceButton);
	    bG.add(noneButton);
	    bG.add(switchButton);
	    panel.add(transportButton);
	    panel.add(resourceButton);
	    panel.add(noneButton);
	    panel.add(switchButton);
	    transportButton.setSelected(true);
	    panel.setBounds(1005,50,175,100);
	    this.add(panel);
	}

	private void initGraphics() {		
		OverviewOfTiles overview = new OverviewOfTiles();		
		JScrollPane scrollPane = new JScrollPane(overview);		
		overview.addMouseListener(new MouseInputAdapter() {
            
			Point press;
			
			@Override
			public void mousePressed(MouseEvent e) {
				press = e.getPoint();
			    
			}
			
			@Override
			public void mouseReleased(MouseEvent release) {				   
			    String valueInCell = "";			    
            	if(resourceButton.isSelected()) { 
                	valueInCell = (String) resourceOverview.getValueAt(resourceOverview.getSelectedRow(), 1); 
                	updateAllObservers("Clicked|"+(press.y/50)+"|"+(press.x/50)+"|"+valueInCell+"|"+(release.getY()/50)+"|"+(release.getX()/50));
                }  else if (noneButton.isSelected()) {
                	valueInCell = "Information";
                	updateAllObservers("Clicked|"+(press.y/50)+"|"+(press.x/50)+"|"+valueInCell+"|"+(release.getY()/50)+"|"+(release.getX()/50));
                }	else if (switchButton.isSelected()) {
                	valueInCell =  (String) switchOverview.getValueAt(switchOverview.getSelectedRow(), 0);
                	updateAllObservers("Switch|"+(press.y/50)+"|"+(press.x/50)+"|"+valueInCell);
                }
                	else {
                	valueInCell = ""+ transportOverview.getValueAt(transportOverview.getSelectedRow(), 0); 
                	updateAllObservers("Transport|"+(press.y/50)+"|"+(press.x/50)+"|"+valueInCell+"|"+(release.getY()/50)+"|"+(release.getX()/50));
                }                
            	
			}			
		
        });
		scrollPane.setBounds(0,50,800,800);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
		add(scrollPane);	
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	
	public void setDisplayImage(Node[][] value) {
		displayedImage = new BufferedImage(value[0].length*50, value.length*50, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D) displayedImage.getGraphics();
		for (int y = 0; y<value.length;y++){
        	for (int x = 0; x < value[0].length;x++){        		
        		switch(value[y][x].getTileCode()) {
        		case 1:
	        		g.setColor(new Color(255, 255, 153));	
	                break;
        		case 2:
	        		g.setColor(new Color(153, 255, 102));	
	                break;
        		case 3:
	        		g.setColor(new Color(0,102,0));	
	                break;
        		case 4:
	        		g.setColor(new Color(153, 153, 102));	
	                break;
        		case 5:
	        		g.setColor(new Color(0, 102, 255));	
	                break;
        		case 6:
	        		g.setColor(new Color(255, 0, 0));	
	                break;
        		default : 
        			g.setColor(Color.GRAY);        		
        		}
        		g.fillRect(x*50, y*50, 50, 50);        		       		
    	        g.setColor(Color.BLACK);
    	        g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
    	        g.drawString(""+value[y][x].getTileCode(), 5+x*50, y*50+13);
    	        g.drawString(value[y][x].getTransportType().getName(), 5+x*50, y*50+23);
    	        g.drawString(value[y][x].getTopNetworkKey()+","+value[y][x].getDetailNetworkKey(), 5+x*50, y*50+33);	
        	}
        }
        g.dispose();
        repaint();
	}
	
	
	/** 
	 * Sets up de scherm settings
	 */ 
	private void initScreen() {
		setTitle("Market");		
		setSize(width,height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width-width)/2, ((dim.height -  height)/2)-200);
		setLayout(null);		
	}
	
	/** 
	 * Initeert stap voor stap de componenten die de UI opmaken
	 * @param controller 
	 */ 
	private void initElements(Controller controller) {				
		Map<String, TableRepresentation> reps = controller.getAllTableReps();
		genericTable(optionsPanel,resourceOverview,reps.get("Resource"));
		genericTable(optionsPanel,supplyOverview,controller.getStockTable());
		genericTable(optionsPanel,producerOverview,reps.get("Manufacturer"));
		genericTable(optionsPanel,transportOverview,reps.get("Transport"));
		genericTable(optionsPanel,tileOverview,reps.get("Tile"));
		genericTable(optionsPanel,switchOverview,reps.get("Switch"));
		optionsPanel.setBounds(1200,50,700,900);
		add(optionsPanel);
	}

	private void genericTable(JPanel optionsPanel2, JTable table, TableRepresentation tableRep) {		
		JScrollPane scrollPane = new JScrollPane(table);		
		table.setModel(new CustomTableModel(tableRep.getObjectRepresentation(), tableRep.getColumnNames()));
		table.setRowSelectionInterval(0, 0);
		optionsPanel2.add(scrollPane);
	}

	/** 
	 * Initeert search knop, en de listener die actief is op die knop. De knop wordt gebruikt om de informatie voor een vestiging op te zoeken
	 */ 
	private void initPauze() {
		JButton button = new JButton("Pauze");
		button.setBounds(100,10,90,40);		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {					
				updateAllObservers("Pauze");
			}
		});
		add(button);
	}
	
	public void updateAllObservers(String message) {
		for(Observer observer:observers) {
			observer.notify(message);
		}
	}

	@Override
	public synchronized void update(List<String> message, Controller controller) {		
		SwingUtilities.invokeLater(new UpdateGUI(message, controller));
	}
	
	class UpdateGUI implements Runnable {
		
		Controller controller;
		List<String> message;
		
		private UpdateGUI(List<String> message, Controller controller) {
			this.controller =controller;
			this.message = message;
		}
		
		@Override
		public void run() {
			if(message.get(0).equals("Simulate")) {
				supplyOverview.setModel(new CustomTableModel(controller.getStockTable().getObjectRepresentation(), controller.getStockTable().getColumnNames()));
			} else if(message.get(0).equals("Clicked")&&!message.get(3).equals("Information")) {
				setDisplayImage(controller.getNodes());
			} else if(message.get(3).equals("Information")) {
				textArea.setText(null);
				for(String string:controller.getNodes()[Integer.parseInt(message.get(1))][Integer.parseInt(message.get(2))].getStatus()) {
					textArea.append(string+"\n");
				}
			}			
		}
		
	}

	@Override
	public void update(String[] args, Controller controller) {
		SwingUtilities.invokeLater(new UpdateGUI2(args, controller));		
	}

	class UpdateGUI2 implements Runnable {
		
		Controller controller;
		String[] args;
		
		private UpdateGUI2(String[] args, Controller controller) {
			this.controller =controller;
			this.args = args;
		}
		
		@Override
		public void run() {
			if(args[0].equals("Transport")) {				
				setDisplayImage(controller.getNodes());			
			}			
		}
		
	}
}
