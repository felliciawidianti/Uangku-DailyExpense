package io.github.fianekame.uangku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.github.fianekame.uangku.Controller.SaldoController;
import io.github.fianekame.uangku.DBHelper.DBHandler;
import io.github.fianekame.uangku.Model.Saldo;
import io.github.fianekame.uangku.Utils.SPManager;
import io.github.fianekame.uangku.Utils.Utils;

public class SettingUp extends Activity {

    private SaldoController saldo;
    EditText saldotxt;
    SPManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saldo = new SaldoController(this);
        prefManager = new SPManager(this);

        if(prefManager.isFirstTimeLaunch()){
            saldo.addSaldo(new Saldo(1, 10000));
        }
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        saldotxt = (EditText) this.findViewById(R.id.setMoney);
        setContentView(R.layout.activity_setting_up);

    }

    public void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent i = new Intent(SettingUp.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void restoreData(View view) {
        Toast.makeText(this, Utils.doRestore(), Toast.LENGTH_LONG).show();
        launchHomeScreen();
    }

    public void firsttimeSetting(View view) {
        saldotxt = (EditText) this.findViewById(R.id.setMoney);
        try {
            saldo.updateSaldo(Integer.parseInt(saldotxt.getText().toString()));
            Log.d("Saldo Baru", saldo.getSaldo());
            AlertDialog alertDialog = new AlertDialog.Builder(SettingUp.this).create();
            alertDialog.setTitle("Succesfully...");
            alertDialog.setMessage(getResources().getString(R.string.settingupmessagesucces));
            alertDialog.setIcon(R.drawable.ic_info_black_24dp);
            alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    launchHomeScreen();
                }
            });
            alertDialog.show();

        } catch (SQLException e) {
            Log.d("Error", "SqlExceptionError");
        }
    }

}
