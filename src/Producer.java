import java.util.ArrayList;
import java.util.Random;

class Producer implements Runnable
{
	//every 'intervalTime' amount of time produce a range number of Trolley objects
	public final static int intervalTime = 1;
	//trolleys on the quick queue have at most 'maxProductNum' number of products
	public final static int maxProductNum =5;
	int queueNum = CheckoutTest.checkOuts;
	int quickQueueNum = Controler.quickCheckoutNum; 
	ArrayList<IQueue> queueList = new ArrayList<IQueue>();

	//produce few trolleys put on a list, then put them on the queue once
	ArrayList<Trolley> trolleyList = new ArrayList<Trolley>();//trolleys have more than 'maxProductNum' number of products
	ArrayList<Trolley> quickTrolleyList = new ArrayList<Trolley>();//trolleys have no more than 'maxProductNum' number of products

	Producer(ArrayList<IQueue> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			queueList.add(list.get(i));
		}
	}

	public void run()
	{
		Random random = new Random();
		while(true)
		{
			int num = random.nextInt(60);
			
			quickTrolleyList.clear();
			trolleyList.clear();
			Trolley trolley;
			//produce a random number of trolleys once
			for(int i = 0; i < num; i ++)
			{	
				trolley = new Trolley();				
				if( trolley.getProductNum() <= maxProductNum)//put on the quick trolley list
				{
					quickTrolleyList.add(trolley);
				}
				else
				{
					trolleyList.add(trolley);
				}
				
			}

			//put products on the current shortest queue
			int queueIndex = 0;
			int mimNum = (queueList.get(queueIndex)).getSize();	
			int customerNum;	
			//search for the shortest queue
			for(int i = queueIndex+1; i < queueNum-quickQueueNum; i++ )
			{
				customerNum = (queueList.get(i)).getSize();
				if(customerNum < mimNum)
				{
					mimNum = customerNum;
					queueIndex = i;
				}
			}
			
			//add trolleys to the shortest queue
			(queueList.get(queueIndex)).addCustomer(trolleyList);

			//search for the shortest quick queue
			queueIndex = queueNum-quickQueueNum;
			mimNum = (queueList.get(queueIndex)).getSize();
			for(int i = queueIndex+1; i < queueNum; i++)
			{
				customerNum = (queueList.get(i)).getSize();
				if(customerNum < mimNum)
				{
					mimNum = customerNum;
					queueIndex = i;
				}
			}
			//add customers to the shortest quick queue
			(queueList.get(queueIndex)).addCustomer(quickTrolleyList);
						
			try 
			{
				Thread.sleep(random.nextInt(intervalTime)+1);
			} 
			catch (InterruptedException e){}
		}
    }
}