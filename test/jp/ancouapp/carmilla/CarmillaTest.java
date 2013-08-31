package jp.ancouapp.carmilla;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CarmillaTest {

	@Test
	public void test() {
		List<Rectangle> list = new ArrayList<Rectangle>();
		list.add(new Rectangle(0, 0, 11, 5));
		list.add(new Rectangle(10, 1, 11, 10));
		list.add(new Rectangle(50, 2, 11, 50));
		list.add(new Rectangle(100, 3, 11, 100));
		
		//select
		Rectangle a;
		//a.getCenterX()
		List<Rectangle> listSelectE = Carmilla.selectE(list , "x.x < ? && ? <= x.centerX" , 75 , 10.5);
		assertEquals(listSelectE.size(), 2);
		assertEquals(listSelectE.get(0).y, 1);
		assertEquals(listSelectE.get(1).y, 2);

		//sort
		
		//collect
		
		//groupBy
		
	}

}
