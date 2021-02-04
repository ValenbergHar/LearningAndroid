package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by oleg on 21.7.17
 */

public class Dialog_sabytie_settings extends DialogFragment {

    private Ringtone ringTone;
    private Uri uriAlarm, uriNotification, uriRingtone;
    private final int RINGTONEPICKER = 90;
    private RadioButton_Roboto_Condensed notificationNotification, notificationAlarm, notificationRingtone, notificationPicker;
    private Uri uri;
    private SharedPreferences k;
    private SharedPreferences.Editor prefEditor;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(R.style.AppCompatDark);
        else getActivity().setTheme(R.style.AppTheme);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_sabytie_settings, null);
        RadioGroup notificationGrupRington = view.findViewById(R.id.notificationGrupRington);
        notificationNotification = view.findViewById(R.id.notificationNotification);
        notificationAlarm = view.findViewById(R.id.notificationAlarm);
        notificationRingtone = view.findViewById(R.id.notificationRingtone);
        notificationPicker = view.findViewById(R.id.notificationPicker);
        notificationNotification.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        notificationAlarm.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        notificationRingtone.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        notificationPicker.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        ImageView play = view.findViewById(R.id.play);
        ImageView stop = view.findViewById(R.id.stop);
        uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        uriNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        notificationAlarm.setText(getString(R.string.uriAlarm, RingtoneManager.getRingtone(getActivity(), uriAlarm).getTitle(getActivity())));
        notificationNotification.setText(getString(R.string.uriNotification, RingtoneManager.getRingtone(getActivity(), uriNotification).getTitle(getActivity())));
        notificationRingtone.setText(getString(R.string.uriRingtone, RingtoneManager.getRingtone(getActivity(), uriRingtone).getTitle(getActivity())));

        String soundURI = k.getString("soundURI", "");
        uri = Uri.parse(soundURI);
        ringTone = RingtoneManager.getRingtone(getActivity(), uri);
        if (Objects.requireNonNull(soundURI).equals("")) notificationPicker.setText("Іншая мелодыя");
        else
            notificationPicker.setText(getString(R.string.uriPicker, ringTone.getTitle(getActivity())));
        int sound = k.getInt("soundnotification", 0);

        notificationGrupRington.setOnCheckedChangeListener(GrupRington());

        if (sound == 0) notificationNotification.setChecked(true);
        else notificationNotification.setChecked(false);
        if (sound == 1) notificationAlarm.setChecked(true);
        else notificationAlarm.setChecked(false);
        if (sound == 2) notificationRingtone.setChecked(true);
        else notificationRingtone.setChecked(false);
        if (sound == 3) notificationPicker.setChecked(true);
        else notificationPicker.setChecked(false);

        SwitchCompat checkBoxG = view.findViewById(R.id.guk);
        if (dzenNoch)
            checkBoxG.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        checkBoxG.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        int guk = k.getInt("guk", 1);
        if (guk == 0) {
            checkBoxG.setChecked(false);
            notificationNotification.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
            notificationAlarm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
            notificationRingtone.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
            notificationPicker.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
            notificationGrupRington.setOnCheckedChangeListener(null);
            notificationNotification.setClickable(false);
            notificationAlarm.setClickable(false);
            notificationRingtone.setClickable(false);
            notificationPicker.setClickable(false);
        } else {
            play.setOnClickListener(play());
            stop.setOnClickListener(stop());
        }
        checkBoxG.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefEditor = k.edit();
            if (isChecked) {
                if (dzenNoch) {
                    notificationNotification.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                    notificationAlarm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                    notificationRingtone.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                    notificationPicker.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                } else {
                    notificationNotification.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                    notificationAlarm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                    notificationRingtone.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                    notificationPicker.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                }
                notificationGrupRington.setOnCheckedChangeListener(GrupRington());
                notificationNotification.setClickable(true);
                notificationAlarm.setClickable(true);
                notificationRingtone.setClickable(true);
                notificationPicker.setClickable(true);
                play.setOnClickListener(play());
                stop.setOnClickListener(stop());
                prefEditor.putInt("guk", 1);
            } else {
                notificationNotification.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                notificationAlarm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                notificationRingtone.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                notificationPicker.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                notificationGrupRington.setOnCheckedChangeListener(null);
                notificationNotification.setClickable(false);
                notificationAlarm.setClickable(false);
                notificationRingtone.setClickable(false);
                notificationPicker.setClickable(false);
                play.setOnClickListener(null);
                stop.setOnClickListener(null);
                prefEditor.putInt("guk", 0);
            }
            prefEditor.apply();
        });

        ad.setView(view);
        ad.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> dialog.cancel());
        AlertDialog alert = ad.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }

    private View.OnClickListener play() {
        return (v -> {
            if (ringTone != null) {
                ringTone.stop();
            }
            if (notificationAlarm.isChecked()) {
                ringTone = RingtoneManager.getRingtone(getActivity(), uriAlarm);
            } else if (notificationNotification.isChecked()) {
                ringTone = RingtoneManager.getRingtone(getActivity(), uriNotification);
            } else if (notificationRingtone.isChecked()) {
                ringTone = RingtoneManager.getRingtone(getActivity(), uriRingtone);
            } else if (notificationPicker.isChecked()) {
                uri = Uri.parse(k.getString("soundURI", ""));
                ringTone = RingtoneManager.getRingtone(getActivity(), uri);
            }
            ringTone.play();
        });
    }

    private View.OnClickListener stop() {
        return (v -> {
            if (ringTone != null) {
                ringTone.stop();
                ringTone = null;
            }
        });
    }

    private RadioGroup.OnCheckedChangeListener GrupRington() {
        return (group, checkedId) -> {
            int sound2 = k.getInt("soundnotification", 0);
            prefEditor = k.edit();
            switch (checkedId) {
                case R.id.notificationNotification:
                    prefEditor.putInt("soundnotification", 0);
                    break;
                case R.id.notificationAlarm:
                    prefEditor.putInt("soundnotification", 1);
                    break;
                case R.id.notificationRingtone:
                    prefEditor.putInt("soundnotification", 2);
                    break;
                case R.id.notificationPicker:
                    if (sound2 != 3) {
                        if (ringTone != null) {
                            ringTone.stop();
                            ringTone = null;
                        }
                        prefEditor.putInt("soundnotification", 3);
                        Intent intent1 = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                        intent1.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Выбар мелодыі для апавяшчэньняў:");
                        intent1.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                        intent1.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                        if (uri == null)
                            uri = Uri.parse(k.getString("soundURI", ""));
                        intent1.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
                        startActivityForResult(intent1, RINGTONEPICKER);
                    }
                    break;
            }
            prefEditor.apply();
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RINGTONEPICKER && resultCode == Activity.RESULT_OK) {
            uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            ringTone = RingtoneManager.getRingtone(getActivity(), uri);
            notificationPicker.setText(getString(R.string.uriPicker, ringTone.getTitle(getActivity())));
            prefEditor = k.edit();
            prefEditor.putString("soundURI", uri.toString());
            prefEditor.apply();
        }
    }
}
