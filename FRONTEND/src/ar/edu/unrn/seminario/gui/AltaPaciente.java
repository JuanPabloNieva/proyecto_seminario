package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AltaPaciente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfDni;
	
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
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(154, 40, 158, 20);
		add(tfNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApellido.setBounds(69, 72, 75, 25);
		add(lblApellido);
		
		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(154, 76, 158, 20);
		add(tfApellido);
		
		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDNI.setBounds(69, 108, 75, 25);
		add(lblDNI);
		
		tfDni = new JTextField();
		tfDni.setColumns(10);
		tfDni.setBounds(154, 112, 158, 20);
		add(tfDni);
		
		JLabel lblObraSocial = new JLabel("Obra social:");
		lblObraSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObraSocial.setBounds(69, 144, 75, 25);
		add(lblObraSocial);
		
		JComboBox<String> cbObraSocial = new JComboBox<String>();
		cbObraSocial.setBounds(154, 144, 158, 25);
		add(cbObraSocial);
		
		for (ObraSocialDTO os : this.obrasSociales) {
			cbObraSocial.addItem(os.getNombre());
		}
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObraSocialDTO oSocial = obrasSociales.get(cbObraSocial.getSelectedIndex());

				try {
					api.registrarPaciente(tfNombre.getText(), tfApellido.getText(), Integer.parseInt(tfDni.getText()), oSocial.getCodigo());
					JOptionPane.showMessageDialog(null, "Paciente registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
				} catch (DataEmptyException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(178, 217, 89, 23);
		add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(277, 217, 89, 23);
		add(btnCancelar);

	}

}
