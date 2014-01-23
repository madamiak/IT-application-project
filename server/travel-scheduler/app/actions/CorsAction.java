package actions;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Response;
import play.mvc.SimpleResult;

public class CorsAction extends Action.Simple {

public Promise<SimpleResult> call(Context context) throws Throwable {
    Response response = context.response();
	response.setHeader("Access-Control-Allow-Origin", "*");
	response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
	response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,Cache-Control,Pragma,Date"); 
    return delegate.call(context);
}

}