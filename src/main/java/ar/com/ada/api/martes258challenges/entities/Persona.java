package ar.com.ada.api.martes258challenges.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.Converter;

import ar.com.ada.api.martes258challenges.entities.Pais.PaisEnum;
import ar.com.ada.api.martes258challenges.entities.Pais.TipoDocuEnum;
import ch.qos.logback.core.joran.util.StringToObjectConverter;

// anotación de la clase padre 
@MappedSuperclass
public class Persona {
    private String nombre;
    @Column(name = "pais_id")
    private Integer paisId;
    @Column(name = "tipo_documento_id")
    private Integer tipoDocumentoId;
    private String documento;
    @Column(name = "fecha_nacimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PaisEnum getPaisId() {
        return PaisEnum.parse(this.paisId);
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public TipoDocuEnum getTipoDocumentoId() {
        return TipoDocuEnum.parse(this.tipoDocumentoId);
    }

    public void setTipoDocumentoId(TipoDocuEnum tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId.getValue();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
    
       fechaNacimiento = new Date();
       this.fechaNacimiento = fechaNacimiento;
        
        
    }

}