package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.mgj.base.Character;
import es.mgj.base.GameUser;
import es.mgj.base.Magic;
import es.mgj.base.Pet;
import es.mgj.base.Summoning;

import javax.swing.JScrollPane;

import es.mgj.beans.JTableCharacters;
import es.mgj.util.Util;
import es.mgj.util.Util.Accion;
import es.mgj.base.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

public class GuiCharacter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private GameUser gameUser;
	private JPanel panel;
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnModify;
	private JScrollPane scrollPane;
	private JTableCharacters tableCharacters;
	private JButton btnExit;
	
	/**
	 * Create the dialog.
	 * @param jLogin 
	 * @param u 
	 */
	public GuiCharacter(GameUser u) {
		
		this.setModal(true);
		this.gameUser = u;
		initialize();
		inicializar();
		this.setVisible(true);
	}
	
	private void inicializar(){
		this.tableCharacters.listar(gameUser);
	}
	
	protected void estado() {
		if(this.tableCharacters.getSelectedRow() == -1){
			this.setTitle("Listando " + this.tableCharacters.getRowCount() + 
					" personajes, ninguna fila seleccionada" );
			return;
		}
			
		
		this.setTitle("Listando " + this.tableCharacters.getRowCount() + 
				" personajes, fila " + (this.tableCharacters.getSelectedRow() + 1) 
				+ " seleccionada" );
		
		
	}
	
	protected void insertar() {
		JCharacter jc = new JCharacter(this.gameUser);
		
		if(jc.mostrarDialogoInsertar() == Accion.CANCELAR)
			return;

		Util.db.store(jc.getCharacter());
		
		for(Pet p : jc.getCharacter().getPets()){
			p.setCharacter(jc.getCharacter());
			Util.db.store(p);
		}
		
		for(es.mgj.base.Object o : jc.getCharacter().getObjects()){
			o.setCharacter(jc.getCharacter());
			Util.db.store(o);
		}
		
		for(Magic m : jc.getCharacter().getMagics()){
			
			m.getCharacters().add(jc.getCharacter());
			Util.db.store(m);
		}
		
		for(Summoning s : jc.getCharacter().getSummonings()){
			
			s.getCharacters().add(jc.getCharacter());
			Util.db.store(s);
		}
			
		this.tableCharacters.listar(this.gameUser);
		
	}
	
	protected void modificar() {
		
		if (this.tableCharacters.getSelectedRow() == -1)
			return;
		
		JCharacter jc = new JCharacter(this.gameUser);
		
		Character character = new Character();
		character.setId(tableCharacters.getCharacter().getId());

		if(jc.mostrarDialogoModificar((Character) Util.db.queryByExample(character).next()) == Accion.CANCELAR)
			return;
		
		Util.db.store(jc.getCharacter());
		
		for(Pet p : jc.getPetsBorradas()){
			
			Character c = new Character();
			c.setId("1");
			c.setName("free");
			
			p.setCharacter(c);
			Util.db.store(p);
		}
		
		for(es.mgj.base.Object o : jc.getObjectBorrados()){
			Util.db.delete(o);
		}
		
		for(Pet p : jc.getCharacter().getPets()){
			p.setCharacter(jc.getCharacter());
			Util.db.store(p);
		}
		
		for(es.mgj.base.Object o : jc.getCharacter().getObjects()){
			o.setCharacter(jc.getCharacter());
			Util.db.store(o);
		}
		
		for(Magic m : jc.getMagicBorradas()){
			
			m.getCharacters().remove(jc.getCharacter());
			Util.db.store(m);
		}
		
		for(Magic m : jc.getCharacter().getMagics()){
			
			m.getCharacters().add(jc.getCharacter());
			Util.db.store(m);
		}
		
		for(Summoning s : jc.getSummoningBorrados()){
			
			s.getCharacters().remove(jc.getCharacter());
			Util.db.store(s);
		}
		
		for(Summoning s : jc.getCharacter().getSummonings()){
			
			s.getCharacters().add(jc.getCharacter());
			Util.db.store(s);
		}
		
		this.tableCharacters.listar(this.gameUser);
		

	}
	
	protected void borrar() {
		if (this.tableCharacters.getSelectedRow() == -1)
			return;
		
		for(es.mgj.base.Object o : this.tableCharacters.getCharacter().getObjects()){
			Util.db.delete(o);
		}
		
		for(Pet p : this.tableCharacters.getCharacter().getPets()){
			
			Character c = new Character();
			c.setId("1");
			c.setName("free");
			
			p.setCharacter(c);
			Util.db.store(p);
		}
		
		Util.db.delete(this.tableCharacters.getCharacter());
		
		this.tableCharacters.listar(gameUser);
		
	}

	
	private void initialize(){
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 635, 301);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPanel(), BorderLayout.SOUTH);
		contentPanel.add(getScrollPane(), BorderLayout.CENTER);
	}

	public JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnNew());
			panel.add(getBtnModify());
			panel.add(getBtnDelete());
			panel.add(getBtnExit());
		}
		return panel;
	}
	public JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton("New");
			btnNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					insertar();
				}
			});
		}
		return btnNew;
	}
	
	public JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("Delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					borrar();
				}
			});
		}
		return btnDelete;
	}
	

	public JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new JButton("Modify");
			btnModify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modificar();
				}
			});
		}
		return btnModify;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTableCharacters());
		}
		return scrollPane;
	}
	public JTableCharacters getTableCharacters() {
		if (tableCharacters == null) {
			tableCharacters = new JTableCharacters();
			tableCharacters.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					estado();
					if(tableCharacters.getSelectedRow() == -1)
						return;
					if(e.getClickCount() >= 2){
						
						JCharacter jc = new JCharacter(gameUser);
						Character character = new Character();
						character.setId(tableCharacters.getCharacter().getId());

						jc.mostrarDialogoVer((Character) Util.db.queryByExample(character).next());
						
					}
				}
			});
		}
		return tableCharacters;
	}

	public JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}
}
