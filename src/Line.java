
public class Line {
	private int cur_pos;
	private String content;
	
	public Line() {
		cur_pos = 0;
		content = "";
	}
	
	public int getPosition() {
		return cur_pos;
	}
	
	public void setPosition(int position) {
		//x = position % columns;
		//y = position / columns;
		cur_pos = position;
	}
	
	public void incPosition() {
		if(cur_pos != content.length()) {
			cur_pos++;
		}
	}
	
	public void decPosition() {
		if(cur_pos != 0) {
			cur_pos--;
		}
	}
	
	public void goToEnd() {
		cur_pos = 0;
	}
	
	public void goToHome() {
		cur_pos = content.length();
	}
	
	public void backspace() {
		//Delete last character
		if(content.length() != 0) {
			content = content.substring(0, content.length() - 1);
			cur_pos--;
		}
	}
	
	public String getContent() {
		return content;
	}
	
	public void addChar(char c) {
		content = content + Character.toString(c);
		incPosition();
	}
	
	public String toString() {
		return content;
	}
}
