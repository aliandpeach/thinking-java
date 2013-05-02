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
package chapter08_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-5-2
 */
public class SortDemo {
    private static class JSONModel {
        List<Module> moduleList;
    }
    private static class Group {
        String groupKey;
        List<Module> moduleList;
    }
    private static class Module {
        String moduleKey;
        List<Org> orgList;
    }
    private static class Org {
        String orgKey;
        List<Supply> supplyList;
    }
    private static class Supply {
        String supplyKey;
        List<Authority> authorityList;
    }
    private static class Authority {
        String authority;
    }
    private static class GroupModel {
        String id;
        String accountId;
        String orgId;
        String supplyId;
        DomModel dom;
    }
    private static class DomModel {
        String domId;
        String module;
        String moduleName;
        String authority;
        String authorityName;
    }

    private static JSONModel sort(List<GroupModel> list) {
        JSONModel result = new JSONModel();
        Collections.sort(list, new Comparator<GroupModel>() {
            @Override
            public int compare(GroupModel o1, GroupModel o2) {
                return 0;
            }
        });
        return result;
    }

    public static void main(String[] args) {
        List<GroupModel> groupList = new ArrayList<GroupModel>();
        GroupModel group1 = new GroupModel();
        group1.id = "421";
        group1.accountId="group201304107114859";
        group1.orgId="3000000332";
        group1.supplyId="80";
        DomModel dom1 = new DomModel();
        dom1.domId="cm.Ludan";
        dom1.module="Ludan";
        dom1.moduleName="录单";
        dom1.authority="View,Execute,Del,New,Control";
        dom1.authorityName="查看,执行,删除,新建,全控制";
        group1.dom = dom1;
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "414";
        group1.accountId="group201304107114859";
        group1.orgId="1244000000";
        group1.supplyId="*";
        dom1 = new DomModel();
        dom1.domId="cm.Verify";
        dom1.module="Verify";
        dom1.moduleName="初审";
        dom1.authority="View";
        dom1.authorityName="查看";
        group1.dom = dom1;
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "415";
        group1.accountId="group201304107114859";
        group1.orgId="3000000322";
        group1.supplyId="78";
        dom1 = new DomModel();
        dom1.domId="cm.Insure";
        dom1.module="Insure";
        dom1.moduleName="核保";
        dom1.authority="View,Execute,New,Del";
        dom1.authorityName="查看,执行,新增,删除";
        group1.dom = dom1;
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "416";
        group1.accountId="group201304107114859";
        group1.orgId="3000000327";
        group1.supplyId="123";
        dom1 = new DomModel();
        dom1.domId="cm.Payment";
        dom1.module="Payment";
        dom1.moduleName="支付";
        dom1.authority="View,Execute,Del,New,Control";
        dom1.authorityName="查看,执行,删除,新建,全控制";
        group1.dom = dom1;
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "418";
        group1.accountId="group201304107114859";
        group1.orgId="1233000000";
        group1.supplyId="29";
        dom1 = new DomModel();
        dom1.domId="cm.Quote";
        dom1.module="Quote";
        dom1.moduleName="报价";
        dom1.authority="View,Execute,Del,New,Control";
        dom1.authorityName="查看,执行,删除,新建,全控制";
        group1.dom = dom1;
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "422";
        group1.accountId="group201304107114859";
        group1.orgId="3000000332";
        group1.supplyId="82";
        dom1 = new DomModel();
        dom1.domId="cm.Ludan";
        dom1.module="Ludan";
        dom1.moduleName="录单";
        dom1.authority="View,Execute,Del,New,Control";
        dom1.authorityName="查看,执行,删除,新建,全控制";
        group1.dom = dom1;
        groupList.add(group1);

        group1 = new GroupModel();
        group1.id = "423";
        group1.accountId="group201304107114859";
        group1.orgId="3000000323";
        group1.supplyId="120";
        dom1 = new DomModel();
        dom1.domId="cm.Ludan";
        dom1.module="Ludan";
        dom1.moduleName="录单";
        dom1.authority="View,Execute,Del,New,Control";
        dom1.authorityName="查看,执行,删除,新建,全控制";
        group1.dom = dom1;
        groupList.add(group1);


    }
}
