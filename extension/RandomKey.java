/*
 *  This class is part of the 'Random Typewriter' assignment in the
 *  Language Technology course at KTH.
 *
 *  First version: Johan Boye, November 2014.
 */

import java.io.*;
import java.util.Random;
import java.util.HashMap;

public class RandomKey {

	HashMap<char, double[]> neighbours = new HashMap<char, int[]>();
	
	Random random = new Random();

	void initNeighbours(string correctFile, string encryptedFile) {
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
					switch (e) {
    /* We are using the ISO-8859-1 encoding, representing 'ö' by 246, 'ä' by 228, and 'å' by 229. */ 
					case 246: //ö
						neighbours.get(c)[28]+=1;
						break;
					case 228: //ä
						neighbours.get(c)[27]+=1;
						break;
					case 229: //å
						neighbours.get(c)[26]+=1;
						break;
					default:
						neighbours.get(c)[e-'a']+=1;
						break;
					}
				}
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	try { corrIn.close();
				encrIn.close(); } catch (Exception e) { e.printStackTrace();}

	// Turn the values into probabilities
		for (Map.Entry<char, double[]> entry : neighbours.entrySet()) {
			int[] cur = entry.getValue();
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
	if ( i < key.length )
	    return key[i];
	else 
	    return '.';
    }

    char keyPress( char c ) {
	int index = charToIndex( c );
	if ( index == START_END ) 
	    return c;
	else {
	    int r = random.nextInt( 10 );
	    if ( r<neighbour[index].length )
		return neighbour[index][r];
	    else
		return c;
	}
    }
 
    public static void main( String[] args ) {
	RandomKey r = new RandomKey();
	r.readEvalPrint();
    }
}
