/* Tarik Metin
 * 5/20/2022
 * Takes a base text file
 * Takes a directory(branch) of text files
 * Returns the path of the file in the directory most similar to the base file
 */

package mod;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class FileCompare {
	private File Main;//file being compared to
	private File[] Files;//array to hold files
	private String[] Names;//names of files
	
	private TreeMap<File, Double>ScoreChart = new TreeMap<File, Double>();//Keeps track of similarity score associated with each file
	private TreeMap<String,Integer> WordsMain = new TreeMap<String,Integer>();//Words in main
	private TreeMap<String,Integer> WordsFile = new TreeMap<String,Integer>();//Used to temporarily map words in file being compared to 
	
	
	FileCompare(File Main, File Branch){
		this.Main = Main;
		Names = Branch.list();//get names of files in directory
		Files = new File[Names.length];
		for (int i = 0; i < Names.length; i++) {
			Files[i] = new File(Branch.getPath() + "/" + Names[i]);//append each file in directory to file list
			//System.out.println(Files[i].getPath());
		}
	}
	
	public File FindSimilar(){
		ReadFile(WordsMain, Main);
		for (File X : Files) {//Iterate files
			ReadFile(WordsFile, X);//Get all words and quantity in file
			Double SimilarityScore = 0.0;
			for (String Y: WordsMain.keySet()) {//Iterate over words in base file
				if(WordsFile.containsKey(Y)) {//If current file also contains
					SimilarityScore += GetScore(WordsFile.get(Y));//increment the similarity score
				}
			}
			ScoreChart.put(X, SimilarityScore);//Record the similarity score
		}
		double MaxValue = 0;
		File MaxFile = null;
		
		for (File X: ScoreChart.keySet()) {//O(n) find maximum
			if (ScoreChart.get(X) > MaxValue) {
				MaxFile = X;
				MaxValue = ScoreChart.get(X);
			}
		}
		return MaxFile;
	}
	
	private static double GetScore(int Quantity) {
		int Inclusion = 10; //Number of points awarded for simply sharing strings
		int Scalar = 4; //constant multiplied by ln of quantity before adding to score total
		return (Inclusion + Scalar* Math.log(Quantity));
	}
	
	private static void ReadFile(TreeMap<String,Integer> Words, File X) {
		Words.clear();
		Scanner input;//Declares scanner instance used for reading file
		try {
			input = new Scanner(X);//initializes input with file passed in to ReadFile method
			while (input.hasNext()) {//Iterates over every line of the file
			    String Word = input.next().strip().toLowerCase();
			    char[] KeyChars = {';',')','(',',','!','}','{','/','.',']','[','\"','+','-',
									'-','#','&','|',':','\\','<','>','=','*','_'};
			    for (char C: KeyChars) {
			    	Word.replace(Character.toString(C), "");
			    }

			    if (Words.containsKey(Word)){
			    	int Z = Words.get(Word);
			    	Words.put(Word, Z++);
			    }
			    else {
			    	Words.put(Word, 1);
			    }
			}
			input.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found, try again");
		}
	}
}

