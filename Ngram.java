import java.util.regex.*;
import java.util.*;
import java.io.*;

public class Ngram {
	static String text;
	
	public void load(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
                text = text + "#&" + line;
            }
			reader.close();
		}
		catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}

    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
	public void emoticonDetect() {
		String delimiters1 = "#&";
			String[] row = text.split(delimiters1);
			text = null;
			for(int j = 0; j < row.length; ++j) {
    Pattern patt1 = Pattern.compile(":\\-D|:C|:\\-\\)|:\\)|:o\\)|:\\]|:3|:c\\)|:>|=\\]|8\\)|=\\)|:\\}|:^\\)|ğŸ˜€|ğŸ˜‚|ğŸ˜ƒ|ğŸ˜„|ğŸ˜…|ğŸ˜†|ğŸ˜‡|ğŸ˜ˆ|ğŸ˜‰|ğŸ˜Š|ğŸ˜‹|ğŸ˜Œ|ğŸ˜?|ğŸ˜|ğŸ˜—|ğŸ˜˜|ğŸ˜™|ğŸ˜š|ğŸ˜›|ğŸ˜œ|ğŸ˜¸|ğŸ˜¹|ğŸ˜º|ğŸ˜»|ğŸ˜¼|ğŸ˜½");
    Pattern patt2 = Pattern.compile(":\\-\\(|:\\(|:c|:<|:\\[|:\\{|:\\-P|Pos•|Pos‘|Pos³|Pos®|Pos¯|Pos¶|Pos´|Posµ|Pos²|Pos’|Pos“|Pos”|Pos–|Pos|PosŸ|Pos¡|Pos¢|Pos£|Pos¤|Pos¥|Pos¦|Pos§|Pos¨|Pos©|Posª|Pos«|Pos¬|Pos­|Pos¾|Pos¿|Pos°|Pos±|ğŸ™€");
    String replace1 = "zxp";
    String replace2 = "zxn";

      Matcher m = patt1.matcher(row[j]);
      while (m.find()) {
        row[j] = m.replaceAll(replace1);
      }
      Matcher n = patt2.matcher(row[j]);
      while (n.find()) {
        row[j] = n.replaceAll(replace2);
      }
		text = text+"#&"+row[j];
    }
  }

    public static void main (String[] args) throws Exception {
		Ngram obj;
		PrintWriter writer2 = new PrintWriter("trigrams.txt");
		if (args.length < 1)
	System.out.println("Usage: Please enter input file name <file>");
		else {
			obj = new Ngram();
			obj.load(args[0]);
			obj.emoticonDetect();
			//for (int n = 1; n <= 3; n++) {
			int n=3;
			String delimiters1 = "#&";
			String[] row = text.split(delimiters1);
			//text=null;
			for(int j = 0; j < row.length; ++j) {
            for (String ngram : ngrams(n, row[j]))
                writer2.println(ngram);
				}//}
				System.out.println("trigrams file generated.");
		}}
    }