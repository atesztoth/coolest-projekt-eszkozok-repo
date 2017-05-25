package serverTest;

import java.net.*;
import java.util.*;
import java.io.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import torpedo.TorpedoServer;

/**
 * Class for testing the server.
 *
 * @author bpontandzsej
 */
public class ServerTest {
	
	private static Thread thread1;
	private static Thread thread2;
	private static Thread server;
	
	@Before
	public void startServer(){
		server = new Thread() {
            @Override 
			public void run() {
                try{
					TorpedoServer.main(null);
				} catch(Exception e){
					
				};
            }
		};
	}
	
	@Test
	public void testServer() throws IOException {
	    thread1 = new Thread() {
            @Override 
			public void run() {
                try{
					main(null);
				} catch(Exception e){
					
				};
            }
        };
		thread2 = new Thread() {
            @Override 
			public void run() {
                try{
					main(null);
				} catch(Exception e){
					
				};
            }
        };
		thread1.start();
		thread2.start();
		server.start();
		
		try{
			thread1.join();
			thread2.join();	
		} catch(Exception e){
					
		};
    }
	
		public static void main(String[] args) throws IOException, InterruptedException{
		final String gep = "localhost";
        final int port = 60504;
		
		Socket s = new Socket(gep, port);
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        Scanner sc = new Scanner(s.getInputStream());
		
		String valasz;
		String name;
		boolean myTurn;
		
		for (int i = 0; i < 5; i++) {
			valasz = sc.nextLine();				// client's name: FIRST || SECOND -- GOT IT
		}
		
		valasz = sc.nextLine();
        myTurn = valasz.equals("FIRST");
		
		if (myTurn) {
			name = "FIRST";
		} else {
			name = "SECOND";		
		}
		
		System.out.println(name + " megkapta a hajokat");
		
		
		if (myTurn) {												//FIRST's block
			valasz = "1 5";											//F => "1 5"
			pw.println(valasz);
			System.out.println(name + " kuldi: " + valasz);
			thread1.sleep(300);
		} else {													//SECOND's block
			valasz = sc.nextLine();									//S <= "1 5"
			assertEquals(valasz, "1 5");
			System.out.println(name + " kapja: " + valasz);
			if(valasz.equals("1 2")){								//false --> it will send "MISS"
				valasz = "HIT";
			} else {
				valasz = "MISS";
			}
			pw.println(valasz);										//S => "MISS"
			System.out.println(name + " kuldi: " + valasz);
			thread2.sleep(300);
			valasz = "2 4";
			pw.println(valasz);										//S => "2 4"
			System.out.println(name + " kuldi: " + valasz);
			thread2.sleep(300);
		}
		
		if (myTurn) {												//FIRST's block
			valasz = sc.nextLine();									//F <= "MISS"
			assertEquals(valasz, "MISS");
			System.out.println(name + " kapja: " + valasz);
			valasz = sc.nextLine();									//F <= "2 4"
			assertEquals(valasz, "2 4");
			System.out.println(name + " kapja: " + valasz);
			if(valasz.equals("2 4")){								//true --> it will send "VESZTETTEM"
				valasz = "VESZTETTEM";
			} else {
				valasz = "MISS";
			}
			pw.println(valasz);										//F => "VESZTETTEM"
			System.out.println(name + " kuldi: " + valasz);
			thread2.sleep(300);
		} else {													//SECOND's block
			valasz = sc.nextLine();									//S <= "VEGE"
			assertEquals(valasz, "VEGE");
			System.out.println(name + " kapja: " + valasz);
		}
	}	
}
