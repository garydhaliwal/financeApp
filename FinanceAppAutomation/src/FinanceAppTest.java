import android.util.Log;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class FinanceAppTest extends UiAutomatorTestCase {
	
	//Declaring UI elements
	UiObject financeAppIcon = new UiObject(new UiSelector().text("Finance"));
	UiObject searchIcon = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/action_search"));
	UiObject searchBar = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/search_view_body"));		
	UiObject searchResult = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/indices"));

	UiObject currentPrice = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/quote_price"));		
	UiObject yearLow = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/stat_value_left/low"));		
	UiObject yearHigh = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/stat_value_right/high"));		
	UiObject eps = new UiObject(new UiSelector().resourceId("com.yahoo.mobile.client.android.finance:id/stat_value_right/eps"));		

	
	public void testStockData() throws UiObjectNotFoundException {
		
		//Navigate to the home screen and load Finance app
		getUiDevice().pressHome();
		financeAppIcon.click();

		
		//Retrieve WTW stock data
		double[] wtwStockData = retrieveStockData("WTW");
		
		
		//Output percentage comparisons to console
		Log.d("Output", "Todays price of $" + wtwStockData[0] +
				" is " + (wtwStockData[0]/wtwStockData[2] * 100) + "% lower than the 52 week high " +
						"and is " + (wtwStockData[0]/wtwStockData[1] * 100) + "% higher than the 52 week low");

		
		//Retrieve WDAY stock data
		double[] wdayStockData = retrieveStockData("WDAY");

		
		//Output higher EPS between WTW and WDAY
		Log.d("Output", "The higher EPS is " + Math.max(wtwStockData[3], wdayStockData[3]));
		
	}
	
	private double[] retrieveStockData(String symbol) throws UiObjectNotFoundException{
		
		double[] stockData = new double[4];
		
		//Search stock symbol and load details
		searchIcon.click();
		searchBar.setText(symbol);		
		searchResult.click();
		
		
		//Store stock data
		stockData[0] = Double.parseDouble(currentPrice.getText());
		stockData[1] = Double.parseDouble(yearLow.getText());
		stockData[2] = Double.parseDouble(yearHigh.getText());
		stockData[3] = Double.parseDouble(eps.getText());

		
		return stockData;
	}
	
}
