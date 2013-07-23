/*
 * Created on 13-6-22
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
package chapter22_swing.table.button;

import java.awt.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-22
 */
public class GBC extends GridBagConstraints {
    //初始化左上角位置
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    //初始化左上角位置和所占行数和列数
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    //对齐方式
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    //是否拉伸及拉伸方向
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    //x和y方向上的增量
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    //外部填充
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    //外填充
    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    //内填充
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
