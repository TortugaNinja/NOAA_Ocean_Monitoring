package ar.com.ada.api.NOAA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.NOAA.entities.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer>{

    
    
}