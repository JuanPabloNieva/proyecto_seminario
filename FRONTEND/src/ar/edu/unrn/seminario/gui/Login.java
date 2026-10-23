package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JLabel lblContrasena;
	private JPasswordField pfContrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Sistema Salud");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("SISTEMA SALUD - LOGIN");
		lblLogo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(87, 11, 253, 60);
		contentPane.add(lblLogo);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblUsuario.setBounds(136, 77, 87, 14);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();

		txtUsuario.setForeground(Color.LIGHT_GRAY);
		txtUsuario.setBounds(136, 91, 174, 33);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		lblContrasena = new JLabel("Contraseña");
		lblContrasena.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblContrasena.setBounds(136, 133, 87, 14);
		contentPane.add(lblContrasena);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnIniciarSesion.setBounds(136, 191, 174, 33);
		contentPane.add(btnIniciarSesion);
		
		pfContrasena = new JPasswordField();
		pfContrasena.setBounds(136, 147, 174, 33);
		contentPane.add(pfContrasena);

	}
}
