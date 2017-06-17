import java.util.regex.*;
import java.io.*;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class SentimentContext {
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
	public void emoticonDetect() {
		String delimiters1 = "#&";
			String[] row = text.split(delimiters1);
			text = null;
			for(int j = 0; j < row.length; ++j) {
    Pattern patt1 = Pattern.compile(":\\-D|:C|:\\-\\)|:\\)|:o\\)|:\\]|:3|:c\\)|:>|=\\]|8\\)|=\\)|:\\}|:^\\)|ðŸ˜€|ðŸ˜‚|ðŸ˜ƒ|ðŸ˜„|ðŸ˜…|ðŸ˜†|ðŸ˜‡|ðŸ˜ˆ|ðŸ˜‰|ðŸ˜Š|ðŸ˜‹|ðŸ˜Œ|ðŸ˜?|ðŸ˜Ž|ðŸ˜—|ðŸ˜˜|ðŸ˜™|ðŸ˜š|ðŸ˜›|ðŸ˜œ|ðŸ˜¸|ðŸ˜¹|ðŸ˜º|ðŸ˜»|ðŸ˜¼|ðŸ˜½");
    Pattern patt2 = Pattern.compile(":\\-\\(|:\\(|:c|:<|:\\[|:\\{|:\\-P|Pos•|Pos‘|Pos³|Pos®|Pos¯|Pos¶|Pos´|Posµ|Pos²|Pos’|Pos“|Pos”|Pos–|Posž|PosŸ|Pos¡|Pos¢|Pos£|Pos¤|Pos¥|Pos¦|Pos§|Pos¨|Pos©|Posª|Pos«|Pos¬|Pos­|Pos¾|Pos¿|Pos°|Pos±|ðŸ™€");
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
 final String Confusion() throws Exception{
			BufferedReader rd = new BufferedReader(new FileReader("Phase2_Classified.txt"));
			BufferedReader rd2 = new BufferedReader(new FileReader("annotated_mydata.txt"));
			String z;
			String line;
			String ret="";
			int tp=0;
			int tn=0;
			int fp=0;
			int fn=0;
			int totpos=0;
			int prepos=0;
			try {
			while((z = rd.readLine()) != null&&(line = rd2.readLine()) != null) {
				String[] m = z.split("\t");
				String[] g  = line.split("\t");
				for(int i=1;i<=m.length-1;){
					//System.out.println(m[i]+"*****"+g[i]);
					if(m[i].equals(g[i])&&"Positive".equals(g[i])){
						tp = tp+1;
					}
					if(m[i].equals(g[i])&&"Negative".equals(m[i])){
						tn = tn+1;
					}
					if(!m[i].equals(g[i])&&"Negative".equals(g[i])){
						fn = fn+1;
					}
					if(!m[i].equals(g[i])&&"Positive".equals(g[i])){
						fp = fp+1;
					}
					i=i+1;
				}
            }
			System.out.println("tp="+" "+tp);
			System.out.println("fp="+" "+fp);
			System.out.println("tn="+" "+tn);
			System.out.println("fn="+" "+fn);
			prepos= tp+fp;
			totpos= tp+fn;
			System.out.println("prepos="+" "+prepos);
			System.out.println("totpos="+" "+totpos);
		float precision = tp / (float) prepos;
		float recall = tp / (float) totpos;
		ret = "precision :"+precision+"		"+"recall :"+recall;
		//String ret = "precision :"+"0.8324561"+"		"+"recall :"+"0.8235692";
		}
		catch (Exception e) {
			System.out.println("Problem found in confusion matrix. ");
		}
		return ret;
 }
  
	/*public String contextWord() {
	
	try {
			PrintWriter writer1 = new PrintWriter("ContextSensitiveWords.txt");
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
							case "neutral"			: writer1.println(tokens[i]);
													  break;
					}}
					feeling = sentiwordnet.extract(tokens[i],"n");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "neutral"			: writer1.println(tokens[i]);
													  break;
					}}
					feeling = sentiwordnet.extract(tokens[i],"r");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "neutral"			: writer1.println(tokens[i]);
													  break;
					}}
					feeling = sentiwordnet.extract(tokens[i],"v");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "neutral"			: writer1.println(tokens[i]);
													  break;
					}}
		
	}}} writer1.close(); }
		catch (Exception e) {
			System.out.println("Problem found in Searching Context Sensitive Word ");
		}
			return "Context Sensitive Word File Genertaed";
	}*/
	
	public List<String> lerner() {
	    List<String> ngrams = new ArrayList<String>();
		try {
			String delimiters1 = "#&";
			String[] rows = text.split(delimiters1);
			for(int j = 0; j < rows.length; ++j) {
				ngrams.add(rows[j]);
			}
		}
		catch (Exception e) {
			System.out.println("Problem found During learner.");
		}
		return ngrams;
	}
	
	public int check(Classifier<String, String> u,String x,String y,String z) throws Exception {
		int a=0;
		String t;
		try{
			Classifier<String, String> bayes = u;
			String unknownText1 = (x+" "+y+" "+z);
			t=bayes.classify(Arrays.asList(unknownText1)).getCategory();
			((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));
			if(t=="-1") {
				a= -1;
				}
			else {
				a= 1;
			}
		}
		catch (Exception e) {
			System.out.println("Problem found in Checking.");
		}
			return a;
	}

	public String detectSentiment(Classifier<String, String> bayes) {
	
		try {
			Classifier<String, String> d = bayes;
			PrintWriter writer = new PrintWriter("Phase2_Classified.txt");
			String delimiters1 = "#&";
			String[] rows = text.split(delimiters1);
			for(int j = 0; j < rows.length; ++j) {
			int count = 0;
			String delimiters2 = "\\W";
			String[] tokens = rows[j].split(delimiters2);
			String feeling = "";
			for (int i = 1; i < tokens.length-1; ++i) {
				if (!tokens[i].equals("")) {
					feeling = sentiwordnet.extract(tokens[i],"a");
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "neutral"			: count = (check(d,tokens[i-1],tokens[i],tokens[i+1]));
													  break;
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
							case "neutral"			: count = (check(d,tokens[i-1],tokens[i],tokens[i+1]));
													  break;
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
						}
						
					}
					
					feeling = sentiwordnet.extract(tokens[i],"r");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "neutral"			: count = (check(d,tokens[i-1],tokens[i],tokens[i+1]));
													  break;
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
						}
					}
					feeling = sentiwordnet.extract(tokens[i],"v");
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "neutral"			: count = (check(d,tokens[i-1],tokens[i],tokens[i+1]));
													  break;
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
						}
						 
					}
				}
			}
			
		if (count >= 0) 
			{ 
	
			writer.println(rows[j]+"	"+"Positive");
	
			}
		else {
			
			writer.println(rows[j]+"	"+"Negative");

		}
	}
			writer.close();
	}
	catch (Exception e) {
			System.out.println("Problem found when classifying the text");
		}
return "Context Aware classified file generated";
}

public static void main (String[] args) throws Exception {
		SentimentContext classifier;
		final Classifier<String, String> bayes =
                new BayesClassifier<String, String>();
				if (args.length < 3)
	System.out.println("Usage: java SentimentContext <file>");
		else {
			classifier = new SentimentContext();
			classifier.load(args[0]);
			bayes.learn("1",classifier.lerner());
			classifier.load(args[1]);
			bayes.learn("-1",classifier.lerner());
			classifier.load(args[2]);
			classifier.emoticonDetect();
			//System.out.println(classifier.contextWord());
			System.out.println(classifier.detectSentiment(bayes));
			System.out.println(classifier.Confusion());

		}
	}
}
