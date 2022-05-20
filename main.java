package mod;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class main{
	public static void main(String[] args) {
	File Branch = new File("/Users/tarik/Documents/CodeCompare");//branch that contains files to compare to
	File Main = new File("/Users/tarik/Documents/base.txt");//file being compared to
	FileCompare Lab7 = new FileCompare(Main, Branch);
	System.out.println(Lab7.FindSimilar());
	}
}
	

