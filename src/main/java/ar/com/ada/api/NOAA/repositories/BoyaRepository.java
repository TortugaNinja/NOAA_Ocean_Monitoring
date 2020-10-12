package ar.com.ada.api.NOAA.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.NOAA.entities.Boya;
import ar.com.ada.api.NOAA.entities.Muestra;

@Repository
public interface BoyaRepository extends JpaRepository<Boya, Integer> {

    //BOYAS y SENSORES
    // Obtener Boyas y Sensores

    @Query("select b from Boya b where b.boyaId=:boyaId and b.tipoDeviceId=:tipoDeviceEnum and b.sensor=:sensorId")
    Boya findByUIDX(Integer boyaId, Integer tipoDeviceEnum, Integer sensorId);

    @Query("select b from Boya b where b.boyaId=:boyaId order by boyaId")
    List<Boya> findByBoyaId(Integer boyaId);

    List<Boya> findAllBoyas();

    @Query("select b from Boya b where b.faroStatusId=:faroStatusId order by b.boyaId")
    List<Boya> findByFaroStatusId(Integer faroStatusId);

    // Para crear Boyas y Sensores checkear

    @Query("select CASE WHEN count(b) > 0 THEN true ELSE false END from Boya b where b.boyaId=:boyaId")
    boolean boyaAlreadyExists(Integer boyaId);

    @Query("select CASE WHEN count(b) > 0 THEN true ELSE false END from Boya b where b.sensor=:sensorId")
    boolean sensorAlreadyExists(Integer sensor);

    // Checkear si hay Boyas con Status de Faro activo.

    @Query("select CASE WHEN count(b) > 0 and b.faroStatusId != NULL THEN true ELSE false END from Boya b where b.faroStatusId=:faroStatusId and b.boyaId=:boyaId")
    boolean faroStatusNotNull(Integer faroStatusId, Integer boyaId);
    

    //MUESTRAS
    // Obtener Muestras

    List<Muestra> findMuestrasByBoyaId(Integer boyaId);



   

    




    
    
}