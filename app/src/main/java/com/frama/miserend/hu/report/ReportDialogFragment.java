package com.frama.miserend.hu.report;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.api.MiserendApi;
import com.frama.miserend.hu.di.qualifiers.ApplicationContext;
import com.frama.miserend.hu.preferences.Preferences;
import com.frama.miserend.hu.router.Router;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 03. 19..
 */

public class ReportDialogFragment extends DialogFragment {

    @Inject
    Preferences preferences;
    @Inject
    MiserendApi miserendApi;
    @Inject
    @ApplicationContext
    Context context;

    @BindView(R.id.report_radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.report_email)
    TextView email;
    @BindView(R.id.report_text)
    TextView text;

    private SimpleDateFormat dbUpdatedFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
    private int churchId;

    public static ReportDialogFragment newInstance(int churchId) {
        Bundle args = new Bundle();
        args.putInt(Router.IntentExtra.CHURCH_ID, churchId);
        ReportDialogFragment instance = new ReportDialogFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        churchId = getArguments().getInt(Router.IntentExtra.CHURCH_ID);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View contentView = inflater.inflate(R.layout.dialog_report, null);
        ButterKnife.bind(this, contentView);
        builder.setView(contentView)
                .setTitle(R.string.menu_report)
                .setPositiveButton(R.string.ok,
                        (dialog, id) -> sendReport())
                .setNegativeButton(R.string.cancel,
                        (dialog, id) -> dismiss());
        return builder.create();
    }

    private void sendReport() {
        ReportProblemBody reportProblemBody = new ReportProblemBody();
        reportProblemBody.setChurchId(churchId);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_wrong_location:
                reportProblemBody.setProblem(0);
                break;
            case R.id.radio_wrong_masses:
                reportProblemBody.setProblem(1);
                break;
            case R.id.radio_other:
                reportProblemBody.setProblem(2);
                break;
        }
        reportProblemBody.setDbDate(dbUpdatedFormat.format(new Date(preferences.getDatabaseLastUpdated())));
        reportProblemBody.setEmail(email.getText().toString());
        reportProblemBody.setText(text.getText().toString());
        miserendApi.reportProblem(reportProblemBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReportSent);
    }

    private void onReportSent(ReportProblemResponse reportProblemResponse) {
        Toast.makeText(context, reportProblemResponse.getText(), Toast.LENGTH_SHORT).show();
    }

}
