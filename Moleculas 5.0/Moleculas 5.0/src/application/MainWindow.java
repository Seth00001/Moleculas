package application;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import engine.GridHelper;

public class MainWindow extends JFrame{
	private JMenuBar menu;
	private JMenu mBegin, mFile;
	private JMenuItem miStartOne, miStartMany, miStop, miStartUpdates, miStopUpdates, miCalculateAtoms,
						miExportTxt, miExportVMD, miBeginExport, miEndExport, miSave;
	private JLabel stepInfo, layerInfo; 
	private Panel panel;
	
	public GridHelper helper;
	public boolean isUpdating;
//	public ArrayList<IPaintable> paintedChildren;
	
	
	//Action section
	private ActionListener alStartOne = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			helper.startCalculation();
		}
	};
	
	private ActionListener alStartMany = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
//			helper.startCalculation();
		}
	};
	
	private ActionListener alStop = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			helper.stopCalculation();
		}
	};
	
	private ActionListener alStartUpdates = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startUpdates();
		}
	};
	
	private ActionListener alStopUpdates = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			stopUpdates();
		}
	};
	
	private  ActionListener alCalculateAtoms = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {

		}
	};
	
	private ActionListener alExportTxt = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
//			helper.saveForUser("");
		}
	};
	
	private ActionListener alStartExportingVMD = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
			helper.beginExporting();
		}
	};	
	
	private ActionListener alExportVMD = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				helper.exportForVMD();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			helper.saveForUser("");
		}
	};
	
	public MainWindow() {
		
		super();
		initialize();
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				//JOptionPane.showMessageDialog(null, e.getKeyCode());
				
				switch(e.getKeyCode()) {
					case(38):{
						if(helper.currentlyPaintedPlane < helper.grid.dimZ - 1) {
							helper.currentlyPaintedPlane++;
						}
						break;
					}
					case(40):{
						if(helper.currentlyPaintedPlane > 0) {
							helper.currentlyPaintedPlane--;
						}
						break;
					}
					case(107):{
						helper.setTemperature(helper.getTemperature() + 0.1);
						break;
					}
					case(109):{
						helper.setTemperature(helper.getTemperature() - 0.1);
						break;
					}
				}
				setTitle( Integer.toString( helper.currentlyPaintedPlane)  + "   " + helper.getTemperature() + "  " + helper.grid.concentration); 
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
		isUpdating = false;
	}
	
	public void setHelper(GridHelper Helper) {
		helper = Helper;
		panel.addPainter(Helper);
	}
	
	public void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(null);
		
		panel = new Panel();
		panel.setBounds(0, 25, this.getBounds().width, this.getBounds().height - 25);
		add(panel);
		
		menu = new JMenuBar();
		menu.setVisible(true);
		menu.setBounds(0, 0, this.getBounds().width, 25);
		//menu.setLayout(new FlowLayout());
		
		
		//mBegin section
		mBegin = new JMenu("Actions");
		//mBegin.setBounds(0, 0, 50, 20);
		menu.add(mBegin);
		
		miStartOne = new JMenuItem("Start one");
		miStartOne.addActionListener(alStartOne);
		mBegin.add(miStartOne);
		
		miStartMany = new JMenuItem("Start many");
		miStartMany.addActionListener(alStartMany);
		mBegin.add(miStartMany);
		
		miStop = new JMenuItem("Stop");
		miStop.addActionListener(alStop);
		mBegin.add(miStop);

		miStartUpdates = new JMenuItem("Start updates");
		miStartUpdates.addActionListener(alStartUpdates);
		mBegin.add(miStartUpdates);

		miStopUpdates = new JMenuItem("Stop updates");
		miStopUpdates.addActionListener(alStopUpdates);
		mBegin.add(miStopUpdates);
		
		miCalculateAtoms = new JMenuItem("Calculate atoms");
		miCalculateAtoms.addActionListener(alCalculateAtoms);
		mBegin.add(miCalculateAtoms);
		
		//end of mBegin section
		
		
		//mFile section
		
		mFile = new JMenu("File");
		//mBegin.setBounds(0, 0, 50, 20);
		menu.add(mFile);
		
		miExportTxt = new JMenuItem("Export to .txt file");
		miExportTxt.addActionListener(alExportTxt);
		mFile.add(miExportTxt);

		miExportVMD = new JMenuItem("Export for VMD");
		miExportVMD.addActionListener(alExportVMD);
		mFile.add(miExportVMD);
		
		miBeginExport =  new JMenuItem("Start Exporting");
//		miBeginExport.addActionListener(alStartVMDAutosaves);
		mFile.add(miBeginExport);
		
		miEndExport =  new JMenuItem("Stop Exporting");
//		miEndExport.addActionListener(alStopVMDAutosaves);
		mFile.add(miEndExport);
		
		miSave = new JMenuItem("Save as backup");
//		miSave.addActionListener(alSaveGrid);
		mFile.add(miSave);
		
		menu.add(mFile);
		
		
		
		add(menu);
		menu.updateUI();
	}
	
	public void startUpdates() {
		isUpdating = true;
		Thread t = new Thread() {
			public void run() {
				while(isUpdating) {					
					panel.repaint();
					try {
						Thread.currentThread().join(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}
	
	public void stopUpdates() {
		isUpdating = false;
	}
	
	
}
