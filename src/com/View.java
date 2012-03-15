package com;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

public class View {

	private JFrame frame;
	private IListener controller;
	private JLabel lblOrders;
	private JLabel lblWorker;
	private JLabel lblWarehouse;
	private JLabel lblOrderbox;
	private JLabel lblWarehousebox;
	private JLabel lblOneworker;

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

		final IModel manager = new Manager(fi, fo);		
		final IListener controller = new Controller();
		manager.setController(controller);
		controller.setModel(manager);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					controller.setView(window);
					window.setController(controller);
					window.frame.setVisible(true);
					manager.initialise();
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
		
		lblOrders = new JLabel("Orders");
		lblOrders.setVerticalAlignment(SwingConstants.TOP);
		
		lblWorker = new JLabel("Worker");
		lblWorker.setVerticalAlignment(SwingConstants.TOP);
		
		lblWarehouse = new JLabel("Warehouse");
		lblWarehouse.setVerticalAlignment(SwingConstants.TOP);
		
		lblOneworker = new JLabel("oneWorker");
		lblOneworker.setVerticalAlignment(SwingConstants.TOP);
		
		lblOrderbox = new JLabel("orderBox");
		lblOrderbox.setVerticalAlignment(SwingConstants.TOP);
		
		lblWarehousebox = new JLabel("warehouseBox");
		lblWarehousebox.setVerticalAlignment(SwingConstants.TOP);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOrderbox, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
						.addComponent(lblOrders, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblWorker, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblOneworker, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWarehouse, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
							.addGap(24))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWarehousebox, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrders)
						.addComponent(lblWorker)
						.addComponent(lblWarehouse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrderbox, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
						.addComponent(lblWarehousebox, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
						.addComponent(lblOneworker, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
					.addGap(12))
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
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.pauseModel();
			}
		});
		toolBar.add(btnPause);
		frame.getContentPane().setLayout(groupLayout);
	}

	public void setController(IListener controller2) {
		this.controller = controller2;
	}

	public void initialiseOrdersBox(OrderList allOrders) {
		lblOrderbox.setText(allOrders.listDetails());
//		//StyledDocument doc = txtpnOrderList.getStyledDocument();
//		try {
//			doc.insertString(doc.getLength(), allOrders.listDetails(), null);
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void initialiseItemsBox(HashMap<Integer, IItem> allItems) {
		//lblWarehousebox.setText(allItems.toString());		
	}

	public void updateWorkerBox(String digest) {
		lblOneworker.setText(digest);
		lblOneworker.paintImmediately(lblOneworker.getVisibleRect());
	}
}

