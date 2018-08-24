package com.example.yun.tankboy;

public class Convert {
    final int base1 = 910;
    final double add_base1 = 93.3;

    final int base2 = 1600;
    final double add_base2 = 187.9;
    final int total1 = 18660;

    final int base3 = 7300;
    final double add_base3 = 280.6;
    final int total2 = 56240;

    final double tax = 0.1;
    final double donation = 0.037;

    static String wt_goal = "";
    static int wt_goal_int = 0;

    public String Cost(int wt){ //요금 계산

        int switch_case;
        int switch_value = wt; //kwh
        int value = 0;

        if(switch_value == 200)
            switch_case = 0;
        else if(switch_value == 400)
            switch_case = 1;
        else{
            switch_case = switch_value / 200;
        }

        switch(switch_case){
            case 0 :
                value = case1(switch_value);
                break;
            case 1 :
                value = case2(switch_value);
                break;
            default :
                value = case3(switch_value);
                break;
        }
        String final_cost = Integer.toString(value);

        return final_cost;
    }
    public int case1(int wt){
        int case1_one = (int)(base1 + (add_base1 * wt));
        int case1_two = (int)Math.round(case1_one * tax) + (int)(Math.floor(case1_one * donation * tax) / tax);
        int   cost =  (case1_one + case1_two) / 10 * 10;
        return cost;
    }
    public int case2(int wt){
        int case2_one = (int)(base2 + (add_base2 * (wt - 200)) + total1);
        int case2_two = (int)Math.round(case2_one * tax) + (int)(Math.floor(case2_one * donation * tax) / tax);
        int cost = (case2_one + case2_two) / 10 * 10;
        return cost;
    }
    public int case3(int wt){
        int case3_one = (int)(base3 + (add_base3 * (wt - 400)) + total2);
        int case3_two = (int)Math.round(case3_one * tax) + (int)(Math.floor(case3_one * donation * tax) / tax);
        int cost = (case3_one + case3_two) / 10 * 10;
        return cost;
    }

    public String reverse_won(String won_s){
        if(Integer.parseInt(won_s) < 1130){
            return "1";
        }

        int [] set_purpose = new int [1024];
        int [] set_i = new int [1024];

        int count = 0;
        int s = won_s.length(); //5자리는 5
        int won = Integer.parseInt(won_s);

        int set_length = 10;
        for(int i = 1; i < s; i++){
            set_length *= 10;
        }

        int won_purpose = (won / set_length) * set_length; // 24500 -> 20000

        for(int i = 1 ; i <= 1000 ; i++){
            String cost = Cost(i);
            int p_cost = Integer.parseInt(cost);

            int cost_purpose = (p_cost / set_length) * set_length;

            if(won_purpose == cost_purpose){
                set_purpose[count] = p_cost;
                count++;
            }
        }
        String final_wt = reverse_wt(set_purpose, won);
        int search_wt = Integer.parseInt(final_wt);


        for(int i = 1 ; i <= 1000; i++){
            String cost = Cost(i);
            int p_cost = Integer.parseInt(cost);

            if(search_wt == p_cost){
                wt_goal = Integer.toString(i * 1000);
                wt_goal_int = Integer.parseInt(wt_goal);
                break;
            }
        }

        return wt_goal;

    }

    public String reverse_wt(int [] sp, int won){
        int [] a = sp;
        int min = Integer.MAX_VALUE;
        int temp = 0;
        int length_save = 0;

        for(int i = 0 ; i< a.length ; i++){
            temp = (int)Math.abs(a[i] - won);
            if(min > temp){
                min = temp;
                length_save = i;
            }
        }

        String final_wt = Integer.toString(a[length_save]);

        return final_wt;
    }
}
