package main.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
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
	private JTable resourceOverview= new JTable();
	private JTable supplyOverview= new JTable();
	private JTable producerOverview = new JTable();
	private JTable transportOverview= new JTable();
	private JTable tileOverview= new JTable();
	private JPanel optionsPanel = new JPanel(new GridLayout(2,3));
	private BufferedImage displayedImage = null;
	private JRadioButton transportButton = new JRadioButton("Transport");
	private JRadioButton resourceButton = new JRadioButton("Resource");	
	private List<Observer> observers = new ArrayList<Observer>();

	public TestFrame(Controller controller) {
		super();		
		initScreen();
		initElements(controller);	
		initGraphics();
		addButtons();
		initPauze();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setDisplayImage(controller.getNodes());		
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
                } else {
                	valueInCell = (String) transportOverview.getValueAt(transportOverview.getSelectedRow(), 1);     
                }                
			    updateAllObservers("Clicked|"+(press.y/50)+"|"+(press.x/50)+"|"+valueInCell+"|"+(release.getY()/50)+"|"+(release.getX()/50));
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
	        		g.setColor(Color.YELLOW);	
	                break;
        		case 2:
	        		g.setColor(Color.GREEN);	
	                break;
        		case 3:
	        		g.setColor(new Color(0,102,0));	
	                break;
        		default : 
        			g.setColor(Color.GRAY);        		
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
				
		genericTable(optionsPanel,resourceOverview,controller.getResources(), controller.getResourceColumns());
		genericTable(optionsPanel,supplyOverview,controller.getStocks(), controller.getStockColumns());
		genericTable(optionsPanel,producerOverview,controller.getManufacturer(), controller.getManufactorerColumns());
		genericTable(optionsPanel,transportOverview,controller.getTransportTypes(), controller.getTransportColumns());
		genericTable(optionsPanel,tileOverview,controller.getTiles(), controller.getTileColumns());
		optionsPanel.setBounds(1200,50,700,900);
		add(optionsPanel);
	}

	private void genericTable(JPanel optionsPanel2, JTable table, Object[][] objects, List<String> list) {		
		JScrollPane scrollPane = new JScrollPane(table);		
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
				supplyOverview.setModel(new CustomTableModel(controller.getStocks(), controller.getStockColumns()));
			} else if(message.equals("Click")) {
				setDisplayImage(controller.getNodes());
			}			
		}
		
	}

	
}
