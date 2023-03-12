// https://docs.google.com/presentation/d/1yfpWBtgkAzMD3yBe8IhHuWaOgFLouOphPSNHKcmIICQ/edit#slide=id.p

package test;
import java.util.Arrays;
import java.util.Scanner;

public class Hello {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int frames=10,chance=2;
		int[][] record=new int[frames][chance];
		int bonus=-1; //最後一局第三球，-1表示未使用該球
		for (int[] row:record) Arrays.fill(row,-1);
		
		//input
		System.out.print("Keyboard input：\n");
		for(int i=0;i<frames;++i) {
			if(i==9) {
				record[9][0]=scanner.nextInt();
				record[9][1]=scanner.nextInt();
				if(record[9][0]+record[9][1]>=10) bonus=scanner.nextInt();
			}
			else {
				record[i][0]=scanner.nextInt();
				if(record[i][0]<10) record[i][1]=scanner.nextInt();
			}
		}
		
		//calculate
		int totalScore=0;
		for(int i=0;i<frames-1;++i) {
			int frameScore=0;
			
			frameScore+=record[i][0];
			if(record[i][0]==10) {
				frameScore=10+record[i+1][0];
				frameScore+=((record[i+1][1]==-1)?record[i+2][0]:record[i+1][1]);
			}
			else if(record[i][0]+record[i][1]==10) frameScore=10+record[i+1][0];
			else frameScore=record[i][0]+record[i][1];
			
			totalScore+=frameScore;
		}
		totalScore=totalScore+record[9][0]+record[9][1];
		if(bonus!=-1) totalScore+=bonus;
		
		System.out.printf("Score:%d",totalScore);
        scanner.close();
	}
}

/*
test1=127
4 4
3 7
8 2
3 7
6 0
10
7 0
10
10
2 3

test2=189
10
10
5 5
6 4
10
5 4
10
3 7
10
9 1 10
*/
