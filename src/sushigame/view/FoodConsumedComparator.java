package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class FoodConsumedComparator implements Comparator<Chef> {

	@Override
	public int compare(Chef a, Chef b) {
		/* To compare consumed food 
		 * We do b - a because we want largest to smallest
		 */
		return (int) (Math.round(b.getConsumedAmount()*100.0) - 
				Math.round(a.getConsumedAmount()*100));
	}

}
