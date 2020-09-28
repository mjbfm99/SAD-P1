import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

	public EditableBufferedReader(Reader in) {
		super(in);
		setRaw();
	}
	
	public void setRaw() {
	    String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
	       try {
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unsetRaw(){
		String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
	       try {
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int read() throws IOException {
		int ret = 0;
		setRaw();
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
