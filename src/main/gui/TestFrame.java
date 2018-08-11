package main.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import main.map.Tile;

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
	private int width = 1400;
	private int height = 1040;	
	private JTable resourceOverview;
	private JTable supplyOverview;
	private JTable producerOverview;
	private BufferedImage displayedImage = null;
	private List<Observer> observers = new ArrayList<Observer>();

	
	/** 
	 * Fungeert als het hoofd scherm voor de prik2go interface
	 * @param controller de controller via welke de domein elementen van Prik2Go beheerst worden	
	 */ 
	public TestFrame(Controller controller) {
		super();		
		initScreen();
		initElements();	
		initGraphics();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resourceOverview.setModel(new CustomTableModel(controller.getResources(), controller.getResourceColumnNames()));
		resourceOverview.setRowSelectionInterval(0, 0);
	

		setDisplayImage(controller.getTiles());
		addButtons();
		
	}
	
	private void addButtons() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		JRadioButton jRadioButton1 = new JRadioButton("Producer");
	    JRadioButton jRadioButton2 = new JRadioButton("Resource");
	 // Setting Bounds of "jRadioButton2".
        jRadioButton1.setBounds(120, 30, 120, 50);

        // Setting Bounds of "jRadioButton4".
        jRadioButton2.setBounds(250, 30, 80, 50);
	    ButtonGroup bG = new ButtonGroup();
	    bG.add(jRadioButton1);
	    bG.add(jRadioButton2);
	    panel.add(jRadioButton1);
	    panel.add(jRadioButton2);
	    jRadioButton1.setSelected(true);
	    panel.setBounds(0,0,100,50);
	    this.add(panel);
	}

	private void initGraphics() {		
		OverviewOfTiles overview = new OverviewOfTiles();
		overview.setPreferredSize(new Dimension(400,400));
		JScrollPane scrollPane = new JScrollPane(overview);
		scrollPane.setBounds(240,50,500,300);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {            	            
                 String valueInCell = (String) resourceOverview.getValueAt(resourceOverview.getSelectedRow(), 0);
            	 updateAllObservers("Clicked|"+(e.getY()/20)+"|"+(e.getX()/20)+"|"+valueInCell);
            }
        });
		add(scrollPane);		
		    
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	
	public void setDisplayImage(Tile[][] value) {
		displayedImage = new BufferedImage(value[0].length*20, value.length*20, BufferedImage.TYPE_3BYTE_BGR);
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
        		g.fillRect(x*20, y*20, 20, 20);        		       		
    	        g.setColor(Color.BLACK);
    	        g.drawString(""+value[y][x].getAboveGroundResource().getShortDescription(), 5+x*20, y*20+13);	        		
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
	 */ 
	private void initElements() {	
		initPauze();		
		initResourceOverview();
		initSupplyOverview();	
		initProducerOverview();
	}

	private void initProducerOverview() {
		producerOverview = new JTable();
		JScrollPane scrollPane = new JScrollPane(producerOverview);		
		scrollPane.setBounds(10,450,500,300);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
		add(scrollPane);		
	}


	/** 
	 * Initeert search knop, en de listener die actief is op die knop. De knop wordt gebruikt om de informatie voor een vestiging op te zoeken
	 */ 
	private void initPauze() {
		JButton button = new JButton("Pauze");
		button.setBounds(40,100,90,40);		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {					
				updateAllObservers("Pauze");
			}
		});
		add(button);
	}
	
	/** 
	 * Initeert de tabel waar de klant informatie in terecht komt, en de scrollpane waarin die tabel op zijn beurt weer terecht komt
	 */ 
	private void initResourceOverview() {
		resourceOverview = new JTable();
		JScrollPane scrollPane = new JScrollPane(resourceOverview);		
		scrollPane.setBounds(840,50,500,300);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
		add(scrollPane);		
	}
	
	/** 
	 * Initeert de tabel waar de klant informatie in terecht komt, en de scrollpane waarin die tabel op zijn beurt weer terecht komt
	 */ 
	private void initSupplyOverview() {
		supplyOverview = new JTable();
		JScrollPane scrollPane = new JScrollPane(supplyOverview);		
		scrollPane.setBounds(840,550,500,300);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
		add(scrollPane);		
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
