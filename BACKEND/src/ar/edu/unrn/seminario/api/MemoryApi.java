package ar.edu.unrn.seminario.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.GeneradorID;
import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Medico;
import ar.edu.unrn.seminario.modelo.ObraSocial;
import ar.edu.unrn.seminario.modelo.Paciente;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Turno;

public class MemoryApi implements IApi {

	private Set<Rol> roles = new HashSet();
	private Set<ObraSocial> oSociales = new HashSet();
	private Set<Especialidad> especialidades = new HashSet();
	private List<Usuario> usuarios = new ArrayList<>();
	private List<Medico> medicos = new ArrayList<>();
	private List<Paciente> pacientes = new ArrayList<>();
	private Map<String, String> usuariosRegistrados = new HashMap<String, String>();
	private List<Turno> turnosAsignados = new ArrayList<>();

	public MemoryApi() {

		// datos iniciales
		this.roles.add(new Rol(1, "ADMIN"));
		this.roles.add(new Rol(2, "RECEPCIONISTA"));
		this.roles.add(new Rol(3, "MEDICO"));

		this.oSociales.add(new ObraSocial(1, "IPROSS"));
		this.oSociales.add(new ObraSocial(2, "OSDE"));
		this.oSociales.add(new ObraSocial(3, "SANCORSALUD"));

		this.especialidades.add(new Especialidad(1, "Pediatria"));
		this.especialidades.add(new Especialidad(2, "Cardiologia"));
		this.especialidades.add(new Especialidad(3, "Dermatologia"));

		inicializarUsuarios();
		inicializarMedicos();
		inicializarPacientes();
		inicializarTurnos();
	}

