public class atoi {

	public static int simpleAtoI(String str) {
		int result = 0;

		for (int i = 0;i < str.length() ; i++ ) {
			char c = str.charAt(i);
			int j = c - '0';
			result = (result * 10) + j;
			if( result > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			}
			if(result < Integer.MIN_VALUE) {
				return Integer.MIN_VALUE;
			}
			
		}
		return result;
	}

	 public static void main(String[] args) {
	 	String s = "12";
	 	System.out.println("The atoi value is: "+simpleAtoI(s));

		
	}
}