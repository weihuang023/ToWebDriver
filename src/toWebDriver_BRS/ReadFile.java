package toWebDriver_BRS;
import java.io.*;

public class ReadFile {
	 public static void main(String[] args)throws Exception {
		 
		 File file = new File("C:\\Results_PG5_PO_18F.txt");
		 BufferedReader br = new BufferedReader(new FileReader(file));
		 String st;
		 while ((st = br.readLine()) != null) System.out.println(st);
		 
		 br.close();
		 
	  }
}
