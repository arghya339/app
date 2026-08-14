package com.offlinew.practica.utils;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomNumberUtil {

    // Returns a random number between min (inclusive) and max (exclusive)
    public static int randomNumberBetween(int min, int max) {
        if(min==max){
            return  -1;
        }
        if (min > max) {
            int temp = max;
            max=min;
            min=temp;
        }
        Random random = new Random();
        return random.nextInt((max - min)) + min;
    }

    public static long randomNumberBetweenL(long min, long max) {
        if(min==max){
            return  -1L;
        }
        if (min > max) {
            long temp = max;
            max=min;
            min=temp;
        }
        Random random = new Random();
        long generated = random.nextLong();
        if(generated<0){
            generated = -generated;
        }
        return (generated % (max - min)) + min;
    }

    public static long randomNumberNdigit(int n){
        long ans = 0L;
        if(n>15){
            return ans;
        }
        for(int i=0;i<n;i++){
            ans*=10L;
            ans += randomNumberBetween(0,10);
        }
        return ans;
    }


    public static int weightedRandomNumber(long[] randomSelection,int n_samples){
        long generated_random_number = randomNumberBetween(0,1000000);

        long[] relative_ranking_1M = new long[n_samples];
        long total_weight = 0;
        for(long f:randomSelection){
            total_weight+=f;
        }

        long weight_so_far = 0;
        for(int i=0;i<n_samples;i++){
           weight_so_far += randomSelection[i];
           if((long)((weight_so_far*1e6)/total_weight)>=generated_random_number){
               return i;
           }
        }
        return -1;
    }

    public static ArrayList<Integer> getMrandomLessthanN(int n, int m){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i<n;i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return new ArrayList<>(numbers.subList(0,min(m,n)));
    }


}
