public class GCD {
	
	public static void main(String args[])
	{
		int a=12;
		int b=21;
		int[] ans=ExtEuclid(a, b);
		System.out.println("GCD::"+ans[1]+"*"+a+"+"+ans[2]+"*"+b+"="+ans[0]);
	}

	public static int[] ExtEuclid(int a, int b) {
		int[] ans = new int[3];
		int q;

		if (b == 0) {
			ans[0] = a;
			ans[1] = 1;
			ans[2] = 0;
		} else {
			q = a / b;
			ans = ExtEuclid(b, a % b);
			int temp = ans[1] - ans[2] * q;
			ans[1] = ans[2];
			ans[2] = temp;
		}

		return ans;
	}

}
