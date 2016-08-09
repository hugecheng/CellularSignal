package com.hugecheng.cellularsignal;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public Handler mHandler;
    public static RadioInfo radioInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        radioInfo = new RadioInfo(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        private String updateText() {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber) {
                case 1:
                    String lteCellInfo = String.format(Locale.US, " MCC: %d\n MNC: %d\n CI: %d\n PCI: %d\n TAC: %d\n",
                            radioInfo.getLte_MCC(), radioInfo.getLte_MNC(), radioInfo.getLteCI(),
                            radioInfo.getLtePCI(), radioInfo.getLteTAC());
                    String lteSignal = String.format(Locale.US, "\n RSRP: %d\n RSRQ: %d\n SINR: %d\n",
                            radioInfo.getLteRSRP(), radioInfo.getLteRSRQ(), radioInfo.getLteSINR());
                    return getString(R.string.lte_cellular_info) + lteCellInfo + lteSignal;
                case 2:
                    String wcdmaCellInfo = String.format(Locale.US, " MCC: %d\n MNC: %d\n LAC: %d\n CID: %d\n PSC: %d\n",
                            radioInfo.getWcdma_MCC(), radioInfo.getWcdma_MNC(), radioInfo.getWcdma_LAC(),
                            radioInfo.getWcdma_CID(), radioInfo.getWcdma_PSC());
                    String wcdmaSignal = String.format(Locale.US, "\n RSSI: %d\n", radioInfo.getWcdma_RSSI());
                    return getString(R.string.umts_cellular_info) + wcdmaCellInfo + wcdmaSignal;
                case 3:
                    String gsmCellInfo = String.format(Locale.US, " MCC: %d\n MNC: %d\n LAC: %d\n CID: %d\n",
                            radioInfo.getGsm_MCC(), radioInfo.getGsm_MNC(), radioInfo.getGsm_LAC(),
                            radioInfo.getGsm_CID());
                    String gsmSignal = String.format(Locale.US, "\n RSSI: %d\n", radioInfo.getGsm_RSSI());
                    return getString(R.string.gsm_cellular_info) + gsmCellInfo + gsmSignal;
                case 4:
                    String cdmaCellInfo = String.format(Locale.US, " NID: %d\n SID: %d\n BSID: %d\n",
                            radioInfo.getcdmaNID(), radioInfo.getcdmaSID(), radioInfo.getcdmaBSID());
                    String cdmaSignal = String.format(Locale.US, "\n 1x RSSI: %d\n 1x Ec/Io: %d\n",
                            radioInfo.getcdmaRxPwr(), radioInfo.getcdmaEcIo());
                    return getString(R.string.cdma_cellular_info) + cdmaCellInfo + cdmaSignal;
                default:
                    return null;
            }
        }

        public void updateView() {
            View rootView = getView();
            if (rootView == null) {
                return;
            }

            TextView textView = (TextView) getView().findViewById(R.id.section_label);
            textView.setText(updateText());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(updateText());

            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof PlaceholderFragment) {
                ((PlaceholderFragment) object).updateView();
            }
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "LTE";
                case 1:
                    return "UMTS";
                case 2:
                    return "GSM";
                case 3:
                    return "CDMA";
                case 4:
                    return "TD-SCDMA";
            }
            return null;
        }
    }
}
