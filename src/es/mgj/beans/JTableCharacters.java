package es.mgj.beans;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.db4o.ObjectSet;

import es.mgj.base.GameUser;
import es.mgj.base.Tabla;
import es.mgj.util.Util;
import es.mgj.base.Character;

public class JTableCharacters extends JTable{
	
	private DefaultTableModel modeloTabla;
	
	public JTableCharacters(){
		cabecera();
	}
	
	public void listar(GameUser gu) {
		
		cabecera();
		
		this.modeloTabla.setNumRows(0);
		
		es.mgj.base.Character c2 = new es.mgj.base.Character();
		c2.setGameUser(gu);
		ObjectSet<Tabla> listaC = Util.db.queryByExample(c2);
		
		for(Object c : listaC){
			Object fila[] = {((Character)c).getId(), ((Character)c).getName()
					,((Character)c).getRace(), ((Character)c).getClass_(),
					((Character)c).getLevel(), ((Character)c).getCreationDate(),
					((Character)c).getLife(), ((Character)c).getMana(),
					(((Character)c).getGender()) ? "Male" : "Female"};
			this.modeloTabla.addRow(fila);
		}
		
	}

	private void cabecera() {
		
		this.modeloTabla = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		
		this.modeloTabla.setNumRows(0);
		this.removeAll();
		
		this.modeloTabla.addColumn("ID");
		this.modeloTabla.addColumn("Name");
		this.modeloTabla.addColumn("Race");
		this.modeloTabla.addColumn("Class");
		this.modeloTabla.addColumn("level");
		this.modeloTabla.addColumn("Creation date");
		this.modeloTabla.addColumn("Life");
		this.modeloTabla.addColumn("Mana");
		this.modeloTabla.addColumn("Gender");
		
		this.setModel(modeloTabla);
		this.removeColumn(this.getColumnModel().getColumn(0));
		
	}
	
	public Character getCharacter(){
		
		Character c = new Character();
		c.setId((String)this.modeloTabla.getValueAt(this.getSelectedRow(), 0));
		return (Character) Util.db.queryByExample(c).next();
	}

}
