
package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.OperationException;
import ar.edu.unrn.seminario.exception.PersistenceException;

public class AltaTurno extends JPanel {

    private static final long serialVersionUID = 1L;

    private IApi api;

    private List<PacienteDTO> pacientes;
    private List<PacienteDTO> pacientesFiltrados = new ArrayList<>();
    private Set<EspecialidadDTO> especialidades;
    private List<MedicoDTO> medicos;
    private List<MedicoDTO> medicosFiltrados = new ArrayList<>();

    private JTextField tfBuscarPaciente;
    private JComboBox<String> cbPaciente;
    private JComboBox<String> cbEspecialidad;
    private JComboBox<String> cbMedico;

    private JDateChooser calendario;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTurnos;

    private List<TurnoDTO> turnosFiltrados = new ArrayList<>();
    private TurnoDTO turnoSeleccionado;

    private DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    private boolean cargandoDatos;

    public AltaTurno(IApi api, JPanel panelContenedor, CardLayout cardLayout) {
        this.api = api;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblRegistrarTurno = new JLabel("Registrar Turno");
        lblRegistrarTurno.setHorizontalAlignment(SwingConstants.CENTER);
        lblRegistrarTurno.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        add(lblRegistrarTurno, BorderLayout.NORTH);

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel panelPaciente = new JPanel(new GridBagLayout());
        panelPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del paciente"));

        GridBagConstraints gbcPaciente = new GridBagConstraints();
        gbcPaciente.insets = new Insets(8, 8, 8, 8);
        gbcPaciente.fill = GridBagConstraints.HORIZONTAL;
        gbcPaciente.weightx = 1.0;

        JLabel lblBuscarPaciente = new JLabel("Buscar paciente (Nombre o Apellido):");
        gbcPaciente.gridx = 0;
        gbcPaciente.gridy = 0;
        panelPaciente.add(lblBuscarPaciente, gbcPaciente);

        tfBuscarPaciente = new JTextField();
        tfBuscarPaciente.setPreferredSize(new java.awt.Dimension(250, 30));

        tfBuscarPaciente.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarPacientes(tfBuscarPaciente.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarPacientes(tfBuscarPaciente.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarPacientes(tfBuscarPaciente.getText());
            }
        });

        gbcPaciente.gridy = 1;
        panelPaciente.add(tfBuscarPaciente, gbcPaciente);

        JLabel lblPaciente = new JLabel("Paciente:");
        gbcPaciente.gridy = 2;
        panelPaciente.add(lblPaciente, gbcPaciente);

        cbPaciente = new JComboBox<>();
        cbPaciente.setPreferredSize(new java.awt.Dimension(250, 30));
        gbcPaciente.gridy = 3;
        panelPaciente.add(cbPaciente, gbcPaciente);

        JPanel panelMedico = new JPanel(new GridBagLayout());
        panelMedico.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del turno"));

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
            if (cargandoDatos) {
                return;
            }

            int seleccionado = cbMedico.getSelectedIndex();
            turnoSeleccionado = null;
            modeloTurnos.setRowCount(0);
            turnosFiltrados.clear();

            if (seleccionado >= 0 && seleccionado < medicosFiltrados.size()) {
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
                turnoSeleccionado = null;
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

        modeloTurnos = new DefaultTableModel(new Object[][] {}, new String[] { "Horario" }) {
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

        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            int indiceMedico = cbMedico.getSelectedIndex();
            int indicePaciente = cbPaciente.getSelectedIndex();

            if (turnoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fecha y un horario.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            MedicoDTO medico = medicosFiltrados.get(indiceMedico);
            PacienteDTO paciente = pacientesFiltrados.get(indicePaciente);

            try {
                api.registrarTurno(medico, turnoSeleccionado, paciente);
                JOptionPane.showMessageDialog(null, "Turno registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(panelContenedor, "PANELDEFAULT");
            } catch (DataEmptyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (PersistenceException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de persistencia", JOptionPane.ERROR_MESSAGE);
            }
        });

        pBotones.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(panelContenedor, "PANELDEFAULT"));
        pBotones.add(btnCancelar);

        add(pBotones, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarDatosIniciales();
            }
        });
    }

    private void cargarDatosIniciales() {
        cargandoDatos = true;

        try {
            pacientes = api.obtenerPacientes();
            especialidades = api.obtenerEspecialidades();
            medicos = api.obtenerMedicos();

            Collections.sort(pacientes, Comparator.comparing(PacienteDTO::getApellido, String.CASE_INSENSITIVE_ORDER).thenComparing(PacienteDTO::getNombre, String.CASE_INSENSITIVE_ORDER));

            tfBuscarPaciente.setText("");
            filtrarPacientes("");

            cbEspecialidad.removeAllItems();

            for (EspecialidadDTO especialidad : especialidades) {
                cbEspecialidad.addItem(especialidad.getNombre());
            }

            if (cbEspecialidad.getSelectedItem() != null) {
                recargarMedicos(cbEspecialidad.getSelectedItem().toString());
            }

            calendario.setDate(null);
            modeloTurnos.setRowCount(0);
            turnosFiltrados.clear();
            turnoSeleccionado = null;

        } catch (PersistenceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de persistencia", JOptionPane.ERROR_MESSAGE);
        } finally {
            cargandoDatos = false;
        }
    }

    private void filtrarPacientes(String textoBusqueda) {
        String filtro = textoBusqueda == null ? "" : textoBusqueda.trim().toLowerCase();

        pacientesFiltrados = pacientes.stream().filter(p -> {
            String nombreCompleto = (p.getNombre() + " " + p.getApellido()).toLowerCase();
            String apellidoNombre = (p.getApellido() + " " + p.getNombre()).toLowerCase();
            return nombreCompleto.contains(filtro) || apellidoNombre.contains(filtro);
        }).collect(Collectors.toList());

        cbPaciente.removeAllItems();

        for (PacienteDTO paciente : pacientesFiltrados) {
            cbPaciente.addItem(paciente.getApellido() + ", " + paciente.getNombre());
        }
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
        LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            turnosFiltrados = api.obtenerHorariosDisponibles(medico.getMatricula(), fecha);

            for (TurnoDTO turno : turnosFiltrados) {
                modeloTurnos.addRow(new Object[] { turno.getFecha().format(formatoHora) });
            }
        } catch (OperationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de persistencia", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recargarMedicos(String especialidad) {
        List<MedicoDTO> medicosEspecialidad = medicos.stream().filter(m -> m.getEspecialidad().getNombre().equals(especialidad)).collect(Collectors.toList());

        medicosFiltrados = medicosEspecialidad;
        cbMedico.removeAllItems();

        for (MedicoDTO medico : medicosEspecialidad) {
            cbMedico.addItem(medico.getApellido() + ", " + medico.getNombre());
        }

        modeloTurnos.setRowCount(0);
        turnosFiltrados.clear();
        turnoSeleccionado = null;

        if (!medicosEspecialidad.isEmpty()) {
            cbMedico.setSelectedIndex(0);
        }
    }
}
