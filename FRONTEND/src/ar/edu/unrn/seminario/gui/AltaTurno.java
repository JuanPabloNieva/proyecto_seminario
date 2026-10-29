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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
					JOptionPane.showMessageDialog(null, "El turno seleccionado ya no está disponible.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
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
						GridBagLayout gbl_pForm = new GridBagLayout();
						gbl_pForm.columnWidths = new int[] {200, 200, 200};
						gbl_pForm.rowHeights = new int[] {52, 21, 43, 21, 46, 21, 39, 25, 0, 0};
						gbl_pForm.columnWeights = new double[]{0.0, 0.0};
						gbl_pForm.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						pForm.setLayout(gbl_pForm);
														
																JLabel lblPaciente = new JLabel("Paciente:");
																GridBagConstraints gbc_lblPaciente = new GridBagConstraints();
																gbc_lblPaciente.anchor = GridBagConstraints.WEST;
																gbc_lblPaciente.weightx = 1.0;
																gbc_lblPaciente.insets = new Insets(0, 0, 5, 5);
																gbc_lblPaciente.gridx = 1;
																gbc_lblPaciente.gridy = 1;
																pForm.add(lblPaciente, gbc_lblPaciente);
												
														cbPaciente = new JComboBox();
														GridBagConstraints gbc_cbPaciente = new GridBagConstraints();
														gbc_cbPaciente.weightx = 1.0;
														gbc_cbPaciente.fill = GridBagConstraints.HORIZONTAL;
														gbc_cbPaciente.insets = new Insets(0, 0, 5, 5);
														gbc_cbPaciente.gridx = 1;
														gbc_cbPaciente.gridy = 2;
														pForm.add(cbPaciente, gbc_cbPaciente);
										
												JLabel lblMedico = new JLabel("Especialidad:");
												GridBagConstraints gbc_lblMedico = new GridBagConstraints();
												gbc_lblMedico.weightx = 1.0;
												gbc_lblMedico.fill = GridBagConstraints.HORIZONTAL;
												gbc_lblMedico.insets = new Insets(0, 0, 5, 5);
												gbc_lblMedico.gridx = 1;
												gbc_lblMedico.gridy = 3;
												pForm.add(lblMedico, gbc_lblMedico);
										
												cbEspecialidad = new JComboBox();
												cbEspecialidad.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) {
														if (cbEspecialidad.getSelectedItem() != null) {
															String especialidad = cbEspecialidad.getSelectedItem().toString();
															recargarMedicos(especialidad);
														}
													}
												});
												GridBagConstraints gbc_cbEspecialidad = new GridBagConstraints();
												gbc_cbEspecialidad.weightx = 1.0;
												gbc_cbEspecialidad.fill = GridBagConstraints.HORIZONTAL;
												gbc_cbEspecialidad.insets = new Insets(0, 0, 5, 5);
												gbc_cbEspecialidad.gridx = 1;
												gbc_cbEspecialidad.gridy = 4;
												pForm.add(cbEspecialidad, gbc_cbEspecialidad);
								
										JLabel lblNewLabel_1_1 = new JLabel("Médico:");
										GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
										gbc_lblNewLabel_1_1.weightx = 1.0;
										gbc_lblNewLabel_1_1.fill = GridBagConstraints.HORIZONTAL;
										gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
										gbc_lblNewLabel_1_1.gridx = 1;
										gbc_lblNewLabel_1_1.gridy = 5;
										pForm.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
										
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
												GridBagConstraints gbc_cbMedico = new GridBagConstraints();
												gbc_cbMedico.weightx = 1.0;
												gbc_cbMedico.fill = GridBagConstraints.HORIZONTAL;
												gbc_cbMedico.insets = new Insets(0, 0, 5, 5);
												gbc_cbMedico.gridx = 1;
												gbc_cbMedico.gridy = 6;
												pForm.add(cbMedico, gbc_cbMedico);
								
										JLabel lblTurnosDisponibles = new JLabel("Turnos Disponibles:");
										GridBagConstraints gbc_lblTurnosDisponibles = new GridBagConstraints();
										gbc_lblTurnosDisponibles.weightx = 1.0;
										gbc_lblTurnosDisponibles.fill = GridBagConstraints.HORIZONTAL;
										gbc_lblTurnosDisponibles.insets = new Insets(0, 0, 5, 5);
										gbc_lblTurnosDisponibles.gridx = 1;
										gbc_lblTurnosDisponibles.gridy = 7;
										pForm.add(lblTurnosDisponibles, gbc_lblTurnosDisponibles);
								
										cbTurnosDisponibles = new JComboBox();
										GridBagConstraints gbc_cbTurnosDisponibles = new GridBagConstraints();
										gbc_cbTurnosDisponibles.weightx = 1.0;
										gbc_cbTurnosDisponibles.insets = new Insets(0, 0, 0, 5);
										gbc_cbTurnosDisponibles.fill = GridBagConstraints.HORIZONTAL;
										gbc_cbTurnosDisponibles.gridx = 1;
										gbc_cbTurnosDisponibles.gridy = 8;
										pForm.add(cbTurnosDisponibles, gbc_cbTurnosDisponibles);

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
