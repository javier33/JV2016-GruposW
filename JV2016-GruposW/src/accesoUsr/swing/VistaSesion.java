package accesoUsr.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import accesoUsr.OperacionesVista;
import java.awt.Font;

public class VistaSesion extends JDialog implements OperacionesVista{
	private static final long serialVersionUID = 1L;
	private static String OK = "OK";
	private static String CANCEL = "Cancelar";
	
private JPanel panelTexto;
private JPanel panelCampos;
private JPanel panelEtiquetas;
private JPanel panelBotones;
private JPanel panelTrabajo;
private JPanel panelTitulo;

private JTextField campoUsuario;
private JPasswordField campoClaveAcceso;

private JButton botonOk;
private JButton botonCancelar;

private JLabel lblClaveDeAcceso;
private JLabel lblUsuario;
private JLabel lblTitulo;
private JLabel lblAyuda;
private JLabel lblSubtitulo;
	
	public VistaSesion(){
		initVistaSesion();
	}
	
	public void initVistaSesion(){
	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	setModal(true);
	setType(Type.POPUP);
	setResizable(false);
		
		
	Dimension sizePantalla = Toolkit.getDefaultToolkit().getScreenSize();
	setLocation((sizePantalla.width - getSize().width)/2,
			(sizePantalla.height - getSize().height)/2);
	
	panelTitulo = new JPanel();
	getContentPane().add(panelTitulo, BorderLayout.NORTH);
	panelTitulo.setLayout(new BorderLayout(0, 0));
	
	lblSubtitulo = new JLabel("                               ");
	panelTitulo.add(lblSubtitulo, BorderLayout.WEST);
	
	lblTitulo = new JLabel("JV-2016 Control de Acceso");
	lblTitulo.setFont(new Font("Dialog", Font.BOLD, 14));
	panelTitulo.add(lblTitulo, BorderLayout.CENTER);
	
	lblAyuda = new JLabel(" ? ");
	panelTitulo.add(lblAyuda, BorderLayout.EAST);
	
	panelTrabajo = new JPanel();
	getContentPane().add(panelTrabajo, BorderLayout.CENTER);
	
	panelTexto = new JPanel();
	panelTrabajo.add(panelTexto);
	
	panelEtiquetas = new JPanel();
	panelTexto.add(panelEtiquetas);
	panelEtiquetas.setLayout(new BorderLayout(0, 0));
	
	lblClaveDeAcceso = new JLabel("Clave de Acceso:");
	lblClaveDeAcceso.setHorizontalAlignment(SwingConstants.TRAILING);
	panelEtiquetas.add(lblClaveDeAcceso);
	
	lblUsuario = new JLabel("Usuario:");
	panelEtiquetas.add(lblUsuario, BorderLayout.NORTH);
	
	panelCampos = new JPanel();
	panelTexto.add(panelCampos);
	panelCampos.setLayout(new BorderLayout(0, 0));
	
	campoUsuario = new JTextField();
	panelCampos.add(campoUsuario, BorderLayout.NORTH);
	campoUsuario.setColumns(10);
	
	campoClaveAcceso = new JPasswordField();
	panelCampos.add(campoClaveAcceso, BorderLayout.SOUTH);
	
	panelBotones = new JPanel();
	panelTrabajo.add(panelBotones);
	panelBotones.setLayout(new BorderLayout(0, 0));
	
	botonOk = new JButton(OK);
	panelBotones.add(botonOk, BorderLayout.NORTH);
	
	botonCancelar = new JButton(CANCEL);
	panelBotones.add(botonCancelar, BorderLayout.SOUTH);	
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje,"JV-2016",JOptionPane.INFORMATION_MESSAGE);
		
	}


}
