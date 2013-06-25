package org.scripts.firemaking;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartupGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3762486634843247616L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartupGUI frame = new StartupGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StartupGUI() {
		
		
		setTitle("Thock's Firemaker");
		setResizable(false);
		
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 38);
		contentPane.add(panel);
		
		JLabel lblThocksFiremaker = new JLabel("Thock's Firemaker - GUI Selection Screen");
		lblThocksFiremaker.setFont(new Font("Tunga", Font.BOLD, 18));
		panel.add(lblThocksFiremaker);
		Paint.setState(State.LOADING_GUI);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("Square721 BT", Font.BOLD, 18));
		btnNewButton.setBounds(10, 190, 414, 61);
		contentPane.add(btnNewButton);
		
		JLabel lblSelectLogType = new JLabel("Select Log Type to Burn");
		lblSelectLogType.setBounds(106, 60, 158, 19);
		contentPane.add(lblSelectLogType);
		lblSelectLogType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JComboBox<?> comboBox = new JComboBox();
		comboBox.setBounds(269, 60, 83, 20);
		contentPane.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Normal", "Oak", "Willow", "Maple", "Yew", "Magic"}));
		final JComboBox<?> comboBoxTemp = comboBox;
		
		JLabel lblSelectFiremakingMethod = new JLabel("Select Firemaking Method");
		lblSelectFiremakingMethod.setBounds(68, 116, 166, 19);
		contentPane.add(lblSelectFiremakingMethod);
		lblSelectFiremakingMethod.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Bonfires"}));
		comboBox_1.setBounds(244, 117, 83, 20);
		contentPane.add(comboBox_1);
		final JComboBox<?> comboBoxTemp2 = comboBox_1;
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FiremakingScript.getInstance().setLogToBurn(comboBoxTemp.getSelectedItem().toString());
				FiremakingScript.getInstance().setMethod(Method.getMethod(comboBoxTemp2.getSelectedItem().toString()));
				dispose();
			}
		});
		
	}
}
