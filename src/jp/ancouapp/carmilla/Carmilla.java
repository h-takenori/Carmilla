package jp.ancouapp.carmilla;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import jline.internal.InputStreamReader;

import org.jruby.CompatVersion;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.util.KCode;

public class Carmilla {

	// rubyインスタンス
	private static Ruby _ruby;
	private static IRubyObject _carmillaRb ;
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
		_carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
	}

	/**
	 * 内部処理用Rubyスクリプトファイルを読む
	 * @return
	 */
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

	/**
	 * コンストラクタ使用禁止
	 */
	private Carmilla() {
	}

	/**
	 * block条件により、リストから抽出を行う
	 * @param list 検索元
	 * @param block 抽出条件
	 * @param args 引数、抽出条件の?を置換する
	 * @return 抽出されたリスト
	 */
	public static <E> List<E> selectE(List<E> list, String block,
			Object... args) {
		for (Object arg : args) {
			block = block.replaceFirst("\\?", arg.toString());
		}
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		_carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
		IRubyObject resutl = _carmillaRb.callMethod(_ruby.getCurrentContext() , "select" ,rubyArgs);

		return (List<E>)resutl.convertToArray();
	}

	/**
	 * リストをソートする。Rubyのsortを使用する
	 * @param list
	 * @param block ソート条件
	 * @return ソートされたリスト
	 */
	public static <E> List<E> sortE(List<E> list, String block) {
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		_carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
		IRubyObject resutl = _carmillaRb.callMethod(_ruby.getCurrentContext() , "sort" ,rubyArgs);
		return (List<E>)resutl.convertToArray();
	}

	/**
	 * リストに対してRubyのCollect処理を実施する。
	 * @param list 
	 * @param resultClass 変換結果のクラス情報
	 * @param block　変換処理
	 * @return 変換されたリスト
	 */
	public static <E1,E2> List<E2> collect(List<E1> list, Class<E2> resultClass, String block ) {
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, resultClass.getName()),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		_carmillaRb = _ruby.evalScriptlet("CarmillaScript.new");
		IRubyObject resutl = _carmillaRb.callMethod(_ruby.getCurrentContext() , "collect" ,rubyArgs);
		return (List<E2>)resutl.convertToArray();
	}
	
	/**
	 * リストをblockの条件でGroupByする
	 * @param list リスト
	 * @param block Group条件
	 * @return Group条件の型とGroupされた結果のリスト
	 */
	public static <K,V> Map<K, List<V>> groupByE(List<V> list,
			String block) {
		IRubyObject[] rubyArgs = new IRubyObject[]{
				JavaUtil.convertJavaToRuby(_ruby, list),
				JavaUtil.convertJavaToRuby(_ruby, block)
		};
		IRubyObject resutl = _carmillaRb.callMethod(_ruby.getCurrentContext() , "group_by" ,rubyArgs);
		return (Map<K, List<V>>)resutl.convertToHash();
	}


}
