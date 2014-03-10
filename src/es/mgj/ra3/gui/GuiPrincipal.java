package es.mgj.ra3.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JButton;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectSet;
import com.db4o.cs.Db4oClientServer;
import com.db4o.query.Predicate;

import es.mgj.util.Constantes;
import es.mgj.util.Util;
import es.mgj.util.Util.Accion;
import es.mgj.base.*;

import javax.swing.JScrollPane;








import es.mgj.base.Character;
import es.mgj.base.GameUser;
import es.mgj.base.Magic;
import es.mgj.base.Pet;
import es.mgj.base.Summoning;
import es.mgj.base.Tabla;
import es.mgj.beans.JTablePrincipal;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiPrincipal extends JFrame {

	private JPanel contentPane;
	private JPanel panelInferior;
	private JLabel lblNewLabel;
	private JPanel panelIzquierdo;
	private JButton btPet;
	private JButton btMagic;
	private JButton btSummoning;
	private JButton btObject;
	private JScrollPane scrollPane;
	private JTablePrincipal jTablePrincipal;
	private JTextField tfBuscar;
	
	private ArrayList<Integer> teclas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiPrincipal frame = new GuiPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiPrincipal() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				int opcion = JOptionPane.showOptionDialog(null, "¿Desea guardar antes de cerrar?",
						"Guardar cambios", JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				
				if(opcion == JOptionPane.YES_OPTION){
					Util.db.commit();
				}else if(opcion == JOptionPane.NO_OPTION){
					Util.db.rollback();
				}
				else{
					return;
				}
				
				Util.db.close();
				
				System.exit(0);
			}
		});
		initialize();
		inicializar();
		
	}
	
	private void inicializar() {

		teclas = new ArrayList<Integer>();
		
		// Conecta con la Base de Datos (si el fichero no existe lo creará)
		//Util.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), Constantes.DATABASE_FILENAME);
		// Conecta con la Base de Datos del servidor (si el fichero no existe lo creará)
		Util.db = Db4oClientServer.openClient("localhost", 8090, "root", "58yxp1");
		
		
		if(Constantes.insertar){
			GameUser gu = new GameUser();
			gu.setId(gu.toString());
			gu.setNick("test");
			gu.setPassword("1234");
			
			Character c = new Character();
			c.setId("1");
			c.setName("free");
			
			Character c2 = new Character();
			c2.setId("2");
			c2.setName("Not dropped");
			
			Util.db.store(gu);
			Util.db.store(c);
			Util.db.store(c2);
		}
		
		ObjectSet<Tabla> listaM = Util.db.queryByExample(new Magic());
		
		jTablePrincipal.listar(listaM, new Magic());
		
		estado();
		
	}
	
	public void estado(){
		if(this.jTablePrincipal.getSelectedRow() == -1){
			this.setTitle("Listando: " + this.jTablePrincipal.getRowCount() +  " " +this.jTablePrincipal.getListando()+"s" + 
					" ninguna fila seleccionada");
			return;
		}
		this.setTitle("Listando: " + this.jTablePrincipal.getRowCount() +  " " +this.jTablePrincipal.getListando()+"s" + 
				" fila " + (this.jTablePrincipal.getSelectedRow()+1));
	}
	
	protected void buscar() {
		final String filtro;
		List<Tabla> listaT;
		switch (this.jTablePrincipal.getListando()){
			case(Constantes.MAGIC):
				
				List<Magic> listaM = new ArrayList<Magic>();
			
				filtro = this.tfBuscar.getText();
				
				listaM = Util.db.query(new Predicate<Magic>() {

					@Override
					public boolean match(Magic magic) {
						System.out.println(filtro);
						return (magic.getName().contains(filtro) 
								|| magic.getCharactersList().contains(filtro)
								|| String.valueOf(magic.getDamage()).contains(filtro)
								|| String.valueOf(magic.getRequiredMana()).contains(filtro)
								|| magic.getElement().contains(filtro));
					}
				});
				listaT = new ArrayList<Tabla>();
				
				for(Tabla t : listaM)
					listaT.add(t);

				this.jTablePrincipal.listar(listaT, new Magic());
				estado();
				break;
				
			case(Constantes.PET):
				
				List<Pet> listaP = new ArrayList<Pet>();
			
				filtro = this.tfBuscar.getText();
			
				listaP = Util.db.query(new Predicate<Pet>() {

				@Override
				public boolean match(Pet pet) {
					
					return (pet.getName().contains(filtro) 
							|| pet.getCharacter().getName().contains(filtro)
							|| pet.getKing().contains(filtro)
							|| pet.getAbility().contains(filtro));
					}
				});
				listaT = new ArrayList<Tabla>();
			
				for(Tabla t : listaP)
				listaT.add(t);

				this.jTablePrincipal.listar(listaT, new Pet());
				estado();
				
				break;
			case(Constantes.SUMMONING):
				
				List<Summoning> listaS = new ArrayList<Summoning>();
			
				filtro = this.tfBuscar.getText();
			
				listaS = Util.db.query(new Predicate<Summoning>() {

				@Override
				public boolean match(Summoning summoning) {
					System.out.println(filtro);
					return (summoning.getName().contains(filtro) 
							|| summoning.getCharactersList().contains(filtro)
							|| String.valueOf(summoning.getPower()).contains(filtro)
							|| String.valueOf(summoning.getSummonFrecuency()).contains(filtro)
							|| summoning.getEffect().contains(filtro));
					}
				});
				listaT = new ArrayList<Tabla>();
			
				for(Tabla t : listaS)
					listaT.add(t);

				this.jTablePrincipal.listar(listaT, new Summoning());
				estado();
				break;
			case(Constantes.OBJECT):
				
				List<es.mgj.base.Object> listaO = new ArrayList<es.mgj.base.Object>();
			
				filtro = this.tfBuscar.getText();
		
				listaO = Util.db.query(new Predicate<es.mgj.base.Object>() {

					@Override
					public boolean match(es.mgj.base.Object object) {
						
						return (object.getCharacter().getName().contains(filtro) 
						|| String.valueOf(object.getDefence()).contains(filtro)
						|| String.valueOf(object.getAttack()).contains(filtro)
						|| object.getKind().contains(filtro));
					}
				});
				listaT = new ArrayList<Tabla>();
		
				for(Tabla t : listaO)
					listaT.add(t);

				this.jTablePrincipal.listar(listaT, new es.mgj.base.Object());
				estado();
				
				break;
			default:
				System.out.println("No existe la tabla");
		}
		
	}
	
	public void listarTabla(){
		switch (this.jTablePrincipal.getListando()){
		case(Constantes.MAGIC):
			
			ObjectSet<Tabla> listaM = Util.db.queryByExample(new Magic());
			this.jTablePrincipal.listar(listaM, new Magic());
			
			estado();
			break;
			
		case(Constantes.PET):
			

			ObjectSet<Tabla> listaP = Util.db.queryByExample(new Pet());
			this.jTablePrincipal.listar(listaP, new Pet());
			
			estado();
			break;
		case(Constantes.SUMMONING):
			

			ObjectSet<Tabla> listaS = Util.db.queryByExample(new Summoning());
			this.jTablePrincipal.listar(listaS, new Summoning());
			
			estado();
			break;
		case(Constantes.OBJECT):
			
			ObjectSet<Tabla> listaO = Util.db.queryByExample(new es.mgj.base.Object());
			this.jTablePrincipal.listar(listaO, new es.mgj.base.Object());
			estado();
			break;
		default:
			System.out.println("No existe la tabla");
	}
	}
	
	
	public void insertar(){
		
		switch (this.jTablePrincipal.getListando()){
			case(Constantes.MAGIC):
				
				JMagic jm = new JMagic();
			
				if(jm.mostrarDialogoInsertar() == Accion.CANCELAR)
					return;

				Util.db.store(jm.getMagic());
				
				listarTabla();
				
				estado();
				break;
				
			case(Constantes.PET):
				JPet jp = new JPet();
			
				if(jp.mostrarDialogoInsertar() == Accion.CANCELAR)
					return;
				
				Util.db.store(jp.getPet());
	
				listarTabla();
				
				estado();
				break;
			case(Constantes.SUMMONING):
				JSummoning js = new JSummoning();
			
				if(js.mostrarDialogoInsertar() == Accion.CANCELAR)
					return;
				
				Util.db.store(js.getSummoning());
	
				listarTabla();
				
				estado();
				break;
			case(Constantes.OBJECT):
				JObject jo = new JObject();
			
				if(jo.mostrarDialogoInsertar() == Accion.CANCELAR)
					return;
	
				Util.db.store(jo.getObject());
				
				listarTabla();
				
				estado();
				break;
			default:
				System.out.println("No existe la tabla");
		}
	}
	
	public void modificar(){
		
		if(this.jTablePrincipal.getSelectedRow() == -1)
			return;
		
		switch (this.jTablePrincipal.getListando()){
			case(Constantes.MAGIC):
				
				JMagic jm = new JMagic();
			
				if(jm.mostrarDialogoModificar(this.jTablePrincipal.getMagic()) == Accion.CANCELAR)
					return;
				
				Util.db.store(jm.getMagic());
				
				listarTabla();
				
				estado();
				break;
				
			case(Constantes.PET):
				JPet jp = new JPet();
			
				if(jp.mostrarDialogoModificar(this.jTablePrincipal.getPet()) == Accion.CANCELAR)
					return;
				
				Util.db.store(jp.getPet());
				
				listarTabla();
				
				break;
			case(Constantes.SUMMONING):
				JSummoning js = new JSummoning();
			
				if(js.mostrarDialogoModificar(this.jTablePrincipal.getSummoning()) == Accion.CANCELAR)
					return;
	
				Util.db.store(js.getSummoning());
				
				listarTabla();
				
				estado();
				break;
			case(Constantes.OBJECT):
				JObject jo = new JObject();
			
				if(jo.mostrarDialogoModificar(this.jTablePrincipal.getObject()) == Accion.CANCELAR)
					return;
	
				Util.db.store(jo.getObject());
				
				listarTabla();
				
				estado();
				break;
			default:
				System.out.println("No existe la tabla");
		}
	}
	
	public void eliminar(){
		
		if(this.jTablePrincipal.getSelectedRow() == -1)
			return;
		
		switch (this.jTablePrincipal.getListando()){
			case(Constantes.MAGIC):
				
				for(es.mgj.base.Character c : this.jTablePrincipal.getMagic().getCharacters()){
					c.getMagics().remove(this.jTablePrincipal.getMagic());
					Util.db.store(c);
				}
				
				Util.db.delete(this.jTablePrincipal.getMagic());
			
				listarTabla();
				estado();
			
				break;
				
			case(Constantes.PET):
				
				this.jTablePrincipal.getPet().getCharacter().getPets().remove(
						this.jTablePrincipal.getPet());
				
				Util.db.delete(this.jTablePrincipal.getPet());
			
				listarTabla();
				
				estado();
				break;
				
			case(Constantes.SUMMONING):
				
				for(Character c : this.jTablePrincipal.getSummoning().getCharacters()){
					c.getSummonings().remove(this.jTablePrincipal.getSummoning());
					Util.db.store(c);
				}
				
				Util.db.delete(this.jTablePrincipal.getSummoning());
			
				listarTabla();
			
				estado();
				break;
				
			case(Constantes.OBJECT):
				
				this.jTablePrincipal.getObject().getCharacter().getObjects().remove(
						this.jTablePrincipal.getObject());
				
				Util.db.delete(this.jTablePrincipal.getObject());
			
				listarTabla();
				
				estado();
				break;
			default:
				System.out.println("No existe la tabla");
		}
	}

	public void initialize(){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 820, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelInferior(), BorderLayout.SOUTH);
		contentPane.add(getPanelIzquierdo(), BorderLayout.WEST);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent arg0) {
		    	  
		    	  teclas.add(arg0.getExtendedKeyCode());
		    	  
		    	  if(arg0.getID() == KeyEvent.KEY_PRESSED){
		    		  switch (arg0.getExtendedKeyCode()){
						case(KeyEvent.VK_Z):
							if(teclas.contains(new Integer(KeyEvent.VK_CONTROL))){
								
								int opcion = JOptionPane.showOptionDialog(null, 
										"¿Está seguro de que desea deshacer los cambios?",
										"Guardar cambios", JOptionPane.YES_NO_OPTION, 
										JOptionPane.QUESTION_MESSAGE, null, null, null);
								
								if(opcion == JOptionPane.YES_OPTION){
									Util.db.rollback();
									listarTabla();
									
								}else if(opcion == JOptionPane.NO_OPTION){
									Util.db.commit();
								}
								
							}
								
						break;
						
						
					}
		    	  }
		    	  
		    	  if(arg0.getID() == KeyEvent.KEY_RELEASED){
		    		  teclas = new ArrayList<Integer>();
		    	  }
		    	  
		        return false;
		      }
		});
		
	}
	public JPanel getPanelInferior() {
		if (panelInferior == null) {
			panelInferior = new JPanel();
			final GuiPrincipal tmp = this;
			panelInferior.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					
					double x = arg0.getComponent().getMousePosition().getX();
					double y = arg0.getComponent().getMousePosition().getY();
					
					if((x >= 506 && x <= 520) && (y >= 58 && y <= 72)){
						insertar();
					}
					
					else if((x >= 490 && x <= 505) && (y >= 43 && y <= 58)){
						modificar();
					}
					
					else if((x >= 528 && x <= 537) && (y >= 42 && y <= 58)){
						eliminar();
					}
					
					else if((x >= 382 && x <= 415) && (y >= 34 && y <= 67)){
						JLogin jl = new JLogin();
					}
					
					else if((x >= 468 && x <= 501) && (y >= 67 && y <= 101)){
						buscar();
					}
					
					
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lblNewLabel.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/mandodescripcion.png")));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lblNewLabel.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/mando.png")));
				}
			});
			panelInferior.setBackground(Color.BLACK);
			panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelInferior.add(getTfBuscar());
			panelInferior.add(getLblNewLabel());
		}
		return panelInferior;
	}

	public JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/mando.png")));
		}
		return lblNewLabel;
	}
	public JPanel getPanelIzquierdo() {
		if (panelIzquierdo == null) {
			panelIzquierdo = new JPanel();
			panelIzquierdo.setBackground(Color.BLACK);
			panelIzquierdo.setPreferredSize(new Dimension(75, 10));
			panelIzquierdo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelIzquierdo.add(getBtMagic());
			panelIzquierdo.add(getBtPet());
			panelIzquierdo.add(getBtSummoning());
			panelIzquierdo.add(getBtObject());
		}
		return panelIzquierdo;
	}
	public JButton getBtPet() {
		if (btPet == null) {
			btPet = new JButton("");
			btPet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ObjectSet<Tabla> listaP = Util.db.queryByExample(new Pet());
					jTablePrincipal.listar(listaP, new Pet());
					estado();
				}
			});
			btPet.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/pet.png")));
			btPet.setDefaultCapable(false);
			btPet.setContentAreaFilled(false);
			btPet.setBorderPainted(false);
			btPet.setBorder(null);
			btPet.setPreferredSize(new Dimension(75, 75));
		}
		
		
		return btPet;
	}
	public JButton getBtMagic() {
		if (btMagic == null) {
			btMagic = new JButton("");
			btMagic.setBackground(Color.GRAY);
			btMagic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ObjectSet<Tabla> listaM = Util.db.queryByExample(new Magic());
					jTablePrincipal.listar(listaM, new Magic());
					estado();
				}
			});
			btMagic.setDefaultCapable(false);
			btMagic.setContentAreaFilled(false);
			btMagic.setBorder(null);
			btMagic.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/magic.png")));
			btMagic.setPreferredSize(new Dimension(75, 75));
		}
		return btMagic;
	}
	public JButton getBtSummoning() {
		if (btSummoning == null) {
			btSummoning = new JButton("");
			btSummoning.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ObjectSet<Tabla> listaS = Util.db.queryByExample(new Summoning());
					jTablePrincipal.listar(listaS, new Summoning());
					estado();
				}
			});
			btSummoning.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/summon.png")));
			btSummoning.setDefaultCapable(false);
			btSummoning.setContentAreaFilled(false);
			btSummoning.setBorderPainted(false);
			btSummoning.setBorder(null);
			btSummoning.setPreferredSize(new Dimension(75, 75));
		}
		return btSummoning;
	}
	public JButton getBtObject() {
		if (btObject == null) {
			btObject = new JButton("");
			btObject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ObjectSet<Tabla> listaO = Util.db.queryByExample(new es.mgj.base.Object());
					jTablePrincipal.listar(listaO, new es.mgj.base.Object());
					estado();
				}
			});
			btObject.setIcon(new ImageIcon(GuiPrincipal.class.getResource("/resources/item.png")));
			btObject.setDefaultCapable(false);
			btObject.setContentAreaFilled(false);
			btObject.setBorderPainted(false);
			btObject.setBorder(null);
			btObject.setPreferredSize(new Dimension(75, 75));
		}
		return btObject;
	}
	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.BLACK);
			scrollPane.setViewportView(getJTablePrincipal());
		}
		return scrollPane;
	}
	public JTablePrincipal getJTablePrincipal() {
		if (jTablePrincipal == null) {
			jTablePrincipal = new JTablePrincipal();
			jTablePrincipal.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					estado();
				}
			});
			jTablePrincipal.setSelectionForeground(Color.BLACK);
			jTablePrincipal.setForeground(Color.WHITE);
			jTablePrincipal.setSelectionBackground(Color.RED);
			jTablePrincipal.setBackground(Color.BLACK);
		}
		return jTablePrincipal;
	}
	public JTextField getTfBuscar() {
		if (tfBuscar == null) {
			tfBuscar = new JTextField();
			tfBuscar.setPreferredSize(new Dimension(150, 20));
			tfBuscar.setColumns(10);
			this.getTfBuscar().getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent arg0) {
					buscar();
					
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					buscar();
					
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					buscar();
					
				} 
				
				
		    });
			
			this.getTfBuscar().addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					buscar();
					
				}
			});
		}
		return tfBuscar;
	}
}
