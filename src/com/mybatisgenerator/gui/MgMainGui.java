package com.mybatisgenerator.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.mybatisgenerator.model.Table;
import com.mybatisgenerator.util.AbstractMybatisGeneratorInit;
import com.mybatisgenerator.util.DatabaseDriverManager;
import com.mybatisgenerator.util.DatabaseInfo;
import com.mybatisgenerator.util.DefaultMybatisGeneratorInit;
import com.mybatisgenerator.util.DriverInformationKey;
import com.mybatisgenerator.util.PropertiesManager;
import com.mybatisgenerator.util.RegularTool;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.SpringLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.exception.InvalidConfigurationException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractListModel;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class MgMainGui {

	private JFrame frame;
	private PlaceholderTextField textField;
	private JFileChooser jfc;
	private JFileChooser jfc1;
	private PlaceholderTextField txtPojo;
	private PlaceholderTextField txtDao;
	private PlaceholderTextField txtMapper;
	private PlaceholderTextField ProjectPathField;
	private PlaceholderTextField xmlDirectoryPath;
	private AbstractMybatisGeneratorInit am;
	public static Color bg1 = new Color(0.9f,0.9f,0.9f);
	private static GeneratorJTable table;
	private static JList list = new JList();
	private static Vector<String> schemaList = new Vector<String>();
	private static String username;
	private static String password;
	private static String databaseType;
	private static String url;
	private static String schema;
	
	public static void setPassword(String p){
		if(StringUtils.isNotEmpty(p)){
			password  = p;
		}
	}
	
	public static void setUrl(String u){
		if(StringUtils.isNotEmpty(u)){
			url = u;
		}
	}
	
	public static void setDatabaseType(String type){
		if(StringUtils.isNotEmpty(type)){
			databaseType = type;
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MgMainGui window = new MgMainGui();
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
	public MgMainGui() {
		initialize();
	}
	
	public static void setSchemaList(List<String> l){
		if(null!=l&&l.size()>0){
			schemaList.clear();
			int length = l.size();
			schemaList.setSize(length);
			for(int i =0;i<length;i++){
				schemaList.set(i, l.get(i));
			}
		}
		list.updateUI();
	}
	
	public static void setUsername(String un){
		username = un;
	}
	

	public static void setTableList(String selectedValue) {
		// TODO Auto-generated method stub
		schema = selectedValue;
		try {
			List<String> l = DatabaseDriverManager.getTableList(selectedValue,username);
			if(null!=table){
				table.updateData(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 833, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		table = new GeneratorJTable();
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.WHITE);
		frame.setJMenuBar(menuBar);
		JMenu mnConnection = new JMenu("Connection");
		mnConnection.setHorizontalAlignment(SwingConstants.LEFT);
		mnConnection.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnConnection);
		JMenuItem mntmNewConnection = new JMenuItem("new connection");
		mntmNewConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MgBuildConnectionGui.main(null);
			}
		});
		mntmNewConnection.setHorizontalAlignment(SwingConstants.LEFT);
		mnConnection.add(mntmNewConnection);
		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JMenuItem mntmSelectConnection = new JMenuItem("select connection");
		mntmSelectConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionChoose.main(null);
			}
		});
		mnConnection.add(mntmSelectConnection);
		mntmExit.setHorizontalAlignment(SwingConstants.LEFT);
		mnConnection.add(mntmExit);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -9, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 203, SpringLayout.WEST, frame.getContentPane());
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		am = new DefaultMybatisGeneratorInit();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 185, 278);
		panel.add(scrollPane);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(list.getSelectedIndex()!=-1){
					if(e.getClickCount()==2){
						setTableList((String)list.getSelectedValue());
					}
				}
			}
		});
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return schemaList.size();
			}
			public Object getElementAt(int index) {
				return schemaList.get(index);
			}
		});
		scrollPane.setViewportView(list);
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("d://"));
		jfc1 = new JFileChooser();
		jfc1.setCurrentDirectory(new File("d://"));
		JLabel lblDatabaseList = new JLabel("database list");
		lblDatabaseList.setBackground(Color.WHITE);
		scrollPane.setColumnHeaderView(lblDatabaseList);
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 290, 195, 193);
		panel.add(panel_1);
		panel_1.setLayout(null);
		ProjectPathField = new PlaceholderTextField();
		xmlDirectoryPath = new PlaceholderTextField();
		JLabel lblPackagePath = new JLabel("package path");
		lblPackagePath.setBounds(10, 60, 88, 15);
		panel_1.add(lblPackagePath);
		textField = new PlaceholderTextField();
		textField.setPlaceholder("com.mypath");
		textField.setBounds(101, 56, 94, 21);
		panel_1.add(textField);
		textField.setColumns(10);
		JLabel lblEntrypackage = new JLabel("entry");
		lblEntrypackage.setBounds(10, 86, 88, 15);
		panel_1.add(lblEntrypackage);
		txtPojo = new PlaceholderTextField();
		txtPojo.setPlaceholder("pojo");
		txtPojo.setBounds(101, 82, 94, 21);
		panel_1.add(txtPojo);
		txtPojo.setColumns(10);
		JLabel lblDaoPackage = new JLabel("dao");
		lblDaoPackage.setBounds(10, 111, 88, 15);
		panel_1.add(lblDaoPackage);
		txtDao = new PlaceholderTextField();
		txtDao.setPlaceholder("dao");
		txtDao.setBounds(101, 107, 94, 21);
		panel_1.add(txtDao);
		txtDao.setColumns(10);
		JLabel lblMapperPackage = new JLabel("mapper");
		lblMapperPackage.setBounds(10, 136, 96, 15);
		panel_1.add(lblMapperPackage);
		txtMapper = new PlaceholderTextField();
		txtMapper.setPlaceholder("mapper");
		txtMapper.setBounds(101, 132, 94, 21);
		panel_1.add(txtMapper);
		txtMapper.setColumns(10);
		JButton btnNewButton = new JButton("generator");
		btnNewButton.setBackground(bg1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String packagePath 		 = textField.getText();
        		String entryPackageName  = txtPojo.getText();
        		String daoPackageName 	 = txtDao.getText();
        		String mapperPackageName = txtMapper.getText();
        		String projectPath 		 = ProjectPathField.getText();
        		String xdp  			 = xmlDirectoryPath.getText();
        		if(RegularTool.reName(entryPackageName,daoPackageName,mapperPackageName)&& RegularTool.rePackageName(packagePath)&&RegularTool.checkPath(projectPath)&&null!=url){
					Map<String,String> sm = new  HashMap<String,String>();
					sm.put(DriverInformationKey.connectionUrl.toString(), DatabaseInfo.mysql.get("urlprex")+url+"/"+schema+"?useUnicode=true&useSSL=false&characterEncoding=utf8");
					if("mysql"==databaseType){
						String ap = PropertiesManager.programPath;
						sm.put(DriverInformationKey.connectorLibPath.toString(),ap+"\\"+DatabaseInfo.mysql.get("lib"));
					}
					sm.put(DriverInformationKey.jdbcConfigDriverClass.toString(), DatabaseInfo.mysql.get("classname"));
					sm.put(DriverInformationKey.userId.toString(), username);
					sm.put(DriverInformationKey.password.toString(), password);
	        		am.setBaseGeneratorConfig(sm);
	        		am.setProjectPath(projectPath);
	        		am.setModelPackgetPath(packagePath+"."+entryPackageName);
	        		am.setDaoPackgetPath(packagePath+"."+daoPackageName);
	        		am.setMapPackgetPath(packagePath+"."+mapperPackageName);
	        		am.setMapperXmlPath(xdp);
	        		List<Table> l = table.getTablesData();
	        		if(null!=l&&l.size()>0){
	        			try {
		        			for(Table t:l){
								am.GeneratorTarget(t.getTableName(), t.getPojoName(),t.isPage(), t.isDoc(), t.isAnnotation(), t.isExample());
		        			}
	        			} catch (InvalidConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	        		}
	        		JOptionPane.showMessageDialog(null,"success","",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null,"check your input","",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(101, 158, 94, 23);
		panel_1.add(btnNewButton);
		ProjectPathField.setPlaceholder("d:/");
		ProjectPathField.setColumns(10);
		ProjectPathField.setBounds(101, 0, 66, 21);
		panel_1.add(ProjectPathField);
		JButton button = new JButton("...");
		button.setBackground(bg1);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.setFileSelectionMode(1);
				int state = jfc.showOpenDialog(null);//  
	            if (state == 1) {  
	                return;  
	            } else {  
	                File f = jfc.getSelectedFile();// 
	                ProjectPathField.setText(f.getAbsolutePath());  
	            }  
			}
		});
		button.setBounds(172, 2, 23, 19);
		panel_1.add(button);
		JLabel lblProjectPath = new JLabel("project path");
		lblProjectPath.setBounds(10, 4, 73, 15);
		panel_1.add(lblProjectPath);
		xmlDirectoryPath.setPlaceholder("d:/");
		xmlDirectoryPath.setColumns(10);
		xmlDirectoryPath.setBounds(101, 27, 66, 21);
		panel_1.add(xmlDirectoryPath);
		JButton button_1 = new JButton("...");
		button_1.setBackground(bg1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc1.setFileSelectionMode(1);
				int state = jfc1.showOpenDialog(null);//  
	            if (state == 1) {  
	                return;  
	            } else {  
				 File f = jfc1.getSelectedFile();// 
				 xmlDirectoryPath.setText(f.getAbsolutePath());  
	            }
			}
		});
		button_1.setBounds(172, 29, 23, 19);
		panel_1.add(button_1);
		JLabel label_1 = new JLabel("xml path");
		label_1.setBounds(10, 31, 54, 15);
		panel_1.add(label_1);
		JPanel panel_2 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_2, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_2, 209, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -6, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_2, -10, SpringLayout.EAST, frame.getContentPane());
		panel_2.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_2);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 608, 473);
		scrollPane_1.setViewportView(table);
		panel_2.setLayout(null);
		panel_2.add(scrollPane_1);
	}
}
