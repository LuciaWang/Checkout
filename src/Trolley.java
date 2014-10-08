import java.util.Random;

class Trolley
{
	int productNum;
	long timeEnterQueue;
	long waitTime;
	long timeBeginCheckout;
	long checkoutTime;

	Trolley()
	{
		checkoutTime = 0;
		Random random = new Random();
		productNum = random.nextInt(199) + 1;//1-200
	}

	int getProductNum()
	{
		return productNum;
	}

	void setEnterQueueTime(long time)
	{
		timeEnterQueue = time;
	}

	
	void setBeginCheckTime(long time)
	{
		timeBeginCheckout = time;
	}

	long getWaitTime()
	{
		return timeBeginCheckout-timeEnterQueue;
	}

	long getCheckoutTime()
	{
		return checkoutTime;
	}

	 void checkout()
	 {
		waitTime = System.currentTimeMillis() - timeEnterQueue;
		for(int i = 0; i < productNum; i++)
		{
			Random random = new Random();	
			checkoutTime = random.nextInt(600-50+1)+50;//(6000-500+1)+1;// product checkout time during 500ms -6000ms 
			try 
			{
				Thread.sleep( checkoutTime );
			} 
			catch (InterruptedException e){}
		}
	 }
}