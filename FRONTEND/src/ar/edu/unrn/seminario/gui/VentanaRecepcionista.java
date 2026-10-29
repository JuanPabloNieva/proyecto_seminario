package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.BorderLayout;

public class VentanaRecepcionista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;
	private JPanel panelContenedor;

	private AltaPaciente panelAltaPaciente;
	private ListadoPaciente panelListadoPacientes;
	private ModificarPaciente panelModificarPaciente;
	private HistoriaClinica panelHistoriaClinica;
	private AltaTurno panelAltaTurno;
	private ListadoTurno panelListadoTurno;
	private ModificarTurno panelModificarTurno;
	private JPanel panelDefault;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new MemoryApi();
					VentanaRecepcionista frame = new VentanaRecepcionista(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRecepcionista(IApi api) {

		setTitle("SISTEMA SALUD - Recepcionista");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		cardLayout = new CardLayout();

		panelContenedor = new JPanel(cardLayout);
		contentPane.add(panelContenedor, BorderLayout.CENTER);

		// Instanciar paneles

		panelAltaPaciente = new AltaPaciente(api, panelContenedor, cardLayout);
		panelListadoPacientes = new ListadoPaciente(this, api, panelContenedor, cardLayout);
		panelModificarPaciente = new ModificarPaciente(api, panelContenedor, cardLayout);
		panelHistoriaClinica = new HistoriaClinica(api, panelContenedor, cardLayout);
		panelAltaTurno = new AltaTurno(api, panelContenedor, cardLayout);
		panelListadoTurno = new ListadoTurno(api, this);
		panelModificarTurno = new ModificarTurno(api, panelContenedor, cardLayout);
		panelDefault = new PanelRecepcionistaDefault();

		// Agregarlos al panel contenedor con un nombre clave (identificador)

		panelContenedor.add(panelDefault, "PANELDEFAULT");
		panelContenedor.add(panelAltaPaciente, "ALTAPACIENTE");
		panelContenedor.add(panelModificarPaciente, "MODIFICARPACIENTE");
		panelContenedor.add(panelListadoPacientes, "LISTADOPACIENTE");
		panelContenedor.add(panelHistoriaClinica, "HISTORIACLINICA");
		panelContenedor.add(panelListadoTurno, "LISTADOTURNO");
		panelContenedor.add(panelAltaTurno, "ALTATURNO");
		panelContenedor.add(panelModificarTurno, "MODIFICARTURNO");

		JMenuBar barraMenu = new JMenuBar();
		contentPane.add(barraMenu, BorderLayout.NORTH);

		JMenu menuPacientes = new JMenu("Pacientes");
		barraMenu.add(menuPacientes);

		JMenuItem opcionRegistrarPaciente = new JMenuItem("Registrar Paciente");
		opcionRegistrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarListadoPacientes();
			}
		});
		menuPacientes.add(opcionRegistrarPaciente);

		JMenuItem opcionListadoPacientes = new JMenuItem("Listado de Pacientes");
		opcionListadoPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContenedor, "LISTADOPACIENTE");
			}
		});
		menuPacientes.add(opcionListadoPacientes);

		JMenu menuTurnos = new JMenu("Turnos");
		barraMenu.add(menuTurnos);

		JMenuItem opcionRegistrarTurno = new JMenuItem("Registrar Turnos");
		opcionRegistrarTurno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContenedor, "ALTATURNO");
			}
		});
		menuTurnos.add(opcionRegistrarTurno);

		JMenuItem opcionListadoTurnos = new JMenuItem("Listado de Turnos");
		opcionListadoTurnos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContenedor, "LISTADOTURNO");
			}
		});
		menuTurnos.add(opcionListadoTurnos);

		JMenu menuConfiguracion = new JMenu("Configuración");
		barraMenu.add(menuConfiguracion);

		JMenuItem opcionCerrarSesion = new JMenuItem("Cerrar Sesión");
		opcionCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menuConfiguracion.add(opcionCerrarSesion);

	}

	public void pasarPacienteAModificar(PacienteDTO paciente) {
		this.panelModificarPaciente.datoPacienteActual(paciente);
		cardLayout.show(panelContenedor, "MODIFICARPACIENTE");
	}

	public void pasarTurnoAModificar(TurnoDTO turno) {
		this.panelModificarTurno.datoTurnoActual(turno);
		cardLayout.show(panelContenedor, "MODIFICARTURNO");
	}

	public void pasarPacienteHistoria(PacienteDTO paciente) {
		this.panelHistoriaClinica.datoPacienteActual(paciente);
		cardLayout.show(panelContenedor, "HISTORIACLINICA");
	}

	public void mostrarListadoPacientes() {
		panelListadoPacientes.actualizarTabla();
		cardLayout.show(panelContenedor, "ALTAPACIENTE");
	}
}
