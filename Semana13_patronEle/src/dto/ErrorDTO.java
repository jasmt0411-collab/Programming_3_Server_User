package dto;

public class ErrorDTO {

    private boolean error;
    private int codigo;
    private String mensaje;

    public ErrorDTO(boolean error, int codigo, String mensaje) {
        this.error = error;
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public boolean isError() {
        return error;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }
}