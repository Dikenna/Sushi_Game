package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class FoodSpoiltComparator  implements Comparator<Chef> {

	@Override
	public int compare(Chef a, Chef b) {
		/* To compare consumed food 
		 * We do a - b because we want largest to smallest
		 */
		return (int) (Math.round(a.getSpoiledAmount()*100.0) - 
				Math.round(b.getSpoiledAmount()*100));
	}

}
