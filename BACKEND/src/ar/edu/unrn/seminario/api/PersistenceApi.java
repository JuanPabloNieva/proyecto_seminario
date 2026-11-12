
package ar.edu.unrn.seminario.api;

import ar.edu.unrn.seminario.accesos.PacienteDao;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.PacienteDAOJDBC;
import ar.edu.unrn.seminario.accesos.TurnoDao;
import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;
import ar.edu.unrn.seminario.exception.DniUnicoException;
import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Medico;
import ar.edu.unrn.seminario.modelo.ObraSocial;
import ar.edu.unrn.seminario.modelo.Paciente;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Turno;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.accesos.TurnoDAOJDBC;
import ar.edu.unrn.seminario.accesos.ObraSocialDao;
import ar.edu.unrn.seminario.accesos.ObraSocialDAOJDBC;
import ar.edu.unrn.seminario.accesos.MedicoDao;
import ar.edu.unrn.seminario.accesos.MedicoDAOJDBC;
import ar.edu.unrn.seminario.accesos.EspecialidadDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.accesos.EspecialidadDAOJDBC;

public class PersistenceApi implements IApi {

	private RolDao rolDao = new RolDAOJDBC();
    private PacienteDao pacienteDao = new PacienteDAOJDBC();
    private TurnoDao turnoDao = new TurnoDAOJDBC();
    private ObraSocialDao obraSocialDao = new ObraSocialDAOJDBC();
    private MedicoDao medicoDao = new MedicoDAOJDBC();
    private EspecialidadDao especialidadDao = new EspecialidadDAOJDBC();
    private final PacienteMapper pacienteMapper = new PacienteMapper();
    private final HistoriaMapper historiaMapper = new HistoriaMapper();
    private final MedicoMapper medicoMapper = new MedicoMapper();
    
    
	@Override
	public void registrarUsuario(String username, String password, String email, Integer rol)
			throws DataEmptyException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void registrarMedico(String nombre, String apellido, int dni,
	        String matricula, int codigoEspecialidad, Usuario usuario)
	        throws DataEmptyException, DniErroneoException {
		
	    Especialidad especialidad = especialidadDao.find(codigoEspecialidad);
	    Medico medico = new Medico( nombre,apellido,dni, matricula,especialidad);
	    medico.setUsuario(usuario);
	    
	    medicoDao.create(medico);
	}
	
	@Override
	public void registrarPaciente(String nombre, String apellido, int dni, Integer obraSocial)
	        throws DataEmptyException, DniErroneoException {
	    ObraSocial os = obraSocialDao.find(obraSocial);
	    Paciente existente = pacienteDao.find(dni);
	    if (existente != null) {
	        throw new DniUnicoException("Ya existe un paciente con ese DNI");
	    } 
	    Paciente paciente = new Paciente(nombre, apellido, dni, os);
	    pacienteDao.create(paciente);
	}
	
	
	@Override
	public void registrarTurno(MedicoDTO medico, TurnoDTO turno, PacienteDTO pacienteTurno)
	        throws DataEmptyException {
	   Turno turnoExistente = turnoDao.find(turno.getIdTurno());
	    Paciente paciente = pacienteDao.find(pacienteTurno.getDni());
	    if (turnoExistente.isActivo() && turnoExistente.getDniPaciente() == null) {
	        turnoExistente.setDniPaciente(paciente.getDni());
	        turnoExistente.setActivo(false);
	        turnoDao.update(turnoExistente);

	    }
	}
	@Override
	public void liberarTurno(Integer idTurno) throws DataEmptyException {

	    Turno turno = turnoDao.find(idTurno);
	    turno.setDniPaciente(null);
	    turno.setActivo(true);
	    turno.setDetalle(null);
	    turnoDao.update(turno);
	}
	@Override
	public void modificarPaciente(String nombre, String apellido, int dni, Integer obraSocial)
	        throws DataEmptyException, DniErroneoException {
	    Paciente paciente = pacienteDao.find(dni);
	    ObraSocial os = obraSocialDao.find(obraSocial);
	    paciente.setNombre(nombre);
	    paciente.setApellido(apellido);
	    paciente.setObraSocial(os);
	    pacienteDao.update(paciente);
	}
	
	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void eliminarPaciente(String dniPaciente) {
	    Integer dni = Integer.parseInt(dniPaciente);
	    pacienteDao.remove(dni);
	}
	
