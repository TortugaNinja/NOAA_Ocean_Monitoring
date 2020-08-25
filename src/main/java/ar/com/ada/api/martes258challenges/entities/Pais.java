package ar.com.ada.api.martes258challenges.entities;

public class Pais {
    public enum PaisEnum {

        ARGENTINA(32), VENEZUELA(862), USA(840);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private PaisEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        /**public static PaisEnum parse(Integer id) {
            PaisEnum status = null; // Default
            for (PaisEnum item : PaisEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }*/

        public static PaisEnum parse(Integer id) {
            PaisEnum status = null;   // Default
            for (PaisEnum itemPais : PaisEnum.values()) {
                if (itemPais.getValue().equals(id)) {
                    status = itemPais;
                    break;
                }
            }
            
            
            return status;
        }
    }

    public enum TipoDocuEnum {
        DNI(1), PASAPORTE(2);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private TipoDocuEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoDocuEnum parse(Integer id) {
            TipoDocuEnum status = null; // Default
            for (TipoDocuEnum item : TipoDocuEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}