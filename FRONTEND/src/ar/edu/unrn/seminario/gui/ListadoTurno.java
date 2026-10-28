package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;

public class ListadoTurno extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ListadoTurno() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		add(table, BorderLayout.SOUTH);
		
		JLabel lblTitulo = new JLabel("Listado de turnos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, BorderLayout.NORTH);
		
		JPanel pnlBotones = new JPanel();
		add(pnlBotones, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		pnlBotones.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		pnlBotones.add(btnNewButton_1);

	}

}
