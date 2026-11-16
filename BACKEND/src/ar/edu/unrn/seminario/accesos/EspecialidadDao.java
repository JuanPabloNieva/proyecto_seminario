
package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Especialidad;

public interface EspecialidadDao {

    void create(Especialidad especialidad);

    void update(Especialidad especialidad);

    void remove(Integer codigo);

    Especialidad find(Integer codigo);

    List<Especialidad> findAll();

}
