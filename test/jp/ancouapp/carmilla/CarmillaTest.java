package jp.ancouapp.carmilla;


import static org.junit.Assert.assertEquals;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class CarmillaTest
{

    static List<Rectangle> _list;

    static
    {
        _list = new ArrayList<Rectangle>();
        _list.add( new Rectangle( 0, 0, 10, 10 ) );
        _list.add( new Rectangle( 10, 1, 11, 5 ) );
        _list.add( new Rectangle( 50, 2, 11, 100 ) );
        _list.add( new Rectangle( 100, 3, 11, 50 ) );
    }
    @Rule
    public ThreadRule r = new ThreadRule( 3 );


    @Before
    public void setUp()
    {
    }


    @Test
    public void testSelectE()
    {
        System.out.println( "begin testSelectE" );
        List<Rectangle> listSelectE = Carmilla.selectE( _list,
            "a.x < ? && ? <= a.centerX", 75, 10.5 );
        assertEquals( listSelectE.size(), 2 );
        assertEquals( listSelectE.get( 0 ).y, 1 );
        assertEquals( listSelectE.get( 1 ).y, 2 );
        System.out.println( "end testSelectE" );
    }


    @Test
    public void testSortE()
    {
        // sort
        List<Rectangle> listSortE = Carmilla.sortE( _list,
            "a.height <=> b.height" );
        assertEquals( listSortE.size(), 4 );
        assertEquals( listSortE.get( 0 ).y, 1 );
        assertEquals( listSortE.get( 1 ).y, 0 );
        assertEquals( listSortE.get( 2 ).y, 3 );
        assertEquals( listSortE.get( 3 ).y, 2 );

    }


    @Test
    public void testCollectE()
    {
        // collect
        List<Point> list2 = Carmilla.collectE( _list, Point.class,
            "Point.new(a.x , a.y)" );
        assertEquals( list2.size(), 4 );
        assertEquals( list2.get( 0 ).y, 0 );
        assertEquals( list2.get( 1 ).y, 1 );
        assertEquals( list2.get( 2 ).y, 2 );
        assertEquals( list2.get( 3 ).y, 3 );
    }


    @Test
    public void testGroupByE()
    {
        // groupBy
        Map<Integer, List<Rectangle>> mapGroupeE = Carmilla.groupByE( _list,
            "a.width" );
        assertEquals( mapGroupeE.size(), 2 );
        assertEquals( mapGroupeE.get( 10 ).size(), 1 );
        assertEquals( mapGroupeE.get( 11 ).size(), 3 );

        // groupBy
        Map<String, List<Rectangle>> map2 = Carmilla.groupByE( _list,
            "a.width.to_s" );
        assertEquals( map2.size(), 2 );
        assertEquals( map2.get( "10" ).size(), 1 );
        assertEquals( map2.get( "11" ).size(), 3 );
    }


    //マルチスレッドテスト
    @Test
    public void testS()
    {
        List<Font> result =
            Carmilla
                .select( _list, "a.width == 11" )
                .sort( "b.y <=> a.y" )
                .collect( Font.class, "Font.new( 'MS Gothic', a.x, a.x )" )
                .getList();
        assertEquals( result.size(), 3 );
        assertEquals( result.get( 0 ).getSize(), 100 );
        assertEquals( result.get( 1 ).getSize(), 50 );
        assertEquals( result.get( 2 ).getSize(), 10 );
        
        result =
            Carmilla
                .sort(_list, "b.y <=> a.y" )
                .select("a.width == 11" )
                .collect( Font.class, "Font.new( 'MS Gothic', a.x, a.x )" )
                .getList();
        assertEquals( result.size(), 3 );
        assertEquals( result.get( 0 ).getSize(), 100 );
        assertEquals( result.get( 1 ).getSize(), 50 );
        assertEquals( result.get( 2 ).getSize(), 10 );
        
        List<Point> resultP =
            Carmilla
                .collect(_list , Point.class, "Point.new(a.width, a.height )" )
                .sort("b.y <=> a.y" )
                .select("a.x == 11" )
                .getList();
        assertEquals( resultP.size(), 3 );
        assertEquals( resultP.get( 0 ).y, 100 );
        assertEquals( resultP.get( 1 ).y, 50 );
        assertEquals( resultP.get( 2 ).y, 5 );
        
        Map<Integer, List<Point>> mapP =
            Carmilla
                .collect(_list , Point.class, "Point.new(a.width, a.height )" )
                .sort("b.y <=> a.y" )
                .select("a.x == 11" )
                .groupByE( "a.x" );
        assertEquals( mapP.size(), 1 );
        assertEquals( mapP.get( 11 ).size() , 3);
        assertEquals( mapP.get( 11 ).get(0).x , 11);
        assertEquals( mapP.get( 11 ).get(1).x , 11);
        assertEquals( mapP.get( 11 ).get(2).x , 11);
    }
}
