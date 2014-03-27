package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteChat {
	private boolean continuar;

	public ClienteChat() {
		continuar = true;
		try {
			Socket socket = new Socket("localhost", 35557);
			System.out.println("conectado al servidor");
			System.out
					.println("Bienvenido al chat por turnos (para salir escribe q! y return)");

			DataOutputStream bufferSalida = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream bufferEntrada = new DataInputStream(
					socket.getInputStream());

			while (continuar) {
				leerTeclado_y_Enviar(bufferSalida);
				if (continuar)
					leerServidor_y_Mostrar(bufferEntrada);
			}

			bufferSalida.writeUTF("q!");
			bufferEntrada.close();
			bufferSalida.close();
			socket.close();
			System.out.println("Programa finalizado");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void leerTeclado_y_Enviar(DataOutputStream bufferSalida)
			throws IOException {
		System.out.println("Tu turno de escritura...");
		Scanner finalizar = new Scanner(System.in);
		String datos = finalizar.nextLine();

		if (datos.equals("q!")) {
			continuar = false;
		} else {
			if (datos.length() > 0)
				bufferSalida.writeUTF(datos);
		}
	}

	private void leerServidor_y_Mostrar(DataInputStream bufferEntrada)
			throws IOException {
		System.out.println("Esperando mensaje del servidor...");
		String datos;
		datos = bufferEntrada.readUTF();
		if (datos.length() > 0) {
			if (datos.equals("q!")) {
				continuar = false;
				System.out.println("El servidor ha cerrado la conexion.");
			} else
				System.out.println("Recibido: " + datos);
		}
	}

	public static void main(String[] args) {
		new ClienteChat();
	}

}