package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import es.mgj.base.Magic;
import es.mgj.base.Pet;
import es.mgj.base.Summoning;
import es.mgj.base.Tabla;
import es.mgj.beans.JTablePrincipal;
import es.mgj.util.Constantes;
import es.mgj.util.Util.Accion;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiTablaItems extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JTablePrincipal tablePrincipal;
	private Tabla tabla;

	/**
	 * Create the dialog.
	 */
	public GuiTablaItems() {
		initialize();
		this.setModal(true);
	}
	
	public Accion mostrarDialogo(List<Tabla> datos, Tabla tabla){
		this.tabla = tabla;
		this.tablePrincipal.listar(datos, tabla);
		this.setVisible(true);
		return accion;
	}
	
	public Tabla getItem(){
		
		switch (tabla.getClass().getSimpleName()){
		case(Constantes.MAGIC):
			
			return this.tablePrincipal.getMagic();
			
		case(Constantes.PET):
			
			return this.tablePrincipal.getPet();
			
		case(Constantes.SUMMONING):
			
			return this.tablePrincipal.getSummoning();
			
		case(Constantes.OBJECT):
			
			return this.tablePrincipal.getObject();
		
		default:
			System.out.println("Tabla inexistente");
			return null;
			
		}
	}
	
	public void cancelar(){
		this.accion = Accion.CANCELAR;
		this.setVisible(false);
	}
	
	public void aceptar(){
		this.accion = Accion.ACEPTAR;
		this.setVisible(false);
	}
	
	public void initialize(){
		setBounds(100, 100, 595, 251);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tablePrincipal = new JTablePrincipal();
				scrollPane.setViewportView(tablePrincipal);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
