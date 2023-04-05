//https://docs.google.com/presentation/d/1MvDur8Z5AHtYFgCHN8Ihj7fwayk2J5nEuUd3l-L5qmE/edit#slide=id.g22800733fb1_0_28

package hw4;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		
		Scanner scanner=new Scanner(System.in);
		int[] result={0,0,0,0};
		boolean legal=true;
		
		//發牌階段
		String[][] card=new String[4][13];
		
		for(int i=0;i<4;++i) {
			String[] temp=scanner.nextLine().split(" ");
			for(int j=0;j<temp.length;++j) card[i][j]=temp[j];
		}
		
		//叫牌階段
		int pass=0; //計算連續pass的次數
		int current_num=0;	//記錄目前叫牌到達的大小:數字10權重,A=4,B=3,C=2,D=1
		char king_color='N';
		int first_player=0;
		
		while(pass<3) {
			first_player++;
			String bid=scanner.nextLine();
			if(bid.equals("pass")) pass++;
			else {
				pass=0;
				int bid_num=0;
				char bid_color='N';
				for(int i=0;i<bid.length();++i) {
					char c=bid.charAt(i);
					if (Character.isDigit(c)) bid_num=10*Character.getNumericValue(c);
					else {
						switch(c) {
					    case 'A':
					        bid_num+=4;
					        bid_color='A';
					        break;
					    case 'B':
					        bid_num+=3;
					        bid_color='B';
					        break;
					    case 'C':
					        bid_num+=2;
					        bid_color='C';
					        break;
					    case 'D':
					        bid_num+=1;
					        bid_color='D';
					        break;
					    default:
					        legal=false;
						}
					}	
				}
				
				//確認叫牌合理性
				if(bid_num>current_num && bid_num<40) {
					 current_num=bid_num;
					 king_color=bid_color;
				}
				else legal=false;
			}
		}
		
		first_player=(first_player-2)%4;
		first_player=(first_player==0?4:first_player); //第一局先出牌的玩家
		
		//if(king_color=='N') legal=false;

		//出牌階段
		for(int i=0;i<3;++i) {
			String[] throw_card=scanner.nextLine().split(" ");
			char current_color=throw_card[0].replaceAll("[0-9]", "").charAt(0); //該輪花色
			
			//確認玩家出牌合理性
			for(int j=0;j<4;++j) {
				int player=first_player+j;	
				player=player>4?player%4:player;
				char player_color=throw_card[j].replaceAll("[0-9]", "").charAt(0);
				boolean exist=false,empty=true; //該玩家出的牌是否exist,所需花色是否empty
				for(String s:card[player-1]) {
					if(s.equals(throw_card[j])) exist=true;
					if(s.charAt(1)==current_color) empty=false;
				}
				if(!exist) legal=false; //出了自己沒有的牌
				if(player_color!=current_color && !empty) legal=false; //有該花色但沒出掉
				
				//System.out.println("player"+player+"---exist:"+exist+",empty:"+empty);
			}
			
			//判定獲勝者
			int winner=0,record=0;
			for(int j=0;j<4;++j) {
				int player=first_player+j;	
				player=player>4?player%4:player;
				//取得該牌之花色＆數字
				char color=throw_card[j].replaceAll("[0-9]", "").charAt(0);
				int number=Integer.parseInt(throw_card[j].replaceAll("[a-zA-Z]", ""));
				number=(number==1)?14:number; //把數字1當成14
				//設定權重：王牌10000,該輪花色100,非該輪花色1
				int weight;	 
				if(color==king_color) weight=10000;
				else if(color==current_color) weight=100;
				else weight=1;

				if(number*weight>record) {
					record=number*weight;
					winner=player;
				}
				//System.out.println("player"+player+"'s point:"+number*weight);
			}
			result[winner-1]++;
			first_player=winner;
		}

		//result
		if(legal) for(int res:result) System.out.println(res);
		else System.out.println("ERROR");
		scanner.close();
	}
}

/*
2A 12B 1B 13D 11A 8A 10A 3D 1D 4D 10D 12A 3B
8B 1A 1C 13C 4A 9A 7B 7C 8C 12C 9D 12D 13B
10B 5C 5D 2D 4B 5B 9B 3C 9C 10C 7D 11D 8D
3A 2B 5A 2C 7A 6B 11B 4C 11C 6A 6C 6D 13A
1A
2D
2B
pass
pass
3C
pass
pass
pass
10B 2B 12B 8B
11A 1A 5C 5A
5D 6D 3D 12D

A:1/1/1/0

2A 12B 1C 13D 11A 8A 10A 6C 10C 4D 10D 13A 13C
8B 2B 7A 11C 4A 9A 7B 7C 5C 12C 9D 12D 13B
10B 8C 5D 6A 4B 5B 9B 5A 3C 9C 1D 7D 12A
3A 3B 2C 1A 6B 11B 4C 1B 2D 11D 3D 6D 8D
1C
1A
2D
2B
pass
pass
pass
8A 4A 12A 1A 
2C 1C 5C 5A 
2A 9A 12A 3A

A:ERROR

2A 12B 1C 13D 11A 8A 10A 6C 10C 4D 10D 13A 13C
8B 2B 7A 11C 4A 9A 7B 7C 5C 12C 9D 12D 13B
10B 8C 5D 6A 4B 5B 9B 5A 3C 9C 1D 7D 12A
3A 3B 2C 1A 6B 11B 4C 1B 2D 11D 3D 6D 8D
1C
1A
2D
2B
2C
pass
pass
pass
8A 4A 12A 1A
2C 1C 5C 5A
2A 9A 12A 3A

A:ERROR
*/
