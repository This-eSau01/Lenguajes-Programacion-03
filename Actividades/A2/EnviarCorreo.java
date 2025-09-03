package A2;

public class EnviarCorreo implements Notificador {
	@Override
	public void enviar(String Mensaje) {
		System.out.println("Enviando Correo: " + Mensaje);
	}
	
	
}
