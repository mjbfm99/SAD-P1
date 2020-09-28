import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

	public EditableBufferedReader(Reader in) {
		super(in);
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
		// Usar scanner
		System.in.
		String arrobaComoCadena = Character.toString((char) ret);
		//System.out.print(arrobaComoCadena);
		return ret;
	}
	
	public String readLine() throws IOException {
		Line linea = new Line();
		setRaw();
		int ch = 0;
		do {
			ch = read();
			if(ch > 31) {
				linea.addChar((char) ch);
			}
		} while(ch != 13);
		unsetRaw();
		return Character.toString((char) read());
	}
} 
