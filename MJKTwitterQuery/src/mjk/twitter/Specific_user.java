package mjk.twitter;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.examples.async.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





















import javax.servlet.ServletContext;

import com.google.gson.Gson;

import mjk.model.CountingModel;
import mjk.model.DatabaseConnection;
import mjk.model.FrequencyModel;
import mjk.model.JsonOperation;
import net.sf.json.JSONArray;
/** 
* Find specific user Twitter  
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/
public class Specific_user {
	
	/**
	 * 
	 * @param twitter twitter object used to call the Twitter API
	 * @param number number of keywords specified by user
	 * @param day number of days specified by user
	 * @param user usernames specified by user
	 * @param context set the path of the stop_list
	 * @return JSON string of calculated frequency results from the query
	 * @throws IOException if reading the file fails
	 * @throws SQLException if database connection fails
	 */
	public String getspecificuser(Twitter twitter, int number, int day, String user, ServletContext context) throws IOException, SQLException
	{
		this.context = context;
		String userinput = user;
		String [] username = userinput.split(",");    // handle the input username
		nusers = username;
		database = new DatabaseConnection();
		nword = number;
		npeople = username.length;
		// Model array for FrequencyModel
		FrequencyModel[] fms=new FrequencyModel[username.length];
		for(int i=0;i<username.length;i++)    
		{
			String tweetinfo = getinformation(twitter,username[i],day);
			FrequencyModel fm = WordCounting(username[i],tweetinfo,i);
			fms[i]=fm;
			//Temp.put(username[i], CountingResult);
		}

		GetFrequency(fms);
		System.out.println(JsonString);
        return JsonString;
		
	}
	
	/**
	 * Stores the user tweets for the specified number of days for each user
	 * @param twitter twitter object used to call the Twitter API
	 * @param screenname the user screenname
	 * @param day specified number of days
	 * @return string containing the tweet contents for that user for the number of days
	 * @throws IOException if reading the file fails
	 * @throws SQLException if database connection fails
	 */
	public String getinformation(Twitter twitter, String screenname, int day) throws IOException, SQLException
	{// this method is to get the frequency of words for one user
		String username=null;
		String whole_tweet="";
		
		Calendar c=new GregorianCalendar();
		c.add(c.DAY_OF_MONTH, -day);
		
		int y = c.get(c.YEAR);
		int m = c.get(c.MONTH)+1;
		int d =c.get(c.DAY_OF_MONTH);
		String date = y+"-"+m+"-"+d;
		try {
			// it creates a query and sets the geocode
			//requirement
			
			Query query = new Query("from:"+screenname+" since:"+date);

			//query1.setGeoCode(new GeoLocation(53.383, -1.483), 10,Query.KILOMETERS);
			query.setCount(100);  // 
			
			//it fires the query
			QueryResult result = twitter.search(query);

			//it cycles on the tweets
			List<Status> tweets = result.getTweets();
			
			
			long time = System.currentTimeMillis();
			//System.out.println(time);
			for (Status tweet : tweets) 
			{ ///gets the user
				User user = tweet.getUser();
				
				// calculate the time of the tweets 

				
					if(tweet.isRetweet()==false)
					{
						// Get all the tweets of one user together in order to count word frequency
						whole_tweet = tweet.getText() + " " + whole_tweet;
						// Insert to the database
						String user_screenname = String.valueOf(user.getScreenName());
						String tweet_time = String.valueOf(tweet.getCreatedAt());
						String tweet_sid = String.valueOf(tweet.getId());
						database.InsertTweets(user_screenname, tweet.getText(), tweet_time, tweet_sid, "0");
						database.InsertUsers(user.getName(), user.getScreenName(), user.getLocation(),
								user.getProfileImageURL(), user.getDescription());
						UsersTweets(twitter,user.getScreenName());
					}
					username = user.getScreenName();
				
			
				
			}

		}
			catch (Exception te) 
			{
				te.printStackTrace();
				System.out.println("Failed to search tweets:" +
				te.getMessage());
				System.exit(-1);
			}
		return whole_tweet;
			//
	
		}

	/**
	 * Processes the tweets to remove special characters, applies the stoplist, 
	 * then counts the words and stores the results as a FrequencyModel object.
	 * @param n the user screenname
	 * @param c the contents of the tweets for that user
	 * @param number number of people
	 * @return FrequencyModel object containing two Arraylists: one for words and the other one for frequency
	 * @throws IOException if reading the file fails
	 */
	public FrequencyModel WordCounting(String n, String c, int number) throws IOException
	{
		// handle the content of tweets
		ArrayList<String> ResultWord = new ArrayList();
		ArrayList<Integer> ResultFrequency = new ArrayList();
		String text = c.toLowerCase();
		System.out.println(text);
		
		text = text.replace("#", "");
		text = text.replace(",", "");
		text = text.replace(":", "");
		text = text.replace(".", "");
		text = text.replace("?", "");
		text = text.replace("!", "");
		text = text.replace("(", "");
		text = text.replace(")", "");
		text = text.replace(":", "");
		text = text.replace("-", "");
		//text = text.replace("rt ", "");
		
		// create the stop_list
		
		InputStream is = context.getResourceAsStream("WEB-INF/stop_list.txt");
		BufferedReader br=new BufferedReader(new InputStreamReader(is));

		String listword = br.readLine();
		while(listword!=null)
		{
			StopList.add(listword);
			listword = br.readLine();
		}

		String [] word = text.toString().split(" ");
		
		
		for(int i=0;i<word.length;i++)    
		{
			if(!word[i].equals(""))    
			{
				if(!ResultWord.contains(word[i].toString()))
				{// if this word does not exist in the word list
					if(!StopList.contains(word[i].toString()))
					{   // if this word is not in the stoplist, add this word to the word list and set frequency as 1
						ResultWord.add(word[i].toString());
						ResultFrequency.add(1);
					}
				}
				
				else
				{// if this word has already existed, add one to the frequency
					int item = ResultWord.indexOf(word[i].toString());	
					
					ResultFrequency.set(item, ResultFrequency.get(item)+1);
				}
			}
		}
		
		FrequencyModel fm = new FrequencyModel(ResultWord,ResultFrequency);
		return fm;
	}
	
	/**
	 * Takes a list of FrequencyModel objects and compares them for frequently occurring words. 
	 * Then generates the final JSON string for output.
	 * @param fs a list of FrequencyModel objects
	 */
	public void GetFrequency(FrequencyModel fs[])
	{
		ArrayList<String> TotalWord = new ArrayList();
		ArrayList<Integer> TotalFrequency = new ArrayList();
		FrequencyModel[] fms=new FrequencyModel[fs.length];
		for(int i=0;i<fs.length;i++)
		{
			FrequencyModel cur_person= new FrequencyModel();
			cur_person = fs[i];
			for(int j=0;j<cur_person.getFrequency().size();j++)
			{
				// if the word is not in the list, add the word and the frequency into the word list and frequency list
				if(!TotalWord.contains(cur_person.getWord().get(j).toString()))
				{
					TotalWord.add(cur_person.getWord().get(j).toString());   
					TotalFrequency.add(cur_person.getFrequency().get(j));
				}
				
				else
				{// if the word is already in the list, sum the frequency
					int item = TotalWord.indexOf(cur_person.getWord().get(j).toString());
						
					TotalFrequency.set(item, TotalFrequency.get(item)+cur_person.getFrequency().get(j));
				}
			}
		}
		
		FrequencyModel fm = new FrequencyModel(TotalWord,TotalFrequency);
		fm = GetRank(fm);
		
		// get the top n words
		fms = GetTopWord(fs,fm);
		
		System.out.println(fm.getWord());
		System.out.println(fm.getFrequency());
		for(int i=0;i<fms.length;i++)
		{
			System.out.println(fms[i].getFrequency());
		}
		
		int[][] TermFrequency = new int[npeople][nword];
		String[] Word = new String[nword];
		String[] Person = new String[npeople];
		int[] AllFrequency = new int[nword];
		CountingModel cm = new CountingModel(npeople,nword);
		for(int i=0;i<npeople;i++)
		{
			for(int j=0;j<nword;j++)
			{
				TermFrequency[i][j] = fms[i].getFrequency().get(j);
				Word[j] = fm.getWord().get(j); 
				AllFrequency[j] = fm.getFrequency().get(j);
			}
			Person[i] = nusers[i]; 
		}
		
		cm.setTermFrequency(TermFrequency);
		cm.setTotalFrequency(AllFrequency);
		cm.setPerson(Person);
		cm.setWord(Word);
		result.put("Frequency", cm);
		JsonOperation ujson=new JsonOperation();
		JsonString=ujson.JsonGenerate((HashMap<String, Object>) result);
		
	}
	
	/**
	 * Ranks the co-occurring words in order of frequency
	 * @param f A single FrequencyModel object of all tweets from all users for the specified number of days
	 * @return A single FrequencyModel object with the ranking and sorting order
	 */
	public FrequencyModel GetRank(FrequencyModel f)
	{// sort the frequency
		String tempword=null;
		int tempcount=0;
		ArrayList<String> TotalWord = new ArrayList();
		ArrayList<Integer> TotalFrequency = new ArrayList();
	
	    TotalWord = f.getWord();
	    TotalFrequency = f.getFrequency();
		for(int i=0;i<TotalWord.size()-1;i++)
		{
			for(int j=0;j<TotalWord.size()-1-i;j++)
			if(TotalFrequency.get(j)<TotalFrequency.get(j+1))
			{ // if the frequency of latter word is bigger than that of former one, 
				// exchange their sequence
				
				tempword = TotalWord.get(j);
				TotalWord.set(j, TotalWord.get(j+1));
				TotalWord.set(j+1,tempword);
				tempcount = TotalFrequency.get(j);
				TotalFrequency.set(j, TotalFrequency.get(j+1));
				TotalFrequency.set(j+1,tempcount);
			}
		}
		
		
		for(int i=nword;i<TotalWord.size();i=i)
		{
			TotalWord.remove(i);
			TotalFrequency.remove(i);
		}
		
		FrequencyModel fm = new FrequencyModel(TotalWord,TotalFrequency);
		
		return fm;
	}
	
	/**
	 * Gets the top n words based on frequency, where n is the number of words specified by the user
	 * @param fs A list of the FrequencyModel objects for each user, without ranking or order
	 * @param f A single FrequencyModel object of all tweets from all users 
	 *          for the specified number of days with ranking and sorting order
	 * @return A list of the FrequencyModel objects for each user, with ranking and order
	 */
	public FrequencyModel[] GetTopWord(FrequencyModel fs[],FrequencyModel f)
	{
		ArrayList<String> TotalWord = new ArrayList();
		ArrayList<Integer> TotalFrequency = new ArrayList();
	    TotalWord = f.getWord();
	    TotalFrequency = f.getFrequency();
	    FrequencyModel[] fms=new FrequencyModel[fs.length];
		for(int i=0;i<fs.length;i++)
		{
			ArrayList<String> UserWord = new ArrayList();
			ArrayList<Integer> UserFrequency = new ArrayList();
			ArrayList<String> TempWord = new ArrayList();
			ArrayList<Integer> TempFrequency = new ArrayList();
			UserWord = fs[i].getWord();
			UserFrequency = fs[i].getFrequency();
			
			for(int j=0;j<TotalWord.size();j++)
			{
				if(UserWord.contains(TotalWord.get(j).toString()))
				{  
					TempWord.add(UserWord.get(UserWord.indexOf(TotalWord.get(j).toString())));
					TempFrequency.add(UserFrequency.get(UserWord.indexOf(TotalWord.get(j).toString())));
				}
				else
				{
					TempWord.add(TotalWord.get(j).toString());
					TempFrequency.add(0);
				}
			}

			
			UserWord.clear();
			UserWord.addAll(TempWord);
			
			UserFrequency.clear();
			UserFrequency.addAll(TempFrequency);
			
			FrequencyModel fm = new FrequencyModel(UserWord,UserFrequency);
			fms[i] = fm;
		}
		
		return fms;
	}
	

	public void UsersTweets(Twitter twitter,String userscreenname) throws SQLException, TwitterException
	{
		DatabaseConnection database = new DatabaseConnection();   
		Query query= new Query("from:"+userscreenname);
		
		query.setCount(100);  // number of limiting the query 
		
		QueryResult result = twitter.search(query);
		
		//it cycles on the tweets
		List<Status> tweets = result.getTweets();
		
		
		for (Status tweet : tweets) 
		{ ///gets the user
			User user = tweet.getUser();
			
			String user_screenname = String.valueOf(user.getScreenName());
			String tweet_time = String.valueOf(tweet.getCreatedAt());
			String tweet_sid = String.valueOf(tweet.getId());
			String tweet_isretweet = null;
			if(tweet.isRetweet() == true)
				tweet_isretweet = "1";
			else
				tweet_isretweet = "0";
			database.InsertTweets(user_screenname, tweet.getText(), tweet_time, tweet_sid, tweet_isretweet);
		}
		
	}
	
	private ServletContext context;
	private DatabaseConnection database;
	private int nword;
	private int npeople;
	private String[] nusers;
	//private ArrayList<CountingModel> cms = new ArrayList<CountingModel>();
	private ArrayList<String> StopList = new ArrayList();
	private Map<String,Object> result = new HashMap();
	private String JsonString;
}
