package btHandling;

public class MessageMemory {

	String lastMessage;
	
	final String emptyMessage = "";
	
	public MessageMemory() {
		lastMessage = emptyMessage;
	}

	public boolean hasSavedMessage() {
		return lastMessage.equals(emptyMessage);
	}

	public String getMessage() {
		return lastMessage;
	}

	public void saveMessage(String message) {
		lastMessage = message.trim();
		
	}

	public void clearSavedMessage() {
		lastMessage = emptyMessage;
		
	}
	
	
}
