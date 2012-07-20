/*
*  Copyright 2012 Erkan Molla
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/

package com.batterywidget;

import com.batterywidget.storage.SQLiteDataBase;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.widget.*;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class HistoryViewManager extends Activity {
	
	private SQLiteDataBase            db;
	private XYMultipleSeriesDataset   dataSet;
	private XYMultipleSeriesRenderer  renderer;
	private GraphicalView             chartView;
	private LinearLayout              chartContainer;
	private XYSeries                  xYSeries;
	private TextView                  titleTextView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_view);
		
		db = new SQLiteDataBase(getApplicationContext());
		db.openRead();
		
		titleTextView  = (TextView)     findViewById(R.id.historyTitle);
		chartContainer = (LinearLayout) findViewById(R.id.chart);     
		
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		
		if (db.availableEntries())
		{	
			titleTextView.setText(this.getString(R.string.availableData));
			chartInit();
			
			new Thread( new Runnable ()
			{
				public void run() 
				{
					try 
					{	
						chartDraw();	
					} catch (Exception e) {}	
				}	
			}).start();
		}
		else
		{
			titleTextView.setText(this.getString(R.string.notAvailableDataYet));
			db.close();
		}
		
	}
	
	
	@Override
	public void onPause(){
		super.onPause();
		
		if (chartContainer != null)
			chartContainer.removeAllViews();	
	}
	
	
	public void chartInit() {
		if (chartView == null) 
		{
			renderer = new XYMultipleSeriesRenderer();
			renderer.setMargins(new int[]{30, 35, 30, 10}); //{top, left, bottom, right};
			renderer.setXTitle(Constants.XAxisTitle);
			renderer.setYTitle(Constants.YAxisTitle);
			renderer.setLabelsColor(Color.WHITE);
			renderer.setLabelsTextSize(11);
			renderer.setAxisTitleTextSize(15);
			renderer.setLegendTextSize(15);
			renderer.setYLabelsAlign(Align.RIGHT);
			renderer.setYLabels(10);
			renderer.setYAxisMin(0);
			renderer.setYAxisMax(100);
			renderer.setPanEnabled(true, false);
			renderer.setZoomEnabled(true, false);
			renderer.setShowGrid(true);
			renderer.setZoomButtonsVisible(true);
			
			dataSet  = new XYMultipleSeriesDataset();
			xYSeries = new XYSeries("");
			dataSet.addSeries(xYSeries);
			
			XYSeriesRenderer mMainRenderer = new XYSeriesRenderer();
			mMainRenderer.setColor(Color.CYAN);
			
			renderer.addSeriesRenderer(mMainRenderer);
			
			chartView = ChartFactory.getTimeChartView(this, dataSet, renderer, Constants.DateFormat);
			
			chartContainer.addView(chartView);
		}
	}
	
	
	private void chartDraw(){
		
		int  oldLevel = -1;
		int  level    =  0;
		long time     = System.currentTimeMillis();
		boolean skip  = false;
		
		Cursor cursor = db.getEntries();
		
		if (cursor.moveToFirst())
			do {
				time  = cursor.getLong(SQLiteDataBase._TIME);
				level = cursor.getInt(SQLiteDataBase._LEVEL);
				skip = level == oldLevel;
				if (!skip) 
				{
					xYSeries.add(time, level);
					oldLevel = level;			
				}	
			} while (cursor.moveToNext());
		
		cursor.close();
		db.close();
		
		if (skip)
			xYSeries.add(time, oldLevel);
		
		chartView.repaint();
	}
    
}
