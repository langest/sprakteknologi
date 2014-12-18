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
		static final int START_END = 30;
		static final int NUMBER_OF_CHARS = 30;

	public HashMap<Integer, Double[]> neighbours = new HashMap<Integer, Double[]>();
	
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
					switch (e[i]) {
    /* We are using the ISO-8859-1 encoding, representing 'ö' by 246, 'ä' by 228, and 'å' by 229. */ 
					case 246: //ö
						neighbours.get(29)[28]+=1;
						break;
					case 228: //ä
						neighbours.get(28)[27]+=1;
						break;
					case 229: //å
						neighbours.get(27)[26]+=1;
						break;
					default:
						neighbours.get(c[i]-'a')[e[i]-'a']+=1;
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
			int total = 0;
			for (int i = 0; i < 28; i++) {
				total += cur[i];
			}
      for (int i = 0; i < 28; i++) {
				cur[i] = cur[i] / total;
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
		//TODO fix so it scrambles correctly
	    int r = random.nextInt( 100 );
	    if ( 1<2 ) //r<neighbour[index].length )
		return 'a';//neighbour[index][r];
	    else
		return c;
	}
    }
 
    public static void main( String[] args ) {
	RandomKey r = new RandomKey();
	r.readEvalPrint();
    }
}
