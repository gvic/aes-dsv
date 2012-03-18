package com;

import java.awt.EventQueue;
import java.io.File;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class View {

	private JFrame frmWarehouse;
	private JButton toggle;
	private IListener controller;
	private JLabel lblOrderbox;
	private JLabel lblWarehousebox;
	private JLabel lblWorkerbox;
	private JLabel lblWorkerbox_1;

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
					window.frmWarehouse.setVisible(true);
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

	public void setController(IListener controller2) {
		this.controller = controller2;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWarehouse = new JFrame();
		frmWarehouse.setTitle("Warehouse");
		frmWarehouse.setBounds(100, 100, 902, 523);
		frmWarehouse.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblOrders = new JLabel("Orders");
		lblOrders.setVerticalAlignment(SwingConstants.TOP);

		lblOrderbox = new JLabel("OrderBox");
		lblOrderbox.setVerticalAlignment(SwingConstants.TOP);

		JLabel lblWarehouse = new JLabel("Warehouse");

		lblWarehousebox = new JLabel("WareHousebox");
		lblWarehousebox.setVerticalAlignment(SwingConstants.TOP);

		lblWorkerbox = new JLabel("workerBox");
		lblWorkerbox.setVerticalAlignment(SwingConstants.TOP);

		lblWorkerbox_1 = new JLabel("workerBox2");
		lblWorkerbox_1.setVerticalAlignment(SwingConstants.TOP);

		JButton btnRun_1 = new JButton("Run");
		btnRun_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.runWorker(1);
			}
		});

		JButton btnPause_1 = new JButton("Pause");
		btnPause_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.pauseWorker(1);
			}
		});

		JButton btnRun_2 = new JButton("Run");
		btnRun_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.runWorker(0);
			}
		});

		JButton btnPause_2 = new JButton("Pause");
		btnPause_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.pauseWorker(0);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmWarehouse.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblOrderbox, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
								.addComponent(lblOrders, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblWarehousebox, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
								.addComponent(lblWarehouse, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnRun_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnPause_1)
									.addGap(112))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblWorkerbox, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnRun_2)
									.addGap(18)
									.addComponent(btnPause_2)
									.addPreferredGap(ComponentPlacement.RELATED, 90, Short.MAX_VALUE))
								.addComponent(lblWorkerbox_1, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrders)
						.addComponent(lblWarehouse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrderbox, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
						.addComponent(lblWarehousebox, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRun_1)
						.addComponent(btnPause_1)
						.addComponent(btnRun_2)
						.addComponent(btnPause_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblWorkerbox, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWorkerbox_1, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
					.addGap(14))
		);
		frmWarehouse.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmWarehouse.setJMenuBar(menuBar);

		JButton btnRun = new JButton("Start");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.runModel();
				((JButton)arg0.getSource()).setEnabled(false);
			}
		});
		menuBar.add(btnRun);

		toggle = new JButton("Pause");
		toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton btn = (JButton)arg0.getSource();
				if(btn.getText() == "Pause"){
					controller.pauseModel();
					toggle.setText("Resume");
				}else if(btn.getText() == "Resume"){
					toggle.setText("Pause");
					controller.resumeModel();
				}
			}
		});
		menuBar.add(toggle);
		
		JSlider slider = new JSlider();
		slider.setMinorTickSpacing(10);
		slider.setToolTipText("Worker time");
		slider.setValue(999);
		slider.setMajorTickSpacing(50);
		slider.setMaximum(3000);
		slider.setMinimum(1000);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        int time = (int)source.getValue();
			        controller.setWorkerTime(time);
			    }
			}
		});
		menuBar.add(slider);
	}

	public void initialiseOrdersBox(OrderList allOrders) {
		lblOrderbox.setText(allOrders.listDetails());
	}

	public void initialiseItemsBox(HashMap<Integer, IItem> allItems) {

	}

	public void updateWorkerBox(Integer idWorker, String digest) {
		if (idWorker == 1) {
			lblWorkerbox.setText(digest);
			lblWorkerbox.paintImmediately(lblWorkerbox.getVisibleRect());
		}else{
			lblWorkerbox_1.setText(digest);
			lblWorkerbox_1.paintImmediately(lblWorkerbox_1.getVisibleRect());
		}
	}

	public void updateOrdersBox(OrderList allOrders) {
		lblOrderbox.setText(allOrders.listDetails());
		lblOrderbox.paintImmediately(lblOrderbox.getVisibleRect());
	}

	public void updateWareHouseBox(String output) {
		lblWarehousebox.setText(output);
		lblWarehousebox.paintImmediately(lblWarehousebox.getVisibleRect());
	}

	public JLabel getLblOrderbox() {
		return lblOrderbox;
	}

	public JLabel getLblWarehousebox() {
		return lblWarehousebox;
	}

	public JLabel getLblWorkerbox() {
		return lblWorkerbox;
	}

	public JLabel getLblWorkerbox_1() {
		return lblWorkerbox_1;
	}
}
