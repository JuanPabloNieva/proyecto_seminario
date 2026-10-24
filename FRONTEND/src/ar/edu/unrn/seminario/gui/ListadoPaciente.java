package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListadoPaciente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	DefaultTableModel modelo;
	PacienteDTO paciente;
	IApi api;

	private JButton btnModificarPaciente;
	private JButton btnEliminarPaciente;
	private JButton btnHClinica;

	/**
	 * Create the panel.
	 */
	public ListadoPaciente(VentanaRecepcionista frame, IApi api, JPanel panelContenedor, CardLayout cardLayout) {

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

		modelo = new DefaultTableModel(new Object[][] { { null, null, null, "" }, },
				new String[] { "Nombre", "Apellido", "DNI", "Obra Social" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Ninguna celda es editable
			}
		};

		table.setModel(modelo);

		actualizarTabla();

		scrollPane.setViewportView(table);

		JPanel pnlBotonesOperaciones = new JPanel();
		pnlBotonesOperaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(pnlBotonesOperaciones, BorderLayout.SOUTH);

		btnModificarPaciente = new JButton("Modificar");
		btnModificarPaciente.setEnabled(false);
		btnModificarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dniPaciente = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();
				paciente = api.obtenerPacientePorDni(Integer.parseInt(dniPaciente));
				frame.pasarPacienteAModificar(paciente);
			}
		});
		pnlBotonesOperaciones.add(btnModificarPaciente);

		btnEliminarPaciente = new JButton("Eliminar");
		btnEliminarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Estas seguro que quieres eliminar el Paciente?",
						"Confirmar cambio de estado.", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String dniPaciente = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();

					api.eliminarPaciente(dniPaciente);
					habilitarBotones(false);
					actualizarTabla();

				}
			}
		});
		btnEliminarPaciente.setEnabled(false);
		pnlBotonesOperaciones.add(btnEliminarPaciente);

		btnHClinica = new JButton("Historia Clinica");
		btnHClinica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dniPaciente = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();
				paciente = api.obtenerPacientePorDni(Integer.parseInt(dniPaciente));
				frame.pasarPacienteHistoria(paciente);
			}
		});
		btnHClinica.setEnabled(false);
		pnlBotonesOperaciones.add(btnHClinica);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarBotones(false);
				cardLayout.show(panelContenedor, "PANELDEFAULT");
			}
		});
		pnlBotonesOperaciones.add(btnCerrar);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				habilitarBotones(false);
				actualizarTabla();
			}
		});
	}

	private void habilitarBotones(boolean t) {
		this.btnModificarPaciente.setEnabled(t);
		this.btnEliminarPaciente.setEnabled(t);
		this.btnHClinica.setEnabled(t);
	}

	public void actualizarTabla() {
		modelo.setRowCount(0);

		// Obtiene la lista de usuarios a mostrar
		List<PacienteDTO> pacientes = api.obtenerPacientes();

		// Agrega los usuarios en el model
		for (PacienteDTO p : pacientes) {
			modelo.addRow(new Object[] { p.getNombre(), p.getApellido(), p.getDni(), p.getObraSocial() });
		}
	}
}
