public class Es1_3
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {

	    case 0:
	    	if(Character.isDigit(ch))
	    	{
	    	
	    		state = ch % 2 == 0 ? 1 : 2;
	    		
	    	}
	    	else
	    		state = -1;
	    	break;

	    case 1:
	    	
	    	if(!Character.isDigit(ch) && !(ch >= 'A' && ch <= 'K') )
	    		state = -1;
	    	else {
	    		if(ch >= 'A' && ch <= 'K') state = 3;
	    		else {
	    			state = ch % 2 == 0 ? 1 : 2;
	    		}
	    	}
	        break;

	    case 2:
	    	
	    	if(!Character.isDigit(ch) && !(ch >= 'L' && ch <= 'Z'))
	    		state = -1;
	    	else {
	    		if(ch >= 'L' && ch <= 'Z') state = 4;
	    		else {
	    			state = ch % 2 == 0 ? 1 : 2;
	    		}
	    	}
	        break;

	     case 3:
	     case 4:
	    	if(!(ch >= 'a' && ch <= 'z'))
	    		state = -1;
	    	
	        break;

	    }



	}
	return state == 3 || state == 4;
    }

    public static void main(String[] args)
    {
	System.out.println(scan("1Bianchi") ? "OK" : "NOPE");
    }
}