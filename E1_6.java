public class E1_6{
	public static boolean scan(String s){

		int state=0;
		int i=0;

		while(state >= 0 && i<s.length()){
			final char ch = s.charAt(i++);

			switch (state) {
			case 0:
				if(ch == 'a')
					state=1;
				else if(ch == 'b')
					state=0;
				else 
					state=-1;
				break;

			case 1:
				if(ch == 'a')
					state = 1;
				else if(ch == 'b')
					state = 2;
				else 
					state=-1;
				break;

			case 2:
				if(ch == 'a')
					state = 2;
				else if(ch == 'b')
					state = 3;
				else 
					state = -1;
				break;

			case 3:
				if(ch == 'a')
					state = 1;
				else if(ch == 'b')
					state = 4;
				else  
					state = -1;
				break;

			case 4:
				if(ch == 'a'|| ch == 'b')
					state = 4;
				else  
					state = -1;
				break;
			}
			
		}

		return state == 1 || state == 2 || state == 3;
	}
	
	public static void main(String [] args) {

		System.out.println(scan("abb"));
		System.out.println(scan("bbaba"));
		System.out.println(scan("baaaaaaa"));
		System.out.println(scan("aaaaaaa"));
		System.out.println(scan("a"));
		System.out.println(scan("ba"));
		System.out.println(scan("bba"));
		System.out.println(scan("aa"));
		System.out.println(scan("bbbababab"));
		System.out.println(scan("abbbbbb"));
		System.out.println(scan("bbabbbbbbbb"));
		System.out.println(scan("b"));

	}
}