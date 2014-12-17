/*
 *  This class is part of the 'Random Typewriter' assignment in the
 *  Language Technology course at KTH.
 *
 *  First version: Johan Boye, November 2014.
 */

import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class RandomKey {

	class Key {
		public Key(char k) {
			ch = k;
			prob = 0;
		}
		public char ch;
		public int prob;
	}
    
    /* We are using the ISO-8859-1 encoding, representing 'ö' by 246, 'ä' by 228, and 'å' by 229. */ 
	static ArrayList<Key> keys = new ArrayList<Key>();
	keys.add(new Key('a'));
	keys.add(new Key('b'));
	keys.add(new Key('c'));
	keys.add(new Key('d'));
	keys.add(new Key('e'));
	keys.add(new Key('f'));
	keys.add(new Key('g'));
	keys.add(new Key('h'));
	keys.add(new Key('i'));
	keys.add(new Key('j'));
	keys.add(new Key('k'));
	keys.add(new Key('l'));
	keys.add(new Key('m'));
	keys.add(new Key('n'));
	keys.add(new Key('o'));
	keys.add(new Key('p'));
	keys.add(new Key('q'));
	keys.add(new Key('r'));
	keys.add(new Key('s'));
	keys.add(new Key('t'));
	keys.add(new Key('u'));
	keys.add(new Key('v'));
	keys.add(new Key('w'));
	keys.add(new Key('x'));
	keys.add(new Key('y'));
	keys.add(new Key('z'));
	keys.add(new Key(246));
	keys.add(new Key(228));
	keys.add(new Key(229));
	
	Random random = new Random();

	void initKey(string filename) {
		try {
			FileReader in = new FileReader( filename );
			Scanner scan =  new Scanner(in );
			while ( scan.hasNext() ) {
				string 
			}
		} catch {

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
