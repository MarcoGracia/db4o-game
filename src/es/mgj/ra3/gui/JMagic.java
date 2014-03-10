package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.mgj.base.Magic;
import es.mgj.util.Util.Accion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;

public class JMagic extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Magic magic;
	private Accion accion;
	private JLabel lblName;
	private JLabel lblDamage;
	private JLabel lblElement;
	private JLabel lblRequiredMana;
	private JTextField tfName;
	private JTextField tfDamage;
	private JComboBox cbElement;
	private JSlider sliderMana;

	public JMagic() {
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
			
		
		try{
			Float.parseFloat(this.tfDamage.getText());
		}catch(NumberFormatException pe){
			JOptionPane.showMessageDialog(null, "Damage must be numeric");
			return false;
		}
		
		return true;
		
	}
	
	public Accion mostrarDialogoInsertar(){
		
		this.setTitle("Insert Magic");
		this.magic = new Magic();
		this.setVisible(true);
		return accion;
	}
	
	public Accion mostrarDialogoModificar(Magic magic){
		
		this.magic = magic;
		this.setTitle("Modify " + this.magic.getName());
		this.tfName.setText(magic.getName());
		this.tfDamage.setText(String.valueOf(magic.getDamage()));
		this.cbElement.setSelectedItem(magic.getElement());
		this.sliderMana.setValue(Math.round(magic.getRequiredMana()));
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
		
		this.magic.setName(this.tfName.getText());
		this.magic.setDamage(Float.parseFloat(this.tfDamage.getText()));
		this.magic.setElement((String)this.cbElement.getSelectedItem());
		this.magic.setRequiredMana((float) this.sliderMana.getValue());
		this.magic.setId(String.valueOf(this.magic.hashCode()));

	}
	
	public Magic getMagic(){
		return this.magic;
	}

	/**
	 * Create the dialog.
	 */
	public void initialize() {
		setBounds(100, 100, 271, 241);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(getLblElement())
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(getCbElement(), 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(getLblRequiredMana())
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(getSliderMana(), GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(getLblDamage())
								.addComponent(getLblName()))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(getTfDamage())
								.addComponent(getTfName(), GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(getLblName())
						.addComponent(getTfName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(getLblDamage())
						.addComponent(getTfDamage(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(getLblElement())
						.addComponent(getCbElement(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(getLblRequiredMana())
						.addComponent(getSliderMana(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
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
					public void actionPerformed(ActionEvent arg0) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name");
		}
		return lblName;
	}
	public JLabel getLblDamage() {
		if (lblDamage == null) {
			lblDamage = new JLabel("Damage");
		}
		return lblDamage;
	}
	public JLabel getLblElement() {
		if (lblElement == null) {
			lblElement = new JLabel("Element");
		}
		return lblElement;
	}
	public JLabel getLblRequiredMana() {
		if (lblRequiredMana == null) {
			lblRequiredMana = new JLabel("Required Mana");
		}
		return lblRequiredMana;
	}
	public JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
		}
		return tfName;
	}
	public JTextField getTfDamage() {
		if (tfDamage == null) {
			tfDamage = new JTextField();
			tfDamage.setColumns(10);
		}
		return tfDamage;
	}
	public JComboBox getCbElement() {
		if (cbElement == null) {
			cbElement = new JComboBox();
			cbElement.setModel(new DefaultComboBoxModel(new String[] {"Neutral", "Fire", "Ice", "Earth", "Air", "Arcana", "Holy", "Shadow"}));
		}
		return cbElement;
	}
	public JSlider getSliderMana() {
		if (sliderMana == null) {
			sliderMana = new JSlider();
		}
		return sliderMana;
	}
}
