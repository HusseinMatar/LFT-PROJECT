public class E1_7{
	public static boolean scan (String s){
		int state = 0;
		int i = 0;

		while(state>=0 && i < s.length()){
			final char ch = s.charAt(i++);

			switch(state){
			case 0:
				if(ch == 'M')
					state=1;
				else if(ch != 'M')
					state=6;
				else
					state=-1;
				break;

			case 1:
				if(ch == 'a')
					state=2;
				else if(!(ch == 'a'))
					state=10;
				else
					state=-1;
				break;

			case 2:
				if(ch == 't')
					state=3;
				else if(ch != 't')
					state=8;
				else
					state=-1;
				break;

			case 3:
				if(ch== 'a')
					state=4;
				else if(ch != 'a')
					state=9;
				else
					state=-1;
				break;

			case 4:
				if(ch != ' ')
					state=5;
				else
					state=-1;
				break;

			case 5:
				if(ch != ' ')
					state=-1;
				break;

			case 6:
				if(ch== 'a')
					state=7;
				else
					state=-1;
				break;

			case 7:
				if(ch=='t')
					state=8;
				else 
					state=-1;
				break;

			case 8:
				if(ch=='a')
					state=9;
				else 
					state=-1;
				break;

			case 9:
				if(ch=='r')
					state=5;
				else 
					state=-1;
				break;

			case 10:
				if(ch=='t')
					state=8;
				else 
					state=-1;
				break;

			

			}
		}
		return state == 5;
	}
	public static void main (String [] args){
		
		System.out.println(scan("Matar"));//true
		System.out.println(scan("satar"));//true
		System.out.println(scan("Mdtar"));//true
		System.out.println(scan("Marar"));//true
		System.out.println(scan("Matgr"));//true
		System.out.println(scan("Matab"));//true
		System.out.println(scan("%atar"));//true
		System.out.println(scan("M$tar"));//true
		System.out.println(scan("Ma/ar"));//true
		System.out.println(scan("Mat@r"));//true
		System.out.println(scan("Mata!"));//true
		System.out.println(scan("1atar"));//true
		System.out.println(scan("M tar"));//true
		System.out.println(scan("Ma ar"));//true
		System.out.println(scan("!ata!"));//false
		System.out.println(scan("123"));//false
		System.out.println(scan("masfdas"));//false
		System.out.println(scan("1234"));//false
	}
}