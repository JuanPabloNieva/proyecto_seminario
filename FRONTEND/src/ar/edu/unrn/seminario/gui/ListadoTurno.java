package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListadoTurno extends JPanel {

	private static final long serialVersionUID = 1L;
	DefaultTableModel modelo;
	private IApi api;
	private TurnoDTO turno;

	JButton btnModificarTurno;
	JButton btnCancelarTurno;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ListadoTurno(IApi api, VentanaRecepcionista frame) {
		this.api = api;
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				habilitarBotones(true);
			}
		});
		scrollPane.setViewportView(table);
		modelo = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Fecha", "Paciente", "DNI", "Médico" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.setModel(modelo);

		JLabel lblTitulo = new JLabel("Listado de turnos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, BorderLayout.NORTH);

		JPanel pnlBotones = new JPanel();
		add(pnlBotones, BorderLayout.SOUTH);

		btnModificarTurno = new JButton("Modificar Turno");
		btnModificarTurno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idTurno = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
				turno = api.obtenerTurno(idTurno);
				frame.pasarTurnoAModificar(turno);
			}
		});
		pnlBotones.add(btnModificarTurno);

		btnCancelarTurno = new JButton("Cancelar Turno");
		pnlBotones.add(btnCancelarTurno);

		JButton btnVolver = new JButton("Volver");
		pnlBotones.add(btnVolver);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				habilitarBotones(false);
				actualizarTabla();
			}
		});
	}

	private void habilitarBotones(boolean t) {
		this.btnModificarTurno.setEnabled(t);
		this.btnCancelarTurno.setEnabled(t);
	}

	public void actualizarTabla() {
		modelo.setRowCount(0);

		MedicoDTO medico;
		PacienteDTO paciente;

		List<TurnoDTO> turnosAsignados = api.obtenerTurnosAsignados().stream()
				.filter(t -> t.getFecha().isAfter(LocalDateTime.now())).toList();

		for (TurnoDTO t : turnosAsignados) {
			medico = this.api.obtenerMedico(t.getMedico());
			paciente = this.api.obtenerPacientePorDni(t.getPaciente());
			modelo.addRow(
					new Object[] { t.getIdTurno(), t.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
							paciente.getApellido() + ", " + paciente.getNombre(), paciente.getDni(),
							medico.getApellido() + ", " + medico.getNombre() });
		}
	}

}
