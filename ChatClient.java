//https://github.com/1nhee/SimpleChat.git
	
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.*;
import java.util.Date;

public class ChatClient {

	public static void main(String[] args) {
		if(args.length != 2){
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			String today = (new SimpleDateFormat("H:mm:ss").format(date));
			String time = "["+ today + "] ";
			System.out.println(time + "Usage : java ChatClient <username> <server-ip>");
			System.exit(1);
		}
		Socket sock = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		boolean endflag = false;
		try{
			sock = new Socket(args[1], 10001);
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			pw.println(args[0]);
			pw.flush();
			InputThread it = new InputThread(sock, br);
			it.start();
			String line = null;
			while((line = keyboard.readLine()) != null){
				pw.println(line);
				pw.flush();
				
				if(line.equals("/quit")){
						endflag = true;
						break;
					}
			}
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			String today = (new SimpleDateFormat("H:mm:ss").format(date));
			String time = "["+ today + "] ";
			System.out.println(time + "Connection closed.");
		}catch(Exception ex){
			if(!endflag)
				System.out.println(ex);
		}finally{
			try{
				if(pw != null)
					pw.close();
			}catch(Exception ex){}
			try{
				if(br != null)
					br.close();
			}catch(Exception ex){}
			try{
				if(sock != null)
					sock.close();
			}catch(Exception ex){}
		} // end of finally
	} // end of main
} // end of class

class InputThread extends Thread{
	private Socket sock = null;
	private BufferedReader br = null;
	public InputThread(Socket sock, BufferedReader br){
		this.sock = sock;
		this.br = br;
	}
	
	public void run(){
		try{
			String line = null;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
		}catch(Exception ex){
		}finally{
			try{
				if(br != null)
					br.close();
			}catch(Exception ex){}
			try{
				if(sock != null)
					sock.close();
			}catch(Exception ex){}
		}
	} 
}//end of InputThread
