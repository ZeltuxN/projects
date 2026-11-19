package LAB5;
import java.util.Scanner;
public class StringComparison {
	public static void CompareStrings(String s1, String s2){
		int size,count = 0;
		int[] arrsaver;
		if(s1.length()>s2.length()) {
			size = s2.length();
		}else {
			size = s1.length();
		}
		arrsaver = new int[size];
			for(int i=0; i<size; i++) {
				if(s1.charAt(i) == s2.charAt(i)) {
					arrsaver[count] = i;
							count++;
		}
	}
			for(int i=0; i<count; i++)
			System.out.println(arrsaver[i]);
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Give String 1:");
		String str1 = in.nextLine();
		System.out.println("Give String 2:");
		String str2 = in.nextLine();
		
		CompareStrings(str1,str2);
	}

}
