package in.moneytransfer.ipay;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Progress_bar {
    public static AlertDialog dialog;
    public static void Showdialog(String message,Context context)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView=inflater.inflate(R.layout.content_progressbar,null);
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
        alertDialog.setView(dialogView);

        final TextView msgText= dialogView.findViewById(R.id.progressbar);
        msgText.setText(message);

        dialog= alertDialog.create();
        dialog.show();
    }
    public static void hideDialog() {
        dialog.hide();
    }
}
