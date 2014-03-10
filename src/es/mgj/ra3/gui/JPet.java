package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import es.mgj.base.*;
import es.mgj.base.Character;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;

import es.mgj.base.Pet;
import es.mgj.util.Util.Accion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JPet extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfName;
	private JPanel buttonPane;
	private Pet pet;
	private Accion accion;
	private JComboBox cbAbility;
	private JComboBox cbKind;

	
	public JPet() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		setModal(true);
		initialize();
		inicializar();
	}

	private void inicializar() {
		
		
	}
	
	private boolean controlCampos(){
		
		if(this.tfName.getText().length() < 1){
			JOptionPane.showMessageDialog(null, "Name required");
			return false;
		}
		return true;
			
	}
	
	public Accion mostrarDialogoInsertar(){
		
		this.setTitle("Insertr Pet");
		this.pet = new Pet();
		this.setVisible(true);
		return accion;
	}
	
	public Accion mostrarDialogoModificar(Pet pet){
		
		this.pet = pet;
		this.setTitle("Modify " + this.pet.getName());
		this.tfName.setText(pet.getName());
		this.cbAbility.setSelectedItem(pet.getAbility());
		this.cbKind.setSelectedItem(pet.getKing());
		this.setVisible(true);
		return accion;
	}
	
	private void cancelar() {
		accion = Accion.CANCELAR;
		setVisible(false);
	}
	
	private void aceptar() {
		
		if(!controlCampos())
			return;
		
		accion = Accion.ACEPTAR;
		setVisible(false);
		
		Character c = new Character();
		c.setId("1");
		c.setName("free");
		this.pet.setName(this.tfName.getText());
		this.pet.setAbility((String)this.cbAbility.getSelectedItem());
		this.pet.setKing((String)this.cbKind.getSelectedItem());
		this.pet.setCharacter(c);
		this.pet.setId(String.valueOf(this.pet.hashCode()));
	}
	
	public Pet getPet(){
		return this.pet;
	}

	
	/**
	 * Create the dialog.
	 */
	public void initialize() {
		setBounds(100, 100, 263, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		JLabel lblName = new JLabel("Name");
		JLabel lblAbility = new JLabel("Ability");
		JLabel lblKind = new JLabel("Kind");
		tfName = new JTextField();
		tfName.setColumns(10);
		cbAbility = new JComboBox();
		cbAbility.setModel(new DefaultComboBoxModel(new String[] {"No Ability", "Swing", "Double Attack", "Dodge", "Protection", "Taunt", "Invisibility", "Suicide", "Lifesteal", "Aura"}));
		cbKind = new JComboBox();
		cbKind.setModel(new DefaultComboBoxModel(new String[] {"Beast", "Demon", "Human", "Insect", "Giant"}));
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblName)
							.addGap(18)
							.addComponent(tfName, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAbility)
								.addComponent(lblKind))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(cbKind, 0, 122, Short.MAX_VALUE)
								.addComponent(cbAbility, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(58, Short.MAX_VALUE))
				.addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAbility)
						.addComponent(cbAbility, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKind)
						.addComponent(cbKind, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}

}
