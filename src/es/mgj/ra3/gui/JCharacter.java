package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import es.mgj.base.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;






import com.db4o.ObjectSet;

import es.mgj.base.Character;
import es.mgj.base.GameUser;
import es.mgj.base.Magic;
import es.mgj.base.Object;
import es.mgj.base.Pet;
import es.mgj.base.Summoning;
import es.mgj.base.Tabla;
import es.mgj.util.Util;
import es.mgj.util.Util.Accion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JCharacter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblName;
	private JLabel lblRace;
	private JLabel lblClass;
	private JLabel lblLevel;
	private JLabel lblCreationDate;
	private JLabel lblLife;
	private JLabel lblMana;
	private JLabel lblGender;
	private JLabel lblPet;
	private JScrollPane scrollPane;
	private JLabel lblSummonings;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_1;
	private JLabel lblObjects;
	private JLabel lblMagic;
	private JList<Pet> listPets;
	private JList<Summoning> listSummonings;
	private JList<es.mgj.base.Object> listObjects;
	private JTextField tfName;
	private JComboBox<String> cbClass;
	private JComboBox<String> cbRace;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JScrollPane scrollPane_3;
	private JList<Magic> listMagic;
	private JPanel panelControles;
	private JButton btnInsertar;
	private JButton btnCancelar;
	private JButton btnAddPet;
	private JButton btnAddSummoning;
	private JButton btnAddObject;
	private JButton btnAddMagic;
	private JButton btnRemovePet;
	private JButton btnRemoveSummoning;
	private JButton btnRemoveMagic;
	private JButton btnRemoveObject;
	private GameUser gameUser;
	private ButtonGroup rg;
	private Accion accion;
	private Character character;
	private Set<Pet> pets = new HashSet<Pet>(0);
	private Set<Summoning> summonings = new HashSet<Summoning>(0);
	private Set<Object> objects = new HashSet<Object>(0);
	private Set<Magic> magics = new HashSet<Magic>(0);
	private Set<Pet> petsBorradas = new HashSet<Pet>(0);
	private Set<Object> objectBorrados = new HashSet<Object>(0);
	private Set<Magic> magicBorradas = new HashSet<Magic>(0);
	private Set<Summoning> summoningBorrados = new HashSet<Summoning>(0);
	

	
	public Set<Object> getObjectBorrados() {
		return objectBorrados;
	}

	public Set<Pet> getPetsBorradas() {
		return petsBorradas;
	}

	public JCharacter(GameUser user) {
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.gameUser = user;
		inicializar();
		initialize();
		
	}
	
	public void inicializar(){
		this.rg = new ButtonGroup();
		this.setModal(true);
	}
	
	
	protected void insertarPet() {
		GuiTablaItems gti = new GuiTablaItems();
		
		ObjectSet<Tabla> listaP = Util.db.queryByExample(new Pet());
		
		if(gti.mostrarDialogo(listaP, 
				new Pet()) == Accion.CANCELAR)
			return;
		
		this.pets.add((Pet)gti.getItem());
		
		if(this.petsBorradas.contains((Pet)gti.getItem()))
			this.petsBorradas.remove((Pet)gti.getItem());
			
		this.listarListItems();
	}
	
	protected void eliminarPet() {
		
		this.pets.remove(this.listPets.getSelectedValue());
		this.petsBorradas.add(this.listPets.getSelectedValue());
		
		listarListItems();
		
	}
	
	protected void eliminarMagic() {
		this.magics.remove(this.listMagic.getSelectedValue());
		this.magicBorradas.add(this.listMagic.getSelectedValue());
		listarListItems();
		
	}
	
	protected void eliminarSummoning() {
		this.summonings.remove(this.listSummonings.getSelectedValue());
		this.summoningBorrados.add(this.listSummonings.getSelectedValue());
		listarListItems();
		
	}
	
	protected void eliminarObject() {
		this.objects.remove(this.listObjects.getSelectedValue());
		this.objectBorrados.add(this.listObjects.getSelectedValue());
		listarListItems();
		
	}
	
	protected void insertarObject() {
		GuiTablaItems gti = new GuiTablaItems();
		
		ObjectSet<Tabla> listaO = Util.db.queryByExample(new es.mgj.base.Object());
		
		if(gti.mostrarDialogo(listaO, 
				new es.mgj.base.Object()) == Accion.CANCELAR)
			return;
		
		this.objects.add((es.mgj.base.Object)gti.getItem());
		
		if(this.objectBorrados.contains((es.mgj.base.Object)gti.getItem()))
			this.objectBorrados.remove((es.mgj.base.Object)gti.getItem());
		this.listarListItems();
		
	}
	
	private void insertarSummoning(){
		GuiTablaItems gti = new GuiTablaItems();
		
		ObjectSet<Tabla> listaS = Util.db.queryByExample(new Summoning());
		if(gti.mostrarDialogo(listaS, 
				new Summoning()) == Accion.CANCELAR)
			return;
		
		this.summonings.add((Summoning)gti.getItem());
		this.listarListItems();
	}
	
	private void insertarMagic(){
		GuiTablaItems gti = new GuiTablaItems();
		
		ObjectSet<Tabla> listaM = Util.db.queryByExample(new Magic());
		if(gti.mostrarDialogo(listaM, 
				new Magic()) == Accion.CANCELAR)
			return;
		
		this.magics.add((Magic)gti.getItem());
		this.listarListItems();
	}
	
	public void listarListItems(){
		
		DefaultListModel<Pet> dflp = new DefaultListModel<Pet>();
		for(Pet a : this.pets){
			dflp.addElement(a);
		}
		this.getListPets().setModel(dflp);
		
		DefaultListModel<Magic> dflm = new DefaultListModel<Magic>();
		for(Magic a : this.magics){
			dflm.addElement(a);
		}
		this.getListMagic().setModel(dflm);
		
		DefaultListModel<Summoning> dfls = new DefaultListModel<Summoning>();
		for(Summoning a : this.summonings){
			dfls.addElement(a);
		}
		this.getListSummonings().setModel(dfls);
		
		DefaultListModel<es.mgj.base.Object> dflo = new DefaultListModel<es.mgj.base.Object>();
		for(es.mgj.base.Object a : this.objects){
			dflo.addElement(a);
		}
		this.getListObjects().setModel(dflo);
	}
	
	public void mostrarDialogoVer(Character character){
		
		this.setTitle("Viendo " + character.getName());
		this.character = character;
		
		this.tfName.setText(this.character.getName());
		this.cbClass.setSelectedItem(this.character.getClass());
		this.cbRace.setSelectedItem(this.character.getRace());
		
		if(character.getGender())
			this.rdbtnMale.doClick();
		else
			this.rdbtnFemale.doClick();
			
		this.lblLevel.setText(this.lblLevel.getText() + " " + this.character.getLevel());
		this.lblCreationDate.setText(this.lblCreationDate.getText() + " " + this.character.getCreationDate());
		this.lblLife.setText(this.lblLife.getText() + " " + this.character.getLife());
		this.lblMana.setText(this.lblMana.getText() + " " + this.character.getMana());
		this.pets = this.character.getPets();
		this.magics = this.character.getMagics();
		this.objects = this.character.getObjects();
		this.summonings = this.character.getSummonings();
		this.listarListItems();
		
		this.tfName.setEditable(false);
		this.cbClass.setEnabled(false);
		this.cbRace.setEnabled(false);
		this.rdbtnFemale.setEnabled(false);
		this.rdbtnMale.setEnabled(false);
		this.listMagic.setEnabled(false);
		this.listObjects.setEnabled(false);
		this.listPets.setEnabled(false);
		this.listSummonings.setEnabled(false);
		this.panelControles.setVisible(false);
		
		WindowAdapter windowAdapter = new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                cancelar();
            }
        };

        addWindowListener(windowAdapter);
		this.setVisible(true);
	}
	
	public Accion mostrarDialogoInsertar(){
		this.setTitle("Insertando nuevo character");
		this.character = new Character();
		this.character.setGameUser(gameUser);
		this.setVisible(true);
		return accion;
		
	}
	
	public Accion mostrarDialogoModificar(Character character){
		
		this.setTitle("Modificando " + character.getName());
		this.character = character;
		
		this.tfName.setText(this.character.getName());
		this.cbClass.setSelectedItem(this.character.getClass());
		this.cbRace.setSelectedItem(this.character.getRace());
		
		if(character.getGender())
			this.rdbtnMale.doClick();
		else
			this.rdbtnFemale.doClick();
			
		this.lblLevel.setText(this.lblLevel.getText() + " " + this.character.getLevel());
		this.lblCreationDate.setText(this.lblCreationDate.getText() + " " + this.character.getCreationDate());
		this.lblLife.setText(this.lblLife.getText() + " " + this.character.getLife());
		this.lblMana.setText(this.lblMana.getText() + " " + this.character.getMana());
		this.pets.addAll(this.character.getPets());
		this.magics.addAll(this.character.getMagics());
		this.objects.addAll(this.character.getObjects());
		this.summonings.addAll(this.character.getSummonings());
		this.listarListItems();
		this.setVisible(true);
		return accion;
		
	}
	
	public Character getCharacter(){
		return this.character;
	}
	
	public void aceptar(){
		
		if(!controlCampos())
			return;
		
		accion = Accion.ACEPTAR;
		setVisible(false);
		
		this.character.setGameUser(gameUser);
		this.character.setName(this.tfName.getText());
		this.character.setGender(this.rdbtnMale.isSelected());
		this.character.setClass_((String)this.cbClass.getSelectedItem());
		this.character.setRace((String)this.cbRace.getSelectedItem());
		this.character.setCreationDate(new Date());
		this.character.setLevel(1f);
		this.character.setLife(75f);
		this.character.setMana(25f);
		this.character.setPets(pets);
		this.character.setMagics(magics);
		this.character.setSummonings(summonings);
		this.character.setObjects(objects);
		this.character.setId(this.character.toString());
		
	}
	
	private boolean controlCampos() {
		
		if(this.tfName.getText().length() < 1 ){
			JOptionPane.showMessageDialog(null, "Name required");
			return false;
		}
			
		return true;
	}

	public void cancelar(){
		accion = Accion.CANCELAR;
		this.setVisible(false);
	}
	public void initialize(){
		setResizable(false);
		setBounds(100, 100, 735, 714);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblNewLabel());
		contentPanel.add(getLblLevel());
		contentPanel.add(getLblCreationDate());
		contentPanel.add(getLblNewLabel_1());
		contentPanel.add(getLblNewLabel_2());
		contentPanel.add(getLblMana());
		contentPanel.add(getLblLife());
		contentPanel.add(getLblPet());
		contentPanel.add(getLblName());
		contentPanel.add(getTfName());
		contentPanel.add(getLblClass());
		contentPanel.add(getCbClass());
		contentPanel.add(getLblObjects());
		contentPanel.add(getScrollPane());
		contentPanel.add(getScrollPane_1());
		contentPanel.add(getLblMagic());
		contentPanel.add(getLblRace());
		contentPanel.add(getCbRace());
		contentPanel.add(getLblSummonings());
		contentPanel.add(getLblGender());
		contentPanel.add(getRdbtnMale());
		contentPanel.add(getRdbtnFemale());
		contentPanel.add(getScrollPane_2());
		contentPanel.add(getScrollPane_3());
		contentPanel.add(getPanelControles());
	}
	public JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(15, 11, 300, 546);
			lblNewLabel.setPreferredSize(new Dimension(300, 545));
			lblNewLabel.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/cMale.png")));
		}
		return lblNewLabel;
	}
	public JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
			lblName.setBounds(351, 49, 47, 14);
		}
		return lblName;
	}
	public JLabel getLblRace() {
		if (lblRace == null) {
			lblRace = new JLabel("Race:");
			lblRace.setBounds(517, 49, 47, 14);
		}
		return lblRace;
	}
	public JLabel getLblClass() {
		if (lblClass == null) {
			lblClass = new JLabel("Class:");
			lblClass.setBounds(351, 120, 59, 14);
		}
		return lblClass;
	}
	public JLabel getLblLevel() {
		if (lblLevel == null) {
			lblLevel = new JLabel("Level: ");
			lblLevel.setBounds(15, 563, 124, 14);
		}
		return lblLevel;
	}
	public JLabel getLblCreationDate() {
		if (lblCreationDate == null) {
			lblCreationDate = new JLabel("Creation date:");
			lblCreationDate.setBounds(149, 563, 166, 14);
		}
		return lblCreationDate;
	}
	public JLabel getLblLife() {
		if (lblLife == null) {
			lblLife = new JLabel("Life:");
			lblLife.setBounds(183, 604, 132, 14);
		}
		return lblLife;
	}
	public JLabel getLblMana() {
		if (lblMana == null) {
			lblMana = new JLabel("Mana:");
			lblMana.setBounds(183, 646, 132, 14);
		}
		return lblMana;
	}
	public JLabel getLblGender() {
		if (lblGender == null) {
			lblGender = new JLabel("Gender:");
			lblGender.setBounds(517, 120, 53, 14);
		}
		return lblGender;
	}
	public JLabel getLblPet() {
		if (lblPet == null) {
			lblPet = new JLabel("Pets");
			lblPet.setBounds(351, 211, 59, 14);
		}
		return lblPet;
	}
	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(351, 233, 156, 130);
			scrollPane.setViewportView(getListPets());
		}
		return scrollPane;
	}
	public JLabel getLblSummonings() {
		if (lblSummonings == null) {
			lblSummonings = new JLabel("Summonings");
			lblSummonings.setBounds(557, 211, 102, 14);
		}
		return lblSummonings;
	}
	public JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(351, 389, 156, 145);
			scrollPane_2.setViewportView(getListObjects());
		}
		return scrollPane_2;
	}
	public JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(552, 233, 156, 130);
			scrollPane_1.setViewportView(getListSummonings());
		}
		return scrollPane_1;
	}
	public JLabel getLblObjects() {
		if (lblObjects == null) {
			lblObjects = new JLabel("Objects");
			lblObjects.setBounds(351, 369, 70, 14);
		}
		return lblObjects;
	}
	public JLabel getLblMagic() {
		if (lblMagic == null) {
			lblMagic = new JLabel("Magic");
			lblMagic.setBounds(552, 369, 64, 14);
		}
		return lblMagic;
	}
	public JList<Pet> getListPets() {
		if (listPets == null) {
			listPets = new JList<Pet>();
		}
		return listPets;
	}
	public JList<Summoning> getListSummonings() {
		if (listSummonings == null) {
			listSummonings = new JList<Summoning>();
		}
		return listSummonings;
	}
	public JList<es.mgj.base.Object> getListObjects() {
		if (listObjects == null) {
			listObjects = new JList<es.mgj.base.Object>();
		}
		return listObjects;
	}
	public JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(398, 46, 109, 20);
			tfName.setColumns(10);
		}
		return tfName;
	}
	public JComboBox<String> getCbClass() {
		if (cbClass == null) {
			cbClass = new JComboBox<String>();
			cbClass.setBounds(398, 117, 109, 20);
			cbClass.setModel(new DefaultComboBoxModel<String>(new String[] {"Warrior", "Mage", "Archer", "Guardian", "Warlock", "Priest", "Rogue"}));
		}
		return cbClass;
	}
	public JComboBox<String> getCbRace() {
		if (cbRace == null) {
			cbRace = new JComboBox<String>();
			cbRace.setBounds(574, 46, 134, 20);
			cbRace.setModel(new DefaultComboBoxModel<String>(new String[] {"Human", "Elf", "Dark night elf", "Demon", "Trol", "Shadow chaser"}));
		}
		return cbRace;
	}
	public JRadioButton getRdbtnMale() {
		if (rdbtnMale == null) {
			rdbtnMale = new JRadioButton("Male");
			rdbtnMale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblNewLabel.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/cMale.png")));
				}
			});
			rdbtnMale.setBounds(569, 116, 59, 23);
			rdbtnMale.setSelected(true);
			rdbtnMale.setBackground(Color.GRAY);
			this.rg.add(rdbtnMale);
		}
		return rdbtnMale;
	}
	public JRadioButton getRdbtnFemale() {
		if (rdbtnFemale == null) {
			rdbtnFemale = new JRadioButton("Female");
			rdbtnFemale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblNewLabel.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/cFemale.png")));
				}
			});
			rdbtnFemale.setBounds(630, 116, 78, 23);
			rdbtnFemale.setBackground(Color.GRAY);
			this.rg.add(rdbtnFemale);
		}
		return rdbtnFemale;
	}
	public JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setBounds(15, 583, 162, 47);
			lblNewLabel_1.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/life.png")));
			lblNewLabel_1.setPreferredSize(new Dimension(150, 44));
		}
		return lblNewLabel_1;
	}
	public JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setBounds(15, 631, 162, 44);
			lblNewLabel_2.setPreferredSize(new Dimension(150, 44));
			lblNewLabel_2.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/mana.png")));
		}
		return lblNewLabel_2;
	}
	public JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setBounds(552, 389, 156, 145);
			scrollPane_3.setViewportView(getListMagic());
		}
		return scrollPane_3;
	}
	public JList<Magic> getListMagic() {
		if (listMagic == null) {
			listMagic = new JList<Magic>();
		}
		return listMagic;
	}
	public JPanel getPanelControles() {
		if (panelControles == null) {
			panelControles = new JPanel();
			panelControles.setBounds(351, 568, 357, 109);
			panelControles.setBackground(Color.GRAY);
			panelControles.setForeground(Color.DARK_GRAY);
			panelControles.setLayout(null);
			panelControles.add(getBtnCancelar());
			panelControles.add(getBtnInsertar());
			panelControles.add(getBtnRemovePet());
			panelControles.add(getBtnAddPet());
			panelControles.add(getBtnRemoveSummoning());
			panelControles.add(getBtnAddSummoning());
			panelControles.add(getBtnRemoveMagic());
			panelControles.add(getBtnAddMagic());
			panelControles.add(getBtnRemoveObject());
			panelControles.add(getBtnAddObject());
		}
		return panelControles;
	}
	public JButton getBtnInsertar() {
		if (btnInsertar == null) {
			btnInsertar = new JButton("Guardar");
			btnInsertar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aceptar();
				}
			});
			btnInsertar.setBounds(10, 11, 96, 23);
		}
		return btnInsertar;
	}
	public JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancelar();
				}
			});
			btnCancelar.setBounds(10, 76, 96, 23);
		}
		return btnCancelar;
	}
	public JButton getBtnAddPet() {
		if (btnAddPet == null) {
			btnAddPet = new JButton("");
			btnAddPet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					insertarPet();
				}
			});
			btnAddPet.setFocusTraversalKeysEnabled(false);
			btnAddPet.setFocusPainted(false);
			btnAddPet.setDefaultCapable(false);
			btnAddPet.setBounds(116, 11, 41, 41);
			btnAddPet.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/pet+.png")));
			btnAddPet.setContentAreaFilled(false);
			btnAddPet.setBorderPainted(false);
			btnAddPet.setBorder(null);
			btnAddPet.setPreferredSize(new Dimension(40, 40));
		}
		return btnAddPet;
	}
	

	public JButton getBtnAddSummoning() {
		if (btnAddSummoning == null) {
			btnAddSummoning = new JButton("");
			btnAddSummoning.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					insertarSummoning();
				}
			});
			btnAddSummoning.setFocusPainted(false);
			btnAddSummoning.setFocusTraversalKeysEnabled(false);
			btnAddSummoning.setBounds(182, 11, 41, 41);
			btnAddSummoning.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/summon+.png")));
			btnAddSummoning.setContentAreaFilled(false);
			btnAddSummoning.setBorderPainted(false);
			btnAddSummoning.setBorder(null);
		}
		return btnAddSummoning;
	}
	public JButton getBtnAddObject() {
		if (btnAddObject == null) {
			btnAddObject = new JButton("");
			btnAddObject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					insertarObject();
				}
			});
			btnAddObject.setFocusTraversalKeysEnabled(false);
			btnAddObject.setFocusPainted(false);
			btnAddObject.setLocation(306, 11);
			btnAddObject.setSize(new Dimension(41, 41));
			btnAddObject.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/item+.png")));
			btnAddObject.setContentAreaFilled(false);
			btnAddObject.setBorderPainted(false);
			btnAddObject.setBorder(null);
			btnAddObject.setPreferredSize(new Dimension(40, 40));
		}
		return btnAddObject;
	}
	public JButton getBtnAddMagic() {
		if (btnAddMagic == null) {
			btnAddMagic = new JButton("");
			btnAddMagic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					insertarMagic();
				}
			});
			btnAddMagic.setFocusTraversalKeysEnabled(false);
			btnAddMagic.setFocusPainted(false);
			btnAddMagic.setBounds(243, 11, 41, 41);
			btnAddMagic.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/magic+.png")));
			btnAddMagic.setBorder(null);
			btnAddMagic.setContentAreaFilled(false);
			btnAddMagic.setBorderPainted(false);
		}
		return btnAddMagic;
	}
	public JButton getBtnRemovePet() {
		if (btnRemovePet == null) {
			btnRemovePet = new JButton("");
			btnRemovePet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarPet();
				}
			});
			btnRemovePet.setFocusPainted(false);
			btnRemovePet.setFocusTraversalKeysEnabled(false);
			btnRemovePet.setBounds(116, 58, 41, 41);
			btnRemovePet.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/pet-.png")));
			btnRemovePet.setBorderPainted(false);
			btnRemovePet.setContentAreaFilled(false);
			btnRemovePet.setBorder(null);
		}
		return btnRemovePet;
	}
	

	public JButton getBtnRemoveSummoning() {
		if (btnRemoveSummoning == null) {
			btnRemoveSummoning = new JButton("");
			btnRemoveSummoning.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarSummoning();
				}
			});
			btnRemoveSummoning.setFocusTraversalKeysEnabled(false);
			btnRemoveSummoning.setFocusPainted(false);
			btnRemoveSummoning.setBorderPainted(false);
			btnRemoveSummoning.setBorder(null);
			btnRemoveSummoning.setBounds(180, 58, 43, 41);
			btnRemoveSummoning.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/summon-.png")));
			btnRemoveSummoning.setDefaultCapable(false);
			btnRemoveSummoning.setContentAreaFilled(false);
		}
		return btnRemoveSummoning;
	}
	public JButton getBtnRemoveMagic() {
		if (btnRemoveMagic == null) {
			btnRemoveMagic = new JButton("");
			btnRemoveMagic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarMagic();
				}
			});
			btnRemoveMagic.setFocusTraversalKeysEnabled(false);
			btnRemoveMagic.setFocusPainted(false);
			btnRemoveMagic.setBounds(243, 58, 41, 41);
			btnRemoveMagic.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/magic-.png")));
			btnRemoveMagic.setBorderPainted(false);
			btnRemoveMagic.setContentAreaFilled(false);
		}
		return btnRemoveMagic;
	}
	public JButton getBtnRemoveObject() {
		if (btnRemoveObject == null) {
			btnRemoveObject = new JButton("");
			btnRemoveObject.setFocusTraversalKeysEnabled(false);
			btnRemoveObject.setFocusPainted(false);
			btnRemoveObject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarObject();
				}
			});
			btnRemoveObject.setBounds(306, 58, 41, 41);
			btnRemoveObject.setIcon(new ImageIcon(JCharacter.class.getResource("/resources/item-.png")));
			btnRemoveObject.setContentAreaFilled(false);
			btnRemoveObject.setBorderPainted(false);
		}
		return btnRemoveObject;
	}

	public Set<Magic> getMagicBorradas() {
		return magicBorradas;
	}

	public void setMagicBorradas(Set<Magic> magicBorradas) {
		this.magicBorradas = magicBorradas;
	}

	public Set<Summoning> getSummoningBorrados() {
		return summoningBorrados;
	}

	public void setSummoningBorrados(Set<Summoning> summoningBorrados) {
		this.summoningBorrados = summoningBorrados;
	}
	
	
}
