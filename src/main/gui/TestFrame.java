package main.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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
	private JTable resourceOverview= new JTable();
	private JTable supplyOverview= new JTable();
	private JTable producerOverview = new JTable();
	private JTable transportOverview= new JTable();
	private JPanel optionsPanel = new JPanel(new GridLayout(2,3));
	private BufferedImage displayedImage = null;
	private JRadioButton transportButton = new JRadioButton("Transport");
	private JRadioButton resourceButton = new JRadioButton("Resource");	
	private List<Observer> observers = new ArrayList<Observer>();

	
	/** 
	 * Fungeert als het hoofd scherm voor de prik2go interface
	 * @param controller de controller via welke de domein elementen van Prik2Go beheerst worden	
	 */ 
	public TestFrame(Controller controller) {
		super();		
		initScreen();
		initElements(controller);	
		initGraphics();
		addButtons();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setDisplayImage(controller.getTiles());		
	}
	
	private void addButtons() {
		JPanel panel = new JPanel(new GridLayout(2,1));
        transportButton.setBounds(120, 30, 120, 50);        
        resourceButton.setBounds(250, 30, 80, 50);
	    ButtonGroup bG = new ButtonGroup();
	    bG.add(transportButton);
	    bG.add(resourceButton);
	    panel.add(transportButton);
	    panel.add(resourceButton);
	    transportButton.setSelected(true);
	    panel.setBounds(0,0,100,50);
	    this.add(panel);
	}

	private void initGraphics() {		
		OverviewOfTiles overview = new OverviewOfTiles();		
		JScrollPane scrollPane = new JScrollPane(overview);
		scrollPane.setBounds(0,50,800,800);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {            	            
            	String valueInCell = "";
            	if(resourceButton.isSelected()) { 
                	valueInCell = (String) resourceOverview.getValueAt(resourceOverview.getSelectedRow(), 0);                	
                } else {
                	valueInCell = (String) transportOverview.getValueAt(transportOverview.getSelectedRow(), 0);     
                }
                updateAllObservers("Clicked|"+(e.getY()/50)+"|"+(e.getX()/50)+"|"+valueInCell);
            }
        });
		add(scrollPane);
		optionsPanel.setBounds(1200,50,700,900);
		this.add(optionsPanel);
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	
	public void setDisplayImage(Node[][] value) {
		displayedImage = new BufferedImage(value[0].length*50, value.length*50, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D) displayedImage.getGraphics();
		for (int y = 0; y<value.length;y++){
        	for (int x = 0; x < value[0].length;x++){        		
        		switch(value[y][x].getAboveGroundResource().getName()) {
        		case "Corn":
        		g.setColor(Color.YELLOW);	
                break;
        		case "Wood":
        		g.setColor(Color.GREEN);	
                break;
        		case "Cow":
        		g.setColor(Color.GRAY);	
                break;
        		default : 
        		g.setColor(Color.CYAN);        		
        		}
        		g.fillRect(x*50, y*50, 50, 50);        		       		
    	        g.setColor(Color.BLACK);
    	        g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
    	        g.drawString(""+value[y][x].getAboveGroundResource().getShortDescription(), 5+x*50, y*50+13);
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
		initPauze();		
		genericTable(optionsPanel,resourceOverview,840,50,500,300,controller.getResources(), controller.getResourceColumnNames());
		genericTable(optionsPanel,supplyOverview,840,550,500,300,controller.getStock(), controller.getStockNames());
		genericTable(optionsPanel,producerOverview,10,450,500,300,controller.getManufacturer(), controller.getManufacturerColumnNames());
		genericTable(optionsPanel,transportOverview,10,200,200,200,controller.getTransportTypes(), controller.getTransportColumns());
	}

	private void genericTable(JPanel optionsPanel2, JTable table, int x, int y, int width, int height, Object[][] objects, List<String> list) {		
		JScrollPane scrollPane = new JScrollPane(table);		
		scrollPane.setBounds(x,y,width,height);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
		optionsPanel2.add(scrollPane);
		table.setModel(new CustomTableModel(objects, list));
		table.setRowSelectionInterval(0, 0);
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
	public synchronized void update(String message, Controller controller) {		
		SwingUtilities.invokeLater(new UpdateGUI(message, controller));
	}
	
	class UpdateGUI implements Runnable {
		
		Controller controller;
		String message;
		
		private UpdateGUI(String message, Controller controller) {
			this.controller =controller;
			this.message = message;
		}
		
		@Override
		public void run() {
			if(message.equals("Simulate")) {
				supplyOverview.setModel(new CustomTableModel(controller.getStock(), controller.getStockNames()));
			} else if(message.equals("Click")) {
				setDisplayImage(controller.getTiles());
			}			
		}
		
	}

	
}
