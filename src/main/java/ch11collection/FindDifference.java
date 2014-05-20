package ch11collection;

import net.mindview.util.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2014/5/14
 */
public class FindDifference {
    public static void main(String[] args) throws Exception {
        // 大保网的数据举例
        Map<Integer, Area> aMap = new HashMap<Integer, Area>();
        putArea(aMap, new Area(100000, "大中华", 0));
        putArea(aMap, new Area(100100, "广东省你妹", 100000)); // 这个保网和国家都有，但是名字不一样
        putArea(aMap, new Area(100101, "广州市", 100100));
        putArea(aMap, new Area(100102, "番禺市", 100100));
        putArea(aMap, new Area(100103, "萝岗市", 100100));
        putArea(aMap, new Area(100200, "湖北省", 100000));
        putArea(aMap, new Area(100201, "荆州市", 100200));
        putArea(aMap, new Area(100202, "黄石市", 100200)); // 这个保网有，但是国家没有

        // 国家数据举例
        Map<Integer, Area> bMap = new HashMap<Integer, Area>();
        putArea(bMap, new Area(100000, "大中华", 0));
        putArea(bMap, new Area(100100, "广东省", 100000));  // // 这个保网和国家都有，但是名字不一样
        putArea(bMap, new Area(100101, "广州市", 100100));
        putArea(bMap, new Area(100102, "番禺市", 100100));
        putArea(bMap, new Area(100103, "萝岗市", 100100));
        putArea(bMap, new Area(100200, "湖北省", 100000));
        putArea(bMap, new Area(100201, "荆州市", 100200));
        putArea(bMap, new Area(100203, "监利县", 100201));  // 这个国家有，但是保网没有

        // id保网有，但是国家没有的集合，循环做删除操作
        Set<Integer> aNotB = Sets.difference(aMap.keySet(), bMap.keySet());
        // id国家有，但是保网没有，循环做插入操作
        Set<Integer> bNotA = Sets.difference(bMap.keySet(), aMap.keySet());
        // id两者都有，但是名字不一致
        Map<Integer, Area> aMapClone = new HashMap<Integer, Area>(aMap);
        Map<Integer, Area> bMapClone = new HashMap<Integer, Area>(bMap);
        Set<Integer> abNot = Sets.union(aNotB, bNotA);
        for (Integer i : abNot) {
            aMapClone.remove(i);
            bMapClone.remove(i);
        }

        Set<Area> aa = new HashSet<Area>(aMapClone.values());
        Set<Area> bb = new HashSet<Area>(bMapClone.values());
        // id两者都有，但是名字不一致，保网集合
        Set<Area> aNotName = Sets.difference(aa, bb);
        // id两者都有，但是名字不一致，国家集合
        Set<Area> bNotName = Sets.difference(aa, bb);
        // 接下来对aNotName进行循环，通过id，在bMapClone中找到国家纪录，然后更新保网的name
        // ....todo
    }

    private static class Area {
        int id;
        String name;
        int parentId;

        public Area(int id, String name, int parentId) {
            this.id = id;
            this.name = name;
            this.parentId = parentId;
        }

        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Area))
                return false;
            Area pn = (Area) o;
            return pn.id == id && pn.name.equals(name);
        }

        public int hashCode() {
            int result = 17;
            result = 31 * result + id;
            result = 31 * result + name.hashCode();
            return result;
        }
    }

    private static void putArea(Map<Integer, Area> map, Area a) {
        map.put(a.id, a);
    }
}
