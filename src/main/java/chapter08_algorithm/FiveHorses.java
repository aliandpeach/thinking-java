/*
 * Created on 13-1-25
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2013 the original author or authors.
 */
package chapter08_algorithm;

import java.util.*;

/**
 * 25匹马中找出跑的最快的3匹马
 * <p/>
 * 说明：
 * 25匹马比赛花费时间各不相同，
 * 没有秒表，只能观察排名，而且每次只能5匹马在跑道上比赛
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-1-25
 */
public class FiveHorses {

    public static void main(String[] args) {
        new FiveHorses().imitateRace();
    }

    /**
     * 模拟赛马
     */
    public void imitateRace() {
        List<List<Horse>> all = new ArrayList<List<Horse>>();
        List<Integer> randomInteger = new ArrayList<Integer>();
        for (int i = 0; i < 25; i++) {
            randomInteger.add(i);
        }
        Collections.shuffle(randomInteger);
        for (int i = 0; i < 25; i++) {
            Horse horse = new Horse(i + 1, (i / 5) + 1);
            horse.setExpendTime(randomInteger.get(i));
            if (i % 5 == 0) {
                List<Horse> list = new ArrayList<Horse>();
                all.add(list);
            }
            all.get(all.size() - 1).add(horse);
        }
        int raceCount = 0;
        int groupId = 0;
        List<Horse> headList = new ArrayList<Horse>();
        System.out.println("首先5组分别进行比赛");
        for (List<Horse> eachGroup : all) {
            raceCount++;
            groupId++;
            System.out.println("第" + raceCount + "轮比赛，组号为" + groupId + "的比赛开始");
            Collections.sort(eachGroup, new Comparator<Horse>() {
                @Override
                public int compare(Horse o1, Horse o2) {
                    return o1.getExpendTime() < o2.getExpendTime() ? 1 : 0;
                }
            });
            headList.add(eachGroup.get(4));
            System.out.println("每小组比赛结果，排名从后到前的马儿编号为：");
            for (Horse each : eachGroup) {
                System.out.print(each.getId() + "  ");
            }
            System.out.println();
            System.out.println("--------------------分割线------------------------");
        }

        System.out.println("第6轮比赛，接下来把上面5组比赛的第一名拿出来比赛，这场比赛称作 '头名PK比赛' ");
        String ids = "";
        for (Horse h : headList) {
            ids += h.getId() + " ";
        }
        System.out.println("这组5匹马的编号为： " + ids);
        Collections.sort(headList, new Comparator<Horse>() {
            @Override
            public int compare(Horse o1, Horse o2) {
                return o1.getExpendTime() < o2.getExpendTime() ? 1 : 0;
            }
        });
        System.out.println("'头名PK比赛'比赛结果，排名从后到前的马儿编号为：");
        for (Horse each : headList) {
            System.out.print(each.getId() + "  ");
        }
        System.out.println();
        System.out.println("第一名率先出来了，id编号为" + headList.get(4).getId());

        System.out.println("--------------------分割线------------------------");
        System.out.println("第7轮比赛，把 '头名PK比赛' 中的 '第一名所在的组'的第二和第三名，" +
                "'第二名所在的组'的第一和第二名，'第三名所在的组'的第一名，总共5匹马拿出来比赛");
        List<Horse> lastPK = new ArrayList<Horse>();
        // 取'第一名所在的组'的第二和第三名
        lastPK.add(all.get(4).get(3));
        lastPK.add(all.get(4).get(2));
        // 取'第二名所在的组'的第一和第二名
        lastPK.add(all.get(3).get(4));
        lastPK.add(all.get(3).get(3));
        // 取'第三名所在的组'的第一名
        lastPK.add(all.get(2).get(4));
        Collections.sort(lastPK, new Comparator<Horse>() {
            @Override
            public int compare(Horse o1, Horse o2) {
                return o1.getExpendTime() < o2.getExpendTime() ? 1 : 0;
            }
        });
        System.out.println("最后一场比赛比赛结果，排名从后到前的马儿编号为：");
        for (Horse each : lastPK) {
            System.out.print(each.getId() + "  ");
        }
        System.out.println();
        System.out.println("第二名出来了，id编号为：" + lastPK.get(4).getId());
        System.out.println("第三名出来了，id编号为：" + lastPK.get(3).getId());

        System.out.println("------------------------结束模拟---------------------");
    }


    private class Horse {
        /**
         * 编号
         */
        private int id;

        /**
         * 组号
         */
        private int groupId;

        /**
         * 跑完花费的时间
         */
        private int expendTime;

        private Horse(int id, int groupId) {
            this.id = id;
            this.groupId = groupId;
        }

        /**
         * **********getter and setter***********
         */
        public int getExpendTime() {
            return expendTime;
        }

        public void setExpendTime(int expendTime) {
            this.expendTime = expendTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
    }
}
