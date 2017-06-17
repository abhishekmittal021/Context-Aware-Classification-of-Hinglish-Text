import java.util.regex.*;
import java.io.*;

public class NgramsClassifier {
	String text;
	SWN3 sentiwordnet = new SWN3();
	
			int cpp=0;
			int totpos=0;
			int prepos=0;

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
	/*public void emoticonDetect() {
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
  }*/

	public String detectSentiment() {
	
		try {
			PrintWriter writer1 = new PrintWriter("PosTrigrams.txt");
			PrintWriter writer2 = new PrintWriter("NegTrigrams.txt");
			String delimiters1 = "#&";
			String[] rows = text.split(delimiters1);
			for(int j = 0; j < rows.length; ++j) {
			int count = 0;
			String delimiters2 = "\\W";
			String[] tokens = rows[j].split(delimiters2);
			String feeling = "";
			for (int i = 0; i < tokens.length; ++i) {
				if (!tokens[i].equals("")) {
					feeling = sentiwordnet.extract(tokens[i],"a");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
													  break;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;
													  break;						  
						}
							
					}
				
					feeling =  sentiwordnet.extract(tokens[i],"n");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;						  
						}
						
					}
					
					feeling = sentiwordnet.extract(tokens[i],"r");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;						  
						}
					}
					feeling = sentiwordnet.extract(tokens[i],"v");
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;						  
						}
						 
					}
				}
			}
			
		if (count >= 0) 
			{ 
	
			writer1.println(rows[j]);
	
			}
		else {
			
			writer2.println(rows[j]);

		}
	}
			writer1.close();
			writer2.close();
	}
	catch (Exception e) {
			System.out.println("Problem found when classifying the trigrams.");
		}
return "trigrams classified files generated";
}

public static void main (String[] args) throws Exception {
		NgramsClassifier classifier;
				if (args.length < 1)
	System.out.println("Usage: java NgramsClassifier <file>");
		else {
			classifier = new NgramsClassifier();
			classifier.load(args[0]);
			//classifier.emoticonDetect();
			System.out.println(classifier.detectSentiment());
			//System.out.println(classifier.Confusion());

		}
	}
}
