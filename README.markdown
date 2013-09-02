#Carmilla
========

CarmillaはJavaでRubyのブロックなメソッドを使えるライブラリです。  
条件をRuby構文のStringで書くことで、Rubyで苦手だったソートとか抽出が超簡単に出来ます。


#インストール
Carmilla.jarとruby.jarをプロジェクトに突っ込みます。

#使い方
//テストデータ
List<Rectangle>list = createTestList();
        
        
//collectしてsortしてselectする
List<Point> list2 =  
    Carmilla  
        .collect( _list, Point.class, "Point.new(a.width, a.height )" )  
        .sort( "b.y <=> a.y" )  
        .select( "a.x == 11" )  
        .getList();  
        
        
//collectしてsortしてselectしてgroupbyする  
Map<Integer, List<Point>> map3 =  
    Carmilla  
        .collect( _list, Point.class, "Point.new(a.width, a.height )" )  
        .sort( "b.y <=> a.y" )  
        .select( "a.x == 11" )  
        .groupByE( "a.x" );  
        

#Licence

##The MIT License (MIT)
Copyright (c) Takenori Hirao @ AncouApp

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
