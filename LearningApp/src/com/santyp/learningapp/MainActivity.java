package com.santyp.learningapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.santyp.fragments.AddApplicationFragment;
import com.santyp.fragments.AddApplicationFragment.ListFragmentListener;
import com.santyp.fragments.LogoutFragment;
import com.santyp.fragments.PreferencesFragment;
import com.santyp.fragments.RemoveApplicationFragment;
import com.santyp.models.AppItem;


@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements OnItemClickListener, ListFragmentListener {


	private ListView sliderListView;
	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;
	// Drawer title
	private CharSequence drawerTitle;
	private CharSequence appTitle;
	private String [] items;
	private ArrayList<AppItem> applications;
	private ArrayList<AppItem> appsToLocks;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		applications= new ArrayList<AppItem>();
		appsToLocks= new ArrayList<AppItem>();
		// Get the list
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		sliderListView = (ListView) findViewById(R.id.sliderMenuListViewID);
		// get the data from resources
		items= getResources().getStringArray(R.array.drawer_items);
		ArrayList<DrawerItem> drawerItems= new ArrayList<DrawerItem>();
		for (int i = 0; i < items.length; i++) {
			drawerItems.add(new DrawerItem(items[i]));
		}
		drawerItems.add(new DrawerItem());
		drawerItems.add(new DrawerItem());
		drawerItems.add(new DrawerItem());
		// set list view adapter
		sliderListView.setAdapter(new SliderListAdapter(this, drawerItems));
		sliderListView.setOnItemClickListener(this);

		// Enable action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// set up the drawer icon

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, 
				R.string.app_name, 
				R.string.app_name) {


			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				getActionBar().setTitle(appTitle);
				invalidateOptionsMenu();
			}
		};
		//
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			//displayView(0);
		}
		if(applications.size()== 0) {
			new LoadApplicationTask().execute();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if(drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// This will be triggered whenever invalidateOptionsMenu is triggered
		boolean isDrawerOpen= drawerLayout.isDrawerOpen(sliderListView);
		menu.findItem(R.id.action_settings).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		appTitle = title;
		getActionBar().setTitle(appTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		displayView(position);
	}
	// Display fragment view for selected nav drawer list item
	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new AddApplicationFragment();
			Bundle bundle= new Bundle();
			bundle.putSerializable("apps", applications);
			fragment.setArguments(bundle);

			break;
		case 1:
			fragment = new RemoveApplicationFragment();
			break;
		case 2:
			fragment = new PreferencesFragment();
			break;
		case 3:
			fragment = new LogoutFragment();
			break;
		default:
			break;
		}

		if(fragment != null) {
			FragmentManager fm= getFragmentManager();
			fm.beginTransaction().replace(R.id.frame_container, fragment).commit();

			// UPdate selected item title, and then close the drawer
			sliderListView.setItemChecked(position, true);
			sliderListView.setSelection(position);
			setTitle(items[position]);
			drawerLayout.closeDrawer(sliderListView);
		}
	}

	// Load application task
	private class LoadApplicationTask extends AsyncTask<Integer, Integer, Integer> {

		private ArrayList<AppItem> items = new ArrayList<AppItem>();

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Collections.sort(items);
			applications.addAll(items);
			Log.i("MainActivity => ", "" + applications.size());
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

			List<ResolveInfo> mApps = getPackageManager().queryIntentActivities(mainIntent, 0);

			//String[] list = AppLockerPreference.getInstance(ApplicationListActivity.this).getApplicationList();
			//int len = list.length;

			int length = mApps.size();
			for(int i=0; i<length; i++) {
				ResolveInfo info = mApps.get(i);
				Drawable image = info.loadIcon(getPackageManager());
				boolean included = false;
				items.add(new AppItem(
						info.activityInfo.loadLabel(getPackageManager()).toString(), 
						info.activityInfo.name,
						info.activityInfo.packageName,
						image, included, checkImportance(info.activityInfo.packageName)));
			}

			boolean included = false;

			/*for (int j=0; j<len; j++){
        		if ("com.android.packageinstaller".equals(list[j])){
        			included = true;
        			break;
        		}
        	}
			 */
			// Add default components 
			ApplicationInfo info;
			try {
				info = getPackageManager().getApplicationInfo("com.android.packageinstaller", 0);
				items.add(new AppItem(
						info.loadLabel(getPackageManager()).toString(),
						info.name,
						info.packageName,
						info.loadIcon(getPackageManager()),
						included, true
						));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}

		private boolean checkImportance(String packageName) {
			if ("com.android.vending".equals(packageName)||
					"com.android.settings".equals(packageName)){
				return true;
			}
			return false;
		}
	}

	@Override
	public void onListItemSelected(AppItem item) {
		if(!appsToLocks.contains(item)) {
			appsToLocks.add(item);
		}
		Log.i("MainActivity", "App name => " + item.getLabel());
		Log.i("MainActivity", "total apps => " + appsToLocks.size());
	}
}
