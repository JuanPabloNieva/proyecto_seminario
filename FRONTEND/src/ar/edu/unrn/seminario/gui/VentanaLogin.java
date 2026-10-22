package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField pfContrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new MemoryApi();
					VentanaLogin frame = new VentanaLogin(api);
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
	public VentanaLogin(IApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SISTEMA SALUD");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(121, 25, 145, 32);
		contentPane.add(lblNewLabel);
		
		tfUsuario = new JTextField();
		tfUsuario.setBounds(121, 83, 145, 25);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(121, 67, 44, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Contraseña");
		lblNewLabel_1_1.setBounds(121, 120, 83, 12);
		contentPane.add(lblNewLabel_1_1);
		
		pfContrasena = new JPasswordField();
		pfContrasena.setBounds(121, 142, 145, 25);
		contentPane.add(pfContrasena);
		
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (api.autenticar(tfUsuario.getText(), pfContrasena.getText())) {
					try {

						IApi api = new MemoryApi();
						VentanaPrincipal frame = new VentanaPrincipal(api);
						frame.setVisible(true);
						dispose();
					} catch (Exception ex) {
						ex.printStackTrace();
					} 
				}
			}
		});
		btnLogin.setBounds(121, 177, 145, 32);
		contentPane.add(btnLogin);

	}
}
