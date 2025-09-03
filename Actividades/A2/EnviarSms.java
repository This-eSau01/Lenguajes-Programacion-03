package A2;

public class EnviarSms implements Notificador{
	@Override
	public void enviar(String Mensaje) {
		System.out.println("Enviando SMS: " + Mensaje );
	}

}