	private void inicializarUsuarios() {

		try {
			registrarUsuario("admin", "1234", "admin@unrn.edu.ar", 1);
			registrarUsuario("ldifabio", "4", "ldifabio@unrn.edu.ar", 2);
			registrarUsuario("bjgorosito", "1234", "bjgorosito@unrn.edu.ar", 3);
			registrarUsuario("lgonzalez", "1222", "lgonzalez@unrn.edu.ar", 3);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void inicializarMedicos() {
		try {
			registrarMedico("Bruno", "Gorosito", 35353532, "AFA256", 3, usuarios.get(2));
			registrarMedico("Laura", "Gonzalez", 35893532, "AFG886", 2, usuarios.get(3));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void inicializarPacientes() {
		try {
			registrarPaciente("Jose", "Perez", 34695322, 1);
			registrarPaciente("Maria", "Gutierrez", 16535465, 2);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void inicializarTurnos() {
		try {

			Medico medico1 = medicos.get(0);
			Medico medico2 = medicos.get(1);

			MedicoDTO medicoDTO1 = obtenerMedico(medico1.getMatricula());
			MedicoDTO medicoDTO2 = obtenerMedico(medico2.getMatricula());

			PacienteDTO paciente1 = obtenerPacientePorDni(34695322);
			PacienteDTO paciente2 = obtenerPacientePorDni(16535465);

			LocalDateTime fecha1 = LocalDateTime.of(2026, 4, 25, 14, 30, 0);
			LocalDateTime fecha2 = LocalDateTime.of(2026, 8, 13, 10, 0, 0);
			LocalDateTime fecha3 = LocalDateTime.of(2026, 9, 25, 10, 30, 0);

			TurnoDTO turno1 = new TurnoDTO(GeneradorID.obtenerSiguienteId(), fecha1, medico1.getMatricula(),
					paciente1.getDni(), true);
			TurnoDTO turno2 = new TurnoDTO(GeneradorID.obtenerSiguienteId(), fecha2, medico1.getMatricula(),
					paciente1.getDni(), true);
			TurnoDTO turno3 = new TurnoDTO(GeneradorID.obtenerSiguienteId(), fecha3, medico2.getMatricula(),
					paciente2.getDni(), true);

			registrarTurnoInicial(medicoDTO1, turno1, paciente1);
			registrarTurnoInicial(medicoDTO1, turno2, paciente1);
			registrarTurnoInicial(medicoDTO2, turno3, paciente2);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void registrarTurnoInicial(MedicoDTO medicoTurno, TurnoDTO turnoElegido, PacienteDTO pacienteTurno)
			throws DataEmptyException {
		Paciente paciente = this.buscarPaciente(pacienteTurno.getDni());
		Turno t = new Turno(turnoElegido.getIdTurno(), turnoElegido.getFecha(), turnoElegido.getMatriculaMedico(),
				turnoElegido.getDniPaciente(), false);
		paciente.agregarTurnoHistoriaClinica(t);
		this.turnosAsignados.add(t);

	}

	@Override
	public void registrarUsuario(String username, String password, String email, Integer rol)
			throws DataEmptyException {

		Rol role = this.buscarRol(rol);
		Usuario usuario = new Usuario(username, password, email, role);
		usuariosRegistrados.put(usuario.getUsuario(), usuario.getContrasena());
		this.usuarios.add(usuario);

	}

	@Override
	public void registrarMedico(String nombre, String apellido, int dni, String matricula, int especialidad,
			Usuario usuario) throws DataEmptyException {
		Especialidad espec = this.buscarEspecialidad(especialidad);
		Medico medico = new Medico(nombre, apellido, dni, matricula, espec);
		medico.setUsuario(usuario);
		this.medicos.add(medico);
	}

	@Override
	public void registrarPaciente(String nombre, String apellido, int dni, Integer obraSocial)
			throws DataEmptyException {
		ObraSocial os = this.buscarObraSocial(obraSocial);
		Paciente paciente = new Paciente(nombre, apellido, dni, os);
		this.pacientes.add(paciente);
	}

	@Override
	public void registrarTurno(MedicoDTO medicoTurno, TurnoDTO turnoElegido, PacienteDTO pacienteTurno)
			throws DataEmptyException {
		Medico medico = this.buscarMedico(medicoTurno.getMatricula());
		Paciente paciente = this.buscarPaciente(pacienteTurno.getDni());

		for (Turno t : medico.getTurnosDisponibles()) {
			if (t.getFecha().equals(turnoElegido.getFecha())) {
				t.setActivo(false);
				t.setDniPaciente(paciente.getDni());
				paciente.agregarTurnoHistoriaClinica(t);
				this.turnosAsignados.add(t);
			}
		}

	}

	@Override
	public void liberarTurno(Integer idTurno) throws DataEmptyException {
		TurnoDTO turno = obtenerTurno(String.valueOf(idTurno));
		Turno turnoALiberar = this.turnosAsignados.stream().filter(t -> t.getIdTurno() == turno.getIdTurno())
				.findFirst().orElse(null);
		this.turnosAsignados.removeIf(t -> t.getIdTurno() == turno.getIdTurno());
		/*Medico medico = this.medicos.stream().filter(m -> m.getMatricula().equals(turno.getMatriculaMedico()))
				.findFirst().orElse(null);*/
		turnoALiberar.setActivo(true);
		turnoALiberar.setDniPaciente(null);
		turnoALiberar.setDetalle(null);
	}


	@Override
	public void modificarPaciente(String nombre, String apellido, int dni, Integer obraSocial)
			throws DataEmptyException {
		ObraSocial os = this.buscarObraSocial(obraSocial);
		Paciente paciente = this.pacientes.stream().filter(p -> (p.getDni() == dni)).findFirst().orElse(null);
		if (!paciente.equals(null)) {
			paciente.setNombre(nombre);
			paciente.setApellido(apellido);
			paciente.setDni(dni);
			paciente.setObraSocial(os);
		}
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		for (Usuario u : this.usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getEmail(), u.getRol().getNombre(),
					u.isActivo(), u.obtenerEstado()));
		}
		return dtos;
	}

	@Override
	public List<ObraSocialDTO> obtenerObrasSociales() {
		List<ObraSocialDTO> dtos = new ArrayList<>();
		for (ObraSocial os : this.oSociales) {
			dtos.add(new ObraSocialDTO(os.getCodigo(), os.getNombre(), os.isActivo()));
		}
		return dtos;
	}

	@Override
	public List<MedicoDTO> obtenerMedicos() {
		List<MedicoDTO> dtos = new ArrayList<>();

		for (Medico m : this.medicos) {
			dtos.add(new MedicoDTO(m.getNombre(), m.getApellido(), m.getDni(), m.getMatricula(),
					this.obtenerEspecialidad(m.getEspecialidad().getCodigo()), this.obtenerTurnosDisponibles(m)));
		}
		return dtos;
	}

	@Override
	public List<PacienteDTO> obtenerPacientes() {
		List<PacienteDTO> dtos = new ArrayList<>();
		for (Paciente p : this.pacientes) {
			dtos.add(new PacienteDTO(p.getNombre(), p.getApellido(), p.getDni(), p.getObraSocial().getNombre(),
					this.obtenerHistoriaClinica(p)));
		}

		return dtos;
	}

	@Override
	public List<TurnoDTO> obtenerHistoriaClinica(Paciente paciente) {
		List<TurnoDTO> dtos = new ArrayList<>();

		for (Turno p : paciente.getHistoriaClinica()) {
			dtos.add(new TurnoDTO(p.getIdTurno(), p.getFecha(), p.getMatriculaMedico(), p.getDniPaciente(),
					p.isActivo()));
		}

		return dtos;
	}

	public Set<EspecialidadDTO> obtenerEspecialidades() {
		Set<EspecialidadDTO> dtos = new HashSet<>();

		for (Especialidad e : especialidades) {
			dtos.add(new EspecialidadDTO(e.getCodigo(), e.getNombre()));
		}

		return dtos;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EspecialidadDTO obtenerEspecialidad(Integer codigo) {
		EspecialidadDTO especialidadDTO = this.especialidades.stream().filter(e -> e.getCodigo() == codigo).findFirst()
				.map(e -> new EspecialidadDTO(e.getCodigo(), e.getNombre())).orElse(null);

		return especialidadDTO;
	}

	@Override
	public MedicoDTO obtenerMedico(String matricula) {
		MedicoDTO medicoDTO = this.medicos.stream().filter(medico -> medico.getMatricula() == matricula).findFirst()
				.map(m -> new MedicoDTO(m.getNombre(), m.getApellido(), m.getDni(), m.getMatricula(),
						this.obtenerEspecialidad(m.getEspecialidad().getCodigo()), this.obtenerTurnosDisponibles(m)))
				.orElse(null);
		return medicoDTO;
	}

	@Override
	public TurnoDTO obtenerTurno(String idTurno) {
		TurnoDTO turnoDTO = this.turnosAsignados.stream().filter(t -> t.getIdTurno() == Integer.parseInt(idTurno))
				.findFirst().map(turno -> new TurnoDTO(turno.getIdTurno(), turno.getFecha(), turno.getMatriculaMedico(),
						turno.getDniPaciente(), turno.isActivo()))
				.orElse(null);
		return turnoDTO;
	}

	public List<TurnoDTO> obtenerTurnosDisponibles(Medico medico) {
		List<TurnoDTO> dtos = new ArrayList<>();
		for (Turno t : medico.getTurnosDisponibles()) {

			dtos.add(new TurnoDTO(t.getIdTurno(), t.getFecha(), t.getMatriculaMedico(), t.getDniPaciente(),
					t.isActivo()));
		}
		return dtos;
	}

	@Override
	public List<TurnoDTO> obtenerTurnosAsignados() {
		List<TurnoDTO> dtos = new ArrayList<>();
		for (Turno t : this.turnosAsignados) {
			dtos.add(new TurnoDTO(t.getIdTurno(), t.getFecha(), t.getMatriculaMedico(), t.getDniPaciente(),
					t.isActivo()));
		}
		return dtos;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarPaciente(String dniPaciente) {
		int dni = Integer.parseInt(dniPaciente);
		this.pacientes = this.pacientes.stream().filter(paciente -> !(paciente.getDni() == (dni)))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			if (r.isActivo())
				dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub
		Rol rol = new Rol(codigo, descripcion);
		this.roles.add(rol);
	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PacienteDTO obtenerPacientePorDni(Integer dniPaciente) {
		int dni = dniPaciente;
		Paciente paciente = this.pacientes.stream().filter(p -> (p.getDni() == dni)).findFirst().orElse(null);
		List<TurnoDTO> historiaDTO = paciente
				.getHistoriaClinica().stream().map(turno -> new TurnoDTO(turno.getIdTurno(), turno.getFecha(),
						turno.getMatriculaMedico(), turno.getDniPaciente(), turno.isActivo()))
				.collect(Collectors.toList());
		PacienteDTO pacienteDTO = new PacienteDTO(paciente.getNombre(), paciente.getApellido(), paciente.getDni(),
				paciente.getObraSocial().getNombre(), historiaDTO);
		return pacienteDTO;
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String usuario) {
		Usuario user = this.buscarUsuario(usuario);
		user.activar();
	}

	@Override
	public void desactivarUsuario(String usuario) {
		// TODO: desactivar usuario
	}

	private Rol buscarRol(Integer codigo) {
		for (Rol rol : roles) {
			if (rol.getCodigo().equals(codigo))
				return rol;
		}
		return null;
	}

	private Especialidad buscarEspecialidad(Integer codigo) {
		for (Especialidad espec : especialidades) {
			if (espec.getCodigo().equals(codigo))
				return espec;
		}
		return null;
	}

	private Usuario buscarUsuario(String usuario) {
		for (Usuario user : usuarios) {
			if (user.getUsuario().equals(usuario))
				return user;
		}
		return null;
	}

	private Medico buscarMedico(String matricula) {
		for (Medico m : medicos) {
			if (m.getMatricula().equals(matricula)) {
				return m;
			}
		}
		return null;
	}

	private Paciente buscarPaciente(Integer dni) {
		for (Paciente p : pacientes) {
			if (p.getDni() == dni) {
				return p;
			}
		}
		return null;
	}

	private ObraSocial buscarObraSocial(Integer obraSocial) {
		for (ObraSocial os : oSociales) {
			if (os.getCodigo().equals(obraSocial))
				return os;
		}
		return null;
	}

	public boolean autenticar(String username, String password) {
		if (username.equals("")) {
			return false;
		} else if (usuariosRegistrados.containsKey(username) && usuariosRegistrados.get(username).equals(password)) {
			return true;
		} else {
			return false;
		}

	}
}
