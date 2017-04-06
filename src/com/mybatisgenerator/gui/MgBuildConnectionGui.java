package com.mybatisgenerator.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mybatisgenerator.model.ConnectionEntry;
import com.mybatisgenerator.util.DatabaseDriverManager;
import com.mybatisgenerator.util.DriverInformationKey;
import com.mybatisgenerator.util.PropertiesManager;
import com.mybatisgenerator.util.RegularTool;
import com.mybatisgenerator.util.UuidTool;

import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;

public class MgBuildConnectionGui {

	private JFrame frame;
	private PlaceholderTextField connectionName;
	private PlaceholderTextField urlText;
	private PlaceholderTextField username;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MgBuildConnectionGui window; 
					if(args!=null&&args.length>0){
						String selectedName = args[0];
						window = new MgBuildConnectionGui(selectedName);
					}else{
						window = new MgBuildConnectionGui();
					}
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
	public MgBuildConnectionGui() {
		initialize();
	}

	public MgBuildConnectionGui(String selectedRow) {
		// TODO Auto-generated constructor stub
		initialize(selectedRow);
		
	}

	private void initialize(String selectedName) {
		// TODO Auto-generated method stub
		int selectedIndex = Integer.parseInt(selectedName);
		JsonElement sje = PropertiesManager.getEditEntry(selectedIndex);
		Gson g = new Gson();
		ConnectionEntry ce = g.fromJson(sje,ConnectionEntry.class);
		Map<String,String> information = ce.getContext();
		String objUrl = information.get(DriverInformationKey.connectionUrl.toString());
		String objUsername = information.get(DriverInformationKey.userId.toString());
		String password = information.get(DriverInformationKey.password.toString());
		String objDatebaseType = information.get("type");
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 360, 254);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 10, 314, 161);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cName = new JLabel("name");
		cName.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		cName.setBounds(77, 8, 50, 18);
		
		connectionName = new PlaceholderTextField();
		connectionName.setPlaceholder("name");
		connectionName.setText(ce.getConnectionName());
		connectionName.setBounds(117, 8, 187, 21);
		panel.add(cName);
		panel.add(connectionName);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblUrl.setBounds(77, 28, 50, 18);
		panel.add(lblUrl);
		
		urlText = new PlaceholderTextField();
		urlText.setPlaceholder("url");
		urlText.setBounds(117, 28, 187, 21);
		urlText.setText(objUrl);
		panel.add(urlText);
		urlText.setColumns(10);
		
		JLabel lblUsername = new JLabel("USER");
		lblUsername.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblUsername.setBounds(70, 48, 28, 15);
		panel.add(lblUsername);
		
		username = new PlaceholderTextField();
		username.setPlaceholder("username");
		username.setText(objUsername);
		username.setBounds(117, 48, 187, 21);
		panel.add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("PASSWORD");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblNewLabel.setBounds(23, 68, 75, 15);
		panel.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(117, 68, 187, 19);
		panel.add(passwordField);
		
		JLabel lblType = new JLabel("TYPE");
		lblType.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(44, 88, 54, 15);
		panel.add(lblType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(117, 88, 187, 21);
		comboBox.addItem("mysql");
		if(objDatebaseType == "mysql"){
			comboBox.setSelectedIndex(0);
		}
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("test connection");
		btnNewButton.setBackground(MgMainGui.bg1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isNotBlank(username.getText())&&StringUtils.isNotBlank(urlText.getText())){
					String p;
						if(StringUtils.isNotEmpty(String.valueOf(passwordField.getPassword()))){
							p = String.valueOf(passwordField.getPassword());
						}else{
							p = password;
						}
					String type = (String) comboBox.getSelectedItem();
					if(DatabaseDriverManager.connectToDatabase(type,urlText.getText(), username.getText(), p)){;
						JOptionPane.showMessageDialog(null,"success","",JOptionPane.WARNING_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null,"error","",JOptionPane.WARNING_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null,"check your input","",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnNewButton.setBounds(23, 176, 127, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLogin = new JButton("update");
		btnLogin.setBackground(MgMainGui.bg1);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String cn = "";
					if(StringUtils.isNotEmpty(connectionName.getText())){
						cn = connectionName.getText();
					}else{
						cn = ce.getConnectionName();
					}
					String p="";
					if(StringUtils.isNotEmpty(String.valueOf(passwordField.getPassword()))){
						p = String.valueOf(passwordField.getPassword());
					}else{
						p = password;
					}
					String type = (String) comboBox.getSelectedItem();
					if(DatabaseDriverManager.connectToDatabase(type,urlText.getText(), username.getText(), p)){
						Map<String,String> context = new HashMap<String,String>();
						context.put(DriverInformationKey.connectionUrl.toString(),urlText.getText());
						context.put(DriverInformationKey.userId.toString(),username.getText());
						context.put(DriverInformationKey.password.toString(),p);
						context.put("type", type);
						ConnectionEntry c = new  ConnectionEntry();
						c.setConnectionId(ce.getConnectionId());
						c.setConnectionName(cn);
						c.setContext(context);
						if(PropertiesManager.updateJsonObj(selectedIndex, c)){
							String[] args = {String.valueOf(selectedIndex),c.connectionName};
							ConnectionChoose.main(args);
							frame.dispose();
						}
					}
			}
		});
		btnLogin.setBounds(261, 176, 80, 23);
		frame.getContentPane().add(btnLogin);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 360, 254);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 10, 314, 161);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblUrl.setBounds(77, 8, 21, 18);
		panel.add(lblUrl);
		
		urlText = new PlaceholderTextField();
		urlText.setBounds(117, 5, 187, 21);
		urlText.setPlaceholder("localhost:3306");
		panel.add(urlText);
		urlText.setColumns(10);
		
		JLabel lblUsername = new JLabel("USER");
		lblUsername.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblUsername.setBounds(70, 41, 28, 15);
		panel.add(lblUsername);
		
		username = new PlaceholderTextField();
		username.setPlaceholder("root");
		username.setBounds(117, 37, 187, 21);
		panel.add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("PASSWORD");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblNewLabel.setBounds(23, 72, 75, 15);
		panel.add(lblNewLabel);
		
		JCheckBox chckbxRemeberPassword = new JCheckBox("remember password(save)");
		chckbxRemeberPassword.setBackground(Color.WHITE);
		chckbxRemeberPassword.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxRemeberPassword.setBounds(111, 126, 193, 23);
		chckbxRemeberPassword.setSelected(false);
		panel.add(chckbxRemeberPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(117, 68, 187, 19);
		panel.add(passwordField);
		
		JLabel lblType = new JLabel("TYPE");
		lblType.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(44, 101, 54, 15);
		panel.add(lblType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(117, 97, 187, 21);
		comboBox.addItem("mysql");
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("test connection");
		btnNewButton.setBackground(MgMainGui.bg1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isNotBlank(username.getText())&&passwordField.getPassword().length>0&&StringUtils.isNotBlank(urlText.getText())){
					String type = (String) comboBox.getSelectedItem();
					char[] passwordChar = passwordField.getPassword();
					String password = String.valueOf(passwordChar);
					if(DatabaseDriverManager.connectToDatabase(type,urlText.getText(), username.getText(), password)){;
						JOptionPane.showMessageDialog(null,"success","",JOptionPane.WARNING_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null,"error","",JOptionPane.WARNING_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null,"check your input","",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnNewButton.setBounds(23, 176, 127, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setBackground(MgMainGui.bg1);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!chckbxRemeberPassword.isSelected()){
					String type = (String) comboBox.getSelectedItem();
					char[] passwordChar = passwordField.getPassword();
					String password = String.valueOf(passwordChar);
					if(DatabaseDriverManager.connectToDatabase(type,urlText.getText(), username.getText(), password)){;
						List<String> l = DatabaseDriverManager.getSchemaList();
						MgMainGui.setUsername(username.getText());
						MgMainGui.setPassword(password);
						MgMainGui.setSchemaList(l);
						MgMainGui.setDatabaseType(type);
						MgMainGui.setUrl(urlText.getText());
						frame.dispose();
					}else{
						JOptionPane.showMessageDialog(null,"connect error","",JOptionPane.WARNING_MESSAGE);
					}
				}else{
					String type = (String) comboBox.getSelectedItem();
					String password = String.valueOf(passwordField.getPassword());
					String url = urlText.getText();
					String userId = username.getText();
					if(StringUtils.isAnyEmpty(new String[]{type,password,url,userId})){
						JOptionPane.showMessageDialog(null,"check your input","",JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!DatabaseDriverManager.connectToDatabase(type,url, userId, password)){
							return;
					}
					String inputName = JOptionPane.showInputDialog("enter a name");
					if(StringUtils.isNotEmpty(inputName)){
							if(RegularTool.checkConnectionName(inputName)){
								ConnectionEntry c = new  ConnectionEntry();
								if(PropertiesManager.isNameExist(inputName)){
									Map<String,String> map = new HashMap<String,String>();
									map.put(DriverInformationKey.connectionUrl.toString(), url);
									map.put(DriverInformationKey.userId.toString(), userId);
									map.put(DriverInformationKey.password.toString(),password);
									map.put("type", type);
									c.setConnectionName(inputName);
									c.setConnectionId(UuidTool.getUuid());
									c.setContext(map);
									if(PropertiesManager.addJsonToProperties(c)){
										List<String> l = DatabaseDriverManager.getSchemaList();
										MgMainGui.setUsername(userId);
										MgMainGui.setPassword(password);
										MgMainGui.setSchemaList(l);
										MgMainGui.setDatabaseType(type);
										MgMainGui.setUrl(url);
										frame.dispose();
									}else{
										JOptionPane.showMessageDialog(null,"error","",JOptionPane.WARNING_MESSAGE);
									}
								}else{
									JOptionPane.showMessageDialog(null,"name exist","",JOptionPane.WARNING_MESSAGE);
								}
						}
					}else{
						JOptionPane.showMessageDialog(null,"illegal input","",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnLogin.setBounds(261, 176, 63, 23);
		frame.getContentPane().add(btnLogin);
	}
}
