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
import java.util.List;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class ListadoTurno extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	DefaultTableModel modelo;
	private IApi api;

	JButton btnModificarTurno;
	JButton btnCancelarTurno;

	/**
	 * Create the panel.
	 */
	public ListadoTurno(IApi api) {
		this.api = api;
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		modelo = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Fecha", "Paciente", "DNI", "Médico" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(modelo);
		add(table, BorderLayout.SOUTH);

		JLabel lblTitulo = new JLabel("Listado de turnos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, BorderLayout.NORTH);

		JPanel pnlBotones = new JPanel();
		add(pnlBotones, BorderLayout.SOUTH);

		btnModificarTurno = new JButton("Modificar Turno");
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

		List<TurnoDTO> turnosAsignados = api.obtenerTurnosAsignados();

		for (TurnoDTO t : turnosAsignados) {
			medico = this.api.obtenerMedico(t.getMedico());
			paciente = this.api.obtenerPacientePorDni(t.getPaciente());
			modelo.addRow(new Object[] { t.getIdTurno(), t.getFecha(), paciente, t.getPaciente(), medico });
		}
	}

}
