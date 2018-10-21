import java.io.*;
import java.net.*;

public class Server{
		private final int port;
		private String close;
		// sockets
		private ServerSocket sock;
		private Socket serviceSocket = null;
		private DataInputStream take;
		private PrintStream give;
		
		//constructors
		public Server(int port, String close){
				this.port = port;
				this.close = close;
		}

		public Server(int port){
				this.port = port;
				this.close = "quit";
		}

		public Server(String close){
				this.close = close;
				this.port = 1992;
		}

		public Server(){
				this.close = "quit";
				this.port = 1992;
		}

		//functions
		public void run(){
		
		}
		
		public void open(){
				try{
						sock = new ServerSocket(port);
						serviceSocket = sock.accept();
						take = new DataInputStream(serviceSocket.getInputStream());
						give = new PrintStream(serviceSocket.getOutputStream());
				}
				catch(IOException e){
						System.out.println(e);
				}
		}

		public void close(){
				try{
						give.close();
						take.close();
						serviceSocket.close();
						sock.close();
				}
				catch(IOException e){
						System.out.println(e);
				}
		}
		
		public void send(String text){

		}

		public String recieve(){
				return "hello";
		}

		private String tty(String input){
				return "helo";
		}
}
