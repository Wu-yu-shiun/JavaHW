// https://docs.google.com/presentation/d/1rEx235ejwdRd1dHDZ0Di1-vT00tIeglGnpb4M7Ao8CU/edit#slide=id.g117afd00d56_0_0

package test;
import java.util.Scanner;

public class HW2 {
	public static void main(String[] args) {
		
	Scanner scanner = new Scanner(System.in);
        System.out.print("Keyboard input：");
        
        if (scanner.hasNextInt()){
            int inputNum = scanner.nextInt();
            if(inputNum<0 || inputNum>Math.pow(10,9)) System.out.println("E");
            else {
            	// 若整數n可以分割成k個連續整數，且首項為a => a,a+1,...,a+k-1
                // n=(2a+k-1)*k/2 => a=(2n/k-k+1)/2
            	for(int k=inputNum; k>=1; --k){

            		int a=(2*inputNum/k-k+1)/2;

            		//有首項為a的解
            		if(a>0 && (a*2-1+k)*k/2==inputNum) { 
            			for(int i=a;i<k+a;i++) {
            				System.out.printf("%d",i);
            				if(i==k+a-1) System.out.println();
            				else System.out.print("+");
            			}
            		}
            	}
            }  
        } 
        else System.out.println("E");
   
        scanner.close();
	}
}
