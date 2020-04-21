package com.example.jghafo.trafficscotlandapp.MoreInfoFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jghafo.trafficscotlandapp.R;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.RSSFeed;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class DetailsFragment extends Fragment {

    private View view;
    private List<RSSFeed> RSSFeeds;
    private int position;

    private String titletoolbar;
    private String startdate;
    private String enddate;
    private String infoDesc;
    private String startdateFinal;
    private String enddateFinal;
    private String descInfoFinal;
    private String incidentDesc;
    private String incidentStart;
    private String incidentUrl;
    private String Incident = "incident";
    private String Roadworks = "road";
    private String Planned = "plan";
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.info_fragment, container, false);

        if (getActivity().getIntent() != null) {
            String returnStr = getActivity().getIntent().getStringExtra("listItem");
            Gson gson = new Gson();
            Type listOfdoctorType = new TypeToken<List<RSSFeed>>() {
            }.getType();

            RSSFeeds = gson.fromJson(returnStr, listOfdoctorType);
        }

        if (getActivity().getIntent().getIntExtra("pos", 0) >= 0) {
            position = getActivity().getIntent().getIntExtra("pos", 0);

        }
        if (getActivity().getIntent().getStringExtra("incidents") != null) {
            Incident = getActivity().getIntent().getStringExtra("incidents");
        }
        if (getActivity().getIntent().getStringExtra("RoadWorks") != null) {
            Roadworks = getActivity().getIntent().getStringExtra("RoadWorks");
        }
        if (getActivity().getIntent().getStringExtra("plannedWork") != null) {
            Planned = getActivity().getIntent().getStringExtra("plannedWork");
        }

        if (RSSFeeds.get(position).getTitle() != null) {

            String title = RSSFeeds.get(position).getTitle();
            String[] splited = title.split("-");
            titletoolbar = splited[0];

        }

        if (RSSFeeds.get(position).getDescription() != null) {
            String description = RSSFeeds.get(position).getDescription();
            String[] split_description = description.split("<br />");

            if (split_description.length < 2) {

                startdate = split_description[0];

                if (startdate.startsWith("Start")) {
                    startdateFinal = startdate;
                } else {
                    startdateFinal = "No Information0";
                }
                enddate = "no information1";
                infoDesc = "no information2";

            } else if (split_description.length < 3) {

                startdate = split_description[0];
                enddate = split_description[1];


                if (startdate.startsWith("Start")) {
                    startdateFinal = startdate;
                } else {
                    startdateFinal = "No Information3";
                }

                if (enddate.startsWith("End")) {
                    enddateFinal = enddate;
                } else {
                    enddateFinal = "No Information4";
                }

                infoDesc = "no information5";

            } else {

                startdate = split_description[0];
                enddate = split_description[1];
                infoDesc = split_description[2];

                if (startdate.startsWith("Start")) {
                    startdateFinal = startdate;
                } else {
                    startdateFinal = "No Information6";
                }

                if (enddate.startsWith("End")) {
                    enddateFinal = enddate;
                } else {
                    enddateFinal = "No Information7";
                }
                if (infoDesc.startsWith("Delay")) {
                    descInfoFinal = infoDesc;
                } else {
                    descInfoFinal = "No Information8";
                }

            }
        } else {
            startdate = "no information9";
            enddate = "no information10";
            infoDesc = "no information11";
        }
        if (RSSFeeds.get(position).getDescription() != null) {

            incidentDesc = RSSFeeds.get(position).getTitle();
            incidentStart = RSSFeeds.get(position).getTitle();
            incidentUrl = RSSFeeds.get(position).getLink();
        }

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Variables
        TextView locationTV = view.findViewById(R.id.markerText);
        TextView locationTitleTV = view.findViewById(R.id.markerText2);
        TextView start_dateTv = view.findViewById(R.id.dateInfo);
        TextView startTitleTV = view.findViewById(R.id.dateInfo2);
        TextView end_dateTV = view.findViewById(R.id.endInfo);
        TextView endTitleTV = view.findViewById(R.id.endInfo2);
        TextView delayTV = view.findViewById(R.id.desc1);
        TextView delayTitleTV = view.findViewById(R.id.desc2);

        if (Roadworks.equals("RoadWork")) {

            locationTV.setText("Location");
            start_dateTv.setText("Start Date");
            end_dateTV.setText("End Date");
            delayTV.setText("Delay Information");

            locationTitleTV.setText(RSSFeeds.get(position).getTitle());
            startTitleTV.setText(startdateFinal);
            endTitleTV.setText(enddateFinal);
            delayTitleTV.setText(infoDesc);
        }

        if (Incident.equals("Incident")) {
            locationTV.setText("Location");
            start_dateTv.setText("Reason");
            end_dateTV.setText("Status");
            delayTV.setText("Link");


            locationTitleTV.setText(RSSFeeds.get(position).getTitle());
            startTitleTV.setText(incidentDesc);
            endTitleTV.setText(incidentStart);
            delayTitleTV.setText(incidentUrl);

        }

        if (Planned.equals("PlanedRoadWork")) {
            locationTV.setText("Location");
            start_dateTv.setText("Start Date");
            end_dateTV.setText("End Date");
            delayTV.setText("Description");

            locationTitleTV.setText(RSSFeeds.get(position).getTitle());
            startTitleTV.setText(startdateFinal);
            endTitleTV.setText(enddateFinal);
            delayTitleTV.setText(infoDesc);

        }
    }

}
