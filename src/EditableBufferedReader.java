import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

	public EditableBufferedReader(Reader in) throws InterruptedException, IOException {
		super(in);
	}
	
	public void setRaw() throws InterruptedException, IOException {
	    String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
	    Runtime.getRuntime().exec(cmd).waitFor();
	}

	public void unsetRaw(){
		try {
			Runtime.getRuntime().exec("stty -echo cooked");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int read() throws IOException {
		int ret = 0;
		try {
			setRaw();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			System.out.print(super.read() + " ");
		}
	}
	
	public String readLine() throws IOException {
		String str = null;
		int c;
		c = read();
		System.out.println(c);
		return str;
	}
} 
