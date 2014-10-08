import java.util.ArrayList;

class Controler
{    
	public final static int quickCheckoutNum = 1;//2
	int checkoutNum = CheckoutTest.checkOuts;
	int maxQueneSize = CheckoutTest.maxQueueSize;

    Producer producer;
	ArrayList<Checkout> checkoutList = new ArrayList<Checkout>();
	ArrayList<IQueue> queueList = new ArrayList<IQueue>();

	Controler(CustomerLocation que[][])
	{
		//new checkouts
		for(int i = 0; i < checkoutNum; i++) 
		{ 
			//the last 'quickCheckoutNum' amount of checkouts have quick customer queue
			if(i >= checkoutNum-quickCheckoutNum)
			{
				QuickCustomerQueue quickQueue = new QuickCustomerQueue(que[i]);
				quickQueue.setID(i);
				quickQueue.setControler(this);
				queueList.add(quickQueue);
				checkoutList.add(new Checkout(quickQueue));
			}
			else
			{
				CustomerQueue queue = new CustomerQueue(que[i]);
				queue.setID(i);
				queue.setControler(this);
				queueList.add(queue);
				checkoutList.add(new Checkout(queue));
			}
		}
       
		//new producer
        producer = new Producer(queueList);
	}

	void control()
	{
	 	Thread threadProducer = (new Thread(producer));
	 	//there's only one producer thread, so give it higher priority to avoid it haven't been allocated time slice for a long time
		threadProducer.setPriority(5);
		threadProducer.start();//start producer thread
		
		//start checkout threads
		for(int i = 0; i < checkoutList.size(); i++)
		{
			(new Thread(checkoutList.get(i))).start();
		}
	}

	void calculate()//calculate the data needed
	{
		long totalProductsNum = 0;
		long totalCustomersNum = 0;
		double totalUtiliztion = 0.0;
		long totalWaitTime = 0;
		
		//add the data of each checkout
		for(int i = 0; i < checkoutNum; i++)
		{
			totalProductsNum += (queueList.get(i)).getTotalProductsNum();
			totalCustomersNum += (queueList.get(i)).getCustomerNum();
			totalUtiliztion += (queueList.get(i)).getUtilization();
			totalWaitTime += (queueList.get(i)).getTotalWaitTime();
		}
		
		//total products processed
		System.out.println("\ntotal products processed : " + totalProductsNum);
		//average customer wait time
		double avarageWaitTime = 0.0;
		if(totalCustomersNum != 0)
			avarageWaitTime = totalWaitTime*1.0/totalCustomersNum;	
		System.out.println("\naverage wait time : " + avarageWaitTime);
		//average checkout utilization
		double averageUtilization = 0.0;
		averageUtilization = totalUtiliztion/checkoutNum;
		System.out.println("\naverage utilization : " + averageUtilization);
		//average products per trolley
		double averageProducts = 0.0;
		if(totalCustomersNum != 0)
			averageProducts = totalProductsNum*1.0/totalCustomersNum;
		System.out.println("\naverage products per trolley : " + averageProducts);
		System.out.println("***********************************************\n");
	}
}