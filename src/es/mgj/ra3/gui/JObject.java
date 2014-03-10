package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.mgj.util.Util.Accion;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import es.mgj.base.Character;

public class JObject extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private es.mgj.base.Object object;
	private Accion accion;
	private JTextField tfDefence;
	private JTextField tfAttack;
	private JTextField tfDropRate;
	private JComboBox cbKind;

	/**
	 * Create the dialog.
	 */
	public JObject() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		initialize();
	}
	
	private boolean controlCampos(){
		
		try{
			
			Float.parseFloat(this.tfDefence.getText());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Defence must be numeric");
			return false;
		}
		
		try{
			
			Float.parseFloat(this.tfAttack.getText());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Attack must be numeric");
			return false;
		}
		
		try{
			
			Float.parseFloat(this.tfDropRate.getText());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Drop rate must be numeric");
			return false;
		}
		
		return true;
			
	}
	
	public Accion mostrarDialogoInsertar(){
		
		this.setTitle("Insert Object");
		this.object = new es.mgj.base.Object();
		this.setVisible(true);
		return accion;
	}
	
	public Accion mostrarDialogoModificar(es.mgj.base.Object object){
		
		this.object = object;
		this.setTitle("Modify " + this.object.getKind());
		this.tfAttack.setText(String.valueOf(this.object.getAttack()));
		this.tfDefence.setText(String.valueOf(this.object.getDefence()));
		this.tfDropRate.setText(String.valueOf(this.object.getDropRate()));
		this.cbKind.setSelectedItem((String)this.object.getKind());
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
		c.setName("Not dropped");
		object.setKind((String)this.cbKind.getSelectedItem());
		this.object.setDefence(Float.valueOf(this.tfDefence.getText()));
		this.object.setAttack(Float.valueOf(this.tfAttack.getText()));
		this.object.setDropRate(Float.valueOf(this.tfDropRate.getText()));
		this.object.setCharacter(c);
		this.object.setId(String.valueOf(this.object.hashCode()));
	}
	
	public es.mgj.base.Object getObject(){
		return this.object;
	}
	
	public void initialize(){
		
		setBounds(100, 100, 255, 259);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblKind = new JLabel("Kind");
		JLabel lblDefence = new JLabel("Defence");
		JLabel lblAttack = new JLabel("Attack");
		JLabel lblDropRate = new JLabel("Drop rate");
		cbKind = new JComboBox();
		cbKind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(cbKind.getSelectedIndex() != 0){
					tfAttack.setText("0");
					tfDefence.setText("0");
					tfAttack.setEditable(false);
					tfDefence.setEditable(false);
					return;
				}
				tfAttack.setEditable(true);
				tfDefence.setEditable(true);
			}
		});
		cbKind.setModel(new DefaultComboBoxModel(new String[] {"Equipment", "Usable", "Miscelaneous"}));
		tfDefence = new JTextField();
		tfDefence.setText("0");
		tfDefence.setColumns(10);
		tfAttack = new JTextField();
		tfAttack.setText("0");
		tfAttack.setColumns(10);
		tfDropRate = new JTextField();
		tfDropRate.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblKind)
							.addGap(30)
							.addComponent(cbKind, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblDropRate)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfDropRate, 0, 0, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
								.addComponent(lblAttack)
								.addGap(18)
								.addComponent(tfAttack, 0, 0, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
								.addComponent(lblDefence)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(tfDefence, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKind)
						.addComponent(cbKind, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDefence)
						.addComponent(tfDefence, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAttack)
						.addComponent(tfAttack, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDropRate)
						.addComponent(tfDropRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
