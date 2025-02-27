public class Es1_5{
	public static boolean scan(String s) {

			int state = 0;
			int i = 0;

			while (state >= 0 && i < s.length()) {
			  final char ch = s.charAt(i++);

	 	 	  switch (state){

	 	 	  		case 0:
	 	 	  			if(ch >= 'A' && ch <= 'K')
	 	 	  				state = 1;
	 	 	  			else if (ch >= 'L' && ch <= 'Z')
	 	 	  				state = 2;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 1:
	 	 	  			if(Character.isDigit(ch))
	 	 	  				state = ch % 2 == 0 ? 5:3;
	 	 	  			else if(ch >= 'a' && ch <= 'z')
	 	 	  				state = 1;
	 	 	  			else
	 	 	  				state = -1;

	 	 	  		break;

	 	 	  		case 2:
	 	 	  			if(Character.isDigit(ch))
	 	 	  				state = ch % 2 == 0 ? 4:6;
	 	 	  			else if(ch >= 'a' && ch <= 'z')
	 	 	  				state = 2;
	 	 	  			else
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 3:
	 	 	  			if(Character.isDigit(ch))
	 	 	  				state = ch % 2 == 0 ? 5:3;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	 	 	case 4:
	 	 	 	 		if(Character.isDigit(ch))
	 	 	  				state = ch % 2 == 0 ? 4:6;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 5:
	 	 	  			if(ch % 2 == 0)
	 	 	  				state = 5;
	 	 	  			else if(ch % 2 == 1)
	 	 	  				state = 3;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;

	 	 	  		case 6:
	 	 	  			if(ch % 2 == 0)
	 	 	  				state = 4;
	 	 	  			else if(ch % 2 == 1)
	 	 	  				state = 6;
	 	 	  			else 
	 	 	  				state = -1;
	 	 	  		break;


	 	 		}
	 	 	}

	 	 	return state==5 || state==6;

		}   

		public static void main (String [] args){
			System.out.println(scan(args[0]));
		}
}