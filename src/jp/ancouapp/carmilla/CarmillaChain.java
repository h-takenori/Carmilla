package jp.ancouapp.carmilla;

import java.util.List;
import java.util.Map;

import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * 連続して抽出等を実施する
 * @author hiraotakenori by AncouApp
 *
 */
public class CarmillaChain<E> {

	List<E> _list;
	public CarmillaChain(List<E> list) {
		_list = list;
	}
	
	/**
	 * 処理結果のリストを取得する
	 * @return
	 */
	public List<E> getList(){
		return _list;
	}
	

	/**
	 * block条件により、抽出を実施する
	 * 実施後、チェーン可能。
	 * @param block 抽出条件
	 * @param args 引数、抽出条件の?を置換する
	 * @return CarmillaChain
	 */
	public CarmillaChain<E> select(String block,
			Object... args) {
		return Carmilla.select(_list, block, args);
	}

	/**
	 * ソートする
	 * 実施後、チェーン可能。
	 * @param block ソート条件
	 * @return CarmillaChain
	 */
	public  CarmillaChain<E> sort(String block) {
		return Carmilla.sort(_list, block);
	}

	/**
	 * Collect処理を実施する。
	 * 実施後、チェーン可能。
	 * @param resultClass 変換結果のクラス情報
	 * @param block　変換処理
	 * @return CarmillaChain
	 */
	public <E2> CarmillaChain<E2> collect(Class<E2> resultClass, String block ) {
		return Carmilla.collect(_list, resultClass, block);
	}
	
	/**
	 * リストをblockの条件でGroupByする
	 * @param block Group条件
	 * @return Group条件の型とGroupされた結果のリスト
	 */
	public <K> Map<K, List<E>> groupByE(String block) {
		return Carmilla.groupByE(_list , block);
	}
}
