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
import javax.swing.table.*;
import javax.swing.*;

public class MarketDataModel extends AbstractTableModel
        implements Runnable {

    Thread runner;
    MYOSM market;
    int delay;

    public MarketDataModel(int initialDelay) {
        market = new MYOSM( );
        delay = initialDelay * 1000;
        Thread runner = new Thread(this);
        runner.start( );
    }

    Stock[] stocks = new Stock[0];
    int[] stockIndices = new int[0];
    String[] headers = {"Symbol", "Price", "Change", "Last updated"};

    public int getRowCount( ) { return stocks.length; }
    public int getColumnCount( ) { return headers.length; }
    public String getColumnName(int c) { return headers[c]; }
    public Object getValueAt(int r, int c) {
        switch(c) {
            case 0:
                return stocks[r].symbol;
            case 1:
                return stocks[r].price;
            case 2:
                return stocks[r].delta;
            case 3:
                return stocks[r].lastUpdate;
        }
        throw new IllegalArgumentException("Bad cell (" + r + ", " + c +")");
    }

    public void setDelay(int seconds) { delay = seconds * 1000; }
    public void setStocks(int[] indices) {
        stockIndices = indices;
        updateStocks( );
        fireTableDataChanged( );
    }

    public void updateStocks( ) {
        stocks = new Stock[stockIndices.length];
        for (int i = 0; i < stocks.length; i++) {
            stocks[i] = market.getQuote(stockIndices[i]);
        }
    }

    public void run( ) {
        while(true) {
            // Blind update . . . we could check for real deltas if necessary
            updateStocks( );

            // We know there are no new columns, so don't fire a data change; fire only a
            // row update. This keeps the table from flashing
            fireTableRowsUpdated(0, stocks.length - 1);
            try { Thread.sleep(delay); }
            catch(InterruptedException ie) {}
        }
    }
}
