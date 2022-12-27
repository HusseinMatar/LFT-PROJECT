public class Es1_4{
			
		public static boolean scan(String s) {

			int state = 0;
			int i = 0;

			while (state >= 0 && i < s.length()) {
			  final char ch = s.charAt(i++);

	 	 	  switch (state){

	 	 	  		case 0:
	 	 	  			if(ch == ' ')
	 	 	  				state = 0;
	 	 	  			else if(Character.isDigit(ch))
	 	 	  				state = ch % 2 == 1 ? 1:2;
	 	 	  			else
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 1:
	 	 	  			if(Character.isDigit(ch))
	 	 	  				state = ch % 2 == 1 ? 1:2;
	 	 	  			else if(ch == ' ')
	 	 	  				state = 3;
	 	 	  			else if(ch >= 'L' && ch <= 'Z')
	 	 	  				state=5;
	 	 	  			else
	 	 	  				state = -1;

	 	 	  		break;

	 	 	  		case 2:
	 	 	  			if(Character.isDigit(ch))
	 	 	  				state = ch%2 == 1 ? 1:2;
	 	 	  			else if(ch == ' ')
	 	 	  				state = 4;
	 	 	  			else if(ch >= 'A' && ch <= 'K')
	 	 	  				state = 5;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 3:
	 	 	  			if(ch== ' ')
	 	 	  				state = 3;
	 	 	  			else if(ch >= 'L' && ch <= 'Z')
	 	 	  				state = 5;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	 	 	case 4:
	 	 	 	 		if(ch == ' ')
	 	 	  				state = 4;
	 	 	  			else if(ch >= 'A' && ch <= 'K')
	 	 	  				state = 5;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 5:
	 	 	  			if(ch >= 'a' && ch <= 'z')
	 	 	  				state = 5;
	 	 	  			else if(ch == ' ')
	 	 	  				state = 6;
	 	 	  			else
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 6:
	 	 	  			if(ch == ' ')
	 	 	  				state = 6;
	 	 	  			else if(ch >= 'A' && ch <= 'Z')
	 	 	  				state = 5;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;


	 	 		}
	 	 	}

	 	 	return state==5 || state==6;

		}   

    	public static void main(String[] args) {
			System.out.println(scan("654321 Rossi") ? "OK" : "NOPE"); //ok
			System.out.println(scan(" 123456 Bianchi  ") ? "OK" : "NOPE"); //ok
			System.out.println(scan("1234 56Bianchi") ? "OK" : "NOPE"); //nope
			System.out.println(scan(" 1234 Bia nchi") ? "OK" : "NOPE"); //nope
			System.out.println(scan(" 12 De Za") ? "OK" : "NOPE");//nope
    	}
	
}