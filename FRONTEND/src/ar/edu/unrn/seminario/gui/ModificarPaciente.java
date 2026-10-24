package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ModificarPaciente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfDni;
	JComboBox<String> cbObraSocial;
	
	private List<ObraSocialDTO> obrasSociales = new ArrayList<>();
	/**
	 * Create the panel.
	 */
	public ModificarPaciente(IApi api, JPanel panelContenedor, CardLayout cardLayout) {
		
		this.obrasSociales = api.obtenerObrasSociales();
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(199, 167, 75, 25);
		add(lblNombre);
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(284, 171, 158, 20);
		add(tfNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApellido.setBounds(199, 203, 75, 25);
		add(lblApellido);
		
		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(284, 207, 158, 20);
		add(tfApellido);
		
		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDNI.setBounds(199, 239, 75, 25);
		add(lblDNI);
		
		tfDni = new JTextField();
		tfDni.setEditable(false);
		tfDni.setColumns(10);
		tfDni.setBounds(284, 243, 158, 20);
		add(tfDni);
		
		JLabel lblObraSocial = new JLabel("Obra social:");
		lblObraSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObraSocial.setBounds(199, 275, 75, 25);
		add(lblObraSocial);
		
		cbObraSocial = new JComboBox<String>();
		cbObraSocial.setBounds(284, 275, 158, 25);
		add(cbObraSocial);		
		
		for (ObraSocialDTO os : this.obrasSociales) {
			cbObraSocial.addItem(os.getNombre());
		}
		
		JButton btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObraSocialDTO oSocial = obrasSociales.get(cbObraSocial.getSelectedIndex());

				try {
					String nombre = tfNombre.getText().trim();
					String apellido = tfApellido.getText().trim();
					String dniTxt = tfDni.getText().trim();
					int dni;

					if (dniTxt.matches("^\\d{7,8}$")) {
						dni = Integer.parseInt(dniTxt);
					} else {
						throw new DniErroneoException(
								"El dni no tiene un formato correcto. Ingrese numeros sin puntos");
					}

					api.modificarPaciente(nombre, apellido, dni, oSocial.getCodigo());
					limpiarCampos();
					JOptionPane.showMessageDialog(null, "Paciente modificado con exito!", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					cardLayout.show(panelContenedor, "LISTADOPACIENTE");

				} catch (DataEmptyException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DniErroneoException dnie) {
					JOptionPane.showMessageDialog(null, dnie.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGuardarCambios.setBounds(221, 346, 134, 23);
		add(btnGuardarCambios);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				cardLayout.show(panelContenedor, "LISTADOPACIENTE");
			}
		});
		btnCancelar.setBounds(365, 346, 134, 23);
		add(btnCancelar);
		
		JLabel lblModificarPaciente = new JLabel("Modificar Paciente");
		lblModificarPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		lblModificarPaciente.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblModificarPaciente.setBounds(284, 74, 158, 12);
		add(lblModificarPaciente);

	}
	
	public void datoPacienteActual(PacienteDTO paciente) {
		tfNombre.setText(paciente.getNombre());
		tfApellido.setText(paciente.getApellido());
		tfDni.setText(String.valueOf(paciente.getDni()));
		cbObraSocial.setSelectedItem(paciente.getObraSocial());
	}
	
	private void limpiarCampos() {
		tfNombre.setText("");
		tfApellido.setText("");
		tfDni.setText("");
	}
}
