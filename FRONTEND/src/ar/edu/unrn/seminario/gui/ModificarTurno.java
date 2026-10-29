package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ModificarTurno extends JPanel {

	private IApi api;
	private JComboBox cbPaciente;
	private JComboBox cbEspecialidad;
	private JComboBox cbMedico;
	private JComboBox cbTurnosDisponibles;
	private TurnoDTO turno;

	private static final long serialVersionUID = 1L;
	private JTextField tfIdTurno;

	/**
	 * Create the panel.
	 */
	public ModificarTurno(IApi api, JPanel panelContenedor, CardLayout cardLayout) {
		this.api = api;
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JLabel lblCambiarTurno = new JLabel("Cambiar Turno");
		lblCambiarTurno.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiarTurno.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panel.add(lblCambiarTurno);

		JPanel pBotones = new JPanel();
		add(pBotones, BorderLayout.SOUTH);
		pBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceTurno = cbTurnosDisponibles.getSelectedIndex();
				MedicoDTO medico = api.obtenerMedico(turno.getMatriculaMedico());

				List<TurnoDTO> turnosActivos = medico.getTurnosDisponibles().stream().filter(TurnoDTO::isActivo)
						.collect(Collectors.toList());

				if (indiceTurno >= turnosActivos.size()) {
					JOptionPane.showMessageDialog(null, "El médico no tiene mas turnos disponibles por el momento",
							"Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				List<TurnoDTO> turnosAsignados = api.obtenerTurnosAsignados();

				TurnoDTO turnoNuevo = turnosActivos.get(indiceTurno);

				PacienteDTO paciente = api.obtenerPacientePorDni(turno.getPaciente());

				try {
					api.registrarTurno(medico, turnoNuevo, paciente);
					api.liberarTurno(turno.getIdTurno());
					JOptionPane.showMessageDialog(null, "Turno modificado con éxito.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					cardLayout.show(panelContenedor, "PANELDEFAULT");
				} catch (DataEmptyException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pBotones.add(btnConfirmar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContenedor, "LISTADOTURNO");
			}
		});
		pBotones.add(btnCancelar);

		JPanel pForm = new JPanel();
		add(pForm, BorderLayout.CENTER);
		GridBagLayout gbl_pForm = new GridBagLayout();
		gbl_pForm.columnWidths = new int[]{223, 97, 180, 0};
		gbl_pForm.rowHeights = new int[]{52, 27, 0, 25, 38, 25, 41, 25, 34, 25, 0, 0};
		gbl_pForm.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pForm.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pForm.setLayout(gbl_pForm);
		
				JLabel lblIdTurno = new JLabel("ID Turno:");
				GridBagConstraints gbc_lblIdTurno = new GridBagConstraints();
				gbc_lblIdTurno.weightx = 1.0;
				gbc_lblIdTurno.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblIdTurno.insets = new Insets(0, 0, 5, 5);
				gbc_lblIdTurno.gridx = 1;
				gbc_lblIdTurno.gridy = 1;
				pForm.add(lblIdTurno, gbc_lblIdTurno);
						
								tfIdTurno = new JTextField();
								tfIdTurno.setEditable(false);
								tfIdTurno.setHorizontalAlignment(SwingConstants.CENTER);
								GridBagConstraints gbc_tfIdTurno = new GridBagConstraints();
								gbc_tfIdTurno.weightx = 1.0;
								gbc_tfIdTurno.fill = GridBagConstraints.HORIZONTAL;
								gbc_tfIdTurno.insets = new Insets(0, 0, 5, 5);
								gbc_tfIdTurno.gridx = 1;
								gbc_tfIdTurno.gridy = 2;
								pForm.add(tfIdTurno, gbc_tfIdTurno);
								tfIdTurno.setColumns(10);
				
						JLabel lblPaciente = new JLabel("Paciente:");
						GridBagConstraints gbc_lblPaciente = new GridBagConstraints();
						gbc_lblPaciente.weightx = 1.0;
						gbc_lblPaciente.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblPaciente.insets = new Insets(0, 0, 5, 5);
						gbc_lblPaciente.gridx = 1;
						gbc_lblPaciente.gridy = 3;
						pForm.add(lblPaciente, gbc_lblPaciente);
								
										cbPaciente = new JComboBox();
										cbPaciente.setEditable(true);
										GridBagConstraints gbc_cbPaciente = new GridBagConstraints();
										gbc_cbPaciente.weightx = 1.0;
										gbc_cbPaciente.fill = GridBagConstraints.HORIZONTAL;
										gbc_cbPaciente.insets = new Insets(0, 0, 5, 5);
										gbc_cbPaciente.gridx = 1;
										gbc_cbPaciente.gridy = 4;
										pForm.add(cbPaciente, gbc_cbPaciente);
						
								JLabel lblMedico = new JLabel("Especialidad:");
								GridBagConstraints gbc_lblMedico = new GridBagConstraints();
								gbc_lblMedico.fill = GridBagConstraints.BOTH;
								gbc_lblMedico.insets = new Insets(0, 0, 5, 5);
								gbc_lblMedico.gridx = 1;
								gbc_lblMedico.gridy = 5;
								pForm.add(lblMedico, gbc_lblMedico);
						
								cbEspecialidad = new JComboBox();
								cbEspecialidad.setEditable(true);
								GridBagConstraints gbc_cbEspecialidad = new GridBagConstraints();
								gbc_cbEspecialidad.fill = GridBagConstraints.HORIZONTAL;
								gbc_cbEspecialidad.insets = new Insets(0, 0, 5, 5);
								gbc_cbEspecialidad.gridx = 1;
								gbc_cbEspecialidad.gridy = 6;
								pForm.add(cbEspecialidad, gbc_cbEspecialidad);
				
						JLabel lblNewLabel_1_1 = new JLabel("Médico:");
						GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
						gbc_lblNewLabel_1_1.weightx = 1.0;
						gbc_lblNewLabel_1_1.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_1_1.gridx = 1;
						gbc_lblNewLabel_1_1.gridy = 7;
						pForm.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
				
						cbMedico = new JComboBox();
						cbMedico.setEditable(true);
						GridBagConstraints gbc_cbMedico = new GridBagConstraints();
						gbc_cbMedico.weightx = 1.0;
						gbc_cbMedico.fill = GridBagConstraints.HORIZONTAL;
						gbc_cbMedico.insets = new Insets(0, 0, 5, 5);
						gbc_cbMedico.gridx = 1;
						gbc_cbMedico.gridy = 8;
						pForm.add(cbMedico, gbc_cbMedico);
		
				JLabel lblTurnosDisponibles = new JLabel("Turnos Disponibles:");
				GridBagConstraints gbc_lblTurnosDisponibles = new GridBagConstraints();
				gbc_lblTurnosDisponibles.fill = GridBagConstraints.BOTH;
				gbc_lblTurnosDisponibles.insets = new Insets(0, 0, 5, 5);
				gbc_lblTurnosDisponibles.gridx = 1;
				gbc_lblTurnosDisponibles.gridy = 9;
				pForm.add(lblTurnosDisponibles, gbc_lblTurnosDisponibles);
				
						cbTurnosDisponibles = new JComboBox();
						GridBagConstraints gbc_cbTurnosDisponibles = new GridBagConstraints();
						gbc_cbTurnosDisponibles.weightx = 1.0;
						gbc_cbTurnosDisponibles.insets = new Insets(0, 0, 0, 5);
						gbc_cbTurnosDisponibles.fill = GridBagConstraints.HORIZONTAL;
						gbc_cbTurnosDisponibles.gridx = 1;
						gbc_cbTurnosDisponibles.gridy = 10;
						pForm.add(cbTurnosDisponibles, gbc_cbTurnosDisponibles);
	}

	public void datoTurnoActual(TurnoDTO turno) {

		this.turno = turno;

		tfIdTurno.setText(String.valueOf(turno.getIdTurno()));
		cbPaciente.setSelectedItem(turno.getPaciente());
		MedicoDTO medico = this.api.obtenerMedico(turno.getMatriculaMedico());

		cbEspecialidad.setSelectedItem(medico.getEspecialidad().getNombre());
		cbMedico.setSelectedItem(medico.getApellido() + ", " + medico.getNombre());

		tfIdTurno.setEditable(false);
		cbPaciente.setEditable(false);
		cbEspecialidad.setEditable(false);
		cbMedico.setEditable(false);
		cargarTurnos(medico);
	}

	private void cargarTurnos(MedicoDTO medico) {
		cbTurnosDisponibles.removeAllItems();

		for (TurnoDTO t : medico.getTurnosDisponibles()) {

			if (t.isActivo()) {
				cbTurnosDisponibles.addItem(t.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
			}
		}
	}
}
