import java.util.ArrayList;

class CustomerQueue implements IQueue
{
	public final static int add = 11;
	public final static int remove = 12;

	Controler control;

	ArrayList<CustomerLocation> locationList = new ArrayList<CustomerLocation>();
	ArrayList<Trolley> trolleyList = new ArrayList<Trolley>();
	long lostCustomerNum = 0;
	int maximiumTrolleyNum = CheckoutTest.maxQueueSize;
	int id;
	long totalProductsNum = 0;
	long totalWaitTime = 0; 
	long customerNum = 0;
	double utiliztion = 0.0; 
	long totalWorkTime = 0;
	long totalIdleTime = 0;
	long beginWaitTime = 0;
	long endWaitTime =  0;
	boolean isWaiting = false;

	CustomerQueue(CustomerLocation que[])
	{
		//each trolley relates to an UI panel
		for(int i = 0; i < maximiumTrolleyNum; i++)
		{
			locationList.add(que[i]);
		}
	}

	//call the calculate method of control class to get the number of all threads and print
	public void setControler(Controler cont)
	{
		control = cont;
	}
	
	//producer thread calls this method to add trolleys
	public synchronized void addCustomer(ArrayList<Trolley> addList)
	{
		//if the chosen queue is full, wait
		while(trolleyList.size() >= CheckoutTest.maxQueueSize)
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
		}
		
		//get the end waiting time
		if(isWaiting)
		{
			endWaitTime   = System.currentTimeMillis();
			isWaiting = false;
			totalIdleTime  += (endWaitTime - beginWaitTime);
		}
		
		//add the random amount of trolleys to the queue
		Trolley trolley;
		for(int i = 0; i < addList.size(); i++)
		{	 
			//if the queue is not longer than 'maximiumTrolleyNum', add trolley to it
			if( trolleyList.size() < maximiumTrolleyNum )
			{
				System.out.println("\nQueue " + id + " addCustomer \n");
				trolley = addList.get(i);
				trolleyList.add(trolley);
				updateGUI(trolleyList.size()-1, trolley.getProductNum(), add);
				trolley.setEnterQueueTime(System.currentTimeMillis());//get the time of entering to the queue
			}
			//customer leave if the queue is longer than 'maximiumTrolleyNum'
			else 
			{ 
				lostCustomerNum = lostCustomerNum + 1;
				System.out.println("\nQueue " + id + " lostCustomerNum:" + lostCustomerNum + '\n');
			}
		}
		
		notifyAll();//after adding trolleys to the queue, notify 
	}

	public synchronized void checkoutCustomer()
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
		trolley.setBeginCheckTime(System.currentTimeMillis());//get the begin checkout time
		totalProductsNum += trolley.getProductNum();
		totalWaitTime += trolley.getWaitTime();//calculate the total waiting time of customers
		
		//print 
		System.out.println("\n****************** results *******************");
		System.out.println("Queue " + id + " Waiting time :" + trolley.getWaitTime() + '\n');
		System.out.format("Queue " + id + " utilization %f \n", this.getUtilization());
		control.calculate();
		System.out.println("\nQueue " + id + " checkout... \n");
		
		trolley.checkout();//checkout
		totalWorkTime += trolley.getCheckoutTime();//calculate the total work time of the checkout
		updateGUI(trolleyList.size()-1, trolley.getProductNum(), remove);//change the GUI
		trolleyList.remove(0);//remove the trolley from the list
		customerNum++;//calculate the customer processed number
		
		notifyAll();//once remove trolley from the queue, notify 
	}

	//set the id of the checkout
	public void setID(int id)
	{
		this.id = id;
	}

	public int getSize()
	{
		return trolleyList.size();
	}

	public long getLostCustomerNum()
	{
		return lostCustomerNum;
	}

	public long getCustomerNum()
	{
		return customerNum;
	}

	public long getTotalWaitTime()
	{
		return totalWaitTime;
	}

	public double getUtilization()
	{
		if( totalWorkTime <= 0 )
		{
			return 0.0;
		}
		utiliztion = totalWorkTime*1.0/(totalIdleTime + totalWorkTime);
		return utiliztion;
	}

	public long getTotalProductsNum()
	{
		return totalProductsNum;
	}
		
	// update GUI 
	void updateGUI(int index, int num, int flag)
	{
		if(flag == add)//add trolley
		{					
			locationList.get(index).setNumberofGoods(num);//add trolley panel
			try
			{
				Thread.sleep(30);
			}catch(InterruptedException e){}	
		}
		else if(flag == remove)//remove trolley
		{
			for(int i = 0; i < index; i++)//make the dynamic effect
			{
				locationList.get(i).setNumberofGoods(0);
				
				try
				{
					Thread.sleep(300);
				}catch(InterruptedException e){}

				Trolley trolley = trolleyList.get(i);
				locationList.get(i).setNumberofGoods(trolley.getProductNum());
			}	
			locationList.get(index).setNumberofGoods(0);//remove the last trolley on the queue
		}
	}
}