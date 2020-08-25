package ar.com.ada.api.martes258challenges.models.response;

import ar.com.ada.api.martes258challenges.entities.Usuario.TipoUsuarioEnum;

public class LoginResponse {
    public Integer id;
    public String username;
    public String token;
    public String email;
    public TipoUsuarioEnum userType;
    public Integer entityId; // Si es un Docente, va el Id de Docente, si es estudiante Id Estudiante

}