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

	public HashMap<Character, Double[]> neighbours = new HashMap<Character, Double[]>();
	
	Random random = new Random();

	void initNeighbours(String correctFile, String encryptedFile) {
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
						neighbours.get(c[i])[28]+=1;
						break;
					case 228: //ä
						neighbours.get(c[i])[27]+=1;
						break;
					case 229: //å
						neighbours.get(c[i])[26]+=1;
						break;
					default:
						neighbours.get(c[i])[e[i]-'a']+=1;
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
		for (Map.Entry<Character, Double[]> entry : neighbours.entrySet()) {
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
