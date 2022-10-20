package ro.bithat.dms.microservices.dmsws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import java.util.function.Predicate;

@Service
public class DmswsRestService extends BasicRestService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${dmsws.url}")
	private String dmswsUrl;

	public String getDmswsUrl() {
		return dmswsUrl;
	}
	
	protected <R extends BaseModel> Predicate<R> checkBaseModel(){
		return new CheckBaseModelResult<R>();
	}

	protected <R extends BaseModel> Predicate<R> checkBaseModelWithExtendedInfo(){
		return new CheckBaseModelResultWithExtendedInfo<R>();
	}
	
	protected class CheckBaseModelResultWithExtendedInfo<R extends BaseModel> implements Predicate<R>{

		@Override
		public boolean test(R r) {
			if (r.isError()) {
				logger.error("operation failed with "+ r.getInfo()+"\t"+r.getExtendedInfo()+"\t"+r.getResult());
				throw new ServerWebInputException(r.getExtendedInfo());
			}
			return !r.isError();
		}
		
	}
	
	protected class CheckBaseModelResult<R extends BaseModel> implements Predicate<R>{

		@Override
		public boolean test(R r) {
			if (r.isError()) {
				logger.info("operation failed with "+r);
				throw new ServerWebInputException(r.getInfo());
			}
			return !r.isError();
		}
		
	}

	protected <R extends StandardResponse> Predicate<R> checkStandardResponse(){
		return new CheckStandardResponse<R>();
	}
	
	protected class CheckStandardResponse<R extends StandardResponse> implements Predicate<R>{

		@Override
		public boolean test(R r) {
			if (r.isError()) {
				logger.error("operation failed with "+ r.getStatus()+"\t"+r.getErrString()+"\t"+r.getExtendedStatus());
				throw new ServerWebInputException(r.getErrString()+"\t"+r.getExtendedStatus()+"\t"+r.getStatus());
			}
			return !r.isError();
		}
		
	}

	protected <R extends StandardResponse> Predicate<R> checkStandardResponseErrStringOnly(){
		return new CheckStandardResponseErrStringOnly<R>();
	}

	protected class CheckStandardResponseErrStringOnly<R extends StandardResponse> implements Predicate<R>{

		@Override
		public boolean test(R r) {
			if (r.isError()) {
				logger.error("operation failed with "+ r.getStatus()+"\t"+r.getErrString()+"\t"+r.getExtendedStatus());
				throw new ServerWebInputException(r.getErrString());
			}
			return !r.isError();
		}

	}

}
