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
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;

public class HistoriaClinica extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel modelo;
	
	private List<TurnoDTO> historiaClinica;

	/**
	 * Create the panel.
	 */
	public HistoriaClinica(IApi api, JPanel panelContenedor, CardLayout cardLayout) {
		setLayout(new BorderLayout(0, 0));

		JPanel pBotones = new JPanel();
		add(pBotones, BorderLayout.SOUTH);

		JButton btnVerDetalle = new JButton("Ver Detalle");
		pBotones.add(btnVerDetalle);

		JButton btnVolver = new JButton("Volver");
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
		modelo = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Fecha", "Médico", "Activo" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Ninguna celda es editable
			}
		};

		table.setModel(modelo);
		scrollPane.setViewportView(table);

	}
	
	public void datoPacienteActual(PacienteDTO paciente) {
		historiaClinica = paciente.getHistoriaClinica();
	}

}
