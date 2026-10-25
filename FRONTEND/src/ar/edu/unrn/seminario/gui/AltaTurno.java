package ar.edu.unrn.seminario.gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;

public class AltaTurno extends JPanel {

	private static final long serialVersionUID = 1L;
	private IApi api;
	private List<PacienteDTO> pacientes;
	private Set<EspecialidadDTO> especialidades;
	private List<MedicoDTO> medicos;

	private List<MedicoDTO> medicosFiltrados = new ArrayList<>();

	private JComboBox cbPaciente;
	private JComboBox cbEspecialidad;
	private JComboBox cbMedico;
	private JComboBox cbTurnosDisponibles;

	/**
	 * Create the panel.
	 */
	public AltaTurno(IApi api, JPanel panelContenedor, CardLayout cardLayout) {

		this.api = api;
		pacientes = api.obtenerPacientes();
		especialidades = api.obtenerEspecialidades();
		medicos = api.obtenerMedicos();

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(25);
		add(panel, BorderLayout.NORTH);

		JLabel lblRegistrarTurno = new JLabel("Registrar Turno");
		lblRegistrarTurno.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarTurno.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panel.add(lblRegistrarTurno);

		JPanel pBotones = new JPanel();
		add(pBotones, BorderLayout.SOUTH);
		pBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceMedico = cbMedico.getSelectedIndex();
				int indiceTurno = cbTurnosDisponibles.getSelectedIndex();
				int indicePaciente = cbPaciente.getSelectedIndex();

				if (indiceMedico < 0 || indiceTurno < 0 || indicePaciente < 0) {
					JOptionPane.showMessageDialog(null,
							"Por favor, seleccione un médico, un turno y un paciente de las listas.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				MedicoDTO medico = medicosFiltrados.get(indiceMedico);
				List<TurnoDTO> turnosActivos = medico.getTurnosDisponibles().stream().filter(TurnoDTO::isActivo)
						.collect(Collectors.toList());
				
				if (indiceTurno >= turnosActivos.size()) {
		            JOptionPane.showMessageDialog(null, "El turno seleccionado ya no está disponible.", "Aviso", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				
				TurnoDTO turno = turnosActivos.get(indiceTurno);
				PacienteDTO paciente = pacientes.get(indicePaciente);

				try {
					api.registrarTurno(medico, turno, paciente);
					JOptionPane.showMessageDialog(null, "Turno registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					cardLayout.show(panelContenedor, "PANELDEFAULT");
				} catch (DataEmptyException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pBotones.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContenedor, "PANELDEFAULT");
			}
		});
		pBotones.add(btnCancelar);

		JPanel pForm = new JPanel();
		add(pForm, BorderLayout.CENTER);
		pForm.setLayout(null);

		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setBounds(211, 94, 85, 25);
		pForm.add(lblPaciente);

		cbPaciente = new JComboBox();
		cbPaciente.setBounds(318, 94, 180, 25);
		pForm.add(cbPaciente);

		JLabel lblMedico = new JLabel("Especialidad:");
		lblMedico.setBounds(211, 157, 85, 25);
		pForm.add(lblMedico);

		cbEspecialidad = new JComboBox();
		cbEspecialidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbEspecialidad.getSelectedItem() != null) {
					String especialidad = cbEspecialidad.getSelectedItem().toString();
					recargarMedicos(especialidad);
				}
			}
		});
		cbEspecialidad.setBounds(318, 157, 180, 25);
		pForm.add(cbEspecialidad);

		JLabel lblNewLabel_1_1 = new JLabel("Médico:");
		lblNewLabel_1_1.setBounds(211, 223, 85, 25);
		pForm.add(lblNewLabel_1_1);

		cbMedico = new JComboBox();
		cbMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int seleccionado = cbMedico.getSelectedIndex();
				if (seleccionado >= 0 && medicosFiltrados != null && seleccionado < medicosFiltrados.size()) {
					MedicoDTO medico = medicosFiltrados.get(cbMedico.getSelectedIndex());
					cargarTurnos(medico);
				} else {
					cbTurnosDisponibles.removeAllItems();
				}
			}
		});
		cbMedico.setBounds(318, 223, 180, 25);
		pForm.add(cbMedico);

		JLabel lblTurnosDisponibles = new JLabel("Turnos Disponibles:");
		lblTurnosDisponibles.setBounds(211, 282, 97, 25);
		pForm.add(lblTurnosDisponibles);

		cbTurnosDisponibles = new JComboBox();
		cbTurnosDisponibles.setBounds(318, 282, 180, 25);
		pForm.add(cbTurnosDisponibles);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {

				cargarDatosIniciales();
			}
		});
	}
	
	private void cargarDatosIniciales() {
		this.pacientes = api.obtenerPacientes();
		this.especialidades = api.obtenerEspecialidades();
		this.medicos = api.obtenerMedicos();

		cbPaciente.removeAllItems();
		for (PacienteDTO paciente : pacientes) {
			cbPaciente.addItem(paciente.getNombre() + " " + paciente.getApellido()); 
		}

		cbEspecialidad.removeAllItems();
		for (EspecialidadDTO especialidad : this.especialidades) {
			cbEspecialidad.addItem(especialidad.getNombre());
		}

	}
	
	private void cargarTurnos(MedicoDTO medico) {
		cbTurnosDisponibles.removeAllItems();
		
		for (TurnoDTO t : medico.getTurnosDisponibles()) {
			
			if (t.isActivo()) {
				cbTurnosDisponibles.addItem(t.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
			}
		}
	}

	private void recargarMedicos(String especialidad) {
		List<MedicoDTO> medicosEspecialidad = medicos.stream()
				.filter(m -> m.getEspecialidad().getNombre().equals(especialidad)).collect(Collectors.toList());
		
		this.medicosFiltrados = medicosEspecialidad;
		
		cbMedico.removeAllItems();
		for (MedicoDTO medico : medicosEspecialidad) {
			cbMedico.addItem(medico.getApellido() + ", " + medico.getNombre());
		}
		
		if (!medicosEspecialidad.isEmpty()) {
	        cbMedico.setSelectedIndex(0); 
	        cargarTurnos(medicosEspecialidad.get(0));
	    } else {
	        cbTurnosDisponibles.removeAllItems();
	    }
	}

}
