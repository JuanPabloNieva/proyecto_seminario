package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class ModificarTurno extends JPanel {

	private IApi api;
	private JComboBox cbPaciente;
	private JComboBox cbEspecialidad;
	private JComboBox cbMedico;
	private JComboBox cbTurnosDisponibles;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ModificarTurno(IApi api) {
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
		cbPaciente.setBounds(330, 99, 180, 25);
		pForm.add(cbPaciente);

		JLabel lblMedico = new JLabel("Especialidad:");
		lblMedico.setBounds(223, 162, 85, 25);
		pForm.add(lblMedico);

		cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(330, 162, 180, 25);
		pForm.add(cbEspecialidad);

		JLabel lblNewLabel_1_1 = new JLabel("Médico:");
		lblNewLabel_1_1.setBounds(223, 228, 85, 25);
		pForm.add(lblNewLabel_1_1);

		cbMedico = new JComboBox();
		cbMedico.setBounds(330, 228, 180, 25);
		pForm.add(cbMedico);

		JLabel lblTurnosDisponibles = new JLabel("Turnos Disponibles:");
		lblTurnosDisponibles.setBounds(223, 287, 97, 25);
		pForm.add(lblTurnosDisponibles);

		cbTurnosDisponibles = new JComboBox();
		cbTurnosDisponibles.setEditable(true);
		cbTurnosDisponibles.setBounds(330, 287, 180, 25);
		pForm.add(cbTurnosDisponibles);
	}

	public void datoTurnoActual(TurnoDTO turno) {
		cbPaciente.setSelectedIndex(turno.getPaciente());
		MedicoDTO medico = this.api.obtenerMedico(turno.getMatriculaMedico());

		cbEspecialidad.setSelectedItem(medico.getEspecialidad().getNombre());
		cbMedico.setSelectedItem(medico.getApellido() + ", " + medico.getNombre());
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
