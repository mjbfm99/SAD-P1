import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

	private final static int DERECHA = -62;
	private final static int IZQUIERDA = -61;
	private final static int ARRIBA = -64;
	private final static int ABAJO = -63;
	private final static int HOME = -57;
	private final static int END = -59;
	private final static int SUPR = -3;
	private final static int BACKSPACE = -2;
	private final static int ESC = -102;
	private final static int CTRL_C = 3;
	
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
		int ret = super.read();
		
		if(ret == 27) {
			ret = super.read();
			ret = super.read();
				if(ret != 51) {
					ret = ret - 129;
				}
				else {
					ret = super.read() - 129;
				}
		}
		else if(ret == 127) {
			ret = ret - 129;
		}
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
			else {
				switch (ch) {
				case DERECHA:
					linea.incPosition();
					break;
				case IZQUIERDA:
					linea.decPosition();
					break;
				case HOME:
					linea.goToHome();
					break;
				case END:
					linea.goToEnd();
					break;
				case BACKSPACE:
					System.out.print("\b\b\b   ");
					System.out.print("\b\b\b");
					linea.backspace();
					break;
				default:
					break;
				}
					
			}
		} while(ch != 13);
		System.out.print("\b\b  ");
		unsetRaw();
		return linea.getContent();
	}
} 
