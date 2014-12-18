/*
 *  This class is part of the 'Random Typewriter' assignment in the
 *  Language Technology course at KTH.
 *
 *  First version: Johan Boye, November 2014.
 */

import java.io.*;
import java.util.*;

public class RandomKey {
		// The START_END character is used to represent end of word/end of sentence.
		static final int START_END = 29;
		static final int NUMBER_OF_CHARS = 30;

	public HashMap<Integer, Double[]> neighbours = new HashMap<Integer, Double[]>();
	public int[] numberOfShown = new int[NUMBER_OF_CHARS];
	public int[] numberOfPressed = new int[NUMBER_OF_CHARS];
	
	Random random = new Random();

	void initNeighbours(String correctFile, String encryptedFile) {
		for (int i = 0; i < NUMBER_OF_CHARS; i++) {
			neighbours.put(i, new Double[NUMBER_OF_CHARS]);
			for (int j = 0; j < NUMBER_OF_CHARS; j++) {
				neighbours.get(i)[j] = new Double(0);
			}
		}
		try {
			//Start by counting the observations

			FileReader corrIn = new FileReader( correctFile );
			Scanner cor =  new Scanner( corrIn );
			FileReader encrIn = new FileReader( encryptedFile );
			Scanner enc =  new Scanner( encrIn );
			String corWord, encWord;
			while ( cor.hasNext() && enc.hasNext() ) {
				corWord = cor.next();
				encWord = enc.next();
				char[] c = corWord.toCharArray();
				char[] e = encWord.toCharArray();
				for (int i = 0; i < corWord.length(); i++) {
					int cc = ((int)c[i]-'a');
					int ee = (int)(e[i] - 'a');
					switch (e[i]) {
    /* We are using the ISO-8859-1 encoding, representing 'ö' by 246, 'ä' by 228, and 'å' by 229. */ 
					case 246: //ö
						numberOfShown[29]++;
						if (cc <= 26) {
							neighbours.get(c[i]-'a')[29] += 1;
							numberOfPressed[cc]++;
						}
						else if (cc+'a' == 'å') {
							neighbours.get(27)[29] += 1;
							numberOfPressed[27]++;
						}
						else if (cc+'a' == 'ä') {
							neighbours.get(28)[29] += 1;
							numberOfPressed[28]++;
						}
						else if (cc+'a' == 'ö') {
							neighbours.get(29)[29] += 1;
							numberOfPressed[29]++;
						}
						break;
					case 228: //ä
						numberOfShown[28]++;
						if (cc <= 26) {
							neighbours.get(c[i]-'a')[28] += 1;
							numberOfPressed[cc]++;
						}
						else if (cc+'a' == 'å') {
							neighbours.get(27)[28] += 1;
							numberOfPressed[27]++;
						}
						else if (cc+'a' == 'ä') {
							neighbours.get(28)[28] += 1;
							numberOfPressed[28]++;
						}
						else if (cc+'a' == 'ö') {
							neighbours.get(29)[28] += 1;
							numberOfPressed[29]++;
						}
						break;
					case 229: //å
						numberOfShown[27]++;
						if (cc <= 26) {
							neighbours.get(c[i]-'a')[27] += 1;
							numberOfPressed[cc]++;
						}
						else if (cc+'a' == 'å') {
							neighbours.get(27)[27] += 1;
							numberOfPressed[27]++;
						}
						else if (cc+'a' == 'ä') {
							neighbours.get(28)[27] += 1;
							numberOfPressed[28]++;
						}
						else if (cc+'a' == 'ö') {
							neighbours.get(29)[27] += 1;
							numberOfPressed[29]++;
						}
						break;
					default:
						numberOfShown[ee]++;
						if (cc <= 26) {
							neighbours.get(c[i]-'a')[e[i]-'a'] += 1;
							numberOfPressed[cc]++;
						}
						else if (cc+'a' == 'å') {
							neighbours.get(27)[e[i]-'a'] += 1;
							numberOfPressed[27]++;
						}
						else if (cc+'a' == 'ä') {
							neighbours.get(28)[e[i]-'a'] += 1;
							numberOfPressed[28]++;
						}
						else if (cc+'a' == 'ö') {
							neighbours.get(29)[e[i]-'a'] += 1;
							numberOfPressed[29]++;
						}
						break;
					}
				}
			}

		corrIn.close();
		encrIn.close();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	// Turn the values into probabilities
		for (Map.Entry<Integer, Double[]> entry : neighbours.entrySet()) {
			Double[] cur = entry.getValue();
			double total = 0;
			for (int i = 0; i < 28; i++) {
				total += cur[i];
			}
      for (int i = 0; i < 28; i++) {
				if (total != 0) {
					cur[i] = cur[i] / total;
				}
				else {
					cur[i] = 0.0;
				}
			}
		}
	}

	void readEvalPrint() {
	while ( true ) {
	    try {
		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
		char[] chars = (in.readLine()).toCharArray();
		char[] result = new char[chars.length];
		for ( int i=0; i<chars.length; i++ ) {
		    result[i] = keyPress( chars[i] );
		}
		System.out.println( new String( result ));
	    }
	    catch ( IOException e ) {
		e.printStackTrace();
	    }
	}
    }

    static int charToIndex( char c ) {
	if ( c>='a' && c<='z' )
	    return c-'a';
	else if ( c == 246 ) 
	    return 26;
	else if ( c == 228 ) 
	    return 27;
	else if ( c == 229 )
	    return 28;
	else
	    return START_END;
    }


    static char indexToChar( int i ) {
	if ( i < 27)
	    return (char) (i + 'a');
	if ( i < 30) {
			return '5'; //TODO add åäö
	}
	else 
	    return '.';
    }

    char keyPress( char c ) {
	int index = charToIndex( c );
	if ( index == START_END ) 
	    return c;
	else {
	  double r = random.nextDouble(); //r = 0.0-1.0 inclusive
		Double[] prob;

		switch (c) {
		case 246: //ö
			prob = neighbours.get(29);
			break;
		case 228: //ä
			prob = neighbours.get(28);
			break;
		case 229: //å
			prob = neighbours.get(27);
			break;
		default:
			prob = neighbours.get(c-'a');
			break;
		}
		for (int i = 0; i < RandomKey.NUMBER_OF_CHARS; i++) {
			r -= prob[i];
			if (r <= 0.00004) {
				if (i <= 26) return (char)(i + 'a');
				if (i == 27) return 'å';
				if (i == 28) return 'ä';
				return 'ö';
			}
		}
		return c;
	}
    }
 
    public static void main( String[] args ) {
	RandomKey r = new RandomKey();
	r.readEvalPrint();
    }
}
