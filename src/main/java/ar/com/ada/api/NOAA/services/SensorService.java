package ar.com.ada.api.NOAA.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.NOAA.entities.Boya;
import ar.com.ada.api.NOAA.entities.Sensor;
import ar.com.ada.api.NOAA.entities.Device.DeviceStatusEnum;
import ar.com.ada.api.NOAA.repositories.BoyaRepository;
import ar.com.ada.api.NOAA.repositories.SensorRepository;

@Service
public class SensorService {

    @Autowired
    BoyaRepository boyaRepo;

    @Autowired 
    SensorRepository sensorRepo;

    Boya boya = new Boya();

    public boolean sensorYaExiste(Sensor sensor){

        if (boyaRepo.sensorAlreadyExists(boya.getSensor().getSensorId())) 

            return false;
        sensorRepo.save(sensor);

        return true;
    }

    public Sensor crearSensor(DeviceStatusEnum deviceStatus, BigDecimal alturaNivelDelMar) {

    
        Sensor sensor = new Sensor();
        
        sensor.setDeviceStatus(deviceStatus);
        
        sensor.setAlturaNivelDelMar(alturaNivelDelMar);
        boolean sensorExistente = sensorYaExiste(sensor);

        if (sensorExistente) {

            return sensor;

        } else {

            return null;
        }
        
    }
    
}
