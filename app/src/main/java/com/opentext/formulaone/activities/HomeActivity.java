package com.opentext.formulaone.activities;

/**
 * Created by gasahu on 29-Jun-17.
 */

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.opentext.formulaone.R;
import com.opentext.formulaone.beans.Driver;
import com.opentext.formulaone.dao.FormulaOneDAO;
import com.opentext.formulaone.utilities.FormulaOneLibraryManager;
import com.opentext.formulaone.utilities.Utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class HomeActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SwipeToRefreshListener {
    private final static String LOG_TAG = "HomeActivity";
    private String headers[] = {"Driver Name", "Nationality", "DOB", "Wiki"};
    private FormulaOneLibraryManager fManager;
    private SortableTableView tableView;
    private ImageButton prevButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(LOG_TAG, "HomeActivity created");

        fManager = new FormulaOneLibraryManager();
        populateTable(fManager.getDriverList().subList(0, 10));
        fManager.setStart(0);
        fManager.setEnd(9);

        prevButton = (ImageButton) findViewById(R.id.previousButton);
        nextButton = (ImageButton) findViewById(R.id.nextButton);

        prevButton.setEnabled(false);
        prevButton.setImageResource(R.drawable.ic_navigate_before_grey_48dp);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        ComponentName componentName = getComponentName();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);
        searchView.setSearchableInfo(searchableInfo);
    }

    private void populateTable(List<Driver> driverList) {
        tableView = (SortableTableView) findViewById(R.id.tableView);

        //Setting header adapter
        SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(this, headers);
        headerAdapter.setTextColor(ContextCompat.getColor(this, R.color.table_header_text));
        tableView.setHeaderAdapter(headerAdapter);

        //Setting data adapter
        setDataAdapter(driverList);

        //Setting row styles
        int rowColorEven = ContextCompat.getColor(this, R.color.table_data_row_even);
        int rowColorOdd = ContextCompat.getColor(this, R.color.table_data_row_odd);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
        tableView.setHorizontalScrollBarEnabled(true);

        //Setting column width
        TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(4);
        tableColumnWeightModel.setColumnWeight(0, 1);
        tableColumnWeightModel.setColumnWeight(1, 1);
        tableColumnWeightModel.setColumnWeight(2, 1);
        tableColumnWeightModel.setColumnWeight(3, 3);
        tableView.setColumnModel(tableColumnWeightModel);

        //Enabling swipe to refresh
        tableView.setSwipeToRefreshEnabled(true);
        tableView.setSwipeToRefreshListener(this);
    }

    public void previous(View view) {
        int start = fManager.getStart() - 10;
        int end = fManager.getEnd() - 10;
        int max = fManager.getDriverList().size() - 1;

        //Disabling previous button
        if (start == 0) {
            prevButton.setEnabled(false);
            prevButton.setImageResource(R.drawable.ic_navigate_before_grey_48dp);
        }

        //Enabling next button
        if (end <= max && !nextButton.isEnabled()) {
            nextButton.setEnabled(true);
            nextButton.setImageResource(R.drawable.ic_navigate_next_black_48dp);
        }

        List<Driver> driverList = fManager.getDriverList().subList(start, end+1);
        setDataAdapter(driverList);

        fManager.setStart(start);
        fManager.setEnd(end);
    }

    public void next(View view) {
        int start = fManager.getStart() + 10;
        int end = fManager.getEnd() + 10;
        int max = fManager.getDriverList().size() - 1;

        //Enabling previous button
        if (start != 0 && !prevButton.isEnabled()) {
            prevButton.setEnabled(true);
            prevButton.setImageResource(R.drawable.ic_navigate_before_black_48dp);
        }

        //Disabling next button
        if (end >= max) {
            end = max;
            nextButton.setEnabled(false);
            nextButton.setImageResource(R.drawable.ic_navigate_next_grey_48dp);
        }

        List<Driver> driverList = fManager.getDriverList().subList(start, end+1);
        setDataAdapter(driverList);

        fManager.setStart(start);
        fManager.setEnd(end);
    }

    private void setDataAdapter(List<Driver> driverList) {
        SimpleTableDataAdapter driverListAdapter = new SimpleTableDataAdapter(this, Utilities.getConvertedDriverList(driverList));
        driverListAdapter.setPaddingTop(35);
        driverListAdapter.setPaddingBottom(35);
        tableView.setDataAdapter(driverListAdapter);
        driverListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Search query", query);
        updateDriverTable(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Search query", newText);
        updateDriverTable(newText);
        return true;
    }

    private void updateDriverTable(String query) {
        List<Driver> driverList = fManager.getDriverList();
        List<Driver> resultList = new ArrayList<>();
        Iterator<Driver> driverIterator = driverList.iterator();
        Driver driver;
        query = query.toLowerCase();
        String driverName;

        while (driverIterator.hasNext()) {
            driver = driverIterator.next();
            driverName = driver.getGivenName() + " " + driver.getFamilyName();

            if (driverName.toLowerCase().contains(query)
                    || driver.getNationality().toLowerCase().contains(query)
                    || driver.getDateOfBirth().toLowerCase().contains(query)
                    || driver.getUrl().toLowerCase().contains(query)) {
                resultList.add(driver);
            }
        }

        setDataAdapter(resultList);
    }

    @Override
    public void onRefresh(final RefreshIndicator refreshIndicator) {
        GetDriverDetailsTask task = new GetDriverDetailsTask(this, refreshIndicator);
        task.execute();
    }

    public class GetDriverDetailsTask extends AsyncTask<Void, Void, List<Driver>> {
        private Context context;
        private FormulaOneLibraryManager fManager;
        private RefreshIndicator refreshIndicator;
        List<Driver> oldList;

        GetDriverDetailsTask(Context context, RefreshIndicator refreshIndicator) {
            this.context = context;
            this.refreshIndicator = refreshIndicator;
            fManager = new FormulaOneLibraryManager();
        }


        @Override
        protected List<Driver> doInBackground(Void... voids) {
            oldList = fManager.getDriverList();
            return fManager.getDriverDetails(50, 0);
        }

        protected void onPostExecute(List<Driver> newList) {
            List<Driver> addedDriversList = new ArrayList<>();
            List<Driver> removedDriversList = new ArrayList<>();
            List<Driver> driversList;
            FormulaOneDAO dao = new FormulaOneDAO(context);
            String toastText;

            //Creating list of file names of new and deleted tracks
            if (!newList.isEmpty()) {
                if (oldList != null && !oldList.isEmpty()) {
                    //Getting all newly added tracks
                    addedDriversList = new ArrayList<Driver>(newList);
                    addedDriversList.removeAll(oldList);

                    //Getting all deleted tracks
                    removedDriversList = new ArrayList<Driver>(oldList);
                    removedDriversList.removeAll(newList);
                } else {
                    addedDriversList = newList;
                }
            } else {
                removedDriversList = oldList;
            }

            //Updating driver list in db
            if(!addedDriversList.isEmpty() || !removedDriversList.isEmpty()) {
                if(!addedDriversList.isEmpty()) {
                    dao.addDrivers(addedDriversList);
                }

                if(!removedDriversList.isEmpty()) {
                    dao.removeDrivers(removedDriversList);
                }

                toastText = "Driver list updated";
            } else {
                toastText = "Driver list is up to date";
            }

            Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
            toast.show();

            //Fetching updated driver list from db
            driversList = dao.getDrivers();
            fManager.setDriverList(driversList);
            fManager.setStart(0);
            fManager.setEnd(9);
            setDataAdapter(driversList);

            refreshIndicator.hide();
            dao.closeConnection();
        }
    }
}