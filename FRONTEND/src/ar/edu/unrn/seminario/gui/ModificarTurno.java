
package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;

public class ModificarTurno extends JPanel {

    private static final long serialVersionUID = 1L;

    private IApi api;

    private TurnoDTO turno;

    private Set<EspecialidadDTO> especialidades;
    private List<MedicoDTO> medicos;
    private List<MedicoDTO> medicosFiltrados = new ArrayList<>();

    private JComboBox<String> cbPaciente;
    private JComboBox<String> cbEspecialidad;
    private JComboBox<String> cbMedico;

    private JLabel lblIdTurno;

    private JDateChooser calendario;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTurnos;

    private List<TurnoDTO> turnosFiltrados = new ArrayList<>();
    private TurnoDTO turnoSeleccionado;

    private DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    private boolean cargandoDatos = false;

    public ModificarTurno(IApi api, JPanel panelContenedor, CardLayout cardLayout) {

        this.api = api;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Modificar Turno");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        
        JPanel panelPaciente = new JPanel(new GridBagLayout());
        panelPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del turno actual"));

        GridBagConstraints gbcPaciente = new GridBagConstraints();
        gbcPaciente.insets = new Insets(8, 8, 8, 8);
        gbcPaciente.fill = GridBagConstraints.HORIZONTAL;
        gbcPaciente.weightx = 1.0;

        JLabel lblId = new JLabel("ID del turno:");
        gbcPaciente.gridx = 0;
        gbcPaciente.gridy = 0;
        panelPaciente.add(lblId, gbcPaciente);

        lblIdTurno = new JLabel("-");
        gbcPaciente.gridy = 1;
        panelPaciente.add(lblIdTurno, gbcPaciente);

        JLabel lblPaciente = new JLabel("Paciente:");
        gbcPaciente.gridy = 2;
        panelPaciente.add(lblPaciente, gbcPaciente);

        cbPaciente = new JComboBox<>();
        cbPaciente.setEnabled(false);
        cbPaciente.setPreferredSize(new java.awt.Dimension(250, 30));
        gbcPaciente.gridy = 3;
        panelPaciente.add(cbPaciente, gbcPaciente);

        
        JPanel panelMedico = new JPanel(new GridBagLayout());
        panelMedico.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo turno"));

        GridBagConstraints gbcMedico = new GridBagConstraints();
        gbcMedico.insets = new Insets(8, 8, 8, 8);
        gbcMedico.fill = GridBagConstraints.HORIZONTAL;
        gbcMedico.weightx = 1.0;

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        gbcMedico.gridx = 0;
        gbcMedico.gridy = 0;
        panelMedico.add(lblEspecialidad, gbcMedico);

        cbEspecialidad = new JComboBox<>();
        cbEspecialidad.setPreferredSize(new java.awt.Dimension(250, 30));

        cbEspecialidad.addActionListener(e -> {
            if (!cargandoDatos && cbEspecialidad.getSelectedItem() != null) {
                recargarMedicos(cbEspecialidad.getSelectedItem().toString());
            }
        });

        gbcMedico.gridy = 1;
        panelMedico.add(cbEspecialidad, gbcMedico);

        JLabel lblMedico = new JLabel("Médico:");
        gbcMedico.gridy = 2;
        panelMedico.add(lblMedico, gbcMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setPreferredSize(new java.awt.Dimension(250, 30));

        cbMedico.addActionListener(e -> {
            if (!cargandoDatos) {
                actualizarTablaTurnos();
            }
        });

        gbcMedico.gridy = 3;
        panelMedico.add(cbMedico, gbcMedico);

        JLabel lblFecha = new JLabel("Fecha del turno:");
        gbcMedico.gridy = 4;
        panelMedico.add(lblFecha, gbcMedico);

        calendario = new JDateChooser();
        calendario.setDateFormatString("dd/MM/yyyy");
        calendario.setMinSelectableDate(new Date());

        calendario.addPropertyChangeListener("date", e -> {
            if (!cargandoDatos) {
                actualizarTablaTurnos();
            }
        });

        gbcMedico.gridy = 5;
        panelMedico.add(calendario, gbcMedico);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        pForm.add(panelPaciente, gbc);

        gbc.gridx = 1;
        pForm.add(panelMedico, gbc);

        
        JPanel panelHorarios = new JPanel(new BorderLayout(5, 5));
        panelHorarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Horarios disponibles"));

        modeloTurnos = new DefaultTableModel(new Object[][] {}, new String[] { "Horario", "Disponibilidad" }) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTurnos = new JTable(modeloTurnos);
        tablaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTurnos.setRowHeight(25);

        tablaTurnos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaTurnos.getSelectedRow();

                if (fila >= 0 && fila < turnosFiltrados.size()) {
                    turnoSeleccionado = turnosFiltrados.get(fila);
                } else {
                    turnoSeleccionado = null;
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaTurnos);
        scrollTabla.setPreferredSize(new java.awt.Dimension(500, 180));
        panelHorarios.add(scrollTabla, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(12, 12, 8, 12);
        pForm.add(panelHorarios, gbc);

        // Botones
        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> confirmarModificacion(cardLayout, panelContenedor));
        pBotones.add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(panelContenedor, "LISTADOTURNO"));
        pBotones.add(btnCancelar);

        add(pBotones, BorderLayout.SOUTH);
    }

