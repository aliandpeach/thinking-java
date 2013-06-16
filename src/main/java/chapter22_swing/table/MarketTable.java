/*
 * Created on 13-6-16
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
package chapter22_swing.table;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-16
 */
import java.awt.*;
import javax.swing.*;

public class MarketTable extends JFrame {

    public MarketTable( ) {
        super("Dynamic Data Test");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set up our table model with a 5-second polling delay.
        MarketDataModel mdm = new MarketDataModel(5);

        // Pick which stocks we want to watch . . .
        mdm.setStocks(new int[] { 0, 1, 2 });

        //  . . . and pop up the table.
        JTable jt = new JTable(mdm);
        JScrollPane jsp = new JScrollPane(jt);
        getContentPane( ).add(jsp, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        MarketTable mt = new MarketTable( );
        mt.setVisible(true);
    }
}

