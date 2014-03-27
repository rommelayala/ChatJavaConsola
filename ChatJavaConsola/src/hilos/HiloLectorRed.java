package hilos;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloLectorRed extends Thread {

	private DataInputStream bufferEntrada;

	public HiloLectorRed(Socket socket) throws IOException {
		bufferEntrada = new DataInputStream(socket.getInputStream());
	}

	public void run() {
		boolean continuar = true;
		while (continuar) {
			String datos = null;
			try {
				datos = bufferEntrada.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (datos.length() > 0)
				if (datos.equals("q!")) {
					continuar = false;
					System.out
							.println("El otro extremo solicita cierre de conexion. Confirme con q!");
				} else
					System.out.println("Recibido: " + datos);
		}
	}
}