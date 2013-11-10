package pl.travelscheduler.mobile.dialogs;

import android.app.Activity;

public class ProgressDialog
{
    private Activity activity;
    private android.app.ProgressDialog progress;
    private String message;

    public ProgressDialog(Activity parentActivity, String messageToDisplay)
    {
        activity = parentActivity;
        message = messageToDisplay;
    }

    public void run()
    {
        progress = new android.app.ProgressDialog(activity);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage(message);
        progress.setIndeterminate(true);
        progress.show();
    }

    public void stop()
    {
    	if(progress.isShowing())
    	{
    		progress.cancel();
    	}
    }
}
