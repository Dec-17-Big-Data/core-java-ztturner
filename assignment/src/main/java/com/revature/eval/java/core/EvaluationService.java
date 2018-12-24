package com.revature.eval.java.core;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluationService {

	/**
	 * 1. Without using the StringBuilder or StringBuffer class, write a method that
	 * reverses a String. Example: reverse("example"); -> "elpmaxe"
	 * 
	 * @param string
	 * @return
	 */
	public String reverse(String string) {
		char[] reversed = new char[string.length()];
		for (int i = reversed.length - 1, j=0; i >= 0; i--, j++) {
			reversed[j] = string.charAt(i);
		}
		return new String(reversed);
	}

	/**
	 * 2. Convert a phrase to its acronym. Techies love their TLA (Three Letter
	 * Acronyms)! Help generate some jargon by writing a program that converts a
	 * long name like Portable Network Graphics to its acronym (PNG).
	 * 
	 * @param phrase
	 * @return
	 */
	public String acronym(String phrase) {
		Pattern nonWordRegex = Pattern.compile("\\W+");
		String[] words = nonWordRegex.split(phrase); // split the words based on the non-word regex
		String acronym = "";
		
		for(int w = 0; w < words.length; w++) {
			acronym = acronym + words[w].charAt(0);
		}
		
		return acronym.toUpperCase();
	}

	/**
	 * 3. Determine if a triangle is equilateral, isosceles, or scalene. An
	 * equilateral triangle has all three sides the same length. An isosceles
	 * triangle has at least two sides the same length. (It is sometimes specified
	 * as having exactly two sides the same length, but for the purposes of this
	 * exercise we'll say at least two.) A scalene triangle has all sides of
	 * different lengths.
	 *
	 */
	static class Triangle {
		private double sideOne;
		private double sideTwo;
		private double sideThree;

		public Triangle() {
			super();
		}

		public Triangle(double sideOne, double sideTwo, double sideThree) {
			this();
			this.sideOne = sideOne;
			this.sideTwo = sideTwo;
			this.sideThree = sideThree;
		}

		public double getSideOne() {
			return sideOne;
		}

		public void setSideOne(double sideOne) {
			this.sideOne = sideOne;
		}

		public double getSideTwo() {
			return sideTwo;
		}

		public void setSideTwo(double sideTwo) {
			this.sideTwo = sideTwo;
		}

		public double getSideThree() {
			return sideThree;
		}

		public void setSideThree(double sideThree) {
			this.sideThree = sideThree;
		}

		public boolean isEquilateral() {
			// It can be determined that all sides are equal when A = B and B = C, because
			// A = C must then be true			
			return sideOne == sideTwo && sideTwo == sideThree;
		}

		public boolean isIsosceles() {			
			return sideOne == sideTwo || sideTwo == sideThree || sideOne == sideThree;
		}

		public boolean isScalene() {
			return sideOne != sideTwo && sideTwo != sideThree && sideOne != sideThree;
		}

	}

	/**
	 * 4. Given a word, compute the scrabble score for that word.
	 * 
	 * --Letter Values-- Letter Value A, E, I, O, U, L, N, R, S, T = 1; D, G = 2; B,
	 * C, M, P = 3; F, H, V, W, Y = 4; K = 5; J, X = 8; Q, Z = 10; Examples
	 * "cabbage" should be scored as worth 14 points:
	 * 
	 * 3 points for C, 1 point for A, twice 3 points for B, twice 2 points for G, 1
	 * point for E And to total:
	 * 
	 * 3 + 2*1 + 2*3 + 2 + 1 = 3 + 2 + 6 + 3 = 5 + 9 = 14
	 * 
	 * @param string
	 * @return
	 */
	public int getScrabbleScore(String string) {
		Map<String, Integer> scrabbleMap = new HashMap<String, Integer>();
		Map<String, Integer> wordLetterCountMap = new HashMap<String, Integer>();
		
		// setup the letters and point values for the map
		String[][] letters = {
				new String[] { 
					"a", "e", "i", "o", "u", "l", "n", "r", "s", "t"
				},
				new String[] {
					"d", "g"
				},
				new String[] {
					"b", "c", "m", "p"
				},
				new String[] {
					"f", "h", "v", "w", "y"
				},
				new String[] {
					"k"
				},
				new String[] {
					"j", "x"
				},
				new String[] {
					"q", "z"
				}
		};
		
		Integer[] pointValues = {
				new Integer(1),
				new Integer(2),
				new Integer(3),
				new Integer(4),
				new Integer(5),
				new Integer(8),
				new Integer(10)
		};
		
		int totalScore = 0;
		
		// put the letters and their values in the scrabble map
		for(int g = 0; g < letters.length; g++)
		{
			for(int l = 0; l < letters[g].length; l++)
			{
				scrabbleMap.put(letters[g][l], pointValues[g]);
			}
		}
		
		// trim all non-word and digit characters from the string
		String trimmedWord = string.replaceAll("\\W", "").replaceAll("\\d", "").toLowerCase();
		
		String currentChar;
		Integer currentCharCount;
		
		// go through the given word and get the letter count
		for(int letter = 0; letter < trimmedWord.length(); letter++)
		{
			currentChar = Character.toString(trimmedWord.charAt(letter));
			
			// if the letter has been found in the word already
			if(wordLetterCountMap.containsKey(currentChar))	{				
				// increase the times the letter was found by 1
				currentCharCount = wordLetterCountMap.get(currentChar) + 1;				
			}
			// if the letter has not been found in the word already
			else {
				currentCharCount = new Integer(1); // make an integer with a value of 1
			}
			
			// put the new letter count in the map
			wordLetterCountMap.put(currentChar, currentCharCount);
		}
		
		// calculate the total score
		for(String l: wordLetterCountMap.keySet())
		{
			// for each character in the counted letter set, multiply the value and letter count and add to the total
			totalScore = totalScore + (wordLetterCountMap.get(l) * scrabbleMap.get(l));
		}
		
		return totalScore;
	}

	/**
	 * 5. Clean up user-entered phone numbers so that they can be sent SMS messages.
	 * 
	 * The North American Numbering Plan (NANP) is a telephone numbering system used
	 * by many countries in North America like the United States, Canada or Bermuda.
	 * All NANP-countries share the same international country code: 1.
	 * 
	 * NANP numbers are ten-digit numbers consisting of a three-digit Numbering Plan
	 * Area code, commonly known as area code, followed by a seven-digit local
	 * number. The first three digits of the local number represent the exchange
	 * code, followed by the unique four-digit number which is the subscriber
	 * number.
	 * 
	 * The format is usually represented as
	 * 
	 * 1 (NXX)-NXX-XXXX where N is any digit from 2 through 9 and X is any digit
	 * from 0 through 9.
	 * 
	 * Your task is to clean up differently formatted telephone numbers by removing
	 * punctuation and the country code (1) if present.
	 * 
	 * For example, the inputs
	 * 
	 * +1 (613)-995-0253 613-995-0253 1 613 995 0253 613.995.0253 should all produce
	 * the output
	 * 
	 * 6139950253
	 * 
	 * Note: As this exercise only deals with telephone numbers used in
	 * NANP-countries, only 1 is considered a valid country code.
	 */
	public String cleanPhoneNumber(String string) throws IllegalArgumentException {
		String trimmedString = string.replaceAll("\\D+", ""); // remove all non-digit characters
		Pattern phoneDigitsRegex = Pattern.compile("[2-9]\\d{2}[2-9]\\d{6}"); // look for this pattern in the trimmed string
		
		// improper number of digits should throw an exception
		if(trimmedString.length() <= 9 || trimmedString.length() >= 12) {
			throw new IllegalArgumentException("Invalid number of digits.");
		}
		
		// if the country code is present, check that it is 1.
		if(trimmedString.length() == 11) {
			// if the country code is not 1, throw an exception
			if(Integer.valueOf(trimmedString.charAt(0)) != 1) {
				throw new IllegalArgumentException("Invalid country code.");
			}
			
			trimmedString = trimmedString.substring(1); // else, remove the country code
		}
		
		// if the string does not match the pattern, throw an exception
		if(!trimmedString.matches(phoneDigitsRegex.pattern())) {
			throw new IllegalArgumentException("Invalid digits.");
		}
		
		return trimmedString;
	}

	/**
	 * 6. Given a phrase, count the occurrences of each word in that phrase.
	 * 
	 * For example for the input "olly olly in come free" olly: 2 in: 1 come: 1
	 * free: 1
	 * 
	 * @param string
	 * @return
	 */
	public Map<String, Integer> wordCount(String string) {
		Map<String, Integer> wordCount = new HashMap<String, Integer>();
		String[] separateWords = string.split("\\W+"); // split the words by non-word characters
		String currentWord;
		Integer currentWordCount;
		
		
		for(int word = 0; word < separateWords.length; word++) {
			currentWord = separateWords[word];
			
			// if the word is in the map, increase the word count for the word by 1
			if(wordCount.containsKey(currentWord)) {
				currentWordCount = wordCount.get(currentWord) + 1;
			}
			// else, set word count to 1
			else {
				currentWordCount = new Integer(1);
			}
			
			// put the new word count for word into the map
			wordCount.put(currentWord, currentWordCount);
		}
		
		return wordCount;
	}

	/**
	 * 7. Implement a binary search algorithm.
	 * 
	 * Searching a sorted collection is a common task. A dictionary is a sorted list
	 * of word definitions. Given a word, one can find its definition. A telephone
	 * book is a sorted list of people's names, addresses, and telephone numbers.
	 * Knowing someone's name allows one to quickly find their telephone number and
	 * address.
	 * 
	 * If the list to be searched contains more than a few items (a dozen, say) a
	 * binary search will require far fewer comparisons than a linear search, but it
	 * imposes the requirement that the list be sorted.
	 * 
	 * In computer science, a binary search or half-interval search algorithm finds
	 * the position of a specified input value (the search "key") within an array
	 * sorted by key value.
	 * 
	 * In each step, the algorithm compares the search key value with the key value
	 * of the middle element of the array.
	 * 
	 * If the keys match, then a matching element has been found and its index, or
	 * position, is returned.
	 * 
	 * Otherwise, if the search key is less than the middle element's key, then the
	 * algorithm repeats its action on the sub-array to the left of the middle
	 * element or, if the search key is greater, on the sub-array to the right.
	 * 
	 * If the remaining array to be searched is empty, then the key cannot be found
	 * in the array and a special "not found" indication is returned.
	 * 
	 * A binary search halves the number of items to check with each iteration, so
	 * locating an item (or determining its absence) takes logarithmic time. A
	 * binary search is a dichotomic divide and conquer search algorithm.
	 * 
	 */
	static class BinarySearch<T extends Comparable<T>> {
		private List<T> sortedList;

		public int indexOf(T t) {
			int leftIndex = 0, rightIndex, middleIndex;
			int compareValue; // value from the compareTo method
			int result = -1; // -1 indicates that the object was not found in the list
			
			if(sortedList.size() == 1) {
				if(t.compareTo(sortedList.get(leftIndex)) == 0) {
					result = 0;
				}
			}
			else if(sortedList.size() > 1) {
				rightIndex = sortedList.size() - 1;
				
				// continue until the item is found or the left index is greater than the right index
				while(leftIndex <= rightIndex) {
					middleIndex = ((rightIndex - leftIndex) / 2) + leftIndex;
					compareValue = t.compareTo(sortedList.get(middleIndex));
					
					// input object is equal to the middle list item
					if(compareValue == 0) {
						result = middleIndex;
						break;
					}
					// input object is less than the middle list item
					else if(compareValue < 0) {
						rightIndex = middleIndex - 1;
					}
					// input object is greater than the middle list item
					else {
						leftIndex = middleIndex + 1;
					}
				}
			}
			
			return result;
		}

		public BinarySearch(List<T> sortedList) {
			super();
			this.sortedList = sortedList;
		}

		public List<T> getSortedList() {
			return sortedList;
		}

		public void setSortedList(List<T> sortedList) {
			this.sortedList = sortedList;
		}

	}

	/**
	 * 8. Implement a program that translates from English to Pig Latin.
	 * 
	 * Pig Latin is a made-up children's language that's intended to be confusing.
	 * It obeys a few simple rules (below), but when it's spoken quickly it's really
	 * difficult for non-children (and non-native speakers) to understand.
	 * 
	 * Rule 1: If a word begins with a vowel sound, add an "ay" sound to the end of
	 * the word. Rule 2: If a word begins with a consonant sound, move it to the end
	 * of the word, and then add an "ay" sound to the end of the word. There are a
	 * few more rules for edge cases, and there are regional variants too.
	 * 
	 * See http://en.wikipedia.org/wiki/Pig_latin for more details.
	 * 
	 * @param string
	 * @return
	 */
	public String toPigLatin(String string) {		
		String[] splitString = string.split("[\\W]+"); // split the string by words
		String tempString; // string for manipulation
		
		Pattern wordBreaks = Pattern.compile("[\\W]+"); // pattern to capture sets of characters between words
		Pattern vowels = Pattern.compile("[aeiou]"); // pattern for finding vowels
		Pattern quSpecialCase = Pattern.compile("qu"); // pattern for finding "qu" for moving both characters to the end
		Matcher vowelMatcher, quMatcher, breakMatcher; // matchers for the word breaks, vowels, and "qu" case
		int foundVowelIndex, foundQuIndex; // characters indices for the vowel and "qu"
		boolean quCase = false; // says that the word has the "qu" special case
		
		StringBuilder pigLatinStringBuilder = new StringBuilder(); // builds the pig latin translation
		breakMatcher = wordBreaks.matcher(string);
		
		for(int word = 0; word < splitString.length; word++) {
			vowelMatcher = vowels.matcher(splitString[word]);
			
			// if a vowel is found, get the index of the vowel and check for the "qu" special case
			if(vowelMatcher.find()) {
				foundVowelIndex = vowelMatcher.start(); // get the index of the vowel
				
				quMatcher = quSpecialCase.matcher(splitString[word]);
				quCase = false;
				// if "qu" is found, check if the first vowel is the "u" in the "qu"
				if(quMatcher.find()) {
					foundQuIndex = quMatcher.start();
					
					if(foundQuIndex == foundVowelIndex - 1) {
						quCase = true;
					}
				}
				
				// if this is a "qu" special case, bring the consonants and the "u" to the end and add "ay"
				if(quCase) {
					if(foundVowelIndex == splitString[word].length() - 1) {
						tempString = splitString[word] + "ay";
					}
					else {
						tempString = splitString[word].substring(foundVowelIndex + 1) + splitString[word].substring(0, foundVowelIndex + 1) + "ay";
					}
				}
				// else, bring the consonants to the end and add "ay"
				else {
					if(foundVowelIndex == 0) {
						tempString = splitString[word] + "ay";
					}
					else {
						tempString = splitString[word].substring(foundVowelIndex) + splitString[word].substring(0, foundVowelIndex) + "ay";
					}
				}
			}
			// else, if the word is only consonants, add "ay" to the end
			else {
				tempString = splitString[word] + "ay";
			};
			
			pigLatinStringBuilder.append(tempString);
			
			if(breakMatcher.find()) {
				pigLatinStringBuilder.append(string.substring(breakMatcher.start(), breakMatcher.end()));
			}
		}
		
		return pigLatinStringBuilder.toString();
	}

	/**
	 * 9. An Armstrong number is a number that is the sum of its own digits each
	 * raised to the power of the number of digits.
	 * 
	 * For example:
	 * 
	 * 9 is an Armstrong number, because 9 = 9^1 = 9 10 is not an Armstrong number,
	 * because 10 != 1^2 + 0^2 = 2 153 is an Armstrong number, because: 153 = 1^3 +
	 * 5^3 + 3^3 = 1 + 125 + 27 = 153 154 is not an Armstrong number, because: 154
	 * != 1^3 + 5^3 + 4^3 = 1 + 125 + 64 = 190 Write some code to determine whether
	 * a number is an Armstrong number.
	 * 
	 * @param input
	 * @return
	 */
	public boolean isArmstrongNumber(int input) {
		String convertedInput = Integer.toString(input);
		int numDigits = convertedInput.length();
		int digitValue;
		int sumOfDigits = 0;
		boolean isArmstrong = false;
		
		for(int d = 0; d < numDigits; d++) {
			digitValue = Integer.valueOf(Character.toString(convertedInput.charAt(d)));
			sumOfDigits = sumOfDigits + (int)(Math.pow(digitValue, numDigits));
		}
		
		if(sumOfDigits == input)
		{
			isArmstrong = true;
		}
		
		return isArmstrong;
	}

	/**
	 * 10. Compute the prime factors of a given natural number.
	 * 
	 * A prime number is only evenly divisible by itself and 1.
	 * 
	 * Note that 1 is not a prime number.
	 * 
	 * @param l
	 * @return
	 */
	public List<Long> calculatePrimeFactorsOf(long l) throws IllegalArgumentException {
		List<Long> primeFactors = new ArrayList<Long>();		
		List<Long> primes = new ArrayList<Long>();
		// add the first two prime numbers
		primes.add(2L);
		primes.add(3L);
		
		long reducedInput = l; // input value reduced by factorization
		int primeIndex = 0;
		// numbers less than 2 cannot have prime factors, throw an exception
		if(l <= 1L) {
			throw new IllegalArgumentException("Numbers less than 2 cannot have prime factors.");
		}
		// if input is 2 or 3, add input to the prime factors and return
		else if(l == 2 || l == 3) {
			primeFactors.add(l);
		}
		else {
			// continue until the input has been reduced to 1
			while(reducedInput > 1) {
				while(primeIndex < primes.size()) {
					if(reducedInput % primes.get(primeIndex) == 0) {
						reducedInput = reducedInput / primes.get(primeIndex);
						primeFactors.add(primes.get(primeIndex));
						primeIndex = 0;
					}
					else {
						primeIndex++;
					}
				}
				
				// if all primes found so far have been tested and the input still needs to be reduced, find the next prime
				if(reducedInput > 1) {
					addNextPrime(primes);
				}
			}
		}
		
		return primeFactors;
	}
	
	/**
	 * This method takes a list of primes and finds the next prime to add to the list.
	 * 
	 * @param primes - the list of primes to add to
	 */
	public void addNextPrime(List<Long> primes) {
		long nextTestValue = primes.get(primes.size() - 1) + 2; // go to the next value above the last found prime
		double sqrtValue = Math.sqrt(nextTestValue); // get the square of the test value
		int primeIndex = 1; // start at 3 as no test values will be even
		
		// test primes up to and including the square root of the value
		while(primes.get(primeIndex) <= sqrtValue) {
			// if the value evenly divides with a prime, it is not prime
			if(nextTestValue % primes.get(primeIndex) == 0) {
				nextTestValue = nextTestValue + 2; // next value
				sqrtValue = Math.sqrt(nextTestValue);
				primeIndex = 1; // reset back to 3
			}
			// else, test next prime
			else {
				primeIndex++;
			}
		}
		
		primes.add(nextTestValue); // add the new found prime to the list
	}

	/**
	 * 11. Create an implementation of the rotational cipher, also sometimes called
	 * the Caesar cipher.
	 * 
	 * The Caesar cipher is a simple shift cipher that relies on transposing all the
	 * letters in the alphabet using an integer key between 0 and 26. Using a key of
	 * 0 or 26 will always yield the same output due to modular arithmetic. The
	 * letter is shifted for as many values as the value of the key.
	 * 
	 * The general notation for rotational ciphers is ROT + <key>. The most commonly
	 * used rotational cipher is ROT13.
	 * 
	 * A ROT13 on the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: nopqrstuvwxyzabcdefghijklm It is
	 * stronger than the Atbash cipher because it has 27 possible keys, and 25
	 * usable keys.
	 * 
	 * Ciphertext is written out in the same formatting as the input including
	 * spaces and punctuation.
	 * 
	 * Examples: ROT5 omg gives trl ROT0 c gives c ROT26 Cool gives Cool ROT13 The
	 * quick brown fox jumps over the lazy dog. gives Gur dhvpx oebja sbk whzcf bire
	 * gur ynml qbt. ROT13 Gur dhvpx oebja sbk whzcf bire gur ynml qbt. gives The
	 * quick brown fox jumps over the lazy dog.
	 */
	static class RotationalCipher {
		private int key;
		private Map<Character, Character> rotatedCharMap;

		public RotationalCipher(int key) {
			super();
			this.key = key;
			rotatedCharMap = getRotatedCharMap();
		}

		public String rotate(String string) {			
			if(this.key == 0 || this.key == 26) {
				return string;
			}
			
			char currentChar;
			StringBuilder rotatedStringBuilder = new StringBuilder(string.length());
							
			for(int s = 0; s < string.length(); s++) {				
				currentChar = string.charAt(s);
				if(Character.isLetter(currentChar)) {
					rotatedStringBuilder.append(rotatedCharMap.get(Character.valueOf(currentChar)).charValue());
				}
				else {
					rotatedStringBuilder.append(currentChar);
				}
			}
			
			return rotatedStringBuilder.toString();
		}

		/**
		 * Returns a map of pre-rotated characters as the key and the post-rotated characters as the value
		 * by getting the ASCII values of the characters, adding the letter counter, and
		 * adding the key of the cipher for the rotated characters taking into account characters wrapping around.
		 * 
		 * @return - the map of the pre-rotated characters and their corresponding rotated characters
		 */
		private Map<Character, Character> getRotatedCharMap() {
			Map<Character, Character> rotatedCharMap = new HashMap<Character, Character>();
			
			// ASCII values for characters
			int lowerCaseLetterAValue = (int)'a';
			int upperCaseLetterAValue = (int)'A';
			int currentLetterValue;
			int rotatedLetterValue;			
			
			// go through each character and add the pre-rotated and rotated characters to the map
			for(int letter = 0; letter < 26; letter++) {
				// rotate lowercase letter
				currentLetterValue = lowerCaseLetterAValue + letter;
				rotatedLetterValue = lowerCaseLetterAValue + ((this.key + letter) % 26);
				rotatedCharMap.put(Character.valueOf((char)currentLetterValue), Character.valueOf((char)rotatedLetterValue));
				
				// rotate uppercase letter
				currentLetterValue = upperCaseLetterAValue + letter;
				rotatedLetterValue = upperCaseLetterAValue + ((this.key + letter) % 26);
				rotatedCharMap.put(Character.valueOf((char)currentLetterValue), Character.valueOf((char)rotatedLetterValue));
			}
			
			return rotatedCharMap;
		}
	}

	/**
	 * 12. Given a number n, determine what the nth prime is.
	 * 
	 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
	 * that the 6th prime is 13.
	 * 
	 * If your language provides methods in the standard library to deal with prime
	 * numbers, pretend they don't exist and implement them yourself.
	 * 
	 * @param i
	 * @return
	 */
	public int calculateNthPrime(int i) throws IllegalArgumentException {
		List<Integer> primes = new ArrayList<Integer>();
		primes.add(2);
		primes.add(3);
		int nextTestValue;
		int primeIndex;
		double sqrtValue;
		// throw an exception if the argument is a non-positive number
		if(i <= 0) {
			throw new IllegalArgumentException("Argument must be positive.");
		}
		// if the input is the first two primes, just return the value of the first two primes
		else if(i == 1 || i == 2) {
			return primes.get(i-1);
		}
		else {
			// go through the primes until the nth prime is found
			while(primes.size() < i) {
				
				nextTestValue = primes.get(primes.size()-1) + 2; // next value after the last prime
				sqrtValue = Math.sqrt(nextTestValue); // get the square root for the value to test
				primeIndex = 1; // start with 3 as none of the tested values will be even
				
				// go through the primes until the square root value is reached
				while(primes.get(primeIndex) <= sqrtValue) {
					
					// if the tested value evenly divides with a previous prime, it is not prime
					if(nextTestValue % primes.get(primeIndex) == 0) {
						
						nextTestValue = nextTestValue + 2; // next value to test
						sqrtValue = Math.sqrt(nextTestValue); // new square root value
						primeIndex = 1; // reset to 3
						
					}
					// else, test against the next prime
					else {
						primeIndex++;
					}
				}
				
				primes.add(nextTestValue); // when a prime is found, add to the list
			}
		}
		
		return primes.get(i-1); // return the last prime number found
	}

	/**
	 * 13 & 14. Create an implementation of the atbash cipher, an ancient encryption
	 * system created in the Middle East.
	 * 
	 * The Atbash cipher is a simple substitution cipher that relies on transposing
	 * all the letters in the alphabet such that the resulting alphabet is
	 * backwards. The first letter is replaced with the last letter, the second with
	 * the second-last, and so on.
	 * 
	 * An Atbash cipher for the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: zyxwvutsrqponmlkjihgfedcba It is a
	 * very weak cipher because it only has one possible key, and it is a simple
	 * monoalphabetic substitution cipher. However, this may not have been an issue
	 * in the cipher's time.
	 * 
	 * Ciphertext is written out in groups of fixed length, the traditional group
	 * size being 5 letters, and punctuation is excluded. This is to make it harder
	 * to guess things based on word boundaries.
	 * 
	 * Examples Encoding test gives gvhg Decoding gvhg gives test Decoding gsvjf
	 * rxpyi ldmul cqfnk hlevi gsvoz abwlt gives thequickbrownfoxjumpsoverthelazydog
	 *
	 */
	static class AtbashCipher {
		/**
		 * Question 13
		 * 
		 * @param string
		 * @return
		 */
		public static String encode(String string) {
			Map<Character, Character> encodingAlphabet = AtbashCipher.getEncodingAlphabet(); // get the encoding map
			
			int groupSize = 5; // use the traditional group size of 5 letters
			String trimmedString = string.replaceAll("\\W+", "").toLowerCase(); // remove all non-word characters and make all lowercase
			StringBuilder encodedStringBuilder = new StringBuilder(trimmedString.length());
			char currentChar;
			
			for(int e = 0; e < trimmedString.length(); e++) {
				// if the character index matches the group size, append a space character
				if(e % groupSize == 0) {
					encodedStringBuilder.append(' ');
				}
				currentChar = trimmedString.charAt(e);
				
				// if the character is a lowercase letter, find the encoding character in the encoding map and append
				if(Character.isLowerCase(currentChar)) {
					encodedStringBuilder.append(encodingAlphabet.get(Character.valueOf(currentChar)).charValue());
				}
				// else, append the character from the string
				else {
					encodedStringBuilder.append(currentChar);
				}
			}
			
			return encodedStringBuilder.toString().trim(); // trim to remove the leading space character
		}

		/**
		 * Question 14
		 * 
		 * @param string
		 * @return
		 */
		public static String decode(String string) {
			Map<Character, Character> decodingAlphabet = AtbashCipher.getEncodingAlphabet(); // get the decoding alphabet
			String trimmedString = string.replaceAll("\\s+", ""); // remove all the spaces from the encoded string
			StringBuilder decodingStringBuilder = new StringBuilder(trimmedString.length());
			char currentChar;
			
			for(int d = 0; d < trimmedString.length(); d++) {
				currentChar = trimmedString.charAt(d);
				if(Character.isLowerCase(currentChar)) {
					decodingStringBuilder.append(decodingAlphabet.get(Character.valueOf(currentChar)).charValue());
				}
				else {
					decodingStringBuilder.append(currentChar);
				}
			}
			
			return decodingStringBuilder.toString();
		}
		
		/**
		 * Returns the encoding alphabet for the Atbash cipher by getting the ASCII values
		 * of the lowercase 'a' and 'z' and looping through the values adding to a map
		 * 
		 * @return - the map of the unencoded characters and their respective encoded characters
		 */
		public static Map<Character, Character> getEncodingAlphabet() {
			Map<Character, Character> encodingAlphabet = new HashMap<Character, Character>();
			// get the ASCII values to map the key-value pairs
			int unencodedCharValue = (int)'a';
			int encodedCharValue = (int)'z';
			
			// loop through the ASCII values
			for(int l = 0; l < 26; l++) {
				encodingAlphabet.put(Character.valueOf((char)unencodedCharValue), Character.valueOf((char)encodedCharValue));
				unencodedCharValue++;
				encodedCharValue--;
			}
			
			return encodingAlphabet;
		}
	}

	/**
	 * 15. The ISBN-10 verification process is used to validate book identification
	 * numbers. These normally contain dashes and look like: 3-598-21508-8
	 * 
	 * ISBN The ISBN-10 format is 9 digits (0 to 9) plus one check character (either
	 * a digit or an X only). In the case the check character is an X, this
	 * represents the value '10'. These may be communicated with or without hyphens,
	 * and can be checked for their validity by the following formula:
	 * 
	 * (x1 * 10 + x2 * 9 + x3 * 8 + x4 * 7 + x5 * 6 + x6 * 5 + x7 * 4 + x8 * 3 + x9
	 * * 2 + x10 * 1) mod 11 == 0 If the result is 0, then it is a valid ISBN-10,
	 * otherwise it is invalid.
	 * 
	 * Example Let's take the ISBN-10 3-598-21508-8. We plug it in to the formula,
	 * and get:
	 * 
	 * (3 * 10 + 5 * 9 + 9 * 8 + 8 * 7 + 2 * 6 + 1 * 5 + 5 * 4 + 0 * 3 + 8 * 2 + 8 *
	 * 1) mod 11 == 0 Since the result is 0, this proves that our ISBN is valid.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isValidIsbn(String string) {
		String trimmedString = string.replaceAll("\\W+", "");
		String lastCharacter = Character.toString(trimmedString.charAt(trimmedString.length() - 1));
		int sumOfDigits = 0;
		int digitValue;
		int finalResult;
		boolean isValid = false;
		
		// if the last character is a digit or X character, determine number of digits
		// else, the ISBN is not valid
		if(lastCharacter.matches("\\d|X")) {
			// get only the digits from the string, excluding the last character
			String digits = trimmedString.substring(0, trimmedString.length() - 1).replaceAll("\\D", "");
			
			// if the number of digits is less than 9, the ISBN is not valid
			if(digits.length() == 9) {
				for(int d = 0; d < digits.length(); d++) {
					digitValue = Integer.valueOf(Character.toString(digits.charAt(d))) * (10 - d);
					sumOfDigits = sumOfDigits + digitValue;
				}
				
				if(lastCharacter.compareTo("X") == 0) {
					sumOfDigits = sumOfDigits + 10;
				}
				else {
					sumOfDigits = sumOfDigits + Integer.valueOf(lastCharacter);
				}
				
				finalResult = sumOfDigits % 11;
				
				if(finalResult == 0)
				{
					isValid = true;
				}
			}
		}
		
		return isValid;
	}

	/**
	 * 16. Determine if a sentence is a pangram. A pangram (Greek: παν γράμμα, pan
	 * gramma, "every letter") is a sentence using every letter of the alphabet at
	 * least once. The best known English pangram is:
	 * 
	 * The quick brown fox jumps over the lazy dog.
	 * 
	 * The alphabet used consists of ASCII letters a to z, inclusive, and is case
	 * insensitive. Input will not contain non-ASCII symbols.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isPangram(String string) {
		String trimmedString = string.replaceAll("\\W+", "").replaceAll("\\d+", "").toLowerCase(); // only care about letters
		Set<Character> foundLetters = new HashSet<Character>();
		boolean isPangram = false;
		int stringIndex = 0;
		int missedCharacters = 0; // number of characters that are repeated in the string
		int maxAllowedMissedChars = trimmedString.length() - 26; // maximum allowed characters to be repeated
		
		// if the string is less than 26, it is not a pangram
		if(trimmedString.length() >= 26) {
			// go through the string
			while(stringIndex < trimmedString.length()) {				
				// if the letter was successfully added, check if the number of letters is equal to 26
				if(foundLetters.add(Character.valueOf(trimmedString.charAt(stringIndex)))) {
					// if all the letters are in the set, set pangram to true and break
					if(foundLetters.size() == 26) {
						isPangram = true;
						break;
					}
				}
				else {
					missedCharacters++; // increment the number of repeated characters
					// if the number of missed characters is over the max allowed, break as it is impossible for the string to be a pangram
					if(missedCharacters > maxAllowedMissedChars) {
						break;
					}
				}

				stringIndex++;
			}
		}
		
		
		return isPangram;
	}

	/**
	 * 17. Calculate the moment when someone has lived for 10^9 seconds.
	 * 
	 * A gigasecond is 109 (1,000,000,000) seconds.
	 * 
	 * @param given
	 * @return
	 */
	public Temporal getGigasecondDate(Temporal given) {
		// TODO Write an implementation for this method declaration
		return null;
	}

	/**
	 * 18. Given a number, find the sum of all the unique multiples of particular
	 * numbers up to but not including that number.
	 * 
	 * If we list all the natural numbers below 20 that are multiples of 3 or 5, we
	 * get 3, 5, 6, 9, 10, 12, 15, and 18.
	 * 
	 * The sum of these multiples is 78.
	 * 
	 * @param i
	 * @param set
	 * @return
	 */
	public int getSumOfMultiples(int i, int[] set) {
		Set<Integer> multiplesSet = new HashSet<Integer>();
		int sumOfMultiples = 0;
		int currentMultiple;
		
		for(int m = 0; m < set.length; m++) {
			currentMultiple = set[m];
			
			while(currentMultiple < i) {
				if(multiplesSet.add(currentMultiple)) {
					sumOfMultiples = sumOfMultiples + currentMultiple;
				}
				
				currentMultiple = currentMultiple + set[m];
			}
		}
		
		return sumOfMultiples;
	}

	/**
	 * 19. Given a number determine whether or not it is valid per the Luhn formula.
	 * 
	 * The Luhn algorithm is a simple checksum formula used to validate a variety of
	 * identification numbers, such as credit card numbers and Canadian Social
	 * Insurance Numbers.
	 * 
	 * The task is to check if a given string is valid.
	 * 
	 * Validating a Number Strings of length 1 or less are not valid. Spaces are
	 * allowed in the input, but they should be stripped before checking. All other
	 * non-digit characters are disallowed.
	 * 
	 * Example 1: valid credit card number 1 4539 1488 0343 6467 The first step of
	 * the Luhn algorithm is to double every second digit, starting from the right.
	 * We will be doubling
	 * 
	 * 4_3_ 1_8_ 0_4_ 6_6_ If doubling the number results in a number greater than 9
	 * then subtract 9 from the product. The results of our doubling:
	 * 
	 * 8569 2478 0383 3437 Then sum all of the digits:
	 * 
	 * 8+5+6+9+2+4+7+8+0+3+8+3+3+4+3+7 = 80 If the sum is evenly divisible by 10,
	 * then the number is valid. This number is valid!
	 * 
	 * Example 2: invalid credit card number 1 8273 1232 7352 0569 Double the second
	 * digits, starting from the right
	 * 
	 * 7253 2262 5312 0539 Sum the digits
	 * 
	 * 7+2+5+3+2+2+6+2+5+3+1+2+0+5+3+9 = 57 57 is not evenly divisible by 10, so
	 * this number is not valid.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isLuhnValid(String string) {
		String trimmedString = string.replaceAll("\\s", ""); // remove all whitespace characters
		Pattern nonDigitRegex = Pattern.compile("\\D+"); // regex of non-digit characters
		Matcher matcher = nonDigitRegex.matcher(trimmedString); // matcher for the regex
		int sumOfDigits = 0;
		int charIndex;
		int digitValue;
		boolean isLuhn = false;
		
		// if no non-digit characters are found and the length is more than 1, validate the string
		if(!matcher.find() && trimmedString.length() > 1) {
			charIndex = trimmedString.length() - 1; // start at the last character index
			for(int d = 0; d < trimmedString.length(); d++) {
				digitValue = Integer.valueOf(Character.toString(trimmedString.charAt(charIndex)));
				
				if(d % 2 == 1) {
					digitValue = digitValue + digitValue;
					if(digitValue > 9)
					{
						digitValue = digitValue - 9;
					}
				}
				sumOfDigits = sumOfDigits + digitValue;
				charIndex = charIndex - 1; // decrement to the previous character index
			}
			
			if(sumOfDigits % 10 == 0) {
				isLuhn = true;
			}
		}
		return isLuhn;
	}

	/**
	 * 20. Parse and evaluate simple math word problems returning the answer as an
	 * integer.
	 * 
	 * Add two numbers together.
	 * 
	 * What is 5 plus 13?
	 * 
	 * 18
	 * 
	 * Now, perform the other three operations.
	 * 
	 * What is 7 minus 5?
	 * 
	 * 2
	 * 
	 * What is 6 multiplied by 4?
	 * 
	 * 24
	 * 
	 * What is 25 divided by 5?
	 * 
	 * 5
	 * 
	 * @param string
	 * @return
	 */
	public int solveWordProblem(String string) {
		String[] splitString = string.split("\\s+"); // split the string by whitespace
		String tempString; // string to manipulate input in
		String operatorString = ""; // holds the operator
		int[] operands = new int[2];
		int intCount = 0;
		int result = 0;
		Pattern operatorWords = Pattern.compile("^plus$|^minus$|^multiplied$|^divided$"); // check only for the four operator types
		Pattern nonNumberRegex = Pattern.compile("[^+\\-\\d]"); // regex to remove non-number characters, excluding the positive or negative sign
		
		int word = 0;
		while(word < splitString.length) {
			if(Pattern.matches(operatorWords.pattern(), splitString[word])) {
				operatorString = splitString[word];
			}
			else {
				tempString = splitString[word].replaceAll(nonNumberRegex.pattern(), "");
				if(!tempString.isEmpty()) {
					operands[intCount] = Integer.parseInt(tempString);
					intCount++;
				}
			}
			word++;
		}
		
		// perform operation based on the operator string
		switch(operatorString) {
			case "plus":
				result = operands[0] + operands[1];
				break;
			case "minus":
				result = operands[0] - operands[1];
				break;
			case "multiplied":
				result = operands[0] * operands[1];
				break;
			case "divided":
				result = operands[0] / operands[1];
				break;
		}
		
		return result;
	}

}
