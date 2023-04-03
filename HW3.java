//https://docs.google.com/presentation/d/1KHR_aYT932MpIhTEHEImHV3-E1VADG_uk613FlbvLwY/edit#slide=id.p

package hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Test{
	
	public static boolean check_flush(int[] card_num){
		Arrays.sort(card_num);
		//System.out.println(Arrays.toString(card_num));
		
		int count=1;
		for(int i=0;i<card_num.length-1;++i){
			if(card_num[i+1]==card_num[i]+1) count++;
			else if(card_num[i+1]>card_num[i]+1)count=1;
		}

		//特別處理10,11,12,13,1
		if(count==4 && card_num[0]==1 && card_num[card_num.length-1]==13) 
			count=5;
		
		return count==5?true:false;
	}

	public static void main(String[] args){
		
		Scanner scanner = new Scanner(System.in);
		
		int[] result=new int[6];  //玩家目前偵測出來的最好牌型
		
		//input
		String pub=scanner.nextLine();
		String[] public_card = pub.split(" ");
		String[][] player_card =new String[6][2];
		for(int i=0;i<6;++i) {
			String[] temp=scanner.nextLine().split(" ");
			player_card[i][0]=temp[0];
			player_card[i][1]=temp[1];
		}
		
		//找到原先輸入公共牌字串中出現超過三次的英文字母(之後要找同花)
		char color='N';
		char[] all_color={'S','H','D','C'};
		for(char c:all_color){
			int count=0;
			for(int i=0;i<pub.length();i++)
				if(pub.charAt(i)==c) count++;
			if(count>=3) {
				color=c;
				//System.out.println("find:"+c);
				break;
			}
		}
		
		for(int i=0;i<6;++i) {
			int[] card_num=new int[7]; 
			for(int j=0;j<public_card.length;++j) card_num[j]=Integer.parseInt(public_card[j].replaceAll("[a-zA-Z]", ""));
			card_num[5]=Integer.parseInt(player_card[i][0].replaceAll("[a-zA-Z]", ""));
			card_num[6]=Integer.parseInt(player_card[i][1].replaceAll("[a-zA-Z]", ""));

			//檢查是否有鐵支＆葫蘆＆三條＆兩對＆子			
			Map<Integer,Integer> map=new HashMap<>();
		    for(int num:card_num) map.put(num,map.getOrDefault(num,0)+1);
		    if(map.containsValue(4)) result[i]=7;
		    else if(map.containsValue(3)&&map.containsValue(2)) result[i]=6;
		    else if(map.containsValue(3)) result[i]=3;
		    else {
		    	int count=0;
			    for(Map.Entry<Integer,Integer> entry:map.entrySet()){
			    	 if(entry.getValue()==2) count++;
			    }
			    result[i]=count>=2?2:count;
		    }
		    
			//檢查是否有順子
			if(result[i]<6 && check_flush(card_num)) result[i]=4; 
			
			//檢查是否有同花同花順
			if(result[i]<6 && color!='N'){
				List<Integer> list=new ArrayList<>();
		        for (String card:public_card) {
		        	if(card.contains(Character.toString(color))) 
		            	list.add(Integer.parseInt(card.replaceAll("[a-zA-Z]", "")));
		        }
		        for (String card:player_card[i]) {
		        	if(card.contains(Character.toString(color))) 
		            	list.add(Integer.parseInt(card.replaceAll("[a-zA-Z]", "")));
		        }
		        
		        if(list.size()>=5){
		        	result[i]=5;
		        	int[] new_card_num=new int[list.size()];
			        for (int j=0;j<list.size();j++) {
			        	new_card_num[j]=list.get(j);
			        	
			        }
			        if(check_flush(new_card_num)) result[i]=8;
		        }
			}
		}

		//result
		for(int res:result) System.out.println(res);
		scanner.close();
	}
}

/*

1S 1D 2S 3S 6D
4S 5S
1H 12D
10S 13S
12D 13D
6S 6H
6C 10C 

A:8/3/5/1/6/2

9H 9D 11H 3H 10H
8S 12C
9H 9C
12H 13H
10D 3D
4S 6H
9C 10C 

A:4/7/8/2/5/6

*/
