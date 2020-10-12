package ar.com.ada.api.NOAA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.NOAA.entities.Barco;

@Repository
public interface BarcoRepository extends JpaRepository<Barco, Integer>{

    @Query("select b from Barco b where b.barcoId=:barcoId and b.tipoDeviceId=:tipoDeviceEnum and b.matricula=:matricula")
    Barco findByUIDX(Integer barcoId, Integer tipoDeviceEnum, String matricula);

    @Query("select b from Barco b order by matricula")
    List<Barco> findTodosByMatricula();

    List<Barco> findByMatricula(String matricula);

    @Query("select CASE WHEN count(b) > 0 THEN true ELSE false END from Barco b where b.barcoId=:barcoId and b.matricula=:matricula")
    boolean barcoAlreadyExists(Integer barcoId, String matricula);
}