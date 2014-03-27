package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

	private boolean continuar;

	public ServerChat() {
		continuar = true;
		try {
			ServerSocket socket = new ServerSocket(35557);
			System.out.println("Servidor del chat por turnos");
			System.out.println("Esperando que se conecte el cliente");

			Socket cliente = socket.accept();
			System.out.println("Conectado con cliente "
					+ cliente.getInetAddress());
			System.out.println("Para salir escribe q! y return");

			DataInputStream bufferEntrada = new DataInputStream(
					cliente.getInputStream());
			DataOutputStream bufferSalida = new DataOutputStream(
					cliente.getOutputStream());

			while (continuar) {
				leerCliente_y_Mostrar(bufferEntrada);
				if (continuar)
					leerTeclado_y_Enviar(bufferSalida);
			}

			bufferSalida.writeUTF("q!");
			bufferEntrada.close();
			bufferSalida.close();
			cliente.close();
			socket.close();
			System.out.println("Programa finalizado");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void leerCliente_y_Mostrar(DataInputStream bufferEntrada)
			throws IOException {
		System.out.println("Esperando mensaje del cliente...");
		String datos = bufferEntrada.readUTF();

		if (datos.length() > 0)

			if (datos.equals("q!")) {
				System.out.println("El cliente ha cerrado la conexion.");
				continuar = false;
			} else
				System.out.println("Recibido: " + datos);
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

	public static void main(String[] args) {
		new ServerChat();

	}

}