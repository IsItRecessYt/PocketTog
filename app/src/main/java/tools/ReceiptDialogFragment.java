package tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by mrfre on 8/8/2018.
 */

public class ReceiptDialogFragment  extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Receipt");
        builder.setMessage("Thanks for using Pocket Tog. \n\n" +
                "Your submission will be edited and returned to your email within 14 business days, depending on demand. \n\n" +
                "Your Reference number is: #12345678910 \n\n" +
                "Your email: StevenPaulGalloway@gmail.com")
                .setPositiveButton("Thanks!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
