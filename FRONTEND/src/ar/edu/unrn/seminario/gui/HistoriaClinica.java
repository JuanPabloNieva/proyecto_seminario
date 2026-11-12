package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class HistoriaClinica extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel modelo;
	private IApi api;
	private List<TurnoDTO> historiaClinica;
	private Integer dniPacienteActual;

	/**
	 * Create the panel.
	 */
	public HistoriaClinica(IApi api, JPanel panelContenedor, CardLayout cardLayout) {
		this.api = api;
		setLayout(new BorderLayout(0, 0));

		JPanel pBotones = new JPanel();
		add(pBotones, BorderLayout.SOUTH);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContenedor, "LISTADOPACIENTE");
			}
		});
		pBotones.add(btnVolver);

		JPanel pTitulo = new JPanel();
		add(pTitulo, BorderLayout.NORTH);

		JLabel lblHistoriaClinica = new JLabel("Historia Clínica");
		lblHistoriaClinica.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistoriaClinica.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		pTitulo.add(lblHistoriaClinica);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		modelo = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Fecha", "Paciente", "DNI","Médico" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.setModel(modelo);
		scrollPane.setViewportView(table);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				actualizarTabla();
			}
		});

	}

	public void datoPacienteActual(PacienteDTO paciente) {
		historiaClinica = paciente.getHistoriaClinica();
		dniPacienteActual = paciente.getDni();
	}

	public void actualizarTabla() {
		modelo.setRowCount(0);
		MedicoDTO medico;
		PacienteDTO paciente = this.api.obtenerPacientePorDni(dniPacienteActual);
		
		for (TurnoDTO t : this.historiaClinica) {
			medico = this.api.obtenerMedico(t.getMedico());
			//paciente = this.api.obtenerPacientePorDni(t.getPaciente());
			modelo.addRow(
					new Object[] { t.getIdTurno(), t.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
							paciente.getApellido() + ", " + paciente.getNombre(), paciente.getDni(), medico.getApellido() + ", " + medico.getNombre() });
		}
	}

}
