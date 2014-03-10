package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.mgj.base.GameUser;
import es.mgj.util.Util;

public class JLogin extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfNick;
	private JPasswordField tfPassword;
	/**
	 * Create the dialog.
	 */
	public JLogin() {
		setTitle("Haciendo login");
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		initialize();
	}
	
	protected void cancelar() {
		this.dispose();
		
	}

	protected void aceptar() {
		
		GameUser u ;
		
		if((u = comprobarLogin()) == null)
			return;
		
		this.dispose();
		GuiCharacter gc = new GuiCharacter(u);
		
		
	}
	
	private GameUser comprobarLogin() {
		
		GameUser u = new GameUser();
		u.setNick(this.tfNick.getText());
		u.setPassword(String.copyValueOf(this.tfPassword.getPassword()));
		
		List<GameUser> listGU =  Util.db.queryByExample(u);
		
		if(listGU.size() <=0){
			u = null;
			JOptionPane.showMessageDialog(null, "Nick or password incorrect");
		}else
			u = listGU.get(0);
		
		
		return u;
		
	}

	public void initialize(){
		setBounds(100, 100, 283, 187);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNick = new JLabel("Nick");
		JLabel lblPassword = new JLabel("Password");
		tfNick = new JTextField();
		tfNick.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNick)
							.addGap(18)
							.addComponent(tfNick, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblPassword)
							.addGap(18)
							.addComponent(getTfPassword())))
					.addContainerGap(225, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNick)
						.addComponent(tfNick, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(getTfPassword(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(162, Short.MAX_VALUE))
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
		this.setVisible(true);
	}
	

	public JPasswordField getTfPassword() {
		if (tfPassword == null) {
			tfPassword = new JPasswordField();
		}
		return tfPassword;
	}
}
