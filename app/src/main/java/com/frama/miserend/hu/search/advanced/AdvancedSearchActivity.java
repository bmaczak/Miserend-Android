package com.frama.miserend.hu.search.advanced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;
import com.frama.miserend.hu.firebase.Analytics;
import com.frama.miserend.hu.picker.DatePickerFragment;
import com.frama.miserend.hu.picker.TimePickerFragment;
import com.frama.miserend.hu.router.Router;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by maczak on 2018. 03. 14..
 */

public class AdvancedSearchActivity extends BaseActivity implements TimePickerFragment.TimeSelectedListener, DatePickerFragment.DateSelectedListener {

    private static int ID_TIME_FROM = 0;
    private static int ID_TIME_TO = 1;

    @BindView(R.id.search_church_name)
    EditText churchName;
    @BindView(R.id.search_city_name)
    AutoCompleteTextView cityName;
    @BindView(R.id.search_date_time_layout)
    View dateTimeLayout;
    @BindView(R.id.search_time_range_selection)
    View timeRangeSelection;
    @BindView(R.id.search_date)
    Button date;
    @BindView(R.id.search_time_from_btn)
    Button fromTime;
    @BindView(R.id.search_time_to_btn)
    Button toTime;

    @Inject
    AdvancedSearchViewModel advancedSearchViewModel;
    @Inject
    Router router;
    @Inject
    Analytics analytics;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        ButterKnife.bind(this);
        advancedSearchViewModel.getCities().observe(this, this::onCitiesLoaded);
        updateUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        analytics.setCurrentScreen(this, Analytics.ScreenNames.ADVANCED_SEARCH);
    }

    private void updateUi() {
        date.setText(advancedSearchViewModel.getDate().format(dateFormatter));
        fromTime.setText(advancedSearchViewModel.getFromTime().format(timeFormatter));
        toTime.setText(advancedSearchViewModel.getToTime().format(timeFormatter));
    }

    private void onCitiesLoaded(List<String> strings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings.toArray(new String[strings.size()]));

        cityName.setAdapter(adapter);
        cityName.setThreshold(1);
    }

    @OnClick(R.id.search_date)
    void onDateClicked() {
        DatePickerFragment.newInstance(0, advancedSearchViewModel.getDate()).show(getSupportFragmentManager(), "date_dialog");
    }

    @OnCheckedChanged(R.id.search_all_day_checkbox)
    void onAllDayCheckedChanged(boolean checked) {
        advancedSearchViewModel.setAllDay(checked);
        timeRangeSelection.setVisibility(checked ? View.GONE : View.VISIBLE);
    }

    @OnCheckedChanged(R.id.search_filter_for_masses)
    void onFilterForMassesCheckedChanged(boolean checked) {
        dateTimeLayout.setVisibility(checked ? View.VISIBLE : View.GONE);
        advancedSearchViewModel.setFilterForMasses(checked);
    }

    @OnClick(R.id.search_time_from_btn)
    void onTimeFromButtonClicked() {
        TimePickerFragment.newInstance(ID_TIME_FROM, advancedSearchViewModel.getFromTime()).show(getSupportFragmentManager(), "from_time_dialog");
    }

    @OnClick(R.id.search_time_to_btn)
    void onTimeToButtonClicked() {
        TimePickerFragment.newInstance(ID_TIME_TO, advancedSearchViewModel.getToTime()).show(getSupportFragmentManager(), "to_time_dialog");
    }

    @Override
    public void onDateSelected(int id, LocalDate selectedDate) {
        advancedSearchViewModel.setDate(selectedDate);
        updateUi();
    }

    @Override
    public void onTimeSelected(int id, LocalTime selectedTime) {
        if (id == ID_TIME_FROM) {
            advancedSearchViewModel.setFromTime(selectedTime);
        } else {
            advancedSearchViewModel.setToTime(selectedTime);
        }
        updateUi();
    }

    @OnClick(R.id.search_button)
    void onSearchButtonClicked() {
        String name = churchName.getText().toString();
        String city = cityName.getText().toString();
        router.showSearchResults(advancedSearchViewModel.createSearchParams(name, city));
    }

}
