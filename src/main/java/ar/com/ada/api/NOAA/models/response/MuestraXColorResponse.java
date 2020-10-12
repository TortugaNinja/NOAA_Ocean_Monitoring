package ar.com.ada.api.NOAA.models.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MuestraXColorResponse {

    public MuestraXColorResponse() {

    }

    public MuestraXColorResponse(Integer boyaId, Date horario, BigDecimal alturaNivelDelMar) {

        this.boyaId = boyaId;
        this.horario = horario;
        this.alturaNivelDelMar = alturaNivelDelMar;
        
    }
    
    Integer boyaId;
    Date horario;
    BigDecimal alturaNivelDelMar;

    public static List<MuestraXColorResponse> verdes = new ArrayList<>();

    public static List<MuestraXColorResponse> amarillas = new ArrayList<>();

    public static List<MuestraXColorResponse> rojas = new ArrayList<>();


    
}
