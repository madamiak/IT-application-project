package pl.travelscheduler.mobile.listeners;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.dialogs.LoginDialog;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.helpers.SessionHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class LoginDialogOnClickListener implements OnClickListener
{
	private View positiveButton;
	private View negativeButton;
	private LoginDialog dialog;
	
	public LoginDialogOnClickListener(View positiveButton, View negativeButton, LoginDialog dialog)
	{
		this.positiveButton = positiveButton;
		this.negativeButton = negativeButton;
		this.dialog = dialog;
	}

	@Override
	public void onClick(View view)
	{
		if(view == positiveButton)
		{
			AutoCompleteTextView txtVUserName = (AutoCompleteTextView) dialog.getDialogView().findViewById(R.id.loginDialogUserNameTxtV);
			dialog.setUserName(txtVUserName.getText().toString());
			TextView txtVPassword = (TextView) dialog.getDialogView().findViewById(R.id.loginDialogPasswordTxtV);
			dialog.setPassword(txtVPassword.getText().toString());
			if(dialog.getUserName() == null || dialog.getPassword() == null)
			{
				dialog.showToast("You have to provide both username and password");
			}
			else
			{
				int result = SessionHelper.logIn(dialog.getActivity(), dialog.getUserName(), dialog.getPassword());
    			if(result == 1)
    			{
    				dialog.getActivity().invalidateOptionsMenu();
    				dialog.cancel();
    			}
    			else if(result == 0)
    			{
    				dialog.showToast("Invalid username or password...");
    				ServicesHelper.vibrate(dialog.getActivity(), 100);
    			}
    			else if(result == -1)
    			{
    				dialog.showToast("Server error...");
    				ServicesHelper.vibrate(dialog.getActivity(), 150);
    			}
    			else if(result == -2)
    			{
    				dialog.showToast("Internal error...");
    				ServicesHelper.vibrate(dialog.getActivity(), 200);
    			}
    			else
    			{
    				dialog.showToast("Login failed...");
    				ServicesHelper.vibrate(dialog.getActivity(), 250);
    			}
			}
		}
		else if(view == negativeButton)
		{
			dialog.setUserName(null);
			dialog.setPassword(null);
			dialog.cancel();
		}
	}

}
