
package mjk.model;
/** 
* Model for Counting; used to model final results for frequency queries
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/
public class CountingModel {
	private int npeople;
	private int nword;
	private int[][] TermFrequency;
	private String[] Word;
	private String[] Person;
	private int[] TotalFrequency;
	
	/**
	 * Constructor method for CountingModel
	 * @param p number of people
	 * @param w number of words
	 */
	public CountingModel(int p, int w)
	{
		npeople = p;
		nword = w;
		TermFrequency = new int[p][w];
		Word = new String[w];
		Person = new String[p];
		TotalFrequency = new int[w];
	}

	/**
	 * gets the number of people 
	 * @return number of people
	 */
	public int getNpeople() {
		return npeople;
	}

	/**
	 * sets the number of people 
	 */
	public void setNpeople(int npeople) {
		this.npeople = npeople;
	}

	/**
	 * gets the number of words
	 * @return number of words
	 */
	public int getNword() {
		return nword;
	}

	/**
	 * sets the number of words
	 */
	public void setNword(int nword) {
		this.nword = nword;
	}

	/**
	 * gets the termfrequency array
	 * @return the termfrequency array
	 */
	public int[][] getTermFrequency() {
		return TermFrequency;
	}

	/**
	 * sets the termfrequency array
	 */
	public void setTermFrequency(int[][] termFrequency) {
		TermFrequency = termFrequency;
	}

	/**
	 * gets the word array
	 * @return the word array
	 */
	public String[] getWord() {
		return Word;
	}

	/**
	 * sets the word array
	 */
	public void setWord(String[] word) {
		Word = word;
	}

	/**
	 * gets the people array
	 * @return the people array
	 */
	public String[] getPerson() {
		return Person;
	}
	
	/**
	 * sets the people array
	 */
	public void setPerson(String[] person) {
		Person = person;
	}

	/**
	 * gets the total frequency array
	 * @return the total frequency array
	 */
	public int[] getTotalFrequency() {
		return TotalFrequency;
	}

	/**
	 * sets the total frequency array
	 */
	public void setTotalFrequency(int[] totalFrequency) {
		TotalFrequency = totalFrequency;
	}
	
}
