import java.lang.StringBuilder;

public class Line { // Model
	private int cur_pos;
	//private String content;
	private StringBuilder sb;
	private boolean insertMode;
	
	public Line() {
		cur_pos = 0;
		sb = new StringBuilder("");
		insertMode = false;
	}
	
	public int getPosition() {
		return cur_pos;
	}
	
	public void toggleInsert() {
		insertMode = !insertMode;
	}

	public int getLength() {
		return sb.length();
	}
	
	public void setPosition(int position) {
		//x = position % columns;
		//y = position / columns;
		cur_pos = position;
	}
	
	public void incPosition() {
		if(cur_pos < sb.length()) {
			cur_pos++;
		}
	}
	
	public void decPosition() {
		if(cur_pos != 0) {
			cur_pos--;
			// System.out.print("\033[H\033[2J");
			// System.out.print(sb);
			// System.out.print("\033[2D");
			// System.out.print("\033[s");
		}
	}
	
	public int goToHome() { // move you to the begin of the line = cursor pos
								// and reset de var
		int aux=cur_pos;
		cur_pos = 0;
		return aux;
	}
	
	public int goToEnd() {
        return getLength() - getPosition();
	}
	
	public void backspace() {
		//Delete last character
		if(sb.length() != 0) {
			//content = content.substring(0, content.length() - 1);
			sb.deleteCharAt(cur_pos - 1);
			decPosition();
		}
	}
	
	public void del() {
		if(sb.length() != 0 && getPosition() != sb.length()) {
			sb.deleteCharAt(cur_pos);
		}
	}
	
	public String getContent() {
		return sb.toString();
	}
	
	public void addChar(char c) {
		if(!insertMode) {
			sb.insert(cur_pos, c); 
			incPosition();
		}
		else {
			System.out.print("\033[P");
			sb.insert(cur_pos, c);
			if(cur_pos + 1 < sb.length()) {
				sb.deleteCharAt(cur_pos + 1);
			}
			incPosition();
		}
	}
	
	public String toString() {
		return "Line: " + sb + ", position: " + getPosition();
	}
}
