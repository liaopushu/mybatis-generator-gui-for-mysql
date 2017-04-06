package com.mybatisgenerator.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import com.mybatisgenerator.model.ConnectionEntry;
import com.mybatisgenerator.util.DatabaseDriverManager;
import com.mybatisgenerator.util.DriverInformationKey;
import com.mybatisgenerator.util.PropertiesManager;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Component;

public class ConnectionChoose {

	private JFrame frame;
	private JTable table;
	private String[] columns = {"name", "edit"};
	private PropertiesManager pm = new PropertiesManager();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionChoose window = new ConnectionChoose();
					if(args!=null&&args.length>0){
						window.updateConnectionList(Integer.parseInt(args[0]), args[1]);
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
	public ConnectionChoose() {
		initialize();
	}
	
	public Object[][] readJson(){
		List<ConnectionEntry> list= PropertiesManager.getConnectionInformationFromFile(PropertiesManager.js);
		int length = list.size();
		if(length>0){
			Object[][] o  = new Object[list.size()][2];
			for(int i=0;i<length;i++){
				o[i]=new Object[]{list.get(i).connectionName,"edit"};
			}
			return o;
		}else{
			return null;
		}
	}
	
	public void updateConnectionList(int rowIndex,String aValue){
		table.getModel().setValueAt(aValue, rowIndex, 0);
	}
	
	class ListButtonRender implements TableCellRenderer{
		private JPanel panel;

	    private JButton button;

	    
	    public ListButtonRender(){
	    	initButton();
	    	initPanel();
	    }
	    
	    private void initButton()
	    {
	        this.button = new JButton("edit");
	        this.button.setBackground(MgMainGui.bg1);
	        this.button.setBorderPainted(false);;
	        this.button.setBounds(0, 0, 80, 15);

	    }
	    
	    private void initPanel()
	    {
	        this.panel = new JPanel();
	        this.panel.setLayout(new BorderLayout());
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
	            int column)
	    {
	        this.button.setText(value == null ? "" : String.valueOf(value));
	        this.panel.add(button);
	        return this.panel;
	    }

		
		public Component getTableCellRendererComponent1(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				// TODO Auto-generated method stub
				return this.panel;
			}
		}
	
	 class ProButtonEditor extends DefaultCellEditor  
	 {  
	   
	     /** 
	      * serialVersionUID 
	      */  
	     private static final long serialVersionUID = -6546334664166791132L;  
	   
	     private JPanel panel;  
	   
	     private JButton button;  
	   
	     public ProButtonEditor()  
	     {  
	         super(new JTextField());  
	         this.setClickCountToStart(1);  
	         this.initButton();  
	         this.initPanel();  
	         this.panel.add(this.button);  
	     }  
	   
	     private void initButton()  
	     {  
	         this.button = new JButton("edit");  
	         this.button.setBackground(MgMainGui.bg1);
	         this.button.setBounds(0, 0, 50, 15);  
	         this.button.addActionListener(new ActionListener()  
	         {  
	             public void actionPerformed(ActionEvent e)  
	             {  
	            	 ProButtonEditor.this.fireEditingCanceled();  
	                 MgBuildConnectionGui.main(new String[]{String.valueOf(table.getSelectedRow())});
	                 frame.dispose();
	             }
	         });  
	   
	     }  
	   
	     private void initPanel()  
	     {  
	         this.panel = new JPanel();  
	         this.panel.setLayout(new BorderLayout());
	     }  
	   
	   
	     /** 
	      * 这里重写父类的编辑方法，返回一个JPanel对象即可（也可以直接返回一个Button对象，但是那样会填充满整个单元格） 
	      */  
	     @Override  
	     public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  
	     {  
	         return this.panel;  
	     }  
	   
	     /** 
	      * 重写编辑单元格时获取的值。如果不重写，这里可能会为按钮设置错误的值。 
	      */  
	     @Override  
	     public Object getCellEditorValue()  
	     {  
	         return this.button.getText();  
	     }  
	   
	 } 

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBackground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		scrollPane.setBackground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 434, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(readJson(),columns));
		table.getColumnModel().getColumn(1).setCellRenderer(new ListButtonRender());
		table.getColumnModel().getColumn(1).setCellEditor(new ProButtonEditor());
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 232, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 260, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("new connection");
		btnNewButton.setBackground(MgMainGui.bg1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MgBuildConnectionGui.main(null);
				frame.dispose();
			}
		});
		btnNewButton.setBounds(202, 0, 124, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("enter");
		btnNewButton_1.setBackground(MgMainGui.bg1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				ConnectionEntry ce = PropertiesManager.getConnectionEntry(selectedRow);
				
				Map<String,String> m = ce.getContext();
				String url = m.get(DriverInformationKey.connectionUrl.toString());
				String username = m.get(DriverInformationKey.userId.toString());
				String password = m.get(DriverInformationKey.password.toString());
				String type = m.get("type");
				if(DatabaseDriverManager.connectToDatabase(type,url,username,password)){
					List<String> l =DatabaseDriverManager.getSchemaList();
					MgMainGui.setUsername(username);
					MgMainGui.setPassword(password);
					MgMainGui.setSchemaList(l);
					MgMainGui.setDatabaseType(type);
					MgMainGui.setUrl(url);
					frame.dispose();
				}
			}
		});
		btnNewButton_1.setBounds(341, 0, 93, 23);
		panel.add(btnNewButton_1);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.setBackground(MgMainGui.bg1);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				PropertiesManager.removeJsonObj(selectedRow);
				table.setModel(new DefaultTableModel(readJson(),columns));
				table.updateUI();
			}
		});
		btnDelete.setBounds(10, 0, 93, 23);
		panel.add(btnDelete);
	}
}
