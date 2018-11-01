import java.io.*;
import java.util.Scanner;
import java.net.*;

public class Server{
		private final int port;
		private String close;
		// sockets
		private ServerSocket sock;
		private Socket serviceSocket = null;
		private DataInputStream take;
		private Writer give;
		
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
				open();
				new Thread(new Runnable(){
						public void run(){
						while(serviceSocket != null){
								String input = recieve(); 
								if(input.trim().toLowerCase().equals(close)){
										break;
								}
								if(input.length() >= 2){
										input = input.substring(0, input.length() - 2);
								}
								String output = tty(input);
								send(output);
						}
				close();
		}
				}).start();
		}

		
		public void open(){
				try{
						sock = new ServerSocket(port);
						serviceSocket = sock.accept();
						take = new DataInputStream(serviceSocket.getInputStream());
						give = new BufferedWriter(new OutputStreamWriter(serviceSocket.getOutputStream(), "UTF-8"));
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
				try{
						give.write(text, 0, text.length());
						give.flush();
				}
				catch(IOException e){
						System.out.println(e);
				}
		}

		public String recieve(){
				Scanner s = new Scanner(take).useDelimiter("\\A");
				return s.hasNext() ? s.next() : "";
		}

		private String tty(String input){
				String output = "";
				try{
						ProcessBuilder pb = new ProcessBuilder();
						pb.redirectErrorStream(true);
						pb.command("/bin/bash", "-c", input);
						Process p = pb.start();
						InputStream is = p.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line = null;
						while ((line = br.readLine()) != null){
								output = output + line;
						}
						p.waitFor();
				}
				catch(InterruptedException ie){
						System.out.println(ie);
				
				}
				catch(IOException e){
						System.out.println(e);
				}
				return output;
		}
}

