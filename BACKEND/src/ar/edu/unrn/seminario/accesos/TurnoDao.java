package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Turno;

public interface TurnoDao {

    void create(Turno turno);

    void update(Turno turno);

    void remove(Integer idTurno);

    Turno find(Integer idTurno);

    List<Turno> findAll();

    List<Turno> findByMedico(String matricula);

}