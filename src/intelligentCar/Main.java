package intelligentCar;


public class Main {
	
	public static void main(String[] args) throws InterruptedException
	{
		IntelligentCar rutine = new IntelligentCar();
		
		try {
			rutine.run();
		} catch (Exception ex) {
			
			LCDHandler.notifyException(ex);
			Thread.sleep(3000);
		}
	}
}
