package mjk.model;

import java.util.ArrayList;
/** 
* Model for Frequency array lists; creates FrequencyModel objects containing two array lists: words, and frequency
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/

public class FrequencyModel {
	private ArrayList<String> Word = new ArrayList();
	private ArrayList<Integer> Frequency = new ArrayList();
	public FrequencyModel()
	{
	}
	
	/**
	 * Constructor method for Frequencymodel
	 * @param w the list of words
	 * @param f the list of word frequencies
	 */
	public FrequencyModel(ArrayList<String> w, ArrayList<Integer> f)
	{
		Word = w;
		Frequency = f;
	}
	
	/**
	 * gets the list of words
	 * @return the list of words
	 */
	public ArrayList<String> getWord() {
		return Word;
	}
	
	/**
	 * sets the list of words
	 */
	public void setWord(ArrayList<String> word) {
		Word = word;
	}
	
	/**
	 * gets the list of word frequencies
	 * @return the list of word frequencies
	 */
	public ArrayList<Integer> getFrequency() {
		return Frequency;
	}
	
	/**
	 * sets the list of word frequencies
	 */
	public void setFrequency(ArrayList<Integer> frequency) {
		Frequency = frequency;
	}
}