    public void datoTurnoActual(TurnoDTO turno) {

        this.turno = turno;

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {

        if (turno == null) {
            return;
        }

        cargandoDatos = true;

        especialidades = api.obtenerEspecialidades();
        medicos = api.obtenerMedicos();

        if (especialidades == null) {
            especialidades = new java.util.HashSet<>();
        }

        if (medicos == null) {
            medicos = new ArrayList<>();
        }

        lblIdTurno.setText(String.valueOf(turno.getIdTurno()));

        cbPaciente.removeAllItems();

        PacienteDTO paciente = api.obtenerPacientePorDni(turno.getPaciente());

        if (paciente != null) {
            cbPaciente.addItem(paciente.getNombre() + " " + paciente.getApellido());
        }

        cbEspecialidad.removeAllItems();

        for (EspecialidadDTO especialidad : especialidades) {
            cbEspecialidad.addItem(especialidad.getNombre());
        }

        MedicoDTO medicoActual = api.obtenerMedico(turno.getMatriculaMedico());

        if (medicoActual != null) {
            cbEspecialidad.setSelectedItem(medicoActual.getEspecialidad().getNombre());
        }

       
        Date fechaOriginal = Date.from(turno.getFecha().atZone(ZoneId.systemDefault()).toInstant());

        calendario.setDate(fechaOriginal);

        cargandoDatos = false;

        if (medicoActual != null) {
            recargarMedicos(medicoActual.getMatricula());
        }
    }

    private void recargarMedicos(String matriculaSeleccionada) {

        cargandoDatos = true;

        cbMedico.removeAllItems();

        modeloTurnos.setRowCount(0);
        turnosFiltrados.clear();
        turnoSeleccionado = null;

        String especialidad = (String) cbEspecialidad.getSelectedItem();

        if (especialidad == null) {
            cargandoDatos = false;
            return;
        }

        medicosFiltrados = medicos.stream()
                .filter(m -> m.getEspecialidad().getNombre().equals(especialidad))
                .collect(Collectors.toList());

        for (MedicoDTO medico : medicosFiltrados) {
            cbMedico.addItem(medico.getApellido() + ", " + medico.getNombre());
        }

        if (matriculaSeleccionada != null) {
            for (int i = 0; i < medicosFiltrados.size(); i++) {
                if (medicosFiltrados.get(i).getMatricula().equals(matriculaSeleccionada)) {
                    cbMedico.setSelectedIndex(i);
                    break;
                }
            }
        }

        cargandoDatos = false;

        actualizarTablaTurnos();
    }

    private void actualizarTablaTurnos() {

        modeloTurnos.setRowCount(0);
        turnosFiltrados.clear();
        turnoSeleccionado = null;

        int indiceMedico = cbMedico.getSelectedIndex();
        Date fechaSeleccionada = calendario.getDate();

        if (indiceMedico < 0 || indiceMedico >= medicosFiltrados.size() || fechaSeleccionada == null) {
            return;
        }

        MedicoDTO medico = medicosFiltrados.get(indiceMedico);

        LocalDate fecha = fechaSeleccionada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDateTime ahora = LocalDateTime.now();

        for (TurnoDTO t : medico.getTurnosDisponibles()) {

            if (t.isActivo()
                    && t.getFecha().toLocalDate().equals(fecha)
                    && t.getFecha().isAfter(ahora)) {

                turnosFiltrados.add(t);

                modeloTurnos.addRow(new Object[] {
                        t.getFecha().format(formatoHora),
                        "Disponible"
                });
            }
        }
    }

    private void confirmarModificacion(CardLayout cardLayout, JPanel panelContenedor) {

        int indiceMedico = cbMedico.getSelectedIndex();

        if (indiceMedico < 0 || turnoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un médico y un horario disponible.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (turnoSeleccionado.getIdTurno() == turno.getIdTurno()) {
            JOptionPane.showMessageDialog(null, "Seleccione un horario diferente al turno actual.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MedicoDTO medicoNuevo = medicosFiltrados.get(indiceMedico);

        PacienteDTO paciente = api.obtenerPacientePorDni(turno.getPaciente());

        try {

            api.registrarTurno(medicoNuevo, turnoSeleccionado, paciente);

            api.liberarTurno(turno.getIdTurno());

            JOptionPane.showMessageDialog(null, "Turno modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cardLayout.show(panelContenedor, "LISTADOTURNO");

        } catch (DataEmptyException | IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