	@Override
	public List<RolDTO> obtenerRoles() {
	    return rolDao.findAll().stream().map(rol -> new RolDTO( rol.getCodigo(), rol.getNombre(),rol.isActivo())).collect(Collectors.toList());
	}
	@Override
	public List<TurnoDTO> obtenerTurnosAsignados() {

	    List<Turno> turnos = turnoDao.findAll();

	    return turnos.stream().filter(t -> t.getDniPaciente() != null).map(t -> new TurnoDTO(t.getIdTurno(),
	           t.getFecha(),t.getMatriculaMedico(),t.getDniPaciente(),t.isActivo())).collect(Collectors.toList());
	}
	
	@Override
	public List<ObraSocialDTO> obtenerObrasSociales() {
	    return obraSocialDao.findAll().stream() .map(obraSocial -> new ObraSocialDTO(obraSocial.getCodigo(),obraSocial.getNombre(),obraSocial.isActivo())).collect(Collectors.toList());
	}
	
	@Override
	public List<MedicoDTO> obtenerMedicos() {

	    return medicoDao.findAll().stream() .map(medico -> medicoMapper.transformar(medico)) .collect(Collectors.toList());
	}
	
	@Override
	public List<RolDTO> obtenerRolesActivos() {
	    return rolDao.findAll().stream().filter(Rol::isActivo).map(rol -> new RolDTO( rol.getCodigo(), rol.getNombre(), rol.isActivo())).collect(Collectors.toList());
	}
	
	@Override
	public List<TurnoDTO> obtenerHistoriaClinica(Paciente paciente) {
	    List<Turno> turnos = turnoDao.findAll();
	    List<Turno> historia = turnos.stream()
	            .filter(t -> t.getDniPaciente() != null && t.getDniPaciente().equals(paciente.getDni()))
	            .filter(t -> t.getFecha().isBefore(LocalDateTime.now())).collect(Collectors.toList());

	    return historiaMapper.transformar(historia);
	}
	
	@Override
	public Set<EspecialidadDTO> obtenerEspecialidades() {
	    return especialidadDao.findAll().stream().map(especialidad -> new EspecialidadDTO(especialidad.getCodigo(), especialidad.getNombre())) .collect(Collectors.toSet());
	}
	
	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
	    Rol rol = rolDao.find(codigo);

	    if (rol == null) {
	        rol = new Rol(codigo, descripcion);
	        rol.setActivo(estado);
	        rolDao.create(rol);
	    } else {
	        rol.setNombre(descripcion);
	        rol.setActivo(estado);
	        rolDao.update(rol);
	    }
	}
	
	
	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
	    Rol rol = rolDao.find(codigo);
	    return new RolDTO( rol.getCodigo(), rol.getNombre(), rol.isActivo());
	}
	
	@Override
	public PacienteDTO obtenerPacientePorDni(Integer dniPaciente) {

	    Paciente paciente = pacienteDao.find(dniPaciente);

	    if (paciente == null) {
	        return null;
	    }

	    List<TurnoDTO> historia = obtenerHistoriaClinica(paciente);

	    return new PacienteDTO( paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getObraSocial().getNombre(), historia);
	}
	
	
	@Override
	public EspecialidadDTO obtenerEspecialidad(Integer codigo) {
	    Especialidad especialidad = especialidadDao.find(codigo);

	    if (especialidad == null) {
	        return null;
	    }

	    return new EspecialidadDTO( especialidad.getCodigo(), especialidad.getNombre());
	}
	@Override
	public MedicoDTO obtenerMedico(String matricula) {
	    Medico medico = medicoDao.find(matricula);
	    if (medico == null) {
	        return null;
	    }
	    return medicoMapper.transformar(medico);
	}
	@Override
	public TurnoDTO obtenerTurno(String idTurno) {
	    Integer id = Integer.parseInt(idTurno);
	    Turno turno = turnoDao.find(id);
	    if (turno == null) {
	        return null;
	    }
	    return new TurnoDTO(turno.getIdTurno(), turno.getFecha(), turno.getMatriculaMedico(),turno.getDniPaciente(), turno.isActivo()
	    );
	}
	
	@Override
	public void activarRol(Integer codigo) {
	    Rol rol = rolDao.find(codigo);

	    if (rol != null) {
	        rol.activar();
	        rolDao.update(rol);
	    }
	}
	@Override
	public void desactivarRol(Integer codigo) {
	    Rol rol = rolDao.find(codigo);

	    if (rol != null) {
	        rol.desactivar();
	        rolDao.update(rol);
	    }
	}
	
	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PacienteDTO> obtenerPacientes() {

	    List<Paciente> pacientes = pacienteDao.findAll();

	    return pacientes.stream().map(paciente -> new PacienteDTO(paciente.getNombre(),paciente.getApellido(),
	           paciente.getDni(),paciente.getObraSocial().getNombre(),obtenerHistoriaClinica(paciente))).collect(Collectors.toList());
	}
	
	@Override
	public void activarUsuario(String username) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean autenticar(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
