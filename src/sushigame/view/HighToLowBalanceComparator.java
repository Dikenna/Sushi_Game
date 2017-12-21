package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class HighToLowBalanceComparator implements Comparator<Chef> {

	@Override
	public int compare(Chef a, Chef b) {
		// We do b - a because we want largest to smallest
		return (int) (Math.round(b.getBalance()*100.0) - 
				Math.round(a.getBalance()*100));
	}		
	
	//To compare consumed food
	public int foodConsumed_compare(Chef a, Chef b){
		return (int) (Math.round(b.getConsumedAmount()*100.0) - 
				Math.round(a.getConsumedAmount()*100));
	}
	
	//To compare spoiled food, a - b becasue we want smallest to largest
	public int foodSpoiled_compare(Chef a, Chef b){
		return (int) (Math.round(a.getSpoiledAmount()*100.0) - 
				Math.round(b.getSpoiledAmount()*100));
	}	
}
