package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.mgj.base.Summoning;
import es.mgj.util.Util.Accion;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JSummoning extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Summoning summoning;
	Accion accion;
	private JLabel lblName;
	private JLabel lblPower;
	private JLabel lblEffect;
	private JLabel lblSummonFrecuence;
	private JTextField tfName;
	private JTextField tfPower;
	private JComboBox cbEffect;
	private JTextField tfSummonFrecuence;

	/**
	 * Create the dialog.
	 */
	public JSummoning() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		initialize();
		
	}
	
	private boolean controlCampos(){
		
		if(this.tfName.getText().length() < 1){
			JOptionPane.showMessageDialog(null, "Name required");
			return false;
		}
		
		try{
			
			Integer.parseInt(this.tfPower.getText());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Power must be numeric");
			return false;
		}
		
		try{
			
			Float.parseFloat(this.tfSummonFrecuence.getText());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Summon freccuency must be numeric");
			return false;
		}
		
		return true;
			
	}
	
	public Accion mostrarDialogoInsertar(){
		
		this.setTitle("Insert summoning");
		this.summoning = new Summoning();
		this.setVisible(true);
		return accion;
	}
	
	public Accion mostrarDialogoModificar(Summoning summoning){
		
		this.summoning = summoning;
		this.setTitle("Modify " + this.summoning.getName());
		this.tfName.setText(summoning.getName());
		this.tfPower.setText(String.valueOf(summoning.getPower()));
		this.tfSummonFrecuence.setText(String.valueOf(summoning.getSummonFrecuency()));
		this.cbEffect.setSelectedItem((String)summoning.getEffect());
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
		
		summoning.setName(this.tfName.getText());
		summoning.setEffect((String)this.cbEffect.getSelectedItem());
		summoning.setPower(Integer.parseInt(this.tfPower.getText()));
		summoning.setSummonFrecuency(Float.valueOf(this.tfSummonFrecuence.getText()));
		summoning.setId(String.valueOf(this.summoning.hashCode()));
	}
	
	public Summoning getSummoning(){
		return this.summoning;
	}
	
	public void initialize(){
		setBounds(100, 100, 294, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(getLblName())
							.addGap(18)
							.addComponent(getTfName(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(getLblEffect())
							.addGap(18)
							.addComponent(getCbEffect(), 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(getLblPower())
							.addGap(18)
							.addComponent(getTfPower(), GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(getLblSummonFrecuence())
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(getTfSummonFrecuence(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(227, Short.MAX_VALUE))
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
						.addComponent(getLblPower())
						.addComponent(getTfPower(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(getLblEffect())
						.addComponent(getCbEffect(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(getLblSummonFrecuence())
						.addComponent(getTfSummonFrecuence(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(98, Short.MAX_VALUE))
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
	public JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name");
		}
		return lblName;
	}
	public JLabel getLblPower() {
		if (lblPower == null) {
			lblPower = new JLabel("Power");
		}
		return lblPower;
	}
	public JLabel getLblEffect() {
		if (lblEffect == null) {
			lblEffect = new JLabel("Effect");
		}
		return lblEffect;
	}
	public JLabel getLblSummonFrecuence() {
		if (lblSummonFrecuence == null) {
			lblSummonFrecuence = new JLabel("Summon frecuence");
		}
		return lblSummonFrecuence;
	}
	public JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
		}
		return tfName;
	}
	public JTextField getTfPower() {
		if (tfPower == null) {
			tfPower = new JTextField();
			tfPower.setColumns(10);
		}
		return tfPower;
	}
	public JComboBox getCbEffect() {
		if (cbEffect == null) {
			cbEffect = new JComboBox();
			cbEffect.setModel(new DefaultComboBoxModel(new String[] {"No effect", "Silence", "Stone", "Freeze", "Slow", "Death", "Dizzy", "Confusion", "Betrayal"}));
		}
		return cbEffect;
	}
	public JTextField getTfSummonFrecuence() {
		if (tfSummonFrecuence == null) {
			tfSummonFrecuence = new JTextField();
			tfSummonFrecuence.setColumns(10);
		}
		return tfSummonFrecuence;
	}
}
