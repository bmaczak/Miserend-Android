package com.frama.miserend.hu.churchdetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.utils.StringUtils;
import com.frama.miserend.hu.utils.Validation;

/**
 * Created by maczak on 2018. 03. 20..
 */

public class MassDetailsDialogFragment extends DialogFragment {

    private static final String EXTRA_MASS = "mass";

    private Mass mass;

    public static MassDetailsDialogFragment newInstance(Mass mass) {
        MassDetailsDialogFragment f = new MassDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MASS, mass);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mass = (Mass) getArguments().getSerializable(EXTRA_MASS);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View contentView = inflater.inflate(R.layout.dialog_mass_details, null);

        TextView additionalIfoText = contentView.findViewById(R.id.legend_info);

        additionalIfoText.setText(getDetails());
        builder.setView(contentView)
                .setTitle(R.string.mass_details_title)
                .setPositiveButton(R.string.ok,
                        (dialog, id) -> dismiss());
        return builder.create();
    }

    //TODO Refactor
    private String getDetails() {
        String text = "";
        if (Validation.notEmpty(mass.getComment())) {
            text += mass.getComment() + "\n";
        }
        if (Validation.notEmpty(mass.getLanguage()) && !mass.getLanguage().equals("h") && !mass.getLanguage().equals("hu")) {
            text += "Nyelv: " + mass.getLanguage() + "\n";
        }
        if (Validation.isEmpty(mass.getPeriod()) || mass.getPeriod().equals("0")) {
            text += "Minden héten\n";
        } else if (mass.getPeriod().equals("ps")) {
            text += "Csak páros heteken\n";
        } else if (mass.getPeriod().equals("pt")) {
            text += "Csak páratlan heteken\n";
        } else if (mass.getPeriod().equals("-1")) {
            text += "Csak utolsó heteken\n";
        } else if (StringUtils.isInteger(mass.getPeriod())) {
            text += "Csak a hónap " + Integer.valueOf(mass.getPeriod()) + ". hetén\n";
        }
        text += StringUtils.capitalizeFirstLetter(mass.getSeason()) + "\n";

        if (Validation.notEmpty(mass.getTags())) {
            String[] tags = mass.getTags().split(",");
            for (String tag : tags) {
                text += MassTag.getTagDescription(getContext(), tag) + "\n";
            }
        }

        return text;
    }
}
