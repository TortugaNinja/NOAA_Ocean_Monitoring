package ar.com.ada.api.martes258challenges.models.request;

import java.util.Date;

import ar.com.ada.api.martes258challenges.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.martes258challenges.entities.Usuario.TipoUsuarioEnum;

/**
 * RegistrationRequest
 */
public class RegistrationRequest {

    public String fullName; // Nombre persona
    public Integer country; // pais del usuario
    public TipoDocuEnum identificationType; // Tipo Documento
    public String identification; // nro documento
    public Date birthDate; // fechaNacimiento
    public TipoUsuarioEnum userType;
    public String email; // email
    public String password; // contraseña elegida por el usuario.

}
