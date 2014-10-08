import java.util.ArrayList;

//inherit from CustomerQueue class, 
class QuickCustomerQueue extends CustomerQueue implements IQueue 
{
	QuickCustomerQueue(CustomerLocation que[])
	{
		super(que);//call constructor method of CustomerQueue class
	}

	public synchronized void addCustomer(ArrayList<Trolley> addList)// add trolley to the quick queue
	{
		Trolley trolley = new Trolley();
		for(int i = 0; i < addList.size(); i++)
		{
			while(addList.size() >= CheckoutTest.maxQueueSize)
			{
				if(!isWaiting)
				{
					beginWaitTime  = System.currentTimeMillis();
					isWaiting = true;
				}
				
				try
				{
					wait();
				}catch(InterruptedException e){}
				
				if(isWaiting)
				{
					endWaitTime   = System.currentTimeMillis();
					isWaiting = false;
					totalIdleTime  += (endWaitTime - beginWaitTime);
				}
			}
				
			if( trolleyList.size() < maximiumTrolleyNum )
			{
				trolley = addList.get(i);
				trolleyList.add(trolley);
				updateGUI(trolleyList.size()-1, trolley.getProductNum(), add);
				trolley.setEnterQueueTime(System.currentTimeMillis());
			}
			else //customer leave if the queue is too long
			{
				lostCustomerNum = lostCustomerNum + 1;
				System.out.println("\nQueue " + id + " lostCustomerNum:" + lostCustomerNum + '\n');
			}
		}
		notifyAll();
	}

	public synchronized void checkoutCustomer()// checkout from the quick queue
	{
		//if the queue is empty, wait
		while(trolleyList.isEmpty())
		{
			try
			{
				wait();
			}catch(InterruptedException e){}
		}
		
        Trolley trolley;
		trolley = trolleyList.get(0);//check out the first customer
		trolley.setBeginCheckTime(System.currentTimeMillis());
		totalProductsNum += trolley.getProductNum();
		totalWaitTime += trolley.getWaitTime();
		
		//print
		System.out.println("\n****************** results *******************");
		System.out.println("Queue " + id + " Waiting time :" + trolley.getWaitTime() + '\n');
		System.out.format("Queue " + id + " utilization %f \n", this.getUtilization());
		control.calculate();
		System.out.println("\nQueue " + id + " checkout... \n");
		
		trolley.checkout();
		totalWorkTime += trolley.getCheckoutTime();
		updateGUI(trolleyList.size()-1, trolley.getProductNum(), remove);
		trolleyList.remove(0);
		customerNum++;
		notifyAll();
	}
}
