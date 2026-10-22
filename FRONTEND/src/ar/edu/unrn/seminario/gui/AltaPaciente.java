package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.RolDTO;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class AltaPaciente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	private List<ObraSocialDTO> obrasSociales = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public AltaPaciente(IApi api) {
		setLayout(null);
		
		this.obrasSociales = api.obtenerObrasSociales();
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(69, 36, 75, 25);
		add(lblNombre);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(154, 40, 158, 20);
		add(textField);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApellido.setBounds(69, 72, 75, 25);
		add(lblApellido);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(154, 76, 158, 20);
		add(textField_1);
		
		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDNI.setBounds(69, 108, 75, 25);
		add(lblDNI);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(154, 112, 158, 20);
		add(textField_2);
		
		JLabel lblObraSocial = new JLabel("Obra social:");
		lblObraSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObraSocial.setBounds(69, 144, 75, 25);
		add(lblObraSocial);
		
		JComboBox<String> comboObraSocial = new JComboBox<String>();
		comboObraSocial.setBounds(154, 144, 158, 25);
		add(comboObraSocial);
		
		for (ObraSocialDTO os : this.obrasSociales) {
			comboObraSocial.addItem(os.getNombre());
		}
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(178, 217, 89, 23);
		add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(277, 217, 89, 23);
		add(btnCancelar);

	}

}
