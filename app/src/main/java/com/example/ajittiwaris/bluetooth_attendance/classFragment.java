package com.example.ajittiwaris.bluetooth_attendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Damini on 03-Aug-16.
 */
public class classFragment extends Fragment {
    /**
     * A placeholder fragment containing a simple view.
     */

        Button Class1,Class2,Class3;
        Button EnterDetails;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public  classFragment newInstance(int sectionNumber) {
            classFragment fragment = new classFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public classFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_teacher_account, container, false);
            final Context context = rootView.getContext();
            Class1=(Button)rootView.findViewById(R.id.btclass1);
            Class2=(Button)rootView.findViewById(R.id.btclass2);
            Class3=(Button)rootView.findViewById(R.id.btclass3);
            EnterDetails= (Button)rootView.findViewById(R.id.btclass6);

            Class1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,Register.class));

                }
            });
            EnterDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,ClassDetails.class));

                }
            });


            return rootView;
        }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
