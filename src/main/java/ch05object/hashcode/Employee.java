/*
 * Created on 12-10-22
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
 * Copyright @2012 the original author or authors.
 */
package ch05object.hashcode;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 需要注意记住的事情：
 * <p/>
 * 尽量保证使用对象的同一个属性来生成hashCode()和equals()两个方法。在我们的案例中,我们使用员工id。
 * eqauls方法必须保证一致（如果对象没有被修改，equals应该返回相同的值）
 * 任何时候只要a.equals(b),那么a.hashCode()必须和b.hashCode()相等。
 * 两者必须同时重写。
 * <p/>
 * 当使用ORM的时候特别要注意的：
 * 如果你使用ORM处理一些对象的话，你要确保在hashCode()和equals()对象中使用getter和setter而不是直接引用成员变量。因为在ORM中有的时候成员变量会被延时加载，这些变量只有当getter方法被调用的时候才真正可用。
 * 例如在我们的例子中，如果我们使用e1.id == e2.id则可能会出现这个问题，但是我们使用e1.getId() == e2.getId()就不会出现这个问题。
 */
public class Employee {
    private Integer id;
    private String firstname;
    private String lastName;
    private String department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        return new HashCodeBuilder(getId() % 2 == 0 ? getId() + 1 : getId(), PRIME).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o.getClass() != getClass())
            return false;
        Employee e = (Employee) o;
        return new EqualsBuilder().append(getId(), e.getId()).isEquals();
    }

}