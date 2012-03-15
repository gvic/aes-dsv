package com;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View {

	private JFrame frame;
	private IListener controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("USAGE");
			System.out
					.println("java com.Manager <items_file_name> <orders_file_name>");
			System.exit(0);
		}

		File fi = new File(args[0]);
		File fo = new File(args[1]);
		


		if (!fi.exists()) {
			System.out.println(fi.toString() + " does not exist");
			System.exit(0);
		}
		if (!fo.exists()) {
			System.out.println(fi.toString() + " does not exist");
			System.exit(0);
		}

		IModel manager = new Manager(fi, fo);
//		try {
//			manager.run();
//		} catch (IOException e) {
//			System.out
//					.println("An error occured while the program tried to read the files.");
//			System.exit(0);
//		}
		
		final IListener controller = new Controller();
		manager.AddListener(controller);
		controller.setModel(manager);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					controller.setView(window);
					window.setController(controller);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmFile = new JMenuItem("File");
		menuBar.add(mntmFile);
		
		JToolBar toolBar = new JToolBar();
		
		JTextPane txtpnOrderList = new JTextPane();
		txtpnOrderList.setText("Order List");
		
		JTextPane txtpnWorker = new JTextPane();
		txtpnWorker.setText("Worker 1");
		
		JTextPane txtpnWarehouse = new JTextPane();
		txtpnWarehouse.setText("Warehouse");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(txtpnOrderList, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(txtpnWorker, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(txtpnWarehouse, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(toolBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnOrderList, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtpnWorker)
							.addGap(177))
						.addComponent(txtpnWarehouse, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JButton btnRun = new JButton("Run");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					controller.runModel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnRun);
		
		JButton btnPause = new JButton("Pause");
		toolBar.add(btnPause);
		frame.getContentPane().setLayout(groupLayout);
	}

	public void setController(IListener controller2) {
		this.controller = controller2;
	}
}

