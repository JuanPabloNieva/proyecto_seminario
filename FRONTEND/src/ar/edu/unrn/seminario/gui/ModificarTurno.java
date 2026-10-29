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
		pBotones.add(btnCancelar);

		JPanel pForm = new JPanel();
		pForm.setLayout(null);
		add(pForm, BorderLayout.CENTER);

		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setBounds(223, 99, 85, 25);
		pForm.add(lblPaciente);

		cbPaciente = new JComboBox();
		cbPaciente.setEditable(true);
		cbPaciente.setBounds(330, 99, 180, 25);
		pForm.add(cbPaciente);

		JLabel lblMedico = new JLabel("Especialidad:");
		lblMedico.setBounds(223, 162, 85, 25);
		pForm.add(lblMedico);

		cbEspecialidad = new JComboBox();
		cbEspecialidad.setEditable(true);
		cbEspecialidad.setBounds(330, 162, 180, 25);
		pForm.add(cbEspecialidad);

		JLabel lblNewLabel_1_1 = new JLabel("Médico:");
		lblNewLabel_1_1.setBounds(223, 228, 85, 25);
		pForm.add(lblNewLabel_1_1);

		cbMedico = new JComboBox();
		cbMedico.setEditable(true);
		cbMedico.setBounds(330, 228, 180, 25);
		pForm.add(cbMedico);

		JLabel lblTurnosDisponibles = new JLabel("Turnos Disponibles:");
		lblTurnosDisponibles.setBounds(223, 287, 97, 25);
		pForm.add(lblTurnosDisponibles);

		cbTurnosDisponibles = new JComboBox();
		cbTurnosDisponibles.setEditable(true);
		cbTurnosDisponibles.setBounds(330, 287, 180, 25);
		pForm.add(cbTurnosDisponibles);

		JLabel lblIdTurno = new JLabel("ID Turno:");
		lblIdTurno.setBounds(223, 52, 85, 25);
		pForm.add(lblIdTurno);

		tfIdTurno = new JTextField();
		tfIdTurno.setHorizontalAlignment(SwingConstants.CENTER);
		tfIdTurno.setBounds(330, 54, 180, 25);
		pForm.add(tfIdTurno);
		tfIdTurno.setColumns(10);
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
