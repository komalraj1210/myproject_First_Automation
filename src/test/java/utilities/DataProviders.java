package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	// DataProvider
	@DataProvider(name="Login_data")
	public String[][] getdata() throws IOException
	{
		
		String path=".\\testData\\Login_testData.xlsx";
		ExcelUtility xlutil = new ExcelUtility(path);
		int totalrows=xlutil.getrowcount("Sheet1");
		int totalcells=xlutil.getcellcount("Sheet1", 0);
		String logindata[][]= new String[totalrows][totalcells];
		
		for(int i=1;i<=totalrows;i++)
		{
			for(int j=0;j<totalcells;j++)
			{
				logindata[i-1][j]=xlutil.getcelldata("Sheet1", i, j);
			}
		}
		
		return logindata;
		
	}
	
	

}
