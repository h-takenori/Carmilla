package jp.ancouapp.carmilla;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jline.internal.InputStreamReader;

import org.jruby.CompatVersion;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.RubyRuntimeAdapter;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.util.KCode;

public class Carmilla {

	// rubyインスタンス
	private static Ruby _ruby;
	static {
		initRuby();
	}

	// Ruby初期化
	static private void initRuby() {
		RubyInstanceConfig config = new RubyInstanceConfig();
		config.setKCode(KCode.UTF8);
		config.setCompatVersion(CompatVersion.RUBY1_9);
		_ruby = Ruby.newInstance(config);
		_ruby.evalScriptlet(readScript());
	}

	private static String readScript() {
		StringBuilder result = new StringBuilder();
		try {
			InputStream is = Carmilla.class.getClassLoader().getResourceAsStream("CarmillaScript.rb");
			Reader r = null;
			BufferedReader br = null;
			try {
				r = new InputStreamReader(is, "UTF-8");
				br = new BufferedReader(r);
				for (;;) {
					String text = br.readLine();
					if (text == null) {
						break;
					}
					result.append(text + "\n");
				}
			} finally {
				if (br != null)
					br.close();
				if (r != null)
					r.close();
				if (is != null)
					is.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result.toString();
	}

	private Carmilla() {
	}

	// Ruby:selectメソッド
	public static <E> List<E> selectE(List<E> list, String block,
			Object... args) {
		for (Object arg : args) {
			block = block.replaceFirst("\\?", arg.toString());
		}
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		IRubyObject carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
		IRubyObject resutl = carmillaRb.callMethod(_ruby.getCurrentContext() , "select" ,rubyArgs);
		return (List<E>)resutl.convertToArray();
	}

	public static <E> List<E> sortE(List<E> list, String block) {
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		IRubyObject carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
		IRubyObject resutl = carmillaRb.callMethod(_ruby.getCurrentContext() , "sort" ,rubyArgs);
		return (List<E>)resutl.convertToArray();
	}

	public static List<Point> collect(List<Rectangle> _list, String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static <K,V> Map<K, List<V>> groupByE(List<V> list,
			String block) {
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		IRubyObject carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
		IRubyObject resutl = carmillaRb.callMethod(_ruby.getCurrentContext() , "group_by" ,rubyArgs);
		return (Map<K, List<V>>)resutl.convertToHash();
	}


}
