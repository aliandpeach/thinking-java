/*
 * Created on 13-5-2
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
package ch08algorithm;

import ch08algorithm.model.GroupModel;

import java.util.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-5-2
 */
public class SortDemo {

    private static String toJson(List<GroupModel> list) {
//        ComparatorChain comparatorChain = new ComparatorChain();
//        comparatorChain.addComparator(new BeanComparator("accountId"));
//        comparatorChain.addComparator(new BeanComparator("module"));
//        comparatorChain.addComparator(new BeanComparator("orgId"));
//        comparatorChain.addComparator(new BeanComparator("supplyId"));
//        Collections.sort(list, comparatorChain);
        Map<String, Map<String, Map<String, Map<String, String>>>> dataMap = new HashMap<String, Map<String, Map<String, Map<String, String>>>>();
        for (GroupModel groupModel : list) {
            String accountId = groupModel.getAccountId();
            String module = groupModel.getModule();
            String orgId = groupModel.getOrgId();
            String supplyId = groupModel.getSupplyId();
            String authority = groupModel.getAuthority();
            if (!dataMap.containsKey(accountId)) {
                dataMap.put(accountId, new HashMap<String, Map<String, Map<String, String>>>());
            }
            if (!dataMap.get(accountId).containsKey(module)) {
                dataMap.get(accountId).put(module, new HashMap<String, Map<String, String>>());
            }
            if (!dataMap.get(accountId).get(module).containsKey(orgId)) {
                dataMap.get(accountId).get(module).put(orgId, new HashMap<String, String>());
            }
            dataMap.get(accountId).get(module).get(orgId).put(supplyId, authority);
        }
        Json rootJson = new Json();
        Set<String> groupKeySet = dataMap.keySet();
        for (String groupKey : groupKeySet) {
            Map<String, Map<String, Map<String, String>>> eachGroup = dataMap.get(groupKey);
            Json groupJson = new Json();
            rootJson.add(groupKey, groupJson);
            Set<String> moduleKeySet = eachGroup.keySet();
            for (String moduleKey : moduleKeySet) {
                Map<String, Map<String, String>> eachModule = eachGroup.get(moduleKey);
                Json moduleJson = new Json();
                groupJson.add(moduleKey, moduleJson);
                Set<String> orgKeySet = eachModule.keySet();
                for (String orgKey : orgKeySet) {
                    Map<String, String> eachOrg = eachModule.get(orgKey);
                    Json orgJson = new Json();
                    moduleJson.add(orgKey, orgJson);
                    Set<String> supplyKeySet = eachOrg.keySet();
                    for (String supplyKey : supplyKeySet) {
                        String authority = eachOrg.get(supplyKey);
                        orgJson.add(supplyKey, authority);
                    }
                }
            }
        }
        System.out.println(rootJson.toString());
        return rootJson.toString();
    }

    public static void main(String[] args) {
        List<GroupModel> groupList = new ArrayList<GroupModel>();
        assembleData(groupList);
        toJson(groupList);
        System.out.println("one step");
    }

    private static void assembleData(List<GroupModel> groupList) {
        GroupModel group1 = new GroupModel();
        group1.id = "421";
        group1.accountId="group201304107114859";
        group1.orgId="3000000332";
        group1.supplyId="80";
        group1.domId="cm.Ludan";
        group1.module="Ludan";
        group1.moduleName="录单";
        group1.authority="View,Execute,Del,New,Control";
        group1.authorityName="查看,执行,删除,新建,全控制";
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "414";
        group1.accountId="group201304107114859";
        group1.orgId="1244000000";
        group1.supplyId="*";
        group1.domId="cm.Verify";
        group1.module="Verify";
        group1.moduleName="初审";
        group1.authority="View";
        group1.authorityName="查看";
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "415";
        group1.accountId="group201304107114859";
        group1.orgId="3000000322";
        group1.supplyId="78";
        group1.domId="cm.Insure";
        group1.module="Insure";
        group1.moduleName="核保";
        group1.authority="View,Execute,New,Del";
        group1.authorityName="查看,执行,新增,删除";
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "416";
        group1.accountId="group201304107114859";
        group1.orgId="3000000327";
        group1.supplyId="123";
        group1.domId="cm.Payment";
        group1.module="Payment";
        group1.moduleName="支付";
        group1.authority="View,Execute,Del,New,Control";
        group1.authorityName="查看,执行,删除,新建,全控制";
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "418";
        group1.accountId="group201304107114859";
        group1.orgId="1233000000";
        group1.supplyId="29";
        group1.domId="cm.Quote";
        group1.module="Quote";
        group1.moduleName="报价";
        group1.authority="View,Execute,Del,New,Control";
        group1.authorityName="查看,执行,删除,新建,全控制";
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "422";
        group1.accountId="group201304107114859";
        group1.orgId="3000000332";
        group1.supplyId="82";
        group1.domId="cm.Ludan";
        group1.module="Ludan";
        group1.moduleName="录单";
        group1.authority="View,Execute,Del,New,Control";
        group1.authorityName="查看,执行,删除,新建,全控制";
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "423";
        group1.accountId="group201304107114859";
        group1.orgId="3000000323";
        group1.supplyId="120";
        group1.domId="cm.Ludan";
        group1.module="Ludan";
        group1.moduleName="录单";
        group1.authority="View,Execute,Del,New,Control";
        group1.authorityName="查看,执行,删除,新建,全控制";
        groupList.add(group1);
    }
}
