class Checkout implements Runnable
{
	IQueue queue;
	float workTime;
	float freeTime;

	//programming with interface 
	Checkout(IQueue que)
	{
        queue = que;
		workTime = 0;
		freeTime = 0;
	}

    public void run()
	{	
		while(true)
		{
		//	System.out.format("Before Checkout customeer size = %d \n", queue.getSize());
			queue.checkoutCustomer();
		//	System.out.format("After Checkout customeer size = %d \n", queue.getSize());
		}
    }
}
