package com.mybatisgenerator.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.commons.lang3.StringUtils;

import com.mybatisgenerator.model.Table;

public class GeneratorJTable extends JTable {
	
	private static String[] s = {"select all","table name","entry name","page","annotation","jpa","example"};
	private static TableModelPro tmp;
	final JCheckBox selectBox= new JCheckBox(s[0]);
    final JCheckBox selectBoxA= new JCheckBox(s[3]);
    final JCheckBox selectBoxB= new JCheckBox(s[4]);
    final JCheckBox selectBoxC= new JCheckBox(s[5]);
    final JCheckBox selectBoxD= new JCheckBox(s[6]);
	
	class TableModelPro extends AbstractTableModel{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		public int getDataSize(){
			return data.size();
		}
		
		public void updateDate(List<String> l){
			data.clear();
			if(l.size()>0){
				data.setSize(l.size());
				for(int i =0;i<l.size();i++){
						Vector<Object> v = new  Vector<Object>();
						v.setSize(7);
						v.set(0, false);
						v.set(1, l.get(i));
						v.set(2, mapDatabaseTableName(l.get(i)));
						v.set(3, false);
						v.set(4, false);
						v.set(5, false);
						v.set(6, false);
						data.set(i, v);
				}
			}
		}
		
		public TableModelPro(List<String> l){
			updateDate(l);
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			boolean isSelect = (boolean) data.get(rowIndex).get(0);
			if(true == isSelect&&columnIndex>0){
				if(columnIndex==6){
					boolean flag = (boolean) data.get(rowIndex).get(3);
					if(!(boolean)aValue&&true == flag){
						data.get(rowIndex).set(3, !flag);
						fireTableCellUpdated(rowIndex, 3);
					}
				}else if(columnIndex==3){
					boolean flag = (boolean) data.get(rowIndex).get(6);
					if((boolean)aValue&&false == flag){
						data.get(rowIndex).set(6, true);
						fireTableCellUpdated(rowIndex, 6);
					}
				}
				data.get(rowIndex).set(columnIndex, aValue);
			}else if(columnIndex==0){
				data.get(rowIndex).set(columnIndex, aValue);
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			
			return data.size();
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return s.length;
		}
		
		public String getColumnName(int column) {
	        // TODO Auto-generated method stub
	        return s[column];
		}
	
		public Class<?> getColumnClass(int columnIndex) {
	        // TODO Auto-generated method stub
	        return getValueAt(0, columnIndex).getClass();
	    }
	
		public boolean isCellEditable(int rowIndex, int columnIndex) {
	        // TODO Auto-generated method stub
	        // Only the last column can be edit.
	       return true;
	    }

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return data.get(rowIndex).get(columnIndex);
		}
		public void selectAllOrNull(boolean value,int postion){
	        // Select All. The last column
	        for(int index = 0; index < getRowCount(); index ++){
	            this.setValueAt(value, index, postion);
	        }
		}
		
	}
	
	public void setTableHeader(){
	    selectBox.setSelected(false);
		selectBoxA.setSelected(false);
		selectBoxB.setSelected(false);
		selectBoxC.setSelected(false);
		selectBoxD.setSelected(false);
		this.tableHeader.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void clickCheckBoxEvent(final JCheckBox jb,int postion){
				boolean value = !jb.isSelected();
				jb.setSelected(value);
				tmp.selectAllOrNull(value,postion);
                tableHeader.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					int selectColumn = tableHeader.columnAtPoint(e.getPoint());
					switch(selectColumn){
						case 0:
	                        clickCheckBoxEvent(selectBox,0);
	                        break;
						case 3:
							clickCheckBoxEvent(selectBoxA,3);
							break;
						case 4:
							clickCheckBoxEvent(selectBoxB, 4);
							break;
						case 5:
							clickCheckBoxEvent(selectBoxC,5);
							break;
						case 6:
							clickCheckBoxEvent(selectBoxD,6);
							break;
					}
				}
			}
	});
	}
	
	
	class ProTableHeaderRenderer implements TableCellRenderer{
		
		public ProTableHeaderRenderer(){
		}
		
		
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			String valueStr = (String) value;
	        JLabel label = new JLabel(valueStr);
	        label.setHorizontalAlignment(SwingConstants.CENTER); 
	        selectBox.setHorizontalAlignment(SwingConstants.CENTER);
	        selectBoxA.setHorizontalAlignment(SwingConstants.CENTER);
	        selectBoxB.setHorizontalAlignment(SwingConstants.CENTER);
	        selectBoxC.setHorizontalAlignment(SwingConstants.CENTER);
	        selectBoxD.setHorizontalAlignment(SwingConstants.CENTER);
	        selectBox.setBorderPainted(true);
	        selectBoxA.setBorderPainted(true);
	        selectBoxB.setBorderPainted(true);
	        selectBoxC.setBorderPainted(true);
	        selectBoxD.setBorderPainted(true);
	        JComponent component;
	        if(column == 0){
	        	component = selectBox;
	        }else if(column == 3){
	        	component = selectBoxA;
	        }else if(column == 4){
	        	component = selectBoxB;
	        }else if(column == 5){
	        	component = selectBoxC;
	        }else if(column == 6){
	        	component = selectBoxD;
	        }else{
	        	component = label;
	        }
	        component.setBackground(tableHeader.getBackground());
	        component.setFont(tableHeader.getFont());
	        component.setForeground(tableHeader.getForeground());
	        component.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	        return component;
		}
		
	}
	
	public GeneratorJTable(List l){
		super();
		tmp = new TableModelPro(l);
		this.setModel(tmp);
		setTableHeader();
		this.getTableHeader().setDefaultRenderer(new ProTableHeaderRenderer());
	}
	
	public GeneratorJTable(){
		setTableHeader();
		this.getTableHeader().setDefaultRenderer(new ProTableHeaderRenderer());
	}
	
	public List<Table> getTablesData(){
		List<Table> list = new ArrayList<Table>();
		TableModelPro tp = (TableModelPro) this.getModel();
		int length = tp.getDataSize();
		if(length>0){
			for(int row =0;row<length;row++){
				if((boolean)tp.data.get(row).get(0)){
					Table table = new Table();
					table.setTableName(tp.data.get(row).get(1).toString());
					table.setPojoName(tp.data.get(row).get(2).toString());
					table.setPage((boolean)tp.data.get(row).get(3));
					table.setDoc((boolean)tp.data.get(row).get(4));
					table.setAnnotation((boolean)tp.data.get(row).get(5));
					table.setExample((boolean)tp.data.get(row).get(6));
					list.add(table);
				}
			}
			return list;
		}
		return null;
	}
	
	public String mapDatabaseTableName(String databaseTableName){
		if(!StringUtils.isBlank(databaseTableName)){
			String pojo ="";
			if(StringUtils.countMatches(databaseTableName, "_")>0){
				String[] dtnb = StringUtils.split(databaseTableName,"_");
				for(String i:dtnb){
					pojo += StringUtils.capitalize(i);
				}
				return pojo;
			}else{
				pojo=StringUtils.capitalize(databaseTableName);
				return pojo;
			}
		}
		return null;
	}
	
	public void updateData(List<String> l){
		tmp = new TableModelPro(l);
		this.setModel(tmp);
		updateUI();
	}
	
}
