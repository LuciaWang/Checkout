import java.util.ArrayList;


interface IQueue
{	
	void checkoutCustomer();
	void addCustomer(ArrayList<Trolley> trolleyList);//Trolley trolley
	int getSize();
	long getLostCustomerNum();
	double getUtilization();
	long getTotalProductsNum();
	long getTotalWaitTime();
	long getCustomerNum();
	void setControler(Controler cont);
}