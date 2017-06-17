import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class SWN3 {
	private String pathToSWN = "C:\\Users\\SWASH\\Desktop\\ho\\SentiWordNet.txt";
	private HashMap<String, String> _dict;

	public SWN3() {
		System.out.println("Initializing...");
		_dict = new HashMap<String, String>();
		try {
			HashMap<String, Vector<Double>> rawDataMap = buildHashMap();
			Set<String> temp = rawDataMap.keySet();
			for (Iterator<String> iterator = temp.iterator(); iterator
					.hasNext();) {
				String word = (String) iterator.next();
				Vector<Double> v = rawDataMap.get(word);
				double score = 0.0;
				double sum = 0.0;
				for (int i = 0; i < v.size(); i++)
					score += ((double) 1 / (double) (i + 1)) * v.get(i);
				for (int i = 1; i <= v.size(); i++)
					sum += (double) 1 / (double) i;
				score /= sum;
				String sentiment = getSentiment(score);
				_dict.put(word, sentiment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Initialization Complete");
	}

	String getSentiment(double score) {
		String result = "";
		if (score <= 1.0 && score >= 0.75)
			result = "very_strong_positive";
		else if (score < 0.75 && score >= 0.5)
			result = "strong_positive";
		else if (score < 0.5 && score >= 0.25)
			result = "positive";
		else if (score < 0.25 && score > 0)
			result = "weak_positive";
		else if (score == 0)
			result = "neutral";
		else if (score < 0 && score > -0.25)
			result = "weak_negative";
		else if (score <= -0.25 && score > -0.5)
			result = "negative";
		else if (score <= -0.5 && score > -0.75)
			result = "strong_negative";
		else if (score <= -0.75 && score >= -1)
			result = "very_strong_negative";
		return result;
	}

	private HashMap<String, Vector<Double>> buildHashMap()
			throws FileNotFoundException, IOException {
		HashMap<String, Vector<Double>> rawDataMap = new HashMap<String, Vector<Double>>();
		BufferedReader csv = new BufferedReader(new FileReader(pathToSWN));
		String line = "";
		while ((line = csv.readLine()) != null) {
			String[] data = line.split("\t");
			Double score = Double.parseDouble(data[2])
					- Double.parseDouble(data[3]);
			String[] words = data[4].split(" ");
			for (String word : words) {
				String[] definition = word.split("#");
				definition[0] += "#" + data[0];
				int index = Integer.parseInt(definition[1]) - 1;
				if (rawDataMap.containsKey(definition[0])) {
					Vector<Double> scoreVector = rawDataMap.get(definition[0]);
					if (index > scoreVector.size())
						for (int i = scoreVector.size(); i < index; i++)
							scoreVector.add(0.0);
					scoreVector.add(index, score);
					rawDataMap.put(definition[0], scoreVector);
				} else {
					Vector<Double> v = new Vector<Double>();
					for (int i = 0; i < index; i++)
						v.add(0.0);
					v.add(index, score);
					rawDataMap.put(definition[0], v);
				}
			}
		}
		return rawDataMap;
	}

	public void printAll(){
		Set<String> temp = _dict.keySet();
		for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();){
			String word = (String) iterator.next();
			String sent = _dict.get(word);
			System.out.println("Word: "+word+" Value: "+sent);
		}
	}
	public String extract(String word, String type) {
		return _dict.get(word + "#" + type);
	}
	
	public static void main(String[] args) {
		SWN3 myswn = new SWN3();
		String test = myswn.extract("saintly","a");
		System.out.println("saintly = " + test);
	}
}