package es.rosamarfil.client;

import es.rosamarfil.soap.SOAPI;
import es.rosamarfil.soap.SOAPImplService;
import es.rosamarfil.soap.User;

import java.util.List;

public class UserClient {
	public static void main(String[] args) {
		try {
			// Conecta con el servicio SOAP publicado
			SOAPImplService service = new SOAPImplService();
			SOAPI userService = service.getSOAPImplPort();

			// Obtiene usuarios
			List<User> users = userService.getUsers();
			System.out.println("Usuarios existentes:");
			for (User u : users) {
				System.out.println(u.getName() + " " + u.getUsername());
			}
			User nuevo = new User();
			nuevo.setName("Pablo");
			nuevo.setUsername("Ruiz");


			// Muestra usuarios actualizados
			System.out.println("Usuarios después de agregar:");
			for (User u : userService.getUsers()) {
				System.out.println(u.getName() + " " + u.getUsername());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
