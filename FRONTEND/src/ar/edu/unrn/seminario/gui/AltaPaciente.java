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
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AltaPaciente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfDni;
	private JComboBox<String> cbObraSocial;

	private List<ObraSocialDTO> obrasSociales = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public AltaPaciente(IApi api, JPanel panelContenedor, CardLayout cardLayout) {
		setLayout(null);

		this.obrasSociales = api.obtenerObrasSociales();

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(213, 156, 75, 25);
		add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(298, 160, 158, 20);
		add(tfNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApellido.setBounds(213, 192, 75, 25);
		add(lblApellido);

		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(298, 196, 158, 20);
		add(tfApellido);

		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDNI.setBounds(213, 228, 75, 25);
		add(lblDNI);

		tfDni = new JTextField();
		tfDni.setColumns(10);
		tfDni.setBounds(298, 232, 158, 20);
		add(tfDni);

		JLabel lblObraSocial = new JLabel("Obra social:");
		lblObraSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObraSocial.setBounds(213, 264, 75, 25);
		add(lblObraSocial);

		cbObraSocial = new JComboBox<String>();
		cbObraSocial.setBounds(298, 264, 158, 25);
		add(cbObraSocial);

		for (ObraSocialDTO os : this.obrasSociales) {
			cbObraSocial.addItem(os.getNombre());
		}

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
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

					api.registrarPaciente(nombre, apellido, dni, oSocial.getCodigo());
					limpiarCampos();
					JOptionPane.showMessageDialog(null, "Paciente registrado con exito!", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					cardLayout.show(panelContenedor, "PANELDEFAULT");

				} catch (DataEmptyException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DniErroneoException dnie) {
					JOptionPane.showMessageDialog(null, dnie.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRegistrar.setBounds(322, 337, 89, 23);
		add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				cardLayout.show(panelContenedor, "PANELDEFAULT");
			}
		});
		btnCancelar.setBounds(421, 337, 89, 23);
		add(btnCancelar);
		
		JLabel lblRegistrarPaciente = new JLabel("Registrar Paciente");
		lblRegistrarPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarPaciente.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblRegistrarPaciente.setBounds(213, 50, 297, 39);
		add(lblRegistrarPaciente);

	}

	private void limpiarCampos() {
		tfNombre.setText("");
		tfApellido.setText("");
		tfDni.setText("");
	}
}
