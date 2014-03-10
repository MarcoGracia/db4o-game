package es.mgj.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




import es.mgj.base.Magic;
import es.mgj.base.Pet;
import es.mgj.base.Summoning;
import es.mgj.base.Tabla;
import es.mgj.util.Constantes;
import es.mgj.util.Util;

public class JTablePrincipal extends JTable{
	
	private DefaultTableModel modeloTabla = null;
	
	private String listando;
	
	public JTablePrincipal(){}

	public void cabeceraTabla(Tabla tabla){
		
		modeloTabla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		
		this.modeloTabla.setNumRows(0);
		this.removeAll();
		
		for(String columna : tabla.getCampos())
			this.modeloTabla.addColumn(columna);
		
		this.setModel(modeloTabla);
		removeColumn(getColumnModel().getColumn(0));
		
	}
	
	public void listar(List<Tabla> datos, Tabla tabla){

		cabeceraTabla(tabla);
		
		switch (tabla.getClass().getSimpleName()){
			case(Constantes.MAGIC):
				listando = Constantes.MAGIC;
				for(Tabla m : datos){
					Object fila[] ={((Magic)m).getId(),  ((Magic)m).getName(), ((Magic)m).getDamage(), 
							((Magic)m).getElement(), ((Magic)m).getRequiredMana(), ((Magic)m).getCharactersList()};
					this.modeloTabla.addRow(fila);
				}
				break;
				
			case(Constantes.PET):
				listando = Constantes.PET;
				for(Tabla p : datos){
					Object fila[] ={((Pet)p).getId(), ((Pet)p).getCharacter().getName(), ((Pet)p).getName(),
							((Pet)p).getAbility(), ((Pet)p).getKing()};
					this.modeloTabla.addRow(fila);
				}
				break;
				
			case(Constantes.SUMMONING):
				listando = Constantes.SUMMONING;
				for(Tabla s : datos){
					Object fila[] ={((Summoning)s).getId(), ((Summoning)s).getName(), ((Summoning)s).getPower(),
							((Summoning)s).getEffect(), ((Summoning)s).getSummonFrecuency() + "hours",
							((Summoning)s).getCharactersList()};
					this.modeloTabla.addRow(fila);
				}
				break;
				
			case(Constantes.OBJECT):
				listando = Constantes.OBJECT;
				for(Tabla o : datos){
					Object fila[] ={((es.mgj.base.Object)o).getId(), ((es.mgj.base.Object)o).getCharacter().getName()
							, ((es.mgj.base.Object)o).getKind(), ((es.mgj.base.Object)o).getAttack(),
							((es.mgj.base.Object)o).getDefence(), ((es.mgj.base.Object)o).getDropRate() + "%",
							((es.mgj.base.Object)o).getCharacter()
						};
					this.modeloTabla.addRow(fila);
				}
				break;
			
			default:
				System.out.println("Tabla inexistente");
		}
		
	}
	
	public Magic getMagic(){
		
		Magic m = new Magic();
		m.setId((String)this.modeloTabla.getValueAt(this.getSelectedRow(), 0));
		return (Magic) Util.db.queryByExample(m).next();
		
	}
	
	public Pet getPet(){
		
		Pet p = new Pet();
		p.setId((String)this.modeloTabla.getValueAt(this.getSelectedRow(), 0));
		return (Pet) Util.db.queryByExample(p).next();
	}
	
	public Summoning getSummoning(){
		Summoning s = new Summoning();
		s.setId((String)this.modeloTabla.getValueAt(this.getSelectedRow(), 0));
		return (Summoning) Util.db.queryByExample(s).next();
	}
	
	public es.mgj.base.Object getObject(){
		es.mgj.base.Object o = new es.mgj.base.Object();
		o.setId((String)this.modeloTabla.getValueAt(this.getSelectedRow(), 0));
		return (es.mgj.base.Object) Util.db.queryByExample(o).next();
	}

	public String getListando() {
		return listando;
	}

}
