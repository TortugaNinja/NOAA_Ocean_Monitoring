package ar.com.ada.api.NOAA.models.response;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.ada.api.NOAA.entities.Device.FaroStatusEnum;

public class MuestraSeaLevelMinResponse {

    public MuestraSeaLevelMinResponse() {

    }
    
    public MuestraSeaLevelMinResponse(FaroStatusEnum color, BigDecimal alturaNivelDelMarMin, Date horario) {

        this.color = color;
        this.alturaNivelDelMarMin = alturaNivelDelMarMin;
        this.horario = horario;
        
    }
    
    FaroStatusEnum color;
    BigDecimal alturaNivelDelMarMin;
    Date horario;
    

    
}
