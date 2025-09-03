package A2;

public class Servicio {
	private final Notificador notificador;
	
	public Servicio(Notificador notificador) { // DIP utilizamos lo que nos pide la interfaz
		this.notificador = notificador;
	}
	
	public void EnviarNotifiacion(String Mensaje) {
		notificador.enviar(Mensaje);
	}
	
	public static void main(String[] args) {
		System.out.println("Enviando Correo -------------");
		Notificador notificadordeCorreo = new EnviarCorreo();
		Servicio ServicioCorreo = new Servicio(notificadordeCorreo);
		ServicioCorreo.EnviarNotifiacion("Este es un correo del Ingeniero Mono");
		
		System.out.println("Enviando Sms -------------");
		Notificador notificadordeSms = new EnviarSms();
		Servicio ServicioSms = new Servicio(notificadordeSms);
		ServicioSms.EnviarNotifiacion("Este es un correo del Ingeniero Palomo");
		
		
	}
	
	
	
	
	
	
}
